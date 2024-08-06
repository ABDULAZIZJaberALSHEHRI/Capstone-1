package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotNull(message = "Merchant ID should not be empty !")
    private int id;

    @NotEmpty(message = "Merchant name should not be empty !")
    @Size(min = 3, message = "Merchant name should be more than '3' characters")
    private String name;
}
