package tn.esprit.infini.Pidev.RestController;


import com.lowagie.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Services.ITransaction;
import tn.esprit.infini.Pidev.Services.Icreditservice;
import tn.esprit.infini.Pidev.entities.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200/")
    @RequestMapping("/Credit")
    public class CreditController {
        private Icreditservice icreditservice;
        private ITransaction iTransaction;


            @GetMapping("/Credits")
            @PreAuthorize("hasRole('ADMIN')")

            List<Credit> afficher() {
                return icreditservice.retrieveAllCredits();
            }

            @PostMapping("/ajoutCredits")
            @PreAuthorize("hasRole('USER')")
            CreditResponseDTO ajouterr(@RequestBody CreditRequestDTO creditRequestDTO) {
            return icreditservice.addCredits(creditRequestDTO);
        }

        @GetMapping("/CreditById/{idCredit}")
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        Credit afficherAvecId(@PathVariable Long idCredit) {
            return icreditservice.retrieveCredit(idCredit);
        }

        @PutMapping("/updatecredits/{id}")
        public Credit updateCredit(@RequestBody Credit credit) {
            return icreditservice.updateCredit(credit);
        }

        @DeleteMapping("/deleteCredits/{idCredit}")
        @PreAuthorize("hasRole('ADMIN')")

        void deleteCredit(@PathVariable("idCredit") Long idCredit) {
            icreditservice.deleteCredit(idCredit);
        }

        @GetMapping("/search/")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<Credit>> searchCreditsByAttributes(
                @RequestParam(name = "id", required = false) Long id,
                @RequestParam(name = "amount", required = false) Double amount,
                @RequestParam(name = "dateOfApplication", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfApplication,
                @RequestParam(name = "dateofobtaining", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateofobtaining,
                @RequestParam(name = "dateoffinish", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateoffinish,
                @RequestParam(name = "interestrate", required = false) Double interestrate,
                @RequestParam(name = "duration", required = false) Integer duration,
                @RequestParam(name = "statut", required = false) Statut statut,
                @RequestParam(name = "typeCredit", required = false) TypeCredit typeCredit)
                 {
            List<Credit> credits = icreditservice.findCreditsByAttributes(id, amount, dateOfApplication,
                    dateofobtaining, dateoffinish, interestrate, duration, statut,typeCredit);
            return ResponseEntity.ok(credits);
        }

        @GetMapping("/getcreditsbyiduser/{userId}")
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        public List<Credit> getCreditByiduser(@PathVariable("userId") Long userId) {
            return icreditservice.getCreditByiduser(userId);
        }

        @GetMapping("/newcredit/{creditId}")
        @PreAuthorize("hasRole('USER')")

        public Float newCredit(@PathVariable("creditId") Long creditId) {
            return icreditservice.newCredit(creditId);
        }

        @PostMapping("/TauxtypeCredit")
        @PreAuthorize("hasRole('USER')")
        public Double TauxtypeCredit(@RequestBody Credit c) {
            return icreditservice.TauxtypeCredit(c);
        }

        @PostMapping("/calculateFicoScore")
        @PreAuthorize("hasRole('USER')")
        public float calculateFicoScore(@RequestBody Credit c) {
            return icreditservice.calculateFicoScore(c);
        }

        @PostMapping("/CalculMensualitevariable")
        public List<Double> CalculMensualitevariable(@RequestBody Credit c) {
            return icreditservice.CalculMensualitevariable(c);
        }

        @PostMapping("/CalculMensualite0fixe")
        public double CalculMensualitefixe(@RequestBody Credit c) {
            return icreditservice.CalculMensualitefixe(c);
        }

        @PutMapping("/InterestRateCalculator")
        @PreAuthorize("hasRole('USER')")
        public double InterestRateCalculator(@RequestBody Credit credit) throws IOException {
            return icreditservice.InterestRateCalculator(credit);
        }

        @PostMapping("/listetauxinterests")
        public List<Double> listetauxinterets(@RequestBody Credit credit) {
            return icreditservice.listetauxinterets(credit);
        }
        @PostMapping ("/listeamortissement")
        public List<Double> listeAmortissement(@RequestBody Credit credit){
            return icreditservice.listeAmortissement(credit);
        }
        @PostMapping ("/listemontantrestant")
        public List<Double> listemontantrestant(@RequestBody Credit credit){
            return icreditservice.listemontantrestant(credit);
        }

    @PutMapping("/validatecredit/{id}")
    @PreAuthorize("hasRole('USER')")
        public void ValidateCredit(@PathVariable Long id) throws IOException {
            icreditservice.ValidateCredit(id);
        }

        @GetMapping("/getmm")
        public String runPythonScript() throws Exception {
            return icreditservice.getmm();
        }


        @GetMapping("/percentageByStatus")
        @PreAuthorize("hasRole('ADMIN')")

        public ResponseEntity<Map<String, Double>> PercentageOfCreditsByStatus() {
            List<Credit> credits = icreditservice.retrieveAllCredits();
            Map<Statut, Double> percentageOfCreditsByStatus = icreditservice.percentageOfCreditsByStatus(credits);
            Map<String, Double> response = new HashMap<>();
            for (Map.Entry<Statut, Double> entry : percentageOfCreditsByStatus.entrySet()) {
                response.put(entry.getKey().name(), entry.getValue());
            }
            return ResponseEntity.ok(response);
        }

        @GetMapping("/averageInterestRate")
        @PreAuthorize("hasRole('ADMIN')")

        public Double getAverageInterestRate() {
            List<Credit> credits = icreditservice.retrieveAllCredits();
            return icreditservice.averageInterestRate(credits);
        }
        @PostMapping ("/amountafterinsurance")
        double Calculateamountafterinsurance (@RequestBody Credit credit){
            return icreditservice.Calculateamountafterinsurance(credit);
        }

        @GetMapping("/totalNumberOfLoans")
        @PreAuthorize("hasRole('ADMIN')")

        public Integer getTotalNumberOfLoans() {
            List<Credit> credits = icreditservice.retrieveAllCredits();
            return icreditservice.totalNumberOfLoans(credits);
        }

        @GetMapping("/totalAmountOfLoans")
        @PreAuthorize("hasRole('ADMIN')")

        public Double getTotalAmountOfLoans() {
            return icreditservice.totalAmountOfLoans();
        }

        @GetMapping("/repayment-rates")
        @PreAuthorize("hasRole('ADMIN')")

        public ResponseEntity<Map<TypeRemboursement, Double>> getRepaymentRates() {
            List<Credit> credits = icreditservice.retrieveAllCredits();
            Map<TypeRemboursement, Double> repaymentRates = icreditservice.averageRepaymentRateByType(credits);
            return ResponseEntity.ok(repaymentRates);
        }

            @GetMapping("/pdf/generates/{creditId}")
            @PreAuthorize("hasRole('USER')")
            public void exportpdf(HttpServletResponse response, @PathVariable("creditId") long creditId) throws DocumentException, IOException {
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "attachment; filename=details_credit.pdf");
                icreditservice.exportpdf(response, creditId);
            }

        @GetMapping("/calculatePaymentHistoryScore/{id}")
        @PreAuthorize("hasRole('USER')")
        public double calculatePaymentHistoryScore(@PathVariable Long id){
            return icreditservice.calculatePaymentHistoryScore(id);
        }
        @GetMapping("/send/{idCredit}")
        @PreAuthorize("hasRole('USER')")
        void SendEmail(@PathVariable("idCredit") Long idCredit) throws DocumentException, IOException {
            icreditservice.SendEmail(idCredit);
        }

}










