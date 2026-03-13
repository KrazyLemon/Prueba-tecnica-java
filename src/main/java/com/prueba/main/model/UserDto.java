package com.prueba.main.model;

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

    private String email;

    private String name;

    private String password;

    private String phone;


    private String taxId;

    private List<Adress> adresses;
}
