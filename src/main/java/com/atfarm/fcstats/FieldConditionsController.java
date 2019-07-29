package com.atfarm.fcstats;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atfarm.fcstats.statistics.Statistics;
import com.atfarm.fcstats.statistics.StatisticsService;

@RestController
@RequestMapping("/field-conditions")
public class FieldConditionsController {

		private StatisticsService statisticsService;
		
		@Autowired
		public FieldConditionsController(StatisticsService statisticsService) {
			this.statisticsService = statisticsService;
		}
		
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<Object> input(@Valid @RequestBody FieldConditionEvent fieldConditionEvent){
			ResponseEntity<Object> response;
			
			Map<String, Double> conditions = new HashMap<String, Double>();
			conditions.put("vegetation", fieldConditionEvent.getVegetation());
			
			statisticsService.input(conditions, fieldConditionEvent.getOccurrenceAt());
			
			response = ResponseEntity.ok().build();
			
			return response;
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/v2")
		public ResponseEntity<Object> inputV2(@Valid @RequestBody FieldConditionEventV2 fieldConditionEventV2){
			ResponseEntity<Object> response;
					
			statisticsService.input(fieldConditionEventV2.getConditions(), fieldConditionEventV2.getOccurrenceAt());
			
			response = ResponseEntity.ok().build();
			
			return response;
		}		
		
		@RequestMapping(method = RequestMethod.GET)		
		public Map<String, Statistics> getStatistics() {
			return statisticsService.getStatistics();			
		}
}
