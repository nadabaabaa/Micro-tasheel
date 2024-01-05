package tn.esprit.infini.Pidev.Services;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.Investrepository;
import tn.esprit.infini.Pidev.Repository.TransactionRepository;
import tn.esprit.infini.Pidev.entities.Invest;
import tn.esprit.infini.Pidev.entities.Statut;
import tn.esprit.infini.Pidev.entities.Transaction;
import tn.esprit.infini.Pidev.entities.TypeTransaction;
import tn.esprit.infini.Pidev.mappers.InvestMapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Getter
@Setter
@AllArgsConstructor
public class Investservice implements Iinvestservice {
    @PersistenceContext
    EntityManager entityManager;
    private Investrepository investrepository;
    private TransactionRepository transactionRepository;
    private InvestMapper investMapper;

    @Override
    public List<Invest> retrieveAllInvests() {
        return (List<Invest>) investrepository.findAll();
    }


    @Override
    public InvestResponseDTO addInvest(InvestRequestDTO investRequestDTO) {
        if (investRequestDTO.getMounths() == null || investRequestDTO.getMounths() == 0) {
            Invest invest = Invest.builder()
                    .amount(investRequestDTO.getAmount())
                    .dateofapplication(LocalDate.now())
                    .mounths((int) ChronoUnit.MONTHS.between(investRequestDTO.getDateofobtaining().withDayOfMonth(1), investRequestDTO.getDateoffinish().withDayOfMonth(1)))
                    .dateofobtaining(investRequestDTO.getDateofobtaining())
                    .dateoffinish(investRequestDTO.getDateoffinish())
                    .interestrate(0.06)
                    .statut(Statut.EN_ATTENTE)
                    .build();
            Invest savedInvest = investrepository.save(invest);
            InvestResponseDTO investResponseDTO = investMapper.fromInvest(savedInvest);
            Double montant = invest.getAmount();
            long montantlong = montant.longValue();
            Transaction transaction = new Transaction();
            transaction.setInvest(invest);
            transaction.setTypeTransaction(TypeTransaction.Credit);
            transaction.setDate(new Date());
            transaction.setAmount( montantlong);
            transaction.setStatus("PENDING");
            transactionRepository.save(transaction);
            return investResponseDTO;

        } else {
            Invest invest = Invest.builder()
                    .amount(investRequestDTO.getAmount())
                    .dateofapplication(LocalDate.now())
                    .mounths(investRequestDTO.getMounths())
                    .dateofobtaining(investRequestDTO.getDateofobtaining())
                    .dateoffinish(investRequestDTO.getDateofobtaining().plusMonths(investRequestDTO.getMounths()))
                    .interestrate(0.06)
                    .statut(Statut.EN_ATTENTE)
                    .build();
            Invest savedInvest = investrepository.save(invest);
            InvestResponseDTO investResponseDTO = investMapper.fromInvest(savedInvest);
            Double montant = invest.getAmount();
            long montantlong = montant.longValue();
            Transaction transaction = new Transaction();
            transaction.setInvest(invest);
            transaction.setTypeTransaction(TypeTransaction.Credit);
            transaction.setDate(new Date());
            transaction.setAmount( montantlong);
            transaction.setStatus("PENDING");
            transactionRepository.save(transaction);
            return investResponseDTO;

        }

    }

    @Override
    public Invest updateInvest(Invest i) {
        return investrepository.save(i);
    }

    @Override
    public Invest retrieveInvest(Long id) {
        return investrepository.findById(id).get();
    }

    @Override
    public void deleteInvest(Long id) {
        investrepository.deleteById(id);

    }
    @Override
    public List<Invest> getInvestByiduser(Long userid) {
        return investrepository.getInvestByiduser(userid);
    }

    @Override
    public List<Invest> searchInvests(Long id, Double amount, Date dateofapplication, Date dateofobtaining, Date dateoffinish, Double interestRate, Integer mounths, Statut statut) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Invest> query = builder.createQuery(Invest.class);
        Root<Invest> root = query.from(Invest.class);

