package com.atfarm.fcstats.counter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Snapshot of the information stored in a Counter in a given moment
 * @author bnegrao
 *
 */
public class CounterData {

	protected Long count;
	
	protected BigDecimal sum;
	
	protected Double min;
	
	protected Double max;
	
	protected Date creationDate;

	// This constructor is not public because it is meant to be invoked
	// only from the Counter class
	CounterData(Long count, BigDecimal sum, Double min, Double max) {
		super();
		this.count = count;
		this.sum = sum;
		this.min = min;
		this.max = max;
		this.creationDate = new Date();
	}	

	/**
	 * @return The number of times that a value was put in this Counter
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * @return the sum of all values put in the Counter
	 */
	public BigDecimal getSum() {
		return sum;
	}

	/**
	 * @return the minimum value ever put in the Counter
	 */
	public Double getMin() {
		return min;
	}

	/**
	 * @return the maximum value ever put in the Counter
	 */
	public Double getMax() {
		return max;
	}	
	
	/**
	 * @return the creationDate when this CounterData was created
	 */
	public Date date() {
		return creationDate;
	}

	@Override
	public String toString() {
		return "CounterData [count=" + count + ", sum=" + sum + ", min=" + min + ", max=" + max + ", creationDate="
				+ creationDate + "]";
	}
	
	
}
