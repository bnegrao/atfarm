package com.atfarm.fcstats;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

/**
 * Event holding a Map<String, Double> of field condition data. 
 * 
 * @author bnegrao
 *
 */
@Entity
public class FieldConditionEventV2 {
	
	@NotNull(message = "occurenceAt is mandatory")
	@PastOrPresent(message = "occurrenceAt cannot be in the future")
	private Date occurrenceAt;
	
	@NotNull(message = "conditions is mandatory")
	private Map<String, Double> conditions;
	
	public FieldConditionEventV2() {		
	}

	public FieldConditionEventV2(Date occurrenceAt, Map<String, Double> conditions) {
		super();
		this.occurrenceAt = occurrenceAt;
		this.conditions = conditions;
	}

	public Date getOccurrenceAt() {
		return occurrenceAt;
	}

	public void setOccurrenceAt(Date date) {
		this.occurrenceAt = date;
	}

	public Map<String, Double> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, Double> conditionData) {
		this.conditions = conditionData;
	}

	@Override
	public String toString() {
		return "FieldConditionEventV2 [occurrenceAt=" + occurrenceAt + ", conditions=" + conditions + "]";
	}
	
}
