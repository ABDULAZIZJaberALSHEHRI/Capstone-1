package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {
    private final MerchantStockService merchantStockService;
    ArrayList<Merchant> merchants = new ArrayList<Merchant>();

    public MerchantService(@Lazy MerchantStockService merchantStockService) {
        this.merchantStockService = merchantStockService;
    }

    public boolean addMerchant(Merchant merchant) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId() == merchant.getId()) {
                return false;
            }
        }
        merchants.add(merchant);
        return true;
    }

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public boolean updateMerchant(int id, Merchant merchant){
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId() == id) {
                merchants.set(i,merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(int id) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId() == id) {
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }


}
