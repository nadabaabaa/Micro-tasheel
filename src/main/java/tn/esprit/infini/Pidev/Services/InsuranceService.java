package tn.esprit.infini.Pidev.Services;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.*;
import tn.esprit.infini.Pidev.entities.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor

public class InsuranceService implements IInsuranceService {
    InsuranceRepository insuranceRepository;
    PackRepository packRepository;
    UserRepository userRepository;
    AccountRepository accountRepository;
    SettingsRepository settingsRepository;

    @Override
    public List<Insurance> retrieveAllinsurances() {
        return (List<Insurance>) insuranceRepository.retrieveinsurance();
    }

    @Override
    public List<Insurance> retrieveArchivedinsurance() {
        return (List<Insurance>) insuranceRepository.retrieveArchivedinsurance();
    }

    @Override
    public Insurance addInsurance(Insurance i) {
        i.setArchived(false);
        return insuranceRepository.save(i);
    }
  /*  @Override
public Insurance addInsurance(Insurance i) {
    double totalCost = calculateInsuranceCostWithDiscount(i);
    i.setDeductible(totalCost);
    return insuranceRepository.save(i);
} */


    @Override
    public Insurance updateInsurance(Insurance i) {
        return (Insurance) insuranceRepository.save(i);
    }

    @Override
    public Insurance retrieveInsurance(Integer idinsurance) {
        return insuranceRepository.findById(idinsurance).get();
    }

    @Override
    public Insurance assignInsuranceToPack(int idinsurance, int idPack) {
        Insurance insurance = insuranceRepository.findByIdinsurance(idinsurance);
        Pack pack = packRepository.findByIdPack(idPack);
        insurance.setPack(pack);
        pack.getInsurances().add(insurance);
        return insuranceRepository.save(insurance);
    }

    //fixedRate = 5000
    @Scheduled(cron = "0 0 0 * * *")
    public void archiveExpiredInsurances() {
        // Récupérer les assurances dont la date de fin est dépassée
        List<Insurance> expiredInsurances = insuranceRepository.findByEndinsuranceBefore(new Date());
        for (Insurance insurance : expiredInsurances) {
            System.out.println(insurance.getIdinsurance());
            // Mettre à jour la date de fin d'assurance avec la date actuelle
            // insurance.setEndinsurance(new Date());
            insurance.setArchived(true);
            // Enregistrer la modification dans la base de données
            insuranceRepository.save(insurance);
        }
    }


    @Override
    public float calculateFicoScore(Insurance i) {
        float ficoscore = 0;
        ficoscore += (i.getInsuredAmount() * 2 / 100) +
                (getMonthsBetweenDates(i.getStartinsurance(), i.getEndinsurance()) * 15) +
                (insuranceRepository.findAccountsByNumberOfInsurances().size() * 10) +
                (i.getClaimsHistory().split(",").length * 25);
        return ficoscore;
    }

    @Override
    public double calculateInsuranceCost(Insurance insurance) {

        double insuredAmount = insurance.getInsuredAmount();
        double riskFactor = insurance.getLevelofrisk() / 100.0;
        LocalDate startDate = insurance.getStartinsurance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = insurance.getEndinsurance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long policyDuration = ChronoUnit.MONTHS.between(startDate, endDate) + 1;
        double deductiblePercent = insurance.getDeductible() / 100.0;
        double fixedFee = 350.0;

        double totalCost = (insuredAmount * (1 + riskFactor) * policyDuration * deductiblePercent) / 4200 + fixedFee;
        return totalCost;
    }


    public static int getMonthsBetweenDates(Date startDate, Date endDate) {
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long years = ChronoUnit.YEARS.between(startLocalDate, endLocalDate);
        long months = ChronoUnit.MONTHS.between(startLocalDate.plusYears(years), endLocalDate);

        return (int) (years * 12 + months);
    }

    @Override
    public double calculateInsuranceCostWithDiscount(Insurance insurance) {
        double totalCost = calculateInsuranceCost(insurance);
        List<Account> accounts = insuranceRepository.findAccountsByNumberOfInsurances();

        if (accounts.contains(insurance.getPack().getTransaction().getAccounts()) && accounts.size() > 5) {  //&& accounts.size() > 5

            totalCost *= 0.9;
        }
        return totalCost;
    }  /*Credit credit = insurance.getCredit();
        if (credit != null) {
            Transaction transaction = credit.getTransaction();
            if (transaction != null) {
                Account tAccounts = transaction.getAccount();

                if (tAccounts != null && accounts.contains(tAccounts) && accounts.size() > 5) {
                    totalCost *= 0.9;
                }
            }
        }
        return totalCost;
    }*/

