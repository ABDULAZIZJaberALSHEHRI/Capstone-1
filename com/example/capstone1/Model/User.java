package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class User {

    @NotNull(message = "User id should not be empty !")
    private int id;

    @NotEmpty(message = "Username should not be empty !")
    @Size(min = 5,message = "have to be more than 6 length long")
    @Pattern(regexp = ".*[A-Z0-9 ._,-].*")
    private String username;

    @NotEmpty(message = "Email should not be empty !")
    @Email(message = "Email should contain '@' !")
    private String email;

    @NotEmpty(message = "role should not be empty !")
    @Pattern(regexp = "^(Admin|Customer)$")
    private String role;

    @NotNull(message = "Balance should not be empty !")
    @Positive(message = "balance should be positive number !")
    private double balance;

    ArrayList<Product> cart;
}
