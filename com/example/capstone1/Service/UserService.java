package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class UserService {
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;
    private final ProductService productService;
    ArrayList<User> users = new ArrayList<User>();

    public boolean addUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId() == user.getId()) {
                return false;
            }
        }
        users.add(user);
        return true;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean deleteUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId() == id) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean updateUser(int id,User user) {
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId() == id) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public String buyProduct(int userID, int productID, int merchantID){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == userID) {
                if (users.get(i).getRole().equalsIgnoreCase("Customer")){
                    for (int j = 0; j < productService.getProducts().size(); j++) {
                         if (productService.getProducts().get(j).getId() == productID) {
                             for (int k = 0; k < merchantService.getMerchants().size(); k++) {
                                 if (merchantService.getMerchants().get(k).getId() == merchantID) {
                                     for (int l = 0; l < merchantStockService.getMerchantStocks().size(); l++) {
                                        if (merchantStockService.getMerchantStocks().get(l).getProductID() == productID) {
                                             if (users.get(i).getBalance() >= productService.getProducts().get(j).getPrice()) {
                                                 if (merchantStockService.merchantStocks.get(l).getStock()>0) {
                                                     merchantStockService.merchantStocks.get(l).setStock(merchantStockService.merchantStocks.get(l).getStock() - 1);
                                                     users.get(i).setBalance(users.get(i).getBalance() - productService.getProducts().get(j).getPrice());
                                                     return "buy completed successfully";
                                                 }
                                        }
                                    }
                                }
                            }
                        }
                    }
                 }

              }

            }
        }
        return "'BUYING PRODUCT NOT COMPLETED'\nplease be ensure:\n1- the user exists.\n2- the user is customer not admin.\n3- the product exists.\n4- the merchant exists.\n" +
                "5- the product exist in merchant stock\n" +
                "6- your balance is more than product price.\n7- the product quantity in stock more than 0";
    }

    public ArrayList<Product> getSimilarProducts(int productID) {
        ArrayList<Product> sameCategories = new ArrayList<>();
        for (int i = 0; i < productService.getProducts().size(); i++) {
            if (productService.getProducts().get(i).getId() == productID) {
                for (int j = 0; j < productService.getProducts().size(); j++) {
                    if (productService.getProducts().get(i).getCategoryID() ==productService.getProducts().get(j).getCategoryID()) {
                        sameCategories.add(productService.getProducts().get(j));
                    }
                }

            }
        }
        if (sameCategories.isEmpty()) {
            return null;
        }
        return sameCategories;
    }

    public boolean addReview(int userID, int productID, String review) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == userID) {
                for (int j = 0; j < productService.getProducts().size(); j++) {
                    if (productService.getProducts().get(j).getId() == productID){
                        productService.getProducts().get(j).getReviews().add(review);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String addToCart(int userID, int productID, int merchantID) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == userID) {
                if (users.get(i).getRole().equalsIgnoreCase("Customer")){
                    for (int j = 0; j < productService.getProducts().size(); j++) {
                        if (productService.getProducts().get(j).getId() == productID) {
                            for (int k = 0; k < merchantService.getMerchants().size(); k++) {
                                if (merchantService.getMerchants().get(k).getId() == merchantID) {
                                    for (int l = 0; l < merchantStockService.getMerchantStocks().size(); l++) {
                                        if (merchantStockService.getMerchantStocks().get(l).getProductID() == productID) {
                                            if (merchantStockService.merchantStocks.get(l).getStock()>0) {
                                              merchantStockService.merchantStocks.get(l).setStock(merchantStockService.merchantStocks.get(l).getStock() - 1);
                                                users.get(i).getCart().add(productService.getProducts().get(j));
                                                return "product add to cart successfully";
                                                }

                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
        return "'BUYING PRODUCT NOT COMPLETED'\nplease be ensure:\n1- the user exists.\n2- the user is customer not admin.\n3- the product exists.\n4- the merchant exists.\n" +
                "5- the product exist in merchant stock\n" +
                "6- your balance is more than product price.\n7- the product quantity in stock more than 0";

    }

    public ArrayList<Product> getUserCart(int userid) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == userid) {
                return users.get(i).getCart();
            }
        }
        return null;
    }

    public boolean completeCartPurchase(int userid){
        double total = 0;
        int productWithOfferCounter=0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == userid) {
                for (int j = 0; j < users.get(i).getCart().size(); j++) {
                    if (users.get(i).getCart().get(j).getOffer().equalsIgnoreCase("two for one price")) {
                        productWithOfferCounter++;

                        if (productWithOfferCounter >= 2) {
                            total = (users.get(i).getCart().get(j).getPrice() /2)+ total;
                        }else {
                            total = users.get(i).getCart().get(j).getPrice() + total;
                        }

                    }else {
                        total = users.get(i).getCart().get(j).getPrice() + total;
                    }

                }
                if (users.get(i).getBalance() > total) {
                    users.get(i).setBalance(users.get(i).getBalance() - total);
                }else if (users.get(i).getBalance() < total) {
                    return false;
                }
                users.get(i).getCart().clear();
                return true;
            }

        }

        return false;
    }

    public boolean deleteFromCart(int userid, int productID) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == userid) {
                for (int j = 0; j < users.get(i).getCart().size(); j++) {
                    if(users.get(i).getCart().get(j).getId() == productID){
                        users.get(i).getCart().remove(j);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
