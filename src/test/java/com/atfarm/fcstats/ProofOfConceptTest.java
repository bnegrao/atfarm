package com.atfarm.fcstats;

import static com.atfarm.fcstats.counter.Period.Type.DAY;
import static com.atfarm.fcstats.counter.Period.Type.HOUR;
import static com.atfarm.fcstats.counter.Period.Type.MINUTE;
import static com.atfarm.fcstats.counter.Period.Type.SECOND;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.atfarm.fcstats.counter.Period;
import com.atfarm.fcstats.counter.TemporalCounter;
import com.atfarm.fcstats.counter.TemporalCounterData;

/**
 * This test shows that TemporalCounter.getTemporalCounterData()'s processing time does not grow 
 * according to N, whether N is the number of doubles inserted during the period, instead, 
 * the processing time grows proportional to the Period's size. If the period size is 1, 
 * TemporalCounter.getTemporalCounterData() will work in O(1), if the period size is 2, 
 * it will work in O(2) and so on...
 * 
 * For a period of 30 days, no matter how many doubles were inserted, getTemporalCounterData()
 * will work in O(30), not O(N).  
 * 
 * WARNING: This test takes 2 minutes to complete.
 * 
 * @author bnegrao
 *
 */
public class ProofOfConceptTest {
	@Test
	public void proofOfConceptTest() {		
		System.out.println("WARNING: This test takes 2 minutes to complete!...");
		
		TemporalCounter fourSecondsCounter = new TemporalCounter(new Period(SECOND, 4));
		TemporalCounter oneMinuteCounter = new TemporalCounter(new Period(MINUTE, 1));
		TemporalCounter threeMinutesCounter = new TemporalCounter(new Period(MINUTE, 3));
		TemporalCounter oneHourCounter = new TemporalCounter(new Period(HOUR, 1));
		TemporalCounter oneDayCounter = new TemporalCounter(new Period(DAY, 1));
		
		long twoMinutesElapse = System.currentTimeMillis() + 120*1000;
		Long nInserts = 0l;
		while (System.currentTimeMillis() < twoMinutesElapse) {
			Date creationDate = new Date();
			fourSecondsCounter.put(1d, creationDate);
			oneMinuteCounter.put(1d, creationDate);
			threeMinutesCounter.put(1d, creationDate);	
			oneHourCounter.put(1d, creationDate);
			oneDayCounter.put(1d, creationDate);			
			nInserts++;
		}
				
				
		TemporalCounterData fourSecondsCounterData = fourSecondsCounter.getTemporalCounterData();
		TemporalCounterData oneMinuteCounterData = oneMinuteCounter.getTemporalCounterData();
		TemporalCounterData threeMinutesCounterData = threeMinutesCounter.getTemporalCounterData();
		TemporalCounterData oneHourCounterData = oneHourCounter.getTemporalCounterData();
		TemporalCounterData oneDayCounterData = oneDayCounter.getTemporalCounterData();
		
		System.out.println("fourSecondsCounterData: " + fourSecondsCounterData);	
		System.out.println("oneMinuteCounterData: " +  oneMinuteCounterData);
		System.out.println("threeMinutesCounterData: " +  threeMinutesCounterData);
		System.out.println("oneHourCounterData: " + oneHourCounterData);
		System.out.println("oneDayCounterData: " + oneDayCounterData);		
		
		// these assertions prove that the number of counters used for the period's calculation
		// is equal to the period's size
		assertEquals(fourSecondsCounterData.getPeriod().getSize(), fourSecondsCounterData.getNumberOfCountersUsed());
		assertEquals(oneMinuteCounterData.getPeriod().getSize(), oneMinuteCounterData.getNumberOfCountersUsed());
		assertEquals(threeMinutesCounterData.getPeriod().getSize(), threeMinutesCounterData.getNumberOfCountersUsed());
		assertEquals(oneHourCounterData.getPeriod().getSize(), oneHourCounterData.getNumberOfCountersUsed());
		assertEquals(oneDayCounterData.getPeriod().getSize(), oneDayCounterData.getNumberOfCountersUsed());			
				
		// The TemporalCounters for 3 minutes, one hour and one day could sum up all the
		// doubles inserted in the two minutes period:
		assertEquals(nInserts, threeMinutesCounterData.getCount());
		assertEquals(nInserts, oneHourCounterData.getCount());
		assertEquals(nInserts, oneDayCounterData.getCount());
		
		System.out.println("-------------------------------------------");
		System.out.printf("In two minutes, '%d' double values were inserted in the temporal counters.\n", nInserts);
		// The fourSecondsTemporalCounter sums up the inserts of the current second, and the 3 seconds before:
		System.out.printf("There were '%d' inserts in the last 4 seconds period\n", fourSecondsCounterData.getCount());
		// The oneMinuteTemporalCounter sums up the inserts of the current minute:
		System.out.printf("There were '%d' inserts in the current minute period\n", oneMinuteCounterData.getCount());
					
	}
}
