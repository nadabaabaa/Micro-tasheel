package tn.esprit.infini.Pidev.Services;

import tn.esprit.infini.Pidev.entities.Account;
import tn.esprit.infini.Pidev.entities.Bill;

import java.util.List;
import java.util.Map;

public interface IBill {
    Bill addbill(Bill bill);
    List<Bill> retrieveAllBills();
    Bill updateBill(Bill bill);
    Bill retrieveBill(Long idBill);
    void deleteBill(Long idBill);
   List<Bill> searchBills(Map<String, Object> criteria, int numCriteria);


}
