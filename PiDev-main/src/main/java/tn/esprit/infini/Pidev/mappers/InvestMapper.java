package tn.esprit.infini.Pidev.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import tn.esprit.infini.Pidev.entities.Credit;
import tn.esprit.infini.Pidev.entities.Invest;

@Component
public class InvestMapper {
    public InvestResponseDTO fromInvest(Invest invest){
        InvestResponseDTO investResponseDTO=new InvestResponseDTO();
        BeanUtils.copyProperties(invest,investResponseDTO);
        return investResponseDTO;
    }
    public Invest fromInvestRequestDTO(InvestRequestDTO investRequestDTO){
        Invest invest= new Invest();
        BeanUtils.copyProperties(investRequestDTO,invest);
        return invest;

    }
}
