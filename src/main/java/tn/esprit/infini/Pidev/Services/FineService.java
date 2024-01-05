package tn.esprit.infini.Pidev.Services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.FineRepository;
import tn.esprit.infini.Pidev.entities.Fine;
import tn.esprit.infini.Pidev.entities.FineType;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class FineService implements  IFine {
    FineRepository fineRepository;
    private EntityManager entityManager;
    @Override
    public Fine addFine(Fine fine) {

        return  fineRepository.save(fine);
    }

    @Override
    public List<Fine> retrieveAllFines() {

        return fineRepository.findAll();
    }

    @Override
    public Fine updateFine(Fine fine) {

        return fineRepository.save(fine);
    }

    @Override
    public Fine retrieveFine(Long idFine) {
        return fineRepository.findById(idFine).get();
    }

    @Override
    public void deleteFine(Long idFine) {
        fineRepository.deleteById(idFine);

    }

    @Override
    public List<Fine> searchFines(Map<String, Object> criteria, int numCriteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Fine> cq = cb.createQuery(Fine.class);
        Root<Fine> root = cq.from(Fine.class);
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
                case "fineType":
                    if (value instanceof FineType) {
                        predicates[i] = cb.equal(root.get("fineType"), value);
                    } else if (value instanceof String) {
                        FineType fineType = FineType.valueOf((String) value);
                        predicates[i] = cb.equal(root.get("fineType"), fineType);
                    }
                    break;
                // add more cases for other criteria as needed
            }
            i++;
        }
        cq.where(cb.and(predicates));
        TypedQuery<Fine> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<String> calculatePaymentsByDay( Date startDate, Date dueDate, Double totalAmount){
        List<String> paymentsByDay = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.format(startDate);
        dateFormat.format(dueDate);

        long daysBetween = TimeUnit.DAYS.convert(dueDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS);


        double interestRate =0.025;


        Double amount = totalAmount;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (!calendar.getTime().after(dueDate)) {
            Double dailyPayment = amount * interestRate + amount;
            if (dailyPayment > 1.4 * totalAmount) {
                dailyPayment = 1.4 * totalAmount;
            }
            String dayAndPayment = String.format("%s,%.2f", calendar.getTime(), dailyPayment);
            paymentsByDay.add(dayAndPayment);

            amount = dailyPayment;
            calendar.add(Calendar.DATE, 1);
        }

        return paymentsByDay;
    }




}
