package com.atfarm.fcstats;

import java.util.Date;
import java.util.SortedSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.atfarm.fcstats.counter.Period;
import com.atfarm.fcstats.persistence.PersistenceService;
import com.atfarm.fcstats.statistics.StatisticsService;


/**
 * Before starting to respond to client requests, restore from the DB 
 * any FieldConditionEvents that are valid for the current Period.
 * This is to guarantee that the returned statistics are consistent.
 *		 
 * @author bnegrao
 *
 */
@Component
public class LoadEventsFromTheDatabaseOnAppStartup {
    
	@Value("${periodType}")
	private Period.Type periodType;
	
	@Value("${periodSize}")
	private Integer periodSize;    
	
	@Autowired 
	private PersistenceService persistenceService;
	
	@Autowired
	private StatisticsService statisticsService;
    
    @PostConstruct
    public void loadEventsFromTheDatabase() throws Exception {
        
        Period period = new Period(periodType, periodSize);
        
        Date endDate = new Date();
        
        SortedSet<FieldConditionEventV2> savedEvents = persistenceService.search(period.startDate(endDate), endDate);
        
        if (savedEvents.size() > 0) {    
        	savedEvents.forEach(event -> statisticsService.input(event.getConditions(), event.getOccurrenceAt()));        	        	
        } 
    }
    
    
}