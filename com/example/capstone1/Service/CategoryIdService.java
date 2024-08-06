package com.example.capstone1.Service;

import com.example.capstone1.Model.CategoryID;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryIdService {
    ArrayList<CategoryID> categoriess = new ArrayList<CategoryID>();

    public ArrayList<CategoryID> getCategoriess() {
        return categoriess;
    }

    public void add(CategoryID categoryID) {
        categoriess.add(categoryID);
    }

    public boolean remove(int id) {
        for (int i = 0; i < categoriess.size(); i++) {
            if (categoriess.get(i).getId() == id) {
                categoriess.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean update(int id, CategoryID categoryID) {
        for (int i = 0; i < categoriess.size(); i++) {
            if (categoriess.get(i).getId() == id) {
                categoriess.set(i, categoryID);
                return true;
            }
        }
        return false;
    }
}
