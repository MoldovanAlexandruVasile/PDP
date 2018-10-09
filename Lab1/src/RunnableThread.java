import Domain.Bill;
import Domain.Product;
import Domain.Shop;

import java.text.DecimalFormat;
import java.util.List;

class RunnableThread implements Runnable {
    private Thread t;
    private String threadName;
    private Shop shop;
    private static final DecimalFormat df = new DecimalFormat("#.##");

    RunnableThread(Shop shop, String name) {
        this.shop = shop;
        this.threadName = name;
    }

    @Override
    public void run() {
        Bill bill = new Bill();
        List<Product> products = shop.getProductsInShop();
        try {
            Integer productsNumber = products.size();
            Integer productsBought = randomNumber(2, productsNumber) / 2;
            System.out.println("Transaction " + threadName + ": products bought: " + String.valueOf(productsBought));
            for (Integer i = 1; i <= productsBought; i++) {
                synchronized (shop) {
                    if (!checkProductsAvailability())
                        break;
                    Integer aux = randomNumber(0, productsNumber);
                    Product prod = products.get(aux);
                    if (!bill.getBillProducts().containsKey(prod)) {
                        if (prod.getQuantity() > 0) {
                            Integer quantityBought = randomNumber(1, prod.getQuantity());
                            if (prod.getQuantity() >= quantityBought) {
                                bill = sellProduct(bill, prod, quantityBought);
                                shop.getArchive().addSoldProductPrice(Double.valueOf(df.format(prod.getPrice() * quantityBought)));
                            }
                        } else {
                            i--;
                            if (prod.getQuantity() == 0)
                                System.out.println("Transaction " + threadName + ": OUT OF " +
                                        "STOCK -> " + prod.getProductName() + "\n");
                        }
                        Thread.sleep(250);
                    } else i--;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Transaction " + threadName + " interrupted.");
        }
        synchronized (shop) {
            shop.setTransactions(shop.getTransactions() - 1);
            shop.addBillIntoArchive(bill);
            if (shop.getTransactions() != 0)
                System.out.println("\n\t Checking income accuracy: " + checkIncomes().toString().toUpperCase() +
                        " \n\t   ~~~~~ Sold: " + df.format(shop.getTotalIncomesPrice()) + " ~~~~~\n");
            else
                System.out.println("\n\t Final income accuracy: " + checkFinalIncomes().toString().toUpperCase() +
                        " \n\t   ~~~~~ Sold: " + df.format(shop.getTotalIncomesPrice()) + " ~~~~~\n");
        }
    }

    private Bill sellProduct(Bill bill, Product prod, Integer quantityBought) {
        System.out.println("Transaction " + threadName + ": " + prod.toString() +
                " \n\t\t\t\t- Quantity bought: " + String.valueOf(quantityBought) +
                " \n\t\t\t\t- Quantity remained: " + String.valueOf(prod.getQuantity() - quantityBought) +
                " \n\t\t\t\t- Income: " + df.format(prod.getPrice() * quantityBought) + "\n");
        bill.addProductAndQuantityToBill(prod, quantityBought);
        shop.addToTotalIncomes(Double.valueOf(df.format(prod.getPrice() * quantityBought)));
        return bill;
    }

    public void start() {
        System.out.println("Starting transaction " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            synchronized (shop) {
                t.start();
            }
        }
    }

    private Boolean checkProductsAvailability() {
        Integer k = 0;
        for (Product prod : shop.getProductsInShop()) {
            if (prod.getQuantity() == 0)
                k++;
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

    private Boolean checkIncomes() {
        Double shopRegisteredIncomes = Double.valueOf(df.format(shop.getTotalIncomesPrice()));
        Double productsSoldPrice = Double.valueOf(df.format(shop.getArchive().getSoldProductsPrice()));
        if (shopRegisteredIncomes - productsSoldPrice == 0.0)
            return true;
        return false;
    }

    private Boolean checkFinalIncomes() {
        Double shopRegisteredIncomes = Double.valueOf(df.format(shop.getTotalIncomesPrice()));
        Double billsPrice = Double.valueOf(df.format(shop.getArchive().getTotalCostOfBills()));
        if (shopRegisteredIncomes - billsPrice == 0.0)
            return true;
        return false;
    }
}