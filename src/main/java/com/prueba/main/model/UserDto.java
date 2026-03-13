package com.prueba.main.model;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    @Pattern(
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*$",
            message = "Invalid email format")
    private String email;

    private String name;

    private String password;
    @Pattern(
            regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\\\s./0-9]*$",
            message = "Invalid phone number format")
    private String phone;

    @Pattern(
            regexp = "^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))([A-Z\\d]{3})?$",
            message = "Invalid tax ID format")
    private String taxId;

    private List<Adress> adresses;
}
