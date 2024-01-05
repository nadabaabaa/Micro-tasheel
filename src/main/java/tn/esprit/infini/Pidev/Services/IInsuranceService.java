package tn.esprit.infini.Pidev.Services;

import tn.esprit.infini.Pidev.entities.Insurance;

import java.io.IOException;
import java.util.List;

public interface IInsuranceService {
    List<Insurance>  retrieveAllinsurances();

    List<Insurance> retrieveArchivedinsurance();

    Insurance addInsurance(Insurance i);

    Insurance updateInsurance (Insurance i);

    Insurance retrieveInsurance (Integer idinsurance);


    float calculateFicoScore(Insurance i);

    double calculateInsuranceCost(Insurance insurance);

    double calculateInsuranceCostWithDiscount(Insurance insurance);

    List<Object[]> getInsuranceByType();

   //  double findMaxNumber(List<Object[]> insuranceList);

    boolean processInsuranceRequest(Insurance insurance);

    /*
          public Insurance assignInsuranceToPack(Integer idinsurance, Integer idPack) {
              Pack pack = packRepository.findByIdPack(idPack);
              Insurance insurance = insuranceRepository.findById(idinsurance);
              insurance.s
              return insurance;
          } */
    Insurance addInsuranceToPack(int insuranceId, int packId);




    double LevelOfRiskCalculator(Insurance insurance) throws IOException;





    Insurance assignInsuranceToPack(int idinsurance, int idPack);


}
