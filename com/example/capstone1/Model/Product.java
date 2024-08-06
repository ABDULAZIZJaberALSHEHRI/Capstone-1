package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Product {

    @NotNull(message = "Product ID should not be empty !")
    private int id;

    @NotEmpty(message = "Product name should not be empty !")
    @Size(min = 3, message = "Product name should be more than 3 characters")
    private String name;

    @NotNull(message = "Product price should not be empty !")
    @Positive(message = "Product price should be positive number !")
    private double price;

    @NotNull(message = "Product categoryID should not be empty !")
    private int categoryID;

    ArrayList<String> reviews;

    private String offer;
}
