package com.mobisoft.mobisoftapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.configs.exceptions.FinancialNotFoundException;
import com.mobisoft.mobisoftapi.dtos.financial.FinancialDTO;
import com.mobisoft.mobisoftapi.models.Financial;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.repositories.FinancialRepository;

@Service
public class FinancialService {

	@Autowired
	private FinancialRepository financialRepository;

	@Autowired
	private ProjectService projectService;

	public Financial createFinancial(FinancialDTO financialDTO) {
		Project project = projectService.getProjectById(financialDTO.getProjectId());
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

	public void deleteFinancial(Long id) {
		Financial existingFinancial = financialRepository.findById(id)
				.orElseThrow(() -> new FinancialNotFoundException(id));
		financialRepository.delete(existingFinancial);
	}
	
	public void save(Financial financial) {
	    financialRepository.save(financial);
	}
}
