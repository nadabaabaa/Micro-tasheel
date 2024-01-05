package tn.esprit.infini.Pidev.dto;

import tn.esprit.infini.Pidev.entities.Stateofcomplaint;
import tn.esprit.infini.Pidev.entities.Typecomplaint;

import javax.persistence.*;
import java.time.LocalDate;

public class Complaintresponse  {
    private Long idcomplaint;
    private LocalDate dateofcomplaint= LocalDate.now();
    private Stateofcomplaint stateofcomplaint=Stateofcomplaint.complaintpending;
    @Enumerated(EnumType.STRING)
    private Typecomplaint typecomplaint;
}
