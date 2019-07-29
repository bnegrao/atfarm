package com.atfarm.fcstats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.atfarm.fcstats.counter.Period;
import com.atfarm.fcstats.persistence.PersistenceService;
import com.atfarm.fcstats.statistics.StatisticsService;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class AtFarmStatisticsApplication {
	
	@Value("${periodType}")
	private Period.Type periodType;
	
	@Value("${periodSize}")
	private Integer periodSize;
	
	public static void main(String[] args) {			
		SpringApplication.run(AtFarmStatisticsApplication.class, args);
	}
	
	@Bean 
	public StatisticsService statisticsService() {
		return new StatisticsService(new Period(periodType, periodSize));
	}

	@Bean
	public PersistenceService persistenceService() {
		return new PersistenceService();
	}
}
