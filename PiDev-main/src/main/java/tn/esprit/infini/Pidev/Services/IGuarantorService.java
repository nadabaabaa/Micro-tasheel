package tn.esprit.infini.Pidev.Services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.infini.Pidev.entities.Credit;
import tn.esprit.infini.Pidev.entities.Guarantor;

import java.util.List;

public interface IGuarantorService {

    List<Guarantor> retrieveAllGuarantor();

    Guarantor addGuarantor(Guarantor g) throws Exception;

    Guarantor updateGuarantor(Guarantor g);

    Guarantor retrieveGuarantor(Integer idGurantor);

    void deleteGuarantor(Integer idGurantor);

    boolean VerifyGuarantor(Guarantor guarantor, double amount);


    boolean ValidCin(int cin);

}
