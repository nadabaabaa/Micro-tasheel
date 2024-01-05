package tn.esprit.infini.Pidev.Services;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.entities.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@Configuration
@EnableScheduling
@Service
public interface Icreditservice {
    List<Credit> retrieveAllCredits();
    CreditResponseDTO addCredits(CreditRequestDTO creditDTO);
    Credit updateCredit (Credit c);

    Credit retrieveCredit (Long id);

    void deleteCredit( Long id);
    List<Credit> findCreditsByAttributes(Long id, Double amount, LocalDate dateOfApplication, LocalDate dateofobtaining, LocalDate dateoffinish, Double interestrate, Integer duration, Statut statut, Guarantor guarantor, TypeCredit typeCredit, Insurance insurance);
    List<Credit> getCreditByiduser(Long userid);
    Float newCredit(Long idcredit);
    Double TauxtypeCredit(Credit credit);
    double CalculMensualitefixe(Credit credit);
    List<Double> CalculMensualitevariable(Credit credit);
    List<Double> listetauxinterets(Long id);
    void ValidateCredit(Long id) throws IOException;
     float calculateFicoScore(Credit credit);
    List<Double> listeAmortissement(Long id);
    List<Double> listemontantrestant(Long id);
    double InterestRateCalculator(Credit credit) throws IOException;

    String getmm() throws Exception;
    Double averageInterestRate(List<Credit> credits);
     Integer totalNumberOfLoans(List<Credit> credits);
    Double totalAmountOfLoans();
    Map<Statut, Double> percentageOfCreditsByStatus(List<Credit> credits);

    Map<TypeRemboursement, Double> averageRepaymentRateByType(List<Credit> credits);
    void SendEmail(Long idCredit) throws DocumentException, IOException;

    void exportpdf(HttpServletResponse response, Long idCredit) throws IOException, DocumentException;
     byte[] exportPdfs(Long idCredit) throws IOException, DocumentException;
    double calculatePaymentHistoryScore(Long id);
    double calculateAmountsOwedScore(Credit credit);
     int calculateLengthOfCreditHistoryScore(Credit credit);
     double Calculateamountafterinsurance (Long id);
    @Scheduled(cron = "0 0 1 1 * *")
    void generateCreditReport();

    }





