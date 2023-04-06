# Grocery List Demo Application

## What it does
Provides a simple RESTful API that allows to create a grocery list, then add the ingredients of meals to it.
Meals can be specified by name and https://www.themealdb.com is used to search for the meal and look up its ingredients.
Then, individual ingredients can be crossed off the list (while going through the grocery store).

## Requirements
* Java 17+ JDK
* Docker & Docker Compose 20+

## Dev Setup
1. Run `docker-compose up` to spin up a local MySQL 8 container.
2. Run the Spring Boot application in profile `dev`:
   * either in the IDE or
   * using `mvn package`, then `java -jar target/grocery-list-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev`
3. The application will connect to the container db and use the real public https://www.themealdb.com API endpoint.

## Test Approach
There is a "system test suite" with a focus on realistic end-to-end testing:
* The Spring Boot application is started based on a real Tomcat HTTP server, but in profile `test`
* Based on https://www.testcontainers.org/, a real MySQL 8 is started in a container and used as data source
* https://wiremock.org/ is used to spin up a real HTTP server and fake the https://www.themealdb.com endpoints

## Possible Improvements
* Parse the "measure" fields into numerical amount and unit in order to sum them sup
* Allow to specify "number of servings" when adding a meal
