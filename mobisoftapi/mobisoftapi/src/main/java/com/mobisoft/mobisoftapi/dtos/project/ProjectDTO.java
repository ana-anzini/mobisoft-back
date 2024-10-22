package com.mobisoft.mobisoftapi.dtos.project;

import java.util.Calendar;

import lombok.Data;

@Data
public class ProjectDTO {

	private String description;
    private Long costumerId;
    private Long projectDesignerId;
    private Long sellerId;
    private Calendar referenceDate;
    private boolean conclusionPending;
    private String notes;
}
