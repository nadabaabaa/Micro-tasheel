package tn.esprit.infini.Pidev.dto;



public class CreatePayment {


    private Long amount;


    private String featureRequest;

    public CreatePayment() {
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public String getFeatureRequest() {
        return featureRequest;
    }

    public void setFeatureRequest(String featureRequest) {
        this.featureRequest = featureRequest;
    }
}