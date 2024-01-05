package tn.esprit.infini.Pidev.dto;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.infini.Pidev.entities.Typecomplaint;
@Setter
@Getter
public class ComplaintrequestDTO {
    public String description;
    @Enumerated(EnumType.STRING)
    private Typecomplaint typecomplaint;
   // private User user;
}
