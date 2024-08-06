package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-users")
    public ResponseEntity getAllUsers() {
        ArrayList<User> user = userService.getUsers();
        return ResponseEntity.status(200).body(user);
    }

    @PostMapping("/add-user")
    public ResponseEntity addUser(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isAdded = userService.addUser(user);
        if (isAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID is used by another user, please try another id."));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity updateUser(@PathVariable int id, @Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated = userService.updateUser(id, user);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not exists"));
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not exists"));
    }

    @PutMapping("/buy-product/{userid}/{productid}/{merchantid}")
    public ResponseEntity buyProduct(@PathVariable int userid, @PathVariable int productid, @PathVariable int merchantid) {

        if (userService.buyProduct(userid, productid, merchantid).equals("buy completed successfully")) {
            return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
        }
        return ResponseEntity.status(400).body(userService.buyProduct(userid, productid, merchantid));
    }

    @GetMapping("/same-products/{productid}")
    public ResponseEntity sameProducts(@PathVariable int productid) {
        if (userService.getSimilarProducts(productid) == null){
            return ResponseEntity.status(400).body(new ApiResponse("Product not exists"));
        }
        return ResponseEntity.status(200).body(userService.getSimilarProducts(productid));
    }

    @PutMapping("/add-review/{userid}/{productid}/{review}")
    public ResponseEntity addReview(@PathVariable int userid, @PathVariable int productid,@PathVariable String review) {
        boolean isReviewd = userService.addReview(userid, productid, review);
        if (isReviewd) {
            return ResponseEntity.status(200).body(new ApiResponse("Review added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Error occurred, please ensure that user id AND product id is correct."));
    }

    @PutMapping("/add-product-to-cart/{userid}/{productid}/{merchantid}")
    public ResponseEntity addProductToCart(@PathVariable int userid, @PathVariable int productid, @PathVariable int merchantid) {
        String isAddedToCart = userService.addToCart(userid,productid,merchantid);
        if (isAddedToCart.equals("product add to cart successfully")) {
            return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
        }
        return ResponseEntity.status(400).body(userService.addToCart(userid,productid,merchantid));
    }

    @GetMapping("/show-cart/{userid}")
    public ResponseEntity showCart(@PathVariable int userid) {
        if (userService.getUserCart(userid) == null){
            return ResponseEntity.status(400).body(new ApiResponse("User not exists"));
        }
        return ResponseEntity.status(200).body(userService.getUserCart(userid));
    }

    @PutMapping("/complete-purchase/{userid}")
    public ResponseEntity completePurchase(@PathVariable int userid) {
        boolean isPurchaseCompleted = userService.completeCartPurchase(userid);
        if (isPurchaseCompleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Purchase completed successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Error occurred while completing the purchase, please ensure that user id " +
                "is correct AND your balance is more than total products price."));
    }

    @DeleteMapping("/del-from-cart/{userid}/{productid}")
    public ResponseEntity delFromCart(@PathVariable int userid, @PathVariable int productid) {
        boolean isDel = userService.deleteFromCart(userid, productid);
        if (isDel) {
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Error occurred while deleting from the cart, please ensure that user id " +
                "is correct AND product id is correct."));
    }
}
