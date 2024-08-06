package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {
    private final ProductService productService;
    private final MerchantService merchantService;
    ArrayList<MerchantStock> merchantStocks = new ArrayList<MerchantStock>();

    public MerchantStockService(@Lazy ProductService productService, MerchantService merchantService) {
        this.productService = productService;
        this.merchantService = merchantService;
    }

    public String add(MerchantStock merchantStock) {

        //check if there is already Merchant stock with the same id or not.
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId() == merchantStock.getId()) {
                return "there is already a merchant stock with the same id, please choose another one";
            }
        }

        /* check if there is Merchant and product added in the system to fill 'Merchant stock ArrayList'
        with the Merchant and product ids */
        for (int l = 0; l < productService.getProducts().size(); l++) {
            if (productService.getProducts().get(l).getId() == merchantStock.getProductID()) {
                for (int j = 0; j < merchantService.getMerchants().size(); j++) {
                    if (merchantService.getMerchants().get(j).getId() == merchantStock.getMerchantID()) {
                        merchantStocks.add(merchantStock);
                        return "merchant stock added successfully";
                    }
                }
            }

        }

        return "merchant stock 'not' added successfully\n There is no merchant OR product with same input ids.\n" +
                "please be sure that there already exist product and merchant in the system.";

    }

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public boolean remove(int id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId()==id) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean update(int id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId()==id) {
                merchantStocks.set(i,merchantStock);
                return true;
            }
        }
        return false;
    }

    //in this method user "merchant" can update product stock value
    public boolean updateStocks(int productId, int merchantID ,int amount) {
        for (int i = 0; i < merchantStocks.size(); i++) {

            //check the product id that entered by user is equals the product id in merchantStock arrayList
            if (merchantStocks.get(i).getProductID() == productId) {

                //check the merchant id that entered by user is equals the merchant id in merchantStock arrayList
                if (merchantStocks.get(i).getMerchantID() == merchantID) {
                    merchantStocks.get(i).setStock(amount);// set the new stock value
                    return true;
                }
            }
        }
        return false;
    }

}
