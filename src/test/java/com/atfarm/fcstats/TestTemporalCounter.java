package com.atfarm.fcstats;

import static com.atfarm.fcstats.counter.Period.Type.SECOND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.atfarm.fcstats.counter.CounterData;
import com.atfarm.fcstats.counter.Period;
import com.atfarm.fcstats.counter.TemporalCounter;

public class TestTemporalCounter {
	
	@Test
	public void testAddingFiveNums(){
		TemporalCounter temporalCounter = new TemporalCounter(new Period(SECOND, 1));
		
		Date now = new Date();
		
		for (double i=1; i<6; i++) {
			temporalCounter.put(i, now);
		}
		
		CounterData cd = temporalCounter.getTemporalCounterData();
		validate(1d, 5d, 5l, new BigDecimal(15), cd);
	}
	
	@Test 
	public void testExpiredDataDoesNotCount() {
		
		for (Period.Type periodType: Period.Type.values()) {
			
			TemporalCounter temporalCounter = new TemporalCounter(new Period(periodType, 1), 1000*300);
			
			Date now = new Date();
			Date oneSecondAgo = Period.sumPeriodToDate(now, -1, periodType);
			
			temporalCounter.put(1d, now);		
			// the value bellow is inserted with an expired date for (one second ago)
			// so it should not  appear on the results
			temporalCounter.put(9d, oneSecondAgo);
			
			validate(1d, 1d, 1l, new BigDecimal(1), temporalCounter.getTemporalCounterData());			
			
		}				
	}
	
	@Test
	public void waitForDataToExpire() throws InterruptedException {
		TemporalCounter temporalCounter = new TemporalCounter(new Period(SECOND, 1));
		temporalCounter.put(6.6d, new Date());
		validate(6.6d, 6.6d, 1l, new BigDecimal("6.6"), temporalCounter.getTemporalCounterData());
		
		// lets wait for one second. after that the counter should be zeroed.
		Thread.sleep(1000);
		
		validate(0d, 0d, 0l, BigDecimal.ZERO, temporalCounter.getTemporalCounterData());		
	}	
	
	@Test
	public void makeSureTheCleanerWontCleanValidCounters() throws InterruptedException {
		TemporalCounter temporalCounter = new TemporalCounter(new Period(SECOND, 2), 200);
		temporalCounter.put(1d, new Date());
		Thread.sleep(1000);
		validate(1d, 1d, 1l, new BigDecimal(1), temporalCounter.getTemporalCounterData());			
	}
	
	private void validate(double min, double max, Long count, BigDecimal sum, CounterData test) {
		assertEquals(min, test.getMin(), 0d);
		assertEquals(max, test.getMax(), 0d);
		assertEquals(count, test.getCount());
		assertTrue(sum.compareTo(test.getSum()) == 0);				
	}

}
