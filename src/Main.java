
import Domain.Product;
import Domain.Shop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
              System.out.print("Number of threads: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String threads = reader.readLine();
        Integer threadsNumber = Integer.valueOf(threads);

        Shop shop = new Shop();
        addProductsToShop(shop);

        for (Integer i = 1; i <= threadsNumber; i++) {
            RunnableThread runnableThread = new RunnableThread(shop, String.valueOf(i));
            runnableThread.start();
        }
        System.out.println();

    }

    private static void addProductsToShop(Shop shop) {
        Product product1 = new Product("Coca Cola", 6.50, 25);
        Product product2 = new Product("Banana", 5.00, 101);
        Product product3 = new Product("Capsune", 13.90, 130);
        Product product4 = new Product("Fanta", 6.50, 56);
        Product product5 = new Product("Sprite", 6.50, 60);
        Product product6 = new Product("Salam", 19.40, 100);
        Product product7 = new Product("Cascaval", 9.99, 80);
        Product product8 = new Product("Sunca", 16.70, 200);
        Product product9 = new Product("Pizza", 43.60, 25);
        Product product10 = new Product("Laptop", 3900.00, 5);
        Product product11 = new Product("Papuci", 99.99, 15);
        Product product12 = new Product("Miere", 10.0, 30);
        Product product13 = new Product("Mustar", 5.99, 22);
        Product product14 = new Product("Jucarie", 11.99, 5);
        Product product15 = new Product("Rafaelo", 13.99, 123);
        Product product16 = new Product("Minge", 50.99, 21);
        Product product17 = new Product("Monitor", 900.00, 100);
        Product product18 = new Product("Boxe", 500.00, 91);
        Product product19 = new Product("Mouse", 125.99, 80);
        Product product20 = new Product("Tastatura", 359.99, 70);
        shop.addProductInShop(product1);
        shop.addProductInShop(product2);
        shop.addProductInShop(product3);
        shop.addProductInShop(product4);
        shop.addProductInShop(product5);
        shop.addProductInShop(product6);
        shop.addProductInShop(product7);
        shop.addProductInShop(product8);
        shop.addProductInShop(product9);
        shop.addProductInShop(product10);
        shop.addProductInShop(product11);
        shop.addProductInShop(product12);
        shop.addProductInShop(product13);
        shop.addProductInShop(product14);
        shop.addProductInShop(product15);
        shop.addProductInShop(product16);
        shop.addProductInShop(product17);
        shop.addProductInShop(product18);
        shop.addProductInShop(product19);
        shop.addProductInShop(product20);
    }
}
