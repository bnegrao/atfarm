package com.atfarm.fcstats;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.atfarm.fcstats.counter.Period;
import static com.atfarm.fcstats.counter.Period.Type.*;
import com.atfarm.fcstats.statistics.Statistics;
import com.atfarm.fcstats.statistics.StatisticsService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestStatisticsService {
	
	@Test
	public void testAddingASingleValue() {
		StatisticsService statisticsService = new StatisticsService(new Period(SECOND, 2));
		Map<String, Double> data = makeDataMap("vegetation", 1d);
		statisticsService.input(data, new Date());
				
		Statistics stats = statisticsService.getStatistics().get("vegetation");
		
		assertTrue(stats.equals(new Statistics(1d,1d,1d)));
	}

	@Test
	public void testAddingTwoProperties() {
		StatisticsService statisticsService = new StatisticsService(new Period(SECOND, 2));
		
		Map<String, Double> data = new HashMap<String, Double>();		
		data.put("vegetation", 1d);
		data.put("nitrogen", 2d);
		
		for (int i = 0; i<5; i++) {
			statisticsService.input(data, new Date());
		}		
		
		// input new data to both properties
		data.put("vegetation", 25d);
		data.put("nitrogen", 8d);				
		statisticsService.input(data, new Date());
		
		Statistics vegetationStatistics = statisticsService.getStatistics().get("vegetation");
		Statistics nitrogenStatistics = statisticsService.getStatistics().get("nitrogen");
		
		assertTrue(vegetationStatistics.equals(new Statistics(1d,25d,5d)));
		assertTrue(nitrogenStatistics.equals(new Statistics(2d, 8d, 3d)));
	}
	
	/**
	 * Make sure that the statistics don't include data
	 * that is expired.
	 */
	@Test
	public void testExpiredDataShouldNotBeInTheResults() {
		StatisticsService statisticsService = new StatisticsService(new Period(SECOND, 1));
		statisticsService.input(makeDataMap("nitrogen", 1d), new Date());		
		// now lets add data with creationDate of 1 second ago
		Date creationDateOneSecAgo = new Date(new Date().getTime() - 1000l);
		statisticsService.input(makeDataMap("nitrogen", 9999.999d), creationDateOneSecAgo);
		
		// the statistics should not include that data
		assertTrue(statisticsService.getStatistics().get("nitrogen").equals(new Statistics(1d,1d,1d)));
	}
	
	private Map<String, Double> makeDataMap(String propertyName, Double value) {
		Map<String, Double> data = new HashMap<String, Double>();
		data.put(propertyName, value);
		return data;
	}
	
}
