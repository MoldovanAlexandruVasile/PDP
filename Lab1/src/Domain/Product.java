package Domain;

public class Product {

    private String productName;
    private final Double price;
    private Integer quantity;

    public Product(String productName, Double price, Integer quantity) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public synchronized Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public synchronized Integer decreaseQuantity(Integer quantity) {
        Integer remained = this.quantity - quantity;
        this.setQuantity(remained);
        return remained;
    }

    @Override
    public String toString() {
        return "Product = '" + productName + '\'' + ", Price = " + price + ", Quantity before selling = " + quantity;
    }
}
