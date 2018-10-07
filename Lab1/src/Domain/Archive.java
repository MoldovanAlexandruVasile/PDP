package Domain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Archive {

    private List<Bill> billsArchive;
    private Double soldProductsPrice = 0.0;
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public Archive() {
        this.billsArchive = new ArrayList<>();
    }

    public synchronized void addBillToArchive(Bill bill) {
        this.billsArchive.add(bill);
    }

    public synchronized void addSoldProductPrice(Double price){
        this.soldProductsPrice += price;
    }

    public synchronized Double getSoldProductsPrice() {
        return soldProductsPrice;
    }

    public Double getTotalCostOfBills(){
        Double income = 0.0;
        for (Bill bill : billsArchive)
            income += Double.valueOf(df.format(bill.getTotalBillCost()));
        return income;
    }
}
