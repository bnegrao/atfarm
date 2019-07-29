package com.atfarm.fcstats.counter;

import java.util.Date;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemporalCounter {

	private TreeMap<String, Counter> timeSortedCounters;

	private Period period;
	
	Logger logger = LoggerFactory.getLogger(TemporalCounter.class);
	
	private long cleanExpiredCountersTaskInterval;

	public TemporalCounter(Period period, long cleanupRoutineIntervalMillis) {
		if (period.getSize() < 1) {
			throw new IllegalArgumentException("period size must be equal or greater than 1");
		}
		this.timeSortedCounters = new TreeMap<String, Counter>();
		this.period = period;
		this.cleanExpiredCountersTaskInterval = cleanupRoutineIntervalMillis;

		scheduleCleanerTask();
	}
	
	public TemporalCounter(Period period) {
		this (period, 1000l * 300l);
	}	

	public void put(Double value, Date creationDate) {
		synchronized (this) {
			String dateKey = Period.formatDate(creationDate, period.getType());
			Counter counter = timeSortedCounters.get(dateKey);
			if (counter == null) {
				counter = new Counter();
				timeSortedCounters.put(dateKey, counter);
			}
			counter.put(value);
		}
	}

	/**
	 * This method returns the CounterData for the whole period in O(1) time.
	 * @return
	 */
	public TemporalCounterData getTemporalCounterData() {
		synchronized (this) {
			if (timeSortedCounters.size() == 0) {
				return new TemporalCounterData();
			}

			SortedMap<String, Counter> validCounters = timeSortedCounters.tailMap(period.startDateAsString(new Date()));

			logger.debug(String.format(period.toString() + ": '%d' counters exist. Expired counters: '%d'.",
					timeSortedCounters.size(), timeSortedCounters.size() - validCounters.size()));			
			
			if (validCounters.size() == 0) {
				return new TemporalCounterData();
			}
			
			Counter consolidatedCounter = new Counter();
						
			validCounters.forEach((dateStr, counter) -> consolidatedCounter.merge(counter));
			
			TemporalCounterData temporalCounterData = new TemporalCounterData(consolidatedCounter.getCounterData()); 
			temporalCounterData.setNumberOfCountersUsed(validCounters.size());
			temporalCounterData.setStartDateKey(validCounters.firstKey());
			temporalCounterData.setEndDateKey(validCounters.lastKey());
			temporalCounterData.setPeriod(period);			
			
			return temporalCounterData;
		}
	}
	
	private void scheduleCleanerTask() {		
		final TemporalCounter temporalCounterInstance = this;
		
		TimerTask cleanExpiredCounters = new TimerTask() {
			public void run() {
				synchronized(temporalCounterInstance) {
					if (timeSortedCounters.size() == 0) {
						return;
					}
					SortedMap<String, Counter> expiredCounters = timeSortedCounters.headMap(temporalCounterInstance.period.startDateAsString(new Date()));
					if (expiredCounters.size() == 0) {
						return;
					}
					logger.debug("cleaner will clean " + expiredCounters.size() + " expired counters: " + expiredCounters.keySet().toString());
					expiredCounters.clear();
				}				
			}			
		};
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(cleanExpiredCounters, 0, cleanExpiredCountersTaskInterval);				
	}	

}
