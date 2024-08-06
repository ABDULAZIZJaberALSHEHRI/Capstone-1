package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.CategoryID;
import com.example.capstone1.Service.CategoryIdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/categoryid")
@RequiredArgsConstructor
public class CategoryIdController {
    private final CategoryIdService categoryService;

    @RequestMapping("/add-categoryid")
    public ResponseEntity addCategoryId(@Valid @RequestBody CategoryID categoryID, Errors errors) {
        if (errors.hasErrors()){
            String message =errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        categoryService.add(categoryID);
        return ResponseEntity.status(200).body(new ApiResponse("CategoryID added successfully"));
    }

    @RequestMapping("/get-categoryid")
    public ResponseEntity getCategoryId() {
        ArrayList<CategoryID> categoryIDS = categoryService.getCategoriess();
        return ResponseEntity.status(200).body(categoryIDS);
    }

    @RequestMapping("/remove/categoryid/{id}")
    public ResponseEntity removeCategoryId(@PathVariable int id) {
        boolean isRemoved = categoryService.remove(id);
        if (isRemoved){
            return ResponseEntity.status(200).body(new ApiResponse("CategoryID removed successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("CategoryID not found"));
    }

    @RequestMapping("/update-categoryid/{id}")
    public ResponseEntity updateCategoryid(@PathVariable int id, @Valid @RequestBody CategoryID categoryID, Errors errors) {
        if (errors.hasErrors()){
            String message =errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated = categoryService.update(id, categoryID);
        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("CategoryID removed successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("CategoryID not found"));
    }

}
