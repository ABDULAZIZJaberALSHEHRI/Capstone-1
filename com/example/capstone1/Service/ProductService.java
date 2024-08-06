package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@RequiredArgsConstructor
@Service
public class ProductService {
    ArrayList<Product> products = new ArrayList<Product>();

    private final CategoryIdService categoryIdService;
    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean addProduct(Product product) {

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                return false;
            }
        }
        for (int j = 0; j < categoryIdService.getCategoriess().size(); j++) {
            if (categoryIdService.getCategoriess().get(j).getId() == product.getCategoryID()) {
                products.add(product);
                return true;
            }
        }
        return false;
    }

    public boolean removeProduct(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(int id,Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }


}
