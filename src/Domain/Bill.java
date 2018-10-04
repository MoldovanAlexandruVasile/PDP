package Domain;

import java.util.HashMap;
import java.util.Map;

public class Bill {

    private Map<Product, Integer> bill;
    private Double totalBillCost = 0.0;

    public Bill() {
        this.bill = new HashMap<Product, Integer>() {};
    }

    public synchronized void addProductAndQuantityToBill(Product product, Integer quantity) {
        Double priceOverQuantity = product.getPrice() * quantity;
        this.bill.put(product, quantity);
        this.totalBillCost += priceOverQuantity;
        product.decreaseQuantity(quantity);
    }

    public Map<Product, Integer> getBillProducts() {
        return this.bill;
    }

    public Double getTotalBillCost() {
        return this.totalBillCost;
    }
}
