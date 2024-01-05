package tn.esprit.infini.Pidev.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.GuarantorRepository;
import tn.esprit.infini.Pidev.entities.Guarantor;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class GuarantorService implements IGuarantorService{

    GuarantorRepository guarantorRepository;

    @Override
    public List<Guarantor> retrieveAllGuarantor() {
        return guarantorRepository.findAll();    }


    @Override
    public Guarantor addGuarantor(Guarantor g) throws Exception {

        if (!ValidCin(g.getCinGuarantor())) {
            throw new Exception("Invalid cin format. Must be composed of 8 digits.");
        }
        return guarantorRepository.save(g);
    }

    @Override
    public Guarantor updateGuarantor(Guarantor g) {
        return guarantorRepository.save(g);
    }

    @Override
    public Guarantor retrieveGuarantor(Integer idGurantor) {
        return guarantorRepository.findById(idGurantor).get();
    }

    @Override
    public void deleteGuarantor(Integer idGurantor) {
        guarantorRepository.deleteById(idGurantor);
    }

    @Override
    public boolean VerifyGuarantor(Guarantor guarantor, double amount) {
        double requiredValue = amount / 5;
        if (guarantor.getSalary() >= requiredValue) {
            return true;
        }
    return false;
    }
        //...
    @Override
    public boolean ValidCin(int cin) {
        String cinString = String.valueOf(cin);
        return cinString.matches("\\d{8}");
    }

   /* public String classifyGuarantorAdmissibility(Guarantor guarantor) {
        double score = 0.0;
        int age = calculateAge(guarantor.getDateOfBirth());

        // Critère 1: Âge minimum de 18 ans
        if (age >= 18) {
            score += 0.25;
        }

        // Critère 2: Revenu minimal de 1500 dinars
        if (guarantor.getSalary() >= 1500) {
            score += 0.25;
        }

        // Critère 3: Type d'emploi stable (CDI)
        if (guarantor.getJob().equalsIgnoreCase("CDI")) {
            score += 0.25;
        }

        // Critère 4: Historique de crédit positif
        if (guarantor.getCredit().getCreditHistory().equalsIgnoreCase("positif")) {
            score += 0.25;
        }

        // Classification finale
        if (score >= 0.75) {
            return "Admissible";
        } else {
            return "Non-admissible";*/



    // Calculer l'âge à partir de la date de naissance
    public int calculateAge(Date dateOfBirth) {
        LocalDate birthdate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthdate, now);
        return period.getYears();
    }




}




