package com.prueba.main.controller;

import com.prueba.main.model.ErrorResponse;
import com.prueba.main.model.User;
import com.prueba.main.model.UserDto;
import com.prueba.main.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(
        name= "users",
        description = "Endpoints to access users"
)
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @Operation(
            summary = "List users",
            description = "Gets every user in the array",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of users get correctly"
                    )
            }
    )
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value="/users", params= {"sortedBy", "!filter"})
    @Operation(
            summary = "List sort users",
            description = "Get every user but sorted by an attribute chose by the user, sortedBy can be null",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "LList of users get and sorted correctly"
                    )
            }
    )
    public ResponseEntity<List<User>> getUsersSorted(@RequestParam String sortedBy){
        List<User> users = userService.getAllUsersSorted(sortedBy);
        return ResponseEntity.ok(users);
    }

    @GetMapping(value="/users", params={"filter","!sortedBy"})
    @Operation(
            summary = "List filter users",
            description = "Get a list of users filter by an attribute chose by the user, filter cannot be null",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of users get and sorted correctly"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Filter format is incorrect",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Filter operator is incorrect",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
            }
    )
    public ResponseEntity<List<User>> getUsersFiltered(@RequestParam String filter){
        List<User> users = userService.getUsersFiltered(filter);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users")
    @Operation(
            summary = "Adds user",
            description = "Adds new user to the array",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User added correctly"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Tax Id is already in use",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Tax id format is incorrect",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Phone format is incorrect",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error due to pass encrypt",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<User> addUser( @RequestBody UserDto user) {
        Optional<User> newUser = userService.addUser(user);
        return ResponseEntity.status(201).body(newUser.get());
    }

    @Operation(
            summary = "Patch an user",
            description = "Patch an attribute of a user by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User patch correctly"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Tax Id is already in use",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Tax id format is incorrect",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Phone format is incorrect",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error due to pass encrypt",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UserDto user)  {
        Optional<User> updatedUser = userService.updateUser(id,user);
        if(updatedUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser.get());
    }

    @Operation(
            summary = "Deletes user",
            description = "Deletes a user by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "201",
                            description = "User deleted correctly"
                    )
            }
    )
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        boolean deleted = userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
