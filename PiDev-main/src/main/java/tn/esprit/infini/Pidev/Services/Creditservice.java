package tn.esprit.infini.Pidev.Services;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.*;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.*;
import tn.esprit.infini.Pidev.Services.user_management.EmailService;
import tn.esprit.infini.Pidev.entities.*;
import tn.esprit.infini.Pidev.exceptions.ResourceNotFoundException;
import tn.esprit.infini.Pidev.mappers.CreditMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

@Service
@Configuration
@EnableScheduling
@AllArgsConstructor
public class Creditservice implements Icreditservice {
    private CreditMapper creditMapper;
    private Creditrepository creditrepository;
    private SettingsRepository settingsRepository;
    private IUserRepo userRepository;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private EmailService emailService;
    private ComplaintService complaintService;
    @Override
    public List<Credit> retrieveAllCredits() {
        return  creditrepository.findAll();

    }

    @Override
    public CreditResponseDTO addCredits(CreditRequestDTO creditDTO) {
        if (creditDTO.getDuration() == null || creditDTO.getDuration() == 0 ) {
            Credit credit = Credit.builder()
                    .amount(creditDTO.getAmount())
                    .duration((int) ChronoUnit.MONTHS.between(creditDTO.getDateofobtaining().withDayOfMonth(1), creditDTO.getDateoffinish().withDayOfMonth(1)))
                    .dateOfApplication(LocalDate.now())
                    .dateofobtaining(creditDTO.getDateofobtaining())
                    .dateoffinish(creditDTO.getDateoffinish())
                    .statut(Statut.EN_ATTENTE)
                    .typeRemboursement(creditDTO.getTypeRemboursement())
                    .typeCredit(creditDTO.getTypeCredit())
                    .build();
            Credit savedCredit = creditrepository.save(credit);
            CreditResponseDTO creditResponseDTO = creditMapper.fromCredit(savedCredit);
           Double montant = credit.getAmount();
           long montantlong = montant.longValue();
            Transaction transaction = new Transaction();
            transaction.setCredit(credit);
            transaction.setTypeTransaction(TypeTransaction.Credit);
            transaction.setDate(new Date());
            transaction.setAmount( montantlong);
            transaction.setStatus("PENDING");
            transactionRepository.save(transaction);

            return creditResponseDTO;

        } else {
            Credit credit = Credit.builder()
                    .amount(creditDTO.getAmount())
                    .duration(creditDTO.getDuration())
                    .dateOfApplication(LocalDate.now())
                    .dateofobtaining(creditDTO.getDateofobtaining())
                    .dateoffinish(creditDTO.getDateofobtaining().plusMonths(creditDTO.getDuration()))
                    .statut(Statut.EN_ATTENTE)
                    .typeRemboursement(creditDTO.getTypeRemboursement())
                    .typeCredit(creditDTO.getTypeCredit())
                    .build();
            Credit savedCredit = creditrepository.save(credit);
            CreditResponseDTO creditResponseDTO = creditMapper.fromCredit(savedCredit);

            Double montant = credit.getAmount();
            long montantlong = montant.longValue();
            Transaction transaction = new Transaction();
            transaction.setCredit(credit);
            transaction.setTypeTransaction(TypeTransaction.Credit);
            transaction.setDate(new Date());
            transaction.setAmount( montantlong);
            transaction.setStatus("PENDING");
            transactionRepository.save(transaction);
            return creditResponseDTO;

        }
    }



    @Override
    public Credit updateCredit(Credit c) {
        return creditrepository.save(c);
    }

    @Override
    public Credit retrieveCredit(Long id) {

        Optional<Credit> credit = creditrepository.findById(id);

        if (credit.isPresent()) {
            return creditrepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("Credit not found with id " + id);
        }
    }


    @Override
    public void deleteCredit(Long id) {
        Optional<Credit> credit = creditrepository.findById(id);
        if (credit.isPresent()) {
            creditrepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Credit not found with id " + id);


        }

    }

    @Override
    public List<Credit> findCreditsByAttributes(Long id, Double amount, LocalDate dateOfApplication, LocalDate dateofobtaining, LocalDate dateoffinish, Double interestrate, Integer duration, Statut statut, TypeCredit typeCredit) {
        return creditrepository.findCreditsByAttributes(id, amount, dateOfApplication, dateofobtaining, dateoffinish, interestrate, duration, statut, typeCredit);
    }

    @Override
    public List<Credit> getCreditByiduser(Long userid) {
        return creditrepository.getCreditByiduser(userid);

    }


