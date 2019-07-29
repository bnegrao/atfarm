package com.atfarm.fcstats.counter;

import java.math.BigDecimal;

public class TemporalCounterData extends CounterData {
	
	private int numberOfCountersUsed;
	
	private String oldestCounterDateKey;
	
	private String youngestCounterDateKey;
	
	private Period period;
	
	TemporalCounterData(){
		super(0l, BigDecimal.ZERO, 0d, 0d);
		numberOfCountersUsed = 0;
	}

	TemporalCounterData(CounterData cd) {
		super(cd.count, cd.sum, cd.min, cd.max);
	}	

	TemporalCounterData(Long count, BigDecimal sum, Double min, Double max, int nCounters,
			String firstCounterDateKey, String lastCounterDateKey, Period period) {
		super(count, sum, min, max);
		this.numberOfCountersUsed = nCounters;
		this.oldestCounterDateKey = firstCounterDateKey;
		this.youngestCounterDateKey = lastCounterDateKey;
		this.period = period;
	}

	/**
	 * The number of counters used to sum up the data of the period.
	 * @return
	 */
	public int getNumberOfCountersUsed() {
		return numberOfCountersUsed;
	}

	void setNumberOfCountersUsed(int nCounters) {
		this.numberOfCountersUsed = nCounters;
	}

	/**
	 * The date key associated with the oldest counter of the period.
	 * @return
	 */
	public String getOldestCounterDateKey() {
		return oldestCounterDateKey;
	}

	void setOldestCounterDateKey(String firstCounterDateKey) {
		this.oldestCounterDateKey = firstCounterDateKey;
	}

	/**
	 * The date key associated with the youngest (most recent) counter of the period.
	 * @return
	 */
	public String getYoungestCounterDateKey() {
		return youngestCounterDateKey;
	}
	
	void setYoungestCounterDateKey(String lastCounterDateKey) {
		this.youngestCounterDateKey = lastCounterDateKey;
	}

	/**
	 * The Period associated with the TemporalCounter
	 * @return
	 */
	public Period getPeriod() {
		return period;
	}

	void setPeriod(Period period) {
		this.period = period;
	}

	@Override
	public String toString() {
		return "TemporalCounterData [numberOfCountersUsed=" + numberOfCountersUsed + ", oldestCounterDateKey=" + oldestCounterDateKey
				+ ", youngestCounterDateKey=" + youngestCounterDateKey + ", period=" + period + ", count=" + count
				+ ", sum=" + sum + ", min=" + min + ", max=" + max + ", creationDate=" + creationDate + "]";
	}
	
	

}
