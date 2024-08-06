package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotNull(message = "Merchant Stock ID should not be empty !")
    private int id;

    @NotNull(message = "ProductID should not be empty !")
    private int productID;

    @NotNull(message = "MerchantID should not be empty !")
    private int merchantID;

    @NotNull(message = "Stock should not be empty !")
    @Min(value = 10, message = "Stock have to be more than 10 at start")
    private int stock;
}
