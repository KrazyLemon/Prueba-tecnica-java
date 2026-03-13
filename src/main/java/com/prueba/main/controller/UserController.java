package com.prueba.main.controller;

import com.prueba.main.model.User;
import com.prueba.main.model.UserDto;
import com.prueba.main.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value="/users", params= {"sortedBy", "!filter"})
    public ResponseEntity<List<User>> getUsersSorted(@RequestParam String sortedBy){
        List<User> users = userService.getAllUsersSorted(sortedBy);
        return ResponseEntity.ok(users);
    }

    @GetMapping(value="/users", params={"filter","!sortedBy"})
    public ResponseEntity<List<User>> getUsersFiltered(@RequestParam String filter){
        if(filter == null || filter.isBlank()){
            return ResponseEntity.badRequest().build();
        }
        List<User> users = userService.getUsersFiltered(filter);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDto user) throws Exception {
        Optional<User> newUser = userService.addUser(user);
        if(newUser.isEmpty()){
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.status(201).body(newUser.get());
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UserDto user)  {
        Optional<User> updatedUser = userService.updateUser(id,user);
        if(updatedUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser.get());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        boolean deleted = userService.deleteUser(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }


}
