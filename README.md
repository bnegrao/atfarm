# Atfarm Field Condition Statistics API

#### Running the application

    mvn spring-boot:run

The API will be listening from http://localhost:8080

#### Running the tests

To run all the tests:

    mvn clean verify

**Warning**: the test file *ProofOfConceptTest.java* takes **2 minutes to complete**.  
 
If you to run all the tests but that one use the command bellow:

    mvn test -Dtest=!ProofOfConceptTest
    
#### Testing the API with Postman
    
If you use [Postman](https://www.getpostman.com/) to test REST APIs you can import the collection file
*atFarmStatistics.postman_collection.json* that is available in the root folder of this project. 
It contains examples of all the requests accepted by this API.

#### Configuring the API

This API is able to configure the period relevant for statistics calculation. The options are:

**periodType**: DAY, HOUR, MINUTE, SECOND  
**periodSize**: a positive integer.  

These settings are made on the file *src/main/resources/applications.properties*, like in the example bellow:

    periodType=DAY
    periodSize=30
    
#### API METHODS

##### POST /field-conditions

Request body:

	{
		"occurrenceAt": "2019-07-28T23:01:43+0000",
		"vegetation": 1.001
	}
	
Response HTTP status codes:

- 200: the request was processed successfully
- 400: Bad request due to validation errors.

##### POST /field-conditions/v2

This offers a more flexible interface via the *conditions* object that accepts any pair of String->Double properties.

Request body:

	{
		"occurrenceAt": "2019-07-27T23:01:43Z",
		"conditions":{
			"nitrogen": 2,
			"carbon": 0.001
		}
	}

Response HTTP status codes:

- 200: the request was processed successfully
- 400: Bad request due to validation errors.	


##### GET /field-conditions

Response content-type: application/json

Response body:

If there are no available statistics for the current period the response body will be an empty json object.

When there are statistics available the json returned is a sorted list of field condition property names, each followed by its corresponding statistics objects:

	{
	    "carbon": {
	        "min": 0.001,
	        "max": 0.001,
	        "avg": 0.001
	    },
	    "nitrogen": {
	        "min": 2,
	        "max": 2,
	        "avg": 2
	    },
	    "vegetation": {
	        "min": 1.001,
	        "max": 1.001,
	        "avg": 1.001
	    }
	}

Response HTTP status code: 200

##### IMPORTANT

To interpret the statistics results it's important to note is that if the Period chosen is '1 DAY', the statistics returned are only from the current day, and not from the last 24 hours. You could set the Period to '24 HOUR's to get the statistics from the last 24 hours.

The bottom line is, if the periodSize is 1, the statistics are from the current period type, i.e., the current DAY, HOUR, MINUTE or SECOND.

So for a period of 30 days, the statistics reflect the data from the current day and the 29 days before.

#### Implementation notes

The class that implements the main algorithm is the *TemporalCounter*. 

That class provides two public methods:  

- *void put(Double value, Date creationDate)*: adds a double value into the internal counter structure
- *TemporalCounterData getTemporalCounterData()*: sums up the data relevant for the period and returns it in a *TemporalCounterData* object.
That calculation is done in O(1) speed.

The execution time of *getTemporalCounterData()* does not grow with the amount of put() calls, instead, it grows according to the Period's size.

For a Period of 30 DAYs, the size is '30', so *getTemporalCounterData()* will execute in O(30), which can be considered as O(1) since it is a constant execution time, independent from N size. For a Period of 4 SECONDs, the execution time is O(4), and so on.

 


