package tn.esprit.infini.Pidev.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import tn.esprit.infini.Pidev.entities.Complaint;

@Component
public class Complaintmapper {


    public Complaintresponse fromComplaint(Complaint complaint) {
        Complaintresponse complaintresponse = new Complaintresponse();
        BeanUtils.copyProperties(complaint, complaintresponse);
        return complaintresponse;
    }

    public Complaint fromComplaintRequestDTO(ComplaintrequestDTO complaintrequestDTO) {
        Complaint complaint = new Complaint();
        BeanUtils.copyProperties(complaintrequestDTO, complaint);
        return complaint;


    }
}