package Domain;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private BillsArchive billsArchive;
    private List<Product> productsInShop;
    private Double totalIncomes = 0.0;

    public Shop() {
        this.productsInShop = new ArrayList<>();
        this.billsArchive = new BillsArchive();
    }

    public List<Bill> getBillsArchive() {
        return billsArchive.getBillsArchive();
    }

    public Double getTotalIncomesPrice() {
        return this.totalIncomes;
    }

    public void addToTotalIncomes(Double totalIncomes) {
        this.totalIncomes += totalIncomes;
    }

    public void addProductInShop(Product product) {
        this.productsInShop.add(product);
    }

    public void addBillIntoArchive(Bill bill) {
        this.billsArchive.addBillToArchive(bill);
    }

    public List<Product> getProductsInShop() {
        return productsInShop;
    }
}
