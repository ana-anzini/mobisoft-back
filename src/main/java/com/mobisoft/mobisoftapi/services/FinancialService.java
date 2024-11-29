package com.mobisoft.mobisoftapi.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.configs.exceptions.FinancialNotFoundException;
import com.mobisoft.mobisoftapi.dtos.financial.FinancialDTO;
import com.mobisoft.mobisoftapi.models.Administration;
import com.mobisoft.mobisoftapi.models.Deliveries;
import com.mobisoft.mobisoftapi.models.Financial;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.repositories.FinancialRepository;

@Service
public class FinancialService {

	@Autowired
	private FinancialRepository financialRepository;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private AdministrationService administrationService;
	
	@Autowired
	private DeliveriesService deliveryService;

	public Financial createFinancial(FinancialDTO financialDTO) {
		Financial financial = new Financial();

		financial.setInstallmentsNumber(financialDTO.getInstallmentsNumber());
		financial.setFirstPayment(financialDTO.getFirstPayment());
		financial.setPaymentType(financialDTO.getPaymentType());
		financial.setDiscount(financialDTO.getDiscount());
		financial.setAdditionalExpenses(financialDTO.getAdditionalExpenses());
		financial.setTotalValue(financialDTO.getTotalValue());
		financial.setTotalCusts(financialDTO.getTotalCusts());
		financial.setTotalProjectDesigner(financialDTO.getTotalProjectDesigner());
		financial.setTotalSeller(financialDTO.getTotalSeller());
		financial.setTotalProfit(financialDTO.getTotalProfit());

		return financialRepository.save(financial);
	}

	public Financial findById(Long id) {
		return financialRepository.findById(id).orElseThrow(() -> new FinancialNotFoundException(id));
	}
	
	public Financial findByProjectId(Long projectId) {
		return financialRepository.findByProjectId(projectId);
	}

	public Financial updateFinancial(Long projectId, FinancialDTO financialDTO) {
		Financial existingFinancial = financialRepository.findByProjectId(projectId);

		existingFinancial.setInstallmentsNumber(financialDTO.getInstallmentsNumber());
		existingFinancial.setFirstPayment(financialDTO.getFirstPayment());
		existingFinancial.setPaymentType(financialDTO.getPaymentType());
		existingFinancial.setDiscount(financialDTO.getDiscount());
		existingFinancial.setAdditionalExpenses(financialDTO.getAdditionalExpenses());

		return financialRepository.save(existingFinancial);
	}
	
	public Financial calculateTotalProject(Long projectId) {
	    Financial existingFinancial = financialRepository.findByProjectId(projectId);
	    Deliveries delivery = deliveryService.findByProjectId(projectId); 
	    Administration administrationValues = administrationService.findByUserGroup();
	    BigDecimal totalPercentage;
	    BigDecimal totalValue;
	    
	    if (existingFinancial == null || administrationValues == null || delivery == null) {
	        throw new FinancialNotFoundException(projectId);
	    }
	    
	    // Taxas
	    BigDecimal totalTax = administrationValues.getTax().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
	    BigDecimal totalProfit = administrationValues.getAdditionalFinancial().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
	    BigDecimal totalProjectDesigner = administrationValues.getAdditionalProjectDesigner().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
	    BigDecimal totalSeller = administrationValues.getAdditionalSeller().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
	    BigDecimal totalAssembler = administrationValues.getAdditionalAssembler().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
	    
	    BigDecimal freight = (delivery != null && delivery.getFreight() != null) 
	            ? delivery.getFreight() 
	            : BigDecimal.ZERO;
	    
	    BigDecimal extrasDespenses = (existingFinancial != null && existingFinancial.getAdditionalExpenses() != null) 
	    		? existingFinancial.getAdditionalExpenses() 
	    				: BigDecimal.ZERO;
	    
	    totalPercentage = administrationValues.getAdditionalAssembler()
	            .add(administrationValues.getAdditionalFinancial())
	            .add(administrationValues.getAdditionalProjectDesigner())
	            .add(administrationValues.getAdditionalSeller())
	            .add(administrationValues.getTax());
	    
	    totalPercentage = BigDecimal.valueOf(100).subtract(totalPercentage);
	    totalValue = totalPercentage.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
	    totalPercentage = existingFinancial.getTotalCusts().divide(totalValue, 10, RoundingMode.HALF_UP);
	    totalPercentage = totalPercentage.add(freight).add(extrasDespenses);
	    existingFinancial.setTotalValue(totalPercentage);
	    
	    totalTax = totalPercentage.multiply(totalTax);
	    existingFinancial.setTotalTax(totalTax);
	    
	    totalProfit = totalPercentage.multiply(totalProfit);
	    existingFinancial.setTotalProfit(totalProfit);
	    
	    totalProjectDesigner = totalPercentage.multiply(totalProjectDesigner);
	    existingFinancial.setTotalProjectDesigner(totalProjectDesigner);
	    
	    totalSeller = totalPercentage.multiply(totalSeller);
	    existingFinancial.setTotalSeller(totalSeller);
	    
	    totalAssembler = totalPercentage.multiply(totalAssembler);
	    existingFinancial.setTotalSeller(totalAssembler);
	    
	    return existingFinancial;
	}

	public void deleteFinancial(Long id) {
		Financial existingFinancial = financialRepository.findById(id)
				.orElseThrow(() -> new FinancialNotFoundException(id));
		financialRepository.delete(existingFinancial);
	}
	
	public void save(Financial financial) {
	    financialRepository.save(financial);
	}
}
