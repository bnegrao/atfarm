package com.atfarm.fcstats.statistics;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.atfarm.fcstats.counter.Period;
import com.atfarm.fcstats.counter.TemporalCounter;
import com.atfarm.fcstats.counter.TemporalCounterData;

public class StatisticsService {		
	
	private TreeMap<String, TemporalCounter> dataCounterMap;
	
	private Period period;
	
	public StatisticsService(Period period) {
		if (period.getSize() < 1) {
			throw new IllegalArgumentException("periodSize must be equal or grater than 1");
		}
		this.dataCounterMap = new TreeMap<String, TemporalCounter>();
		this.period = period;
		
	}
	
	public void input(Map<String, Double> data, Date creationDate) {
		synchronized (this) {			
			for (String dataName: data.keySet()) { 			
				TemporalCounter temporalCounter = dataCounterMap.get(dataName);
				if (temporalCounter == null) {
					temporalCounter = new TemporalCounter(period);
					dataCounterMap.put(dataName, temporalCounter);
				}
				temporalCounter.put(data.get(dataName), creationDate);
			}			
		}
	}	

	public Map<String, Statistics> getStatistics() {		
		synchronized(this) {			
			Map<String, Statistics> statisticsMap = new TreeMap<String, Statistics>();
			
			if (dataCounterMap.size() == 0) {
				return statisticsMap;
			}
			
			for (String dataName: dataCounterMap.keySet()) {
				TemporalCounter temporalCounter = dataCounterMap.get(dataName);
				TemporalCounterData temporalCounterData = temporalCounter.getTemporalCounterData();
				statisticsMap.put(dataName, new Statistics(temporalCounterData));						
			}
									
			return statisticsMap;			
		}
	}
	

}
