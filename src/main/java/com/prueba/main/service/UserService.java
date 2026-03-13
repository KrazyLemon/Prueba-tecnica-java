package com.prueba.main.service;
import com.prueba.main.model.User;
import com.prueba.main.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.prueba.main.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Aes256GcmService aes256;

    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }
    public List<User> getAllUsersSorted(String sortedBy) {
        List<User> users = userRepository.getAllUsers();
        if(sortedBy == null || sortedBy.isEmpty()) {
            return users;
        }
        switch (sortedBy) {
            case "email":
                users.sort(Comparator.comparing(User::getEmail));
                break;

            case "name":
                users.sort(Comparator.comparing(User::getName));
                break;

            case "id":
                users.sort(Comparator.comparing(User::getId));
                break;

            case "phone":
                users.sort(Comparator.comparing(User::getPhone));
                break;

            case "tax_id": //Alreves
                users.sort(Comparator.comparing(User::getTaxId));
                break;

            case "created_at": // Alreves
                users.sort(Comparator.comparing(User::getCreatedAt));
                break;
        }
        return users;
    }
    public List<User> getUsersFiltered(String filter){
        List<User> users = userRepository.getAllUsers();
        String[] parts = filter.split(" ");
        System.out.println(filter);
        //System.out.println(parts);

        if(parts.length != 3){
            throw new IllegalArgumentException("Invalid filter format");
        }

        String field = parts[0];
        String operator = parts[1];
        String value = parts[2];

        return users.stream()
                .filter(user -> {
                    String fieldValue = getField(user,field);
                    return switch (operator) {
                        case "co" -> fieldValue.contains(value);
                        case "eq" -> fieldValue.equals(value);
                        case "sw" -> fieldValue.startsWith(value);
                        case "ew" -> fieldValue.endsWith(value);
                        default -> false;
                    };
                })
                .toList();
    }

    public Optional<User> addUser(UserDto user) throws Exception {
        if(userRepository.existsByTaxId(user.getTaxId())) {
            System.out.println("Error al buscar Tax ID");
            return Optional.empty();
        }
        ZoneId zoneId = ZoneId.of("UTC+3");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String createdAt = zonedDateTime.format(formateador);
        String encryptedPass = aes256.encrypt(user.getPassword());

        if(!isPhoneValid(user.getPhone()) || !isTaxIdValid(user.getTaxId())) return Optional.empty();

        User newUser = new User(
                UUID.randomUUID(),
                user.getEmail(),
                user.getName(),
                encryptedPass,
                user.getPhone(),
                user.getTaxId(),
                createdAt,
                user.getAdresses()
        );

        return Optional.of(userRepository.addUser(newUser));
    }

    public Optional<User> updateUser(UUID id, UserDto user) {
        Optional<User> existingUser = userRepository.getUserById(id);
        if(existingUser.isEmpty()) return Optional.empty();
        User updatedUser = existingUser.get();

        if(user.getEmail() != null && !user.getEmail().isBlank()){
            updatedUser.setEmail(user.getEmail());
        }
        if(user.getName() != null && !user.getName().isBlank()  ){
            updatedUser.setName(user.getName());
        }
        if(user.getPassword() != null && !user.getPassword().isBlank()){
            try {
                String encryptedPass = aes256.encrypt(user.getPassword());
                updatedUser.setPassword(encryptedPass);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(user.getPhone() != null && !user.getPhone().isBlank() ){
            if(isPhoneValid(user.getPhone())) updatedUser.setPhone(user.getPhone());
        }
        if(user.getTaxId() != null && !user.getTaxId().isBlank()){
            if(userRepository.existsByTaxId(user.getTaxId())) return Optional.empty();
            if(isTaxIdValid(user.getTaxId())) updatedUser.setTaxId(user.getTaxId());
        }
        if(user.getAdresses() != null && !user.getAdresses().isEmpty()){
            updatedUser.setAdresses(user.getAdresses());
        }

        userRepository.updateUser(updatedUser);
        return  Optional.of(updatedUser);
    }
    public boolean deleteUser(UUID id) {
        if(!userRepository.existsById(id)) return false;
        return userRepository.deleteUser(id);
    }
    public String getField(User user, String field){
        switch(field){
            case "email":
                return user.getEmail();

            case "name":
                return user.getName();

            case "phone":
                return user.getPhone();

            case "tax_id":
                return user.getTaxId();

            default:
                return "";
        }
    }
    public boolean isPhoneValid(String phone) {
        String regex = "^(\\+\\d{1,4}[\\s-]?)?\\d{10}$";
//        if(phone.matches(regex)){
//            System.out.println("Telefono Correcto");
//        }else{
//            System.out.println("Telefono Incorrecto");
//        }
        phone.replace(" ","");
        return phone.matches(regex);
    }
    public boolean isTaxIdValid(String taxId) {
        String regex = "^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))([A-Z\\d]{3})?$";
//        if(taxId.matches(regex)){
//            System.out.println("RFC Correcto");
//        }else{
//            System.out.println("RFC Incorrecto");
//        }
        return taxId.matches(regex);
    }
}
