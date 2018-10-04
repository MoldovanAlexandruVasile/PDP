package Domain;

import java.util.ArrayList;
import java.util.List;

public class BillsArchive {

    private List<Bill> billsArchive;

    public BillsArchive() {
        this.billsArchive = new ArrayList<>();
    }

    public synchronized void addBillToArchive(Bill bill) {
        this.billsArchive.add(bill);
    }

    public synchronized Double computeBillsIncome() {
        Double billsIncome = 0.0;
        for (Bill bill : this.billsArchive) {
            billsIncome += bill.getTotalBillCost();
        }
        return billsIncome;
    }

    public List<Bill> getBillsArchive() {
        return billsArchive;
    }
}
