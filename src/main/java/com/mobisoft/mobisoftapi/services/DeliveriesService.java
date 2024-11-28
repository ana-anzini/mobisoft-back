package com.mobisoft.mobisoftapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.configs.exceptions.DeliveryNotFoundException;
import com.mobisoft.mobisoftapi.dtos.delivery.DeliveryDTO;
import com.mobisoft.mobisoftapi.models.Deliveries;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.DeliveriesRepository;

@Service
public class DeliveriesService {

	@Autowired
	private DeliveriesRepository deliveriesRepository;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;

	public Deliveries createDelivery(DeliveryDTO DeliveryDTO) {
		UserGroup userGroup = userService.getLoggedUser().getGroup();
		Project project = projectService.getProjectById(DeliveryDTO.getProjectId());
		Deliveries delivery = new Deliveries();
		delivery.setAddressClient(DeliveryDTO.isAddressClient());
		delivery.setCep(DeliveryDTO.getCep());
		delivery.setAddress(DeliveryDTO.getAddress());
		delivery.setNumber(DeliveryDTO.getNumber());
		delivery.setNeighborhood(DeliveryDTO.getNeighborhood());
		delivery.setAdditional(DeliveryDTO.getAdditional());
		delivery.setDeliveryDate(DeliveryDTO.getDeliveryDate());
		delivery.setProject(project);
		delivery.setUserGroup(userGroup);

		return deliveriesRepository.save(delivery);
	}

	public List<Deliveries> getAllDeliveries() {
		UserGroup userGroup = userService.getLoggedUser().getGroup();
		return deliveriesRepository.findByUserGroupId(userGroup.getId());
	}

	public Deliveries findById(Long id) {
		return deliveriesRepository.findById(id).orElseThrow(() -> new DeliveryNotFoundException(id));
	}
	
	public Deliveries findByProjectId(Long projectId) {
		return deliveriesRepository.findByProjectId(projectId);
	}

	public Deliveries updateDelivery(Long projectId, DeliveryDTO DeliveryDTO) {
		Deliveries existingDelivery = deliveriesRepository.findByProjectId(projectId);
		existingDelivery.setAddressClient(DeliveryDTO.isAddressClient());
		existingDelivery.setCep(DeliveryDTO.getCep());
		existingDelivery.setAddress(DeliveryDTO.getAddress());
		existingDelivery.setNumber(DeliveryDTO.getNumber());
		existingDelivery.setNeighborhood(DeliveryDTO.getNeighborhood());
		existingDelivery.setAdditional(DeliveryDTO.getAdditional());
		existingDelivery.setDeliveryDate(DeliveryDTO.getDeliveryDate());
		existingDelivery.setFreight(DeliveryDTO.getFreight());

		return deliveriesRepository.save(existingDelivery);
	}

	public void deleteDelivery(Long id) {
		Deliveries delivery = findById(id);
		deliveriesRepository.delete(delivery);
	}
}
