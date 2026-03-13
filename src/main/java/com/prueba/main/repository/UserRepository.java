package com.prueba.main.repository;

/* La clase repositorio en este caso no actua como interfaz de JPA para conectarse a una DB.
*  En este caso es donde se almacena la lista de usuarios y donde se aplican las funciones para
*  los diferentes tipos de querys que el get requiere  */

import com.prueba.main.model.Adress;
import com.prueba.main.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {
    private List<User> userList = new ArrayList<User>();
    public UserRepository (){
        Adress add1 = new Adress(
                1,
                "WorkAddress",
                "street No.1",
                "UK"
        );
        Adress add2 = new Adress(
                2,
                "HomeAddress",
                "street No.2",
                "AU"
        );

        User user1 = new User(
                UUID.randomUUID(),
                "user1@mail.com",
                "user1",
                "7c4a8d09ca3762af61e59520943dc26494f8941b",
                "+1 5555555555",
                "AARR990101XXX",
                "01-01-2026 00:00:00",
                List.of(add1,add2)
        );
        userList.add(user1);
    }
    public List<User> getAllUsers(){
        return userList;
    }
    public User addUser(User user){
        userList.add(user);
        return user;
    }
    public User updateUser(User user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId().equals(user.getId())) {
                userList.set(i, user);
                return user;
            }
        }
        return null;
    }
    public boolean deleteUser(UUID id) {
        return userList.removeIf(user -> user.getId().equals(id));
    }
    public boolean existsById(UUID id) {
        return userList.stream()
                .anyMatch(user -> user.getId().equals(id));
    }
    public Optional<User> getByTaxId(String taxId){
        return userList.stream()
                .filter(user -> user.getTaxId().equals(taxId))
                .findFirst();
    }
    public Boolean existsByTaxId(String taxId){
        return userList.stream()
                .anyMatch(user -> user.getTaxId().equals(taxId));
    }
    public Optional<User> getUserById(UUID id){
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
}