    @Override
    public Float newCredit(Long idCredit) {
        float s = 0;
        int numberOfCreditsIretarde = 0;
        int numberOfCreditsrembourse = 0;

        Long userid = Long.valueOf(userRepository.findUserByCreditId(idCredit).getId());
        if (getCreditByiduser(userid).isEmpty()) {
            return s;
        } else {
            for (Credit credit : getCreditByiduser(userid)
            ) {
                if (credit.getStatut() == Statut.EN_RETARDISSEMENT) {
                    numberOfCreditsIretarde++;
                }
                if (credit.getStatut() == Statut.REMBOURSE) {
                    numberOfCreditsrembourse++;
                }


                s = numberOfCreditsrembourse - numberOfCreditsIretarde;
            }

            return s;

        }


    }
    @Override
    public int calculateLengthOfCreditHistoryScore(Credit credit) {
        LocalDate oldestTransactionDate = LocalDate.now();
        int ageOfOldestTransactionInMonths = 0;
        User user = userRepository.findUserByCreditId(credit.getId());
        Account c = accountRepository.getAccountByUser(user);
        List<Transaction> transactions = transactionRepository.getTransactionByAccounts(c);

        if (transactions.isEmpty()) {
            return 0;
        }

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (transactionDate.isBefore(oldestTransactionDate)) {
                oldestTransactionDate = transactionDate;
            }
        }

        ageOfOldestTransactionInMonths = Period.between(oldestTransactionDate, LocalDate.now()).getYears() * 12
                + Period.between(oldestTransactionDate, LocalDate.now()).getMonths();

