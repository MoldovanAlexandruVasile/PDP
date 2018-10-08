package Domain;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Bill {

    private Map<Product, Integer> bill;
    private Double totalBillCost = 0.0;
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public Bill() {
        this.bill = new HashMap<Product, Integer>() {};
    }

    public synchronized void addProductAndQuantityToBill(Product product, Integer quantity) {
        Double priceOverQuantity = Double.valueOf(df.format(product.getPrice() * quantity));
        this.bill.put(product, quantity);
        this.totalBillCost += priceOverQuantity;
        product.decreaseQuantity(quantity);
    }

    public Map<Product, Integer> getBillProducts() {
        return this.bill;
    }

    public synchronized Double getTotalBillCost() {
        return this.totalBillCost;
    }
}
