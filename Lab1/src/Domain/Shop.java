package Domain;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private Archive archive;
    private List<Product> productsInShop;
    private Double totalIncomes = 0.0;
    private Integer transactions;

    public Shop(Integer transactions) {
        this.productsInShop = new ArrayList<>();
        this.archive = new Archive();
        this.transactions = transactions;
    }

    public synchronized Archive getArchive() {
        return archive;
    }

    public synchronized Double getTotalIncomesPrice() {
        return this.totalIncomes;
    }

    public synchronized void addToTotalIncomes(Double totalIncomes) {
        this.totalIncomes += totalIncomes;
    }

    public synchronized void addProductInShop(Product product) {
        this.productsInShop.add(product);
    }

    public synchronized void addBillIntoArchive(Bill bill) {
        this.archive.addBillToArchive(bill);
    }

    public List<Product> getProductsInShop() {
        return productsInShop;
    }

    public Integer getTransactions() {
        return transactions;
    }

    public void setTransactions(Integer transactions) {
        this.transactions = transactions;
    }
}