        if (ageOfOldestTransactionInMonths >= 300) {
            return 850;
        } else if (ageOfOldestTransactionInMonths >= 240) {
            return 750;
        } else if (ageOfOldestTransactionInMonths >= 180) {
            return 650;
        } else {
            return 500;
        }
    }

    @Override
    public double calculatePaymentHistoryScore(Long id) {
        Credit c=creditrepository.findById(id).orElseThrow(()-> new RuntimeException(String.format("Credit not found")));

        List<Transaction> transactions = transactionRepository.getTransactionBycredit(c);
        int latePayments = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getStatut() == Statut.EN_RETARDISSEMENT && transaction.getDate().compareTo(new Date()) < 0) {
                latePayments++;
            }
        }
        List<Settings> settings = settingsRepository.findAll();
        for (Settings setting : settings) {
            if (setting.getId()-1 == latePayments) {
                return setting.getMaxScore();
            }
        }
        return 400;
    }

    @Override
    public Double TauxtypeCredit(Credit c) {
        double s;
        if ((c.getTypeCredit()) == TypeCredit.CREDITConsommation) {
            s=500;
            return s;
        } else if ((c.getTypeCredit()) == TypeCredit.CreditImmobilier) {
            s=650;
            return s;
        } else if ((c.getTypeCredit()) == TypeCredit.CREDITEtudiant) {
            s=750;
            return s;
        } else {
            s=850;
            return s;
        }

    }


    @Override
    public float calculateFicoScore(Credit c) {
        User user = userRepository.findUserByCreditId(c.getId());
        float ficoscore = 0;
        ficoscore += (TauxtypeCredit(c) * 0.1)+(calculatePaymentHistoryScore(c.getId()) * 0.35)+(calculateLengthOfCreditHistoryScore(c)*0.15)+(calculateAmountsOwedScore(c)*0.3);
        return ficoscore;
    }
    @Override
    public double calculateAmountsOwedScore(Credit credit) {
        double totalBalance = 0.0;
        double totalCredit = 0.0;

        List<Transaction> transactions = credit.getTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getTypeTransaction() == TypeTransaction.Credit) {
                if (transaction.getStatut() == Statut.ACTIF){

                totalCredit += transaction.getAmount();
            } }
            else if (transaction.getTypeTransaction() == TypeTransaction.Invest) {
                    totalBalance += transaction.getAmount();
                }

        }
        if (totalCredit == 0) {
            return 500;
        } else {
            double creditUtilizationRatio = totalBalance / totalCredit;
            if (creditUtilizationRatio <= 0.3) {
                return 850;
            } else if (creditUtilizationRatio <= 0.5) {
                return 750;
            } else if (creditUtilizationRatio <= 0.7) {
                return 650;
            } else {
                return 500;
            }
        }
    }


    @Override
    public double InterestRateCalculator(Credit credit) throws IOException {
        double TMM = 8.05;
        double interestrate = 0;
        float score = 630;
        List<Settings> settings = settingsRepository.findAll();
        for (Settings rate : settings) {
            if (score >= rate.getMinScore() && score <= rate.getMaxScore()) {
                interestrate = (TMM + (rate.getRate()))*0.01;
                credit.setInterestrate(interestrate);
                creditrepository.save(credit);
                return interestrate;
            }
        }
        interestrate = TMM + settings.get(settings.size() - 1).getRate();
        credit.setInterestrate(interestrate);
        creditrepository.save(credit);
        return interestrate;
    }


    @Override
    public String getmm() throws IOException {
        String command = "python C:/Users/zou19/OneDrive/Desktop/pi/scrapping/azz.py";
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        return stringBuilder.toString();
    }

    @Override
        public double CalculMensualitefixe(Credit c) {
            double mensualite;
            double a=0 ;
            double b=0 ;
            double d= c.getInterestrate()/12;
            a = c.getAmount() *d;

            b = 1 - (Math.pow(1 + d, -c.getDuration()));
            mensualite = a / b;

             mensualite= Math.round(mensualite);

            return mensualite;
        }

    @Override
    public List<Double> CalculMensualitevariable(Credit c) {
        double am = (c.getAmount()/c.getDuration());
        double tm = c.getInterestrate()/12;
        double mensualite = 0;
        List<Double> listmensualite = new ArrayList<>();
        for (int i = 1; i <= c.getDuration(); i++) {
            mensualite=am*(1+(tm*(c.getDuration()-i+1)));
            mensualite= Math.round(mensualite);

            listmensualite.add(mensualite);
        }
        return listmensualite;
    }



    @Override
    public List<Double> listetauxinterets(Credit c) {
        double interets;
        double im = c.getInterestrate()/12;
        double montantrestant=c.getAmount();
        if (c.getTypeRemboursement()==TypeRemboursement.Amortissementfixe) {
            List <Double> mensualite=CalculMensualitevariable(c);
            List<Double> listtauxinterets = new ArrayList<>();
             for (int i = 0; i < mensualite.size(); i++) {
                interets = montantrestant*im;
                montantrestant=montantrestant-mensualite.get(i)+interets;
                 interets= Math.round(interets);

                 listtauxinterets.add(interets);

                  }
             listtauxinterets.add(0.0);

            return listtauxinterets;
        }
        else {
            double mensualite=CalculMensualitefixe(c);
            List<Double> listtauxinterets = new ArrayList<>();
           for (int i = 1; i <= c.getDuration(); i++) {
                interets=montantrestant*im;
                montantrestant=montantrestant-mensualite+interets;
                interets= Math.round(interets);
               listtauxinterets.add(interets);

                }
            listtauxinterets.add(0.0);

            return listtauxinterets;
            }
    }
    @Override
    public List<Double> listeAmortissement(Credit c) {
        List<Double> listeamortissement = new ArrayList<>();
        if (c.getTypeRemboursement()==TypeRemboursement.Amortissementfixe) {
            double am = (c.getAmount() / c.getDuration());
            for (int i = 0; i < c.getDuration(); i++) {
                am = Math.round(am);

                listeamortissement.add(am);

            }
            listeamortissement.add(0.0);
            return listeamortissement;

        }
        else{
            double am;
            for (int i = 0; i < c.getDuration(); i++) {
                am=CalculMensualitefixe(c)-listetauxinterets(c).get(i);
                am = Math.round(am);
                listeamortissement.add(am);


        }
            listeamortissement.add(0.0);
            return listeamortissement;


        }

        }
    @Override
    public List<Double> listemontantrestant(Credit c) {
        double interets;
        double im = c.getInterestrate()/12;
        double montantrestant=c.getAmount();
        if (c.getTypeRemboursement()==TypeRemboursement.Amortissementfixe) {
            List <Double> mensualite=CalculMensualitevariable(c);
            List<Double> listmontantrestant = new ArrayList<>();
            for (int i = 0; i < mensualite.size(); i++) {
                interets = montantrestant*im;
                montantrestant=montantrestant-mensualite.get(i)+interets;
                montantrestant = Math.round(montantrestant);
                listmontantrestant.add(montantrestant);

            }
            listmontantrestant.add(0.0);
            return listmontantrestant;
        }
        else {
            double mensualite=CalculMensualitefixe(c);
            List<Double> listmontantrestant = new ArrayList<>();
            for (int i = 1; i <= c.getDuration(); i++) {
                interets=montantrestant*im;
                montantrestant=montantrestant-mensualite+interets;
                montantrestant = Math.round(montantrestant);
                listmontantrestant.add(montantrestant);

            }
            listmontantrestant.add(0.0);
            return listmontantrestant;
        }
    }
        @Override
    public double Calculateamountafterinsurance (Credit c) {
    double amountofinsurance= c.getAmount()*0.03;
    c.setAmount(c.getAmount()*0.97);
    creditrepository.save(c);
    return amountofinsurance;
    }

    @Override
    public void ValidateCredit(Long id) throws IOException {
        Credit c=creditrepository.findById(id).orElseThrow(()-> new RuntimeException(String.format("Credit not found")));
                float score = calculateFicoScore(c);
         if (Optional.ofNullable(c.getGuarantor()).isPresent()) {
             //if{balance.admin>
            if (score < 580) {
                c.setStatut(Statut.Non_Approuvé);
                creditrepository.save(c);
            } else if ((score >= 580 && score <= 669) /*bank.amount*/) {
               c.setAmount((c.getAmount() * 0.80)-250);
                c.setStatut(Statut.Approuvé);
                creditrepository.save(c);
            } else if ((score >= 670 && score <= 739)) {
                c.setStatut(Statut.Approuvé);
                c.setAmount((c.getAmount() * 0.90)-250);
                creditrepository.save(c);
            } else if ((score >= 740 && score <= 799)) {
                  c.setAmount((c.getAmount() * 0.95)-250);
                c.setStatut(Statut.Approuvé);
                creditrepository.save(c);
            } else c.setStatut(Statut.Approuvé);
            creditrepository.save(c);
        }
         else c.setStatut(Statut.Non_Approuvé);
        creditrepository.save(c);
    }


    public Double averageInterestRate(List<Credit> credits) {
        Double totalInterestRate = 0.0;
        for (Credit credit : credits) {
            totalInterestRate += credit.getInterestrate();
        }
        return totalInterestRate / credits.size();
    }

    public Integer totalNumberOfLoans(List<Credit> credits) {
        return credits.size();
    }

    public Double totalAmountOfLoans() {
        List<Credit> credits=creditrepository.findAll();
        Double totalAmount = 0.0;
        for (Credit credit : credits) {
            totalAmount += credit.getAmount();
        }
        return totalAmount;
    }

    public Map<Statut, Double> percentageOfCreditsByStatus(List<Credit> credits) {
        Map<Statut, Integer> numberOfCreditsByStatus = new HashMap<>();
        for (Credit credit : credits) {
            Statut status = credit.getStatut();
            numberOfCreditsByStatus.put(status, numberOfCreditsByStatus.getOrDefault(status, 0) + 1);
        }
        Map<Statut, Double> percentageOfCreditsByStatus = new HashMap<>();
        int totalNumberOfCredits = credits.size();
        for (Map.Entry<Statut, Integer> entry : numberOfCreditsByStatus.entrySet()) {
            Statut status = entry.getKey();
            int numberOfCredits = entry.getValue();
            double percentage = (double) numberOfCredits / totalNumberOfCredits * 100;
            percentageOfCreditsByStatus.put(status, percentage);
        }
        return percentageOfCreditsByStatus;
    }

    public Map<TypeRemboursement, Double> averageRepaymentRateByType(List<Credit> credits) {
        Map<TypeRemboursement, Double> results = new HashMap<>();
        for (Credit credit : credits) {
            TypeRemboursement type = credit.getTypeRemboursement();
            Double amount = credit.getAmount();
            Integer duration = credit.getDuration();
            Double interestRate = credit.getInterestrate();
            Double totalRepayment = amount + amount * interestRate * duration / 12;
            results.put(type, results.getOrDefault(type, 0.0) + amount / totalRepayment);
        }
        for (Map.Entry<TypeRemboursement, Double> entry : results.entrySet()) {
            entry.setValue(entry.getValue() / credits.size());
        }
        return results;
    }
     @Override
     public void exportpdf(HttpServletResponse response, Long idCredit) throws IOException, DocumentException {
        Credit credit = retrieveCredit(idCredit);
        User user =userRepository.findUserByCreditId(idCredit);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph paragraph = new Paragraph("Voici les détails de votre crédit.", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        List<Double> mensualites = CalculMensualitevariable(credit);
        List<Double> tauxInterets = listetauxinterets(credit);
        List<Double> Montantrestant = listemontantrestant(credit);
        List<Double> Amortissement = listeAmortissement(credit);
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell cellNumero = new PdfPCell(new Phrase("Mensualité numéro", fontTitle));
        cellNumero.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellNumero);
        PdfPCell cellMensualites = new PdfPCell(new Phrase("Montant du mensualité", fontTitle));
        cellMensualites.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellMensualites);
        PdfPCell cellTaux = new PdfPCell(new Phrase("montant  d'interets", fontTitle));
        cellTaux.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellTaux);
         PdfPCell cellMontantrestant = new PdfPCell(new Phrase("montant  restant", fontTitle));
         cellTaux.setHorizontalAlignment(Element.ALIGN_CENTER);
         table.addCell(cellMontantrestant);
         PdfPCell cellamortissement= new PdfPCell(new Phrase("amortissement", fontTitle));
         cellTaux.setHorizontalAlignment(Element.ALIGN_CENTER);
         table.addCell(cellamortissement);
        for (int i = 0; i < mensualites.size(); i++) {
            PdfPCell cellNumeroValue = new PdfPCell(new Phrase(Integer.toString(i+1), fontParagraph));
            cellNumeroValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellNumeroValue);
            PdfPCell cellMensualitesValue = new PdfPCell(new Phrase(Double.toString(mensualites.get(i)), fontParagraph));
            cellMensualitesValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellMensualitesValue);
            PdfPCell cellTauxValue = new PdfPCell(new Phrase(Double.toString(tauxInterets.get(i)), fontParagraph));
            cellTauxValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellTauxValue);
            PdfPCell cellMontantrestantValue = new PdfPCell(new Phrase(Double.toString(Montantrestant.get(i)), fontParagraph));
            cellTauxValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellMontantrestantValue);
            PdfPCell cellamortissementValue = new PdfPCell(new Phrase(Double.toString(Amortissement.get(i)), fontParagraph));
            cellTauxValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellamortissementValue);
        }
        document.add(table);
         double totalAmount = credit.getAmount();
         double insuranceAmount = Calculateamountafterinsurance(credit);
         double suminterest = listetauxinterets(credit).stream().mapToDouble(Double::doubleValue).sum();
         double fraisdossier =250;
         double interestPercent = suminterest/totalAmount*100.0;
         double insurancePercent = (insuranceAmount / totalAmount * 100.0);
         double fraisdossierPercent = fraisdossier/totalAmount*100;
         double principalPercent = 100.0 - interestPercent - insurancePercent-fraisdossierPercent;

         DefaultPieDataset dataset = new DefaultPieDataset();
         dataset.setValue("Interest (" + String.format("%.1f", interestPercent) + "%)", suminterest);
         dataset.setValue("Insurance (" + String.format("%.1f", insurancePercent) + "%)", insuranceAmount);
         dataset.setValue("Frais (" + String.format("%.1f", fraisdossierPercent) + "%)", fraisdossier);
         dataset.setValue("Principal (" + String.format("%.1f", principalPercent) + "%)", credit.getAmount());

         JFreeChart chart = ChartFactory.createPieChart(
                 "Credit Details", dataset, true, true, false);

         PiePlot plot = (PiePlot) chart.getPlot();
         plot.setToolTipGenerator(new StandardPieToolTipGenerator(
                 "{0}: {1} ({2})", new DecimalFormat("0.0"), new DecimalFormat("0.0%")));
         ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
         ChartUtilities.writeChartAsPNG(chartOut, chart, 500, 300);
         byte[] chartBytes = chartOut.toByteArray();
         Image chartImage = Image.getInstance(chartBytes);
         document.add(chartImage);
         document.close();
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         PdfWriter.getInstance(document, byteArrayOutputStream);
         document.close();


     }
    @Override
    public byte[] exportPdfs(Long idCredit) throws IOException, DocumentException {
        Credit credit = retrieveCredit(idCredit);
        User user = userRepository.findUserByCreditId(idCredit);
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph paragraph = new Paragraph("Voici les détails de votre crédit.", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        List<Double> mensualites = CalculMensualitevariable(credit);
        List<Double> tauxInterets = listetauxinterets(credit);
        List<Double> Montantrestant = listemontantrestant(credit);
        List<Double> Amortissement = listeAmortissement(credit);
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell cellNumero = new PdfPCell(new Phrase("Mensualité numéro", fontTitle));
        cellNumero.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellNumero);
        PdfPCell cellMensualites = new PdfPCell(new Phrase("Montant du mensualité", fontTitle));
        cellMensualites.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellMensualites);
        PdfPCell cellTaux = new PdfPCell(new Phrase("montant  d'interets", fontTitle));
        cellTaux.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellTaux);
        PdfPCell cellMontantrestant = new PdfPCell(new Phrase("montant  restant", fontTitle));
        cellTaux.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellMontantrestant);
        PdfPCell cellamortissement= new PdfPCell(new Phrase("amortissement", fontTitle));
        cellTaux.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellamortissement);
        for (int i = 0; i < mensualites.size(); i++) {
            PdfPCell cellNumeroValue = new PdfPCell(new Phrase(Integer.toString(i+1), fontParagraph));
            cellNumeroValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellNumeroValue);
            PdfPCell cellMensualitesValue = new PdfPCell(new Phrase(Double.toString(mensualites.get(i)), fontParagraph));
            cellMensualitesValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellMensualitesValue);
            PdfPCell cellTauxValue = new PdfPCell(new Phrase(Double.toString(tauxInterets.get(i)), fontParagraph));
            cellTauxValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellTauxValue);
            PdfPCell cellMontantrestantValue = new PdfPCell(new Phrase(Double.toString(Amortissement.get(i)), fontParagraph));
            cellTauxValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellMontantrestantValue);
            PdfPCell cellamortissementValue = new PdfPCell(new Phrase(Double.toString(Montantrestant.get(i)), fontParagraph));
            cellTauxValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellamortissementValue);
        }
        document.add(table);
        double totalAmount = credit.getAmount();
        double insuranceAmount = Calculateamountafterinsurance(credit);
        double suminterest = listetauxinterets(credit).stream().mapToDouble(Double::doubleValue).sum();
        double fraisdossier =250;
        double interestPercent = suminterest/totalAmount*100.0;
        double insurancePercent = (insuranceAmount / totalAmount * 100.0);
        double fraisdossierPercent = fraisdossier/totalAmount*100;
        double principalPercent = 100.0 - interestPercent - insurancePercent-fraisdossierPercent;

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Interest (" + String.format("%.1f", interestPercent) + "%)", suminterest);
        dataset.setValue("Insurance (" + String.format("%.1f", insurancePercent) + "%)", insuranceAmount);
        dataset.setValue("Frais (" + String.format("%.1f", fraisdossierPercent) + "%)", fraisdossier);
        dataset.setValue("Principal (" + String.format("%.1f", principalPercent) + "%)", credit.getAmount());

        JFreeChart chart = ChartFactory.createPieChart(
                "Credit Details", dataset, true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setToolTipGenerator(new StandardPieToolTipGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0.0"), new DecimalFormat("0.0%")));
        ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
        ChartUtilities.writeChartAsPNG(chartOut, chart, 500, 300);
        byte[] chartBytes = chartOut.toByteArray();
        Image chartImage = Image.getInstance(chartBytes);
        document.add(chartImage);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
        @Override
        public void SendEmail( Long idCredit) throws DocumentException, IOException {
        User user =userRepository.findUserByCreditId(idCredit);
         byte[] pdfBytes = exportPdfs(idCredit);
         String userEmail = user.getEmail();
         String emailBody = "Details credits.";
         Email email = new Email();
         email.setRecipient(userEmail);
         email.setSubject("Detailscredits");
         email.setAttachement(pdfBytes);
         email.setMsgBody(emailBody);
         emailService.sendEmail(email);
     }
     @Override
    @Scheduled(cron = "0 0 1 1 * *")
    public void generateCreditReport() {
        List<Credit> credits = creditrepository.findAll();
        Map<TypeCredit, List<Credit>> creditsByType = new HashMap<>();
        for (Credit credit : credits) {
            TypeCredit type = credit.getTypeCredit();
            List<Credit> creditsOfType = creditsByType.get(type);
            if (creditsOfType == null) {
                creditsOfType = new ArrayList<>();
                creditsByType.put(type, creditsOfType);
            }
            creditsOfType.add(credit);
        }
        for (Map.Entry<TypeCredit, List<Credit>> entry : creditsByType.entrySet()) {
            TypeCredit type = entry.getKey();
            List<Credit> creditsOfType = entry.getValue();
            int numCredits = creditsOfType.size();
            double totalAmount = creditsOfType.stream().mapToDouble(Credit::getAmount).sum();
            System.out.println("Type of credit: " + type);
            System.out.println("Number of credits: " + numCredits);
            System.out.println("Total amount: " + totalAmount);
            System.out.println();
        }
    }


}







