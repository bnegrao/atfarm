package com.atfarm.fcstats.persistence;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import com.atfarm.fcstats.FieldConditionEvent;
import com.atfarm.fcstats.FieldConditionEventV2;

public class PersistenceService {
	
	public void persist (FieldConditionEvent fieldConditionsEvent) {
		// TODO		
	}
	
	public void persist (FieldConditionEventV2 fieldConditionEventV2) {
		// TODO
	}
	
	public SortedSet<FieldConditionEventV2> search(Date startDateInclusive, Date endDateInclusive){
		// TODO
		return new TreeSet<FieldConditionEventV2>();
	}

}
