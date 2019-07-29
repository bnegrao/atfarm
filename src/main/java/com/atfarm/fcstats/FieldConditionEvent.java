package com.atfarm.fcstats;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;


/**
 * Event holding the only the 'vegetation' field condition value.
 * 
 * @author bnegrao
 *
 */

@Entity
public class FieldConditionEvent {
	
	@NotNull(message = "occurrenceAt is mandatory")
	@PastOrPresent(message = "occurrenceAt cannot be in the future")
	private Date occurrenceAt;
	
	@NotNull(message = "vegetation is mandatory")
	private Double vegetation;

	public FieldConditionEvent(Date occurrenceAt, Double vegetation) {
		super();
		this.occurrenceAt = occurrenceAt;
		this.vegetation = vegetation;
	}

	public Date getOccurrenceAt() {
		return occurrenceAt;
	}

	public void setOccurrenceAt(Date occurrenceAt) {
		this.occurrenceAt = occurrenceAt;
	}

	public Double getVegetation() {
		return vegetation;
	}

	public void setVegetation(Double vegetation) {
		this.vegetation = vegetation;
	}
	
	
	
}
