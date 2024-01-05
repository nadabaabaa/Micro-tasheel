package tn.esprit.infini.Pidev.Services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.BillRepository;
import tn.esprit.infini.Pidev.entities.Account;
import tn.esprit.infini.Pidev.entities.Bill;
import tn.esprit.infini.Pidev.entities.BillType;


import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor

public class BillService implements IBill {
    BillRepository billRepository;
    EntityManager entityManager;
    @Override
    public Bill addbill(Bill bill) {

       return billRepository.save(bill);
    }

    @Override
    public List<Bill> retrieveAllBills() {
        return billRepository.findAll();
    }

    @Override
    public Bill updateBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Bill retrieveBill(Long idBill) {
        return billRepository.findById(idBill).get();
    }

    @Override
    public void deleteBill(Long idBill) {
         billRepository.deleteById(idBill);


    }

    @Override
    public List<Bill> searchBills(Map<String, Object> criteria, int numCriteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bill> cq = cb.createQuery(Bill.class);
        Root<Bill> root = cq.from(Bill.class);
        Predicate[] predicates = new Predicate[numCriteria];
        int i = 0;
        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (key) {
                case "id":
                    predicates[i] = cb.equal(root.get("id"), value);
                    break;
                case "idCustomer":
                    predicates[i] = cb.equal(root.get("idCustomer"), value);
                    break;
                case "totalAmount":
                    predicates[i] = cb.equal(root.get("totalAmount"),  value);
                    break;
                case "dueDate":
                    predicates[i] = cb.greaterThanOrEqualTo(root.get("dueDate"),(Date)  value);
                    break;
                case "startDate":
                    predicates[i] = cb.greaterThanOrEqualTo(root.get("startDate"),(Date)  value);
                    break;
                case "verified":
                    predicates[i] = cb.equal(root.get("verified"), Boolean.valueOf(value.toString()));
                    break;

                case "interest":
                    predicates[i] = cb.equal(root.get("interest"),  value);
                    break;
                case "picture":
                    predicates[i] = cb.equal(root.get("picture"), value);
                    break;
                case "declaredDate":
                    predicates[i] = cb.greaterThanOrEqualTo(root.get("declaredDate"),(Date)  value);
                    break;
                case "billType":
                    if (value instanceof BillType) {
                        predicates[i] = cb.equal(root.get("billType"), value);
                    } else if (value instanceof String) {
                        BillType billType = BillType.valueOf((String) value);
                        predicates[i] = cb.equal(root.get("billType"), billType);
                    }
                    break;
                // add more cases for other criteria as needed
            }
            i++;
        }
        cq.where(cb.and(predicates));
        TypedQuery<Bill> query = entityManager.createQuery(cq);
        return query.getResultList();
    }



}
