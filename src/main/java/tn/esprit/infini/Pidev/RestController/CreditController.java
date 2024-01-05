package tn.esprit.infini.Pidev.RestController;


import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Services.ITransaction;
import tn.esprit.infini.Pidev.Services.Icreditservice;
import tn.esprit.infini.Pidev.entities.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@AllArgsConstructor
@RestController
@RequestMapping("/Credit")

public class CreditController {
    private Icreditservice icreditservice;
    private ITransaction iTransaction;


    @GetMapping("/Credits")
    List<Credit> afficher() {
        return icreditservice.retrieveAllCredits();
    }

        @PostMapping("/Credits")
    CreditResponseDTO ajouterr(@RequestBody CreditRequestDTO creditRequestDTO) {
        return icreditservice.addCredits(creditRequestDTO);
    }

    @GetMapping("/CreditById/{idCredit}")
    Credit afficherAvecId(@PathVariable Long idCredit) {
        return icreditservice.retrieveCredit(idCredit);
    }

    @PutMapping("/Credits/{id}")
    public Credit updateCredit(@RequestBody Credit credit) {
        return icreditservice.updateCredit(credit);
    }

    @DeleteMapping("/Credits/{idCredit}")
    void deleteCredit(@PathVariable("idCredit") Long idCredit) {
        icreditservice.deleteCredit(idCredit);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Credit>> searchCreditsByAttributes(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "amount", required = false) Double amount,
            @RequestParam(name = "dateOfApplication", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfApplication,
            @RequestParam(name = "dateofobtaining", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateofobtaining,
            @RequestParam(name = "dateoffinish", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateoffinish,
            @RequestParam(name = "interestrate", required = false) Double interestrate,
            @RequestParam(name = "duration", required = false) Integer duration,
            @RequestParam(name = "statut", required = false) Statut statut,
            @RequestParam(name = "guarantor", required = false) Guarantor guarantor,
            @RequestParam(name = "typeCredit", required = false) TypeCredit typeCredit,
            @RequestParam(name = "insurance", required = false) Insurance insurance) {
        List<Credit> credits = icreditservice.findCreditsByAttributes(id, amount, dateOfApplication,
                dateofobtaining, dateoffinish, interestrate, duration, statut, guarantor,
                typeCredit, insurance);
        return ResponseEntity.ok(credits);
    }

    @GetMapping("/getcreditsbyiduser/{userId}")
    public List<Credit> getCreditByiduser(@PathVariable("userId") Long userId) {
        return icreditservice.getCreditByiduser(userId);
    }

    @GetMapping("/newcredit/{creditId}")
    public Float newCredit(@PathVariable("creditId") Long creditId) {
        return icreditservice.newCredit(creditId);
    }

    @GetMapping("/TauxtypeCredit")
    public Double TauxtypeCredit(@RequestBody Credit c) {
        return icreditservice.TauxtypeCredit(c);
    }

    @GetMapping("/calculateFicoScore")
    public float calculateFicoScore(@RequestBody Credit c) {
        return icreditservice.calculateFicoScore(c);
    }

    @GetMapping("/CalculMensualitevariable")
    public List<Double> CalculMensualitevariable(@RequestBody Credit c) {
        return icreditservice.CalculMensualitevariable(c);
    }

    @GetMapping("/CalculMensualite0fixe")
    public double CalculMensualitefixe(@RequestBody Credit c) {
        return icreditservice.CalculMensualitefixe(c);
    }

    @GetMapping("/InterestRateCalculator")

    public double InterestRateCalculator(@RequestBody Credit credit) throws IOException {
        return icreditservice.InterestRateCalculator(credit);
    }

    @GetMapping("/listetauxinterets/{id}")
    public List<Double> listetauxinterets(@PathVariable Long id) {
        return icreditservice.listetauxinterets(id);
    }
    @GetMapping("/listeamortissement/{id}")
    public List<Double> listeAmortissement(@PathVariable("id")Long id){
        return icreditservice.listeAmortissement(id);
    }
    @GetMapping("/listemontantrestant/{id}")
    public List<Double> listemontantrestant(@PathVariable("id") Long id){
        return icreditservice.listemontantrestant(id);
    }

@PutMapping("/validatecredit/{id}")
    public void ValidateCredit(@PathVariable Long id) throws IOException {
        icreditservice.ValidateCredit(id);
    }


    @GetMapping("/getmm")
    public String runPythonScript() throws Exception {
        return icreditservice.getmm();
    }


    @GetMapping("/percentageByStatus")
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
    public Double getAverageInterestRate() {
        List<Credit> credits = icreditservice.retrieveAllCredits();
        return icreditservice.averageInterestRate(credits);
    }
    @GetMapping("/amountafterinsurance")
    double Calculateamountafterinsurance (Long id){
        return icreditservice.Calculateamountafterinsurance(id);
    }

    @GetMapping("/totalNumberOfLoans")
    public Integer getTotalNumberOfLoans() {
        List<Credit> credits = icreditservice.retrieveAllCredits();
        return icreditservice.totalNumberOfLoans(credits);
    }

    @GetMapping("/totalAmountOfLoans")
    public Double getTotalAmountOfLoans() {
        return icreditservice.totalAmountOfLoans();
    }

    @GetMapping("/repayment-rates")
    public ResponseEntity<Map<TypeRemboursement, Double>> getRepaymentRates() {
        List<Credit> credits = icreditservice.retrieveAllCredits();
        Map<TypeRemboursement, Double> repaymentRates = icreditservice.averageRepaymentRateByType(credits);
        return ResponseEntity.ok(repaymentRates);
    }

    @GetMapping("/pdf/generates")
    public void exportpdf(HttpServletResponse response, @RequestParam("creditId") long creditId) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=details_credit.pdf");
        icreditservice.exportpdf(response, creditId);
    }

    @GetMapping("/calculatePaymentHistoryScore/{id}")
    public double calculatePaymentHistoryScore(@PathVariable Long id){
        return icreditservice.calculatePaymentHistoryScore(id);
    }
    @GetMapping("/send/{idCredit}")
    void SendEmail(@PathVariable("idCredit") Long idCredit) throws DocumentException, IOException {
        icreditservice.SendEmail(idCredit);
    }




}










