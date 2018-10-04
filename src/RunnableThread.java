import Domain.Bill;
import Domain.Product;
import Domain.Shop;

import java.util.List;

class RunnableThread implements Runnable {
    private Thread t;
    private String threadName;
    private Shop shop;

    RunnableThread(Shop shop, String name) {
        this.shop = shop;
        this.threadName = name;
        System.out.println("Creating " + threadName);
    }

    @Override
    public synchronized void run() {
        System.out.println("Running " + threadName);
        Bill bill = new Bill();
        List<Product> products = shop.getProductsInShop();
        try {

            Integer productsNumber = products.size();
            Integer productsBought = randomNumber(1, productsNumber) / 2;
            System.out.println("Transaction " + threadName + ": products bought: " + String.valueOf(productsBought));
            for (Integer i = 1; i <= productsBought; i++) {
                if (!checkProductsAvailability())
                    break;
                Integer aux = randomNumber(0, productsNumber);
                Product prod = products.get(aux);
                if (!bill.getBillProducts().containsKey(prod)) {
                    if (prod.getQuantity() > 0) {
                        Integer quantityBought = randomNumber(1, prod.getQuantity());
                        if (prod.getQuantity() >= quantityBought) {
                            System.out.println("Transaction " + threadName + ": " + prod.toString() +
                                    " \n\t\t\t\t- Quantity bought: " + String.valueOf(quantityBought) +
                                    " \n\t\t\t\t- Quantity remained: " + String.valueOf(prod.getQuantity() - quantityBought) +
                                    " \n\t\t\t\t- Income: " + String.valueOf(prod.getPrice() * quantityBought) + "\n");
                            bill.addProductAndQuantityToBill(prod, quantityBought);
                            shop.addToTotalIncomes(prod.getPrice() * quantityBought);
                        }
                    } else {
                        i--;
                        if (prod.getQuantity() == 0) {
                            System.out.println("Transaction" + threadName + ": OUT OF " +
                                    "STOCK -> " + prod.getProductName());
                        } else
                            System.out.println("Transaction" + threadName + ": ERROR: "
                                    + prod.getProductName() + " -> NOT ENOUGH QUANTITY !");

                    }
                    Thread.sleep(50);
                } else i--;
            }
        } catch (InterruptedException e) {
            System.out.println("Transaction " + threadName + " interrupted.");
        }
        shop.addBillIntoArchive(bill);
        System.out.println("Transaction " + threadName + " finished.");
    }

    public void start() {
        System.out.println("Starting transaction " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    private Boolean checkProductsAvailability() {
        Integer k = 0;
        for (Product prod : shop.getProductsInShop()) {
            if (prod.getQuantity() == 0) k++;
            if (k == shop.getProductsInShop().size()) {
                System.out.println("INFO: All products are sold out !");
                return false;
            }
        }
        return true;
    }

    private Integer randomNumber(Integer aux, Integer quantity) {
        Integer lowBound = aux;
        Integer highBound = quantity;
        Integer range = highBound - lowBound + lowBound;
        Integer rand = (int) (Math.random() * range) + lowBound;
        return rand;
    }
}