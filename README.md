## Route Finder
This API finds land route for the provided start and destination countries using _Breadth First Search_ algorithm.
The application exposes REST endpoint: 
```
/routing/{origin}/{destination} 
```
that returns a list of border crossings to get from origin to destination.

Countries are identified by *cca3* field in country data:
```
https://raw.githubusercontent.com/mledoze/countries/master/countries.json
```

### Prerequisites
- JDK 11
- Maven


### Technology stack:
* Spring Boot


### Starting application
The application uses Spring Boot so it is easy to run.  You can start it any of a few ways:

- Run the main method from `RouteCalculatorApplication`
- Use the Maven Spring Boot plugin: `mvn spring-boot:run`