    @Override
    public List<Object[]> getInsuranceByType() {

        return insuranceRepository.getInsuranceByType();
    }


   /* @Override
    public double findMaxNumber(List<Object[]> insuranceList) {
        // Stocker les nombres dans une Map
        Map<String, Double> numberMap = new HashMap<>();
        for (Object[] obj : insuranceList) {
            String insuranceType = (String) obj[0];
         //   double number = (Double) obj[1];
            String numberAsString = (String) obj[1];
            double number = Double.parseDouble(numberAsString);

            numberMap.put(insuranceType, number);
        }

        // Trouver le plus grand nombre dans la Map
        double maxNumber = Double.MIN_VALUE;
        for (double number : numberMap.values()) {
            if (number > maxNumber) {
                maxNumber = number;
            }
        }

        return maxNumber;
    }*/

    @Override
    public boolean processInsuranceRequest(Insurance insurance) {
        // Vérifier si l'assurance a un historique de sinistres
        if (insurance.getClaimsHistory() != null && !insurance.getClaimsHistory().isEmpty()) {
            // Si l'historique de sinistres existe, vérifier le nombre de sinistres
            int numberOfClaims = insurance.getClaimsHistory().split(",").length;
            if (numberOfClaims >= 5) {
                insurance.setStatut(Statut.Non_Approuvé);
                insuranceRepository.save(insurance);

                // Si le nombre de sinistres est supérieur ou égal à 3, refuser la demande d'assurance
                return false;
            }
        }
        insurance.setStatut(Statut.Approuvé);
        insuranceRepository.save(insurance);

        // Si l'assurance n'a pas d'historique de sinistres ou si le nombre de sinistres est inférieur à 3, approuver la demande d'assurance
        return true;
    }

    /*
      public Insurance assignInsuranceToPack(Integer idinsurance, Integer idPack) {
          Pack pack = packRepository.findByIdPack(idPack);
          Insurance insurance = insuranceRepository.findById(idinsurance);
          insurance.s
          return insurance;
      } */
    @Override
    public Insurance addInsuranceToPack(int insuranceId, int packId) {
        Insurance insurance = insuranceRepository.findById(insuranceId).orElse(null);
        Pack pack = packRepository.findById(packId).orElse(null);
        if (insurance != null && pack != null) {
            pack.getInsurances().add(insurance);
            insurance.setPack(pack);
            packRepository.save(pack);
            insuranceRepository.save(insurance);
            return insurance;
        }
        return null;
    }

    /*
      public Typeinsurance findMostUsedTypeInsurance() {
          List<Object[]> result = insuranceRepository.findMostUsedTypeInsurance();
          if (result.size() > 0) {
              return (Typeinsurance) result.get(0)[0];
          } else {
              return null;
          }
      }*/
  /*  @Override
    public List<Object[]> findMostUsedTypeInsurance() {
        List<Object[]> result = insuranceRepository.findMostUsedTypeInsurance();
    /*8  List<String> typeInsuranceList = new ArrayList<>();
      for (Object[] obj : result) {
          if (obj[0] != null ) {
          String typeInsurance = obj[0].toString();
          typeInsuranceList.add(typeInsurance);
      }
      }
      return typeInsuranceList;
        return result;
    }*/

    @Override
    public double LevelOfRiskCalculator(Insurance insurance) throws IOException {

        double levelofrisk = 0;
        float score = calculateFicoScore(insurance);
        List<Settings> settings = settingsRepository.findAll();

        for (Settings rate : settings) {
            if (score >= rate.getMinScore() && score <= rate.getMaxScore()) {
                levelofrisk = rate.getLevelofrisk();
                insurance.setLevelofrisk(levelofrisk);
                insuranceRepository.save(insurance);
                return levelofrisk;
            }
        }

        levelofrisk = settings.get(settings.size() - 1).getLevelofrisk();
        insurance.setLevelofrisk(levelofrisk);
        insuranceRepository.save(insurance);
        return levelofrisk;
    }




    }