        List<Predicate> predicates = new ArrayList<>();
        if (id != null) {
            predicates.add(builder.equal(root.get("id"), id));
        }
        if (amount != null) {
            predicates.add(builder.equal(root.get("amount"), amount));
        }
        if (dateofapplication != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dateofapplication"), dateofapplication));
        }
        if (dateofobtaining != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dateofobtaining"), dateofobtaining));
        }
        if (dateoffinish != null) {
            predicates.add(builder.equal(root.get("dateoffinish"), dateoffinish));
        }
        if (interestRate != null) {
            predicates.add(builder.equal(root.get("interestrate"), interestRate));
        }
        if (mounths != null) {
            predicates.add(builder.equal(root.get("mounths"), mounths));
        }
        if (statut != null) {
            predicates.add(builder.equal(root.get("statut"), statut));
        }

        if (!predicates.isEmpty()) {
            query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        return entityManager.createQuery(query).getResultList();
    }

         @Override
        @Scheduled(cron = "0 0 1 * * * ")
            public void Amountgiven() {
            List<Invest> invests = investrepository.findAll();
            Double montantinteret;
            Double tax;
            Double x;
            for (int i = 0; i < invests.size(); i++) {
                LocalDate currentdate = LocalDate.now();
                List amountgiven = new ArrayList<>();
                long mounths = ChronoUnit.MONTHS.between(currentdate.withDayOfMonth(1), invests.get(i).getDateoffinish().withDayOfMonth(1));
                if (mounths == 3) {
                    montantinteret = (invests.get(i).getAmount() * invests.get(i).getInterestrate());
                    tax = montantinteret * 0.15;
                    x = (invests.get(i).getAmount() + montantinteret - tax);
                    amountgiven.add(x);
                    invests.get(i).setAmount(x);
                    investrepository.save(invests.get(i));

                }
            }

        }
    @Override
    public Double totalAmountOfInvests() {
        Double totalAmount = 0.0;
        List<Invest> invests = investrepository.findAll();
        for (Invest invest : invests) {
            totalAmount += invest.getAmount();
        }
        return totalAmount;
    }
    @Override
    public Map<Statut, Double> percentageOfinvestsByStatus() {
        List<Invest> invests = investrepository.findAll();
        Map<Statut, Integer> numberOfCreditsByStatus = new HashMap<>();
        for (Invest invest : invests) {
            Statut status = invest.getStatut();
            numberOfCreditsByStatus.put(status, numberOfCreditsByStatus.getOrDefault(status, 0) + 1);
        }
        Map<Statut, Double> percentageOfCreditsByStatus = new HashMap<>();
        int totalNumberOfCredits = invests.size();
        for (Map.Entry<Statut, Integer> entry : numberOfCreditsByStatus.entrySet()) {
            Statut status = entry.getKey();
            int numberOfCredits = entry.getValue();
            double percentage = (double) numberOfCredits / totalNumberOfCredits * 100;
            percentageOfCreditsByStatus.put(status, percentage);
        }
        return percentageOfCreditsByStatus;
    }
    @Override
    public List<Double> Mensualite(Invest invest) {
        Double montantinteret;
        Double tax;
        Double x;
            List amountgiven = new ArrayList<>();
                montantinteret = (invest.getAmount() * invest.getInterestrate());
                tax = montantinteret * 0.15;
                x = (invest.getAmount() + montantinteret - tax);
                amountgiven.add(x);
                invest.setAmount(x);
                investrepository.save(invest);
            return amountgiven;
        }
    @Override
    public List<Double> tax(Invest invest) {
        Double montantinteret;
        Double tax;
        Double x;
        LocalDate currentdate = LocalDate.now();
        List amountgiven = new ArrayList<>();
        List taxes =new ArrayList<>();
            montantinteret = (invest.getAmount() * invest.getInterestrate());
            tax = montantinteret * 0.15;
            x = (invest.getAmount() + montantinteret - tax);
            amountgiven.add(x);
            invest.setAmount(x);
            investrepository.save(invest);
            taxes.add(tax);


        return taxes;
    }


}


