package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchantstock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    @GetMapping("/get-merchant-stock")
    public ResponseEntity getMerchantStock() {
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStocks();
        return ResponseEntity.status(200).body(merchantStocks);
    }

    @PostMapping("/add-merchant-stock")
    public ResponseEntity addMerchantStock(@Valid @RequestBody MerchantStock merchantStock, Errors err) {
        if (err.hasErrors()) {
            String message = err.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        String isAdded = merchantStockService.add(merchantStock);
        if (isAdded.equalsIgnoreCase("merchant stock added successfully")) {
            return ResponseEntity.status(201).body(new ApiResponse("Merchant stock added successfully"));
        }
        return ResponseEntity.status(400).body(isAdded);
    }

    @PutMapping("/update-merchant-stock/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable int id, @Valid @RequestBody MerchantStock merchantStock, Errors err) {
        if (err.hasErrors()) {
            String message = err.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = merchantStockService.update(id, merchantStock);
        if (isUpdated) {
            return ResponseEntity.status(201).body(new ApiResponse("Merchant stock updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock id is not found"));
    }

    @DeleteMapping("/delete-merchant-stock/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable int id) {
        boolean isDeleted=merchantStockService.remove(id);
        if (isDeleted) {
            return ResponseEntity.status(201).body(new ApiResponse("Merchant stock deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock id is not found"));
    }

    //in this ENDPOINT user "merchant" can update product stock value
    @PutMapping("/update-stock/{productID}/{merchantID}/{amount}")
    public ResponseEntity updateStock(@PathVariable int productID,@PathVariable int merchantID ,@PathVariable int amount) {
        boolean isStockUpdated = merchantStockService.updateStocks(productID,merchantID,amount);
        if (isStockUpdated) {
            return ResponseEntity.status(201).body(new ApiResponse("Stock updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Product id or Merchant id not found"));
    }
}
