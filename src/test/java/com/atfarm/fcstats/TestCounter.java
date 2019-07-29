package com.atfarm.fcstats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.atfarm.fcstats.counter.Counter;
import com.atfarm.fcstats.counter.CounterData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCounter {
	
	@Test
	public void testAddingASingleValue() {
		Counter counter = new Counter();
		counter.put(0.25d);
		
		CounterData counterData = counter.getCounterData();		
		assertEquals(1l, counterData.getCount().longValue());
		assertEquals(0.25d, counterData.getMin(), 0d);
		assertEquals(0.25d, counterData.getMax(), 0d);
		assertTrue(counterData.getSum().compareTo(new BigDecimal("0.25")) == 0);						
	}
	
	@Test
	public void testAddingValuesToCounter() {
		Counter counter = new Counter();
		double[] values = new double[] {1.001d, 2.002d, 3.003d, 4.004d, 5.005d};
		for (double d : values) {
			counter.put(d);
		}
		
		CounterData counterData = counter.getCounterData();	
		assertEquals(5l, counterData.getCount().longValue());
		assertEquals(1.001d, counterData.getMin(), 0d);
		assertEquals(5.005d, counterData.getMax(), 0d);
		assertTrue(new BigDecimal("15.015").compareTo(counterData.getSum()) == 0);
	}
	
	@Test 
	public void testAddingZero() {
		Counter counter = new Counter();		
		counter.put(0d);
		
		CounterData counterData = counter.getCounterData();		
		assertEquals(1l, counterData.getCount().longValue());
		assertEquals(0d, counterData.getMin(), 0d);
		assertEquals(0d, counterData.getMax(), 0d);
		assertTrue(counterData.getSum().compareTo(new BigDecimal("0")) == 0);	
		
		counter.put(1d);
		counterData = counter.getCounterData();		
		assertEquals(2l, counterData.getCount().longValue());
		assertEquals(0d, counterData.getMin(), 0d);
		assertEquals(1d, counterData.getMax(), 0d);
		assertTrue(counterData.getSum().compareTo(new BigDecimal("1")) == 0);					
	}
}
