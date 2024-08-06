package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get-products")
    public ResponseEntity getProducts() {
        ArrayList<Product> products = productService.getProducts();
        return ResponseEntity.status(200).body(products);
    }

    @PostMapping("/add-product")
    public ResponseEntity addProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean added = productService.addProduct(product);
        if (added) {
            return ResponseEntity.status(201).body( new ApiResponse("product added successfully"));
        }
        return ResponseEntity.status(400).body( new ApiResponse("product id already exists OR categoryId not found."));
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity updateProduct(@PathVariable int id,@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean updated = productService.updateProduct(id,product);
        if (updated) {
            return ResponseEntity.status(201).body( new ApiResponse("product updated successfully"));
        }
        return ResponseEntity.status(400).body( new ApiResponse("product not found"));

    }

    @DeleteMapping("/del-product/{id}")
    public ResponseEntity deleteProduct(@PathVariable int id) {
        boolean isDeleted = productService.removeProduct(id);
        if (isDeleted) {
            return ResponseEntity.status(201).body( new ApiResponse("product deleted successfully"));
        }
        return ResponseEntity.status(400).body( new ApiResponse("product not found"));
    }
}
