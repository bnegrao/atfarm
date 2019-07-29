package com.atfarm.fcstats.statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.atfarm.fcstats.counter.CounterData;

public class Statistics {
	
	private double min;
	private double max;
	private double avg;
	
	public Statistics(CounterData counterData) {
		this.min = counterData.getMin();
		this.max = counterData.getMax();
		this.avg = avg(counterData.getSum(), counterData.getCount());
	}
	
	
	private static double avg(BigDecimal sum, Long count) {
		if (sum.equals(BigDecimal.ZERO) || count == 0) {
			return BigDecimal.ZERO.doubleValue();
		}
		
		BigDecimal countBigD = new BigDecimal(count.toString());
		BigDecimal avg = sum.divide(countBigD, RoundingMode.HALF_EVEN);
		
		return avg.doubleValue();
	}


	public Statistics(double min, double max, double avg) {
		super();
		this.min = min;
		this.max = max;
		this.avg = avg;
	}
	public double getMin() {
		return min;
	}
	public double getMax() {
		return max;
	}
	public double getAvg() {
		return avg;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(avg);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(max);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(min);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statistics other = (Statistics) obj;
		if (Double.doubleToLongBits(avg) != Double.doubleToLongBits(other.avg))
			return false;
		if (Double.doubleToLongBits(max) != Double.doubleToLongBits(other.max))
			return false;
		if (Double.doubleToLongBits(min) != Double.doubleToLongBits(other.min))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Statistics [min=" + min + ", max=" + max + ", avg=" + avg + "]";
	}
	
	

}
