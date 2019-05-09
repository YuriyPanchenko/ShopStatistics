### Stack of technologies: 
* Java 8 + Spring Boot
* MySQL (v5.1.38)
* FreeMarker
* CSS (Bootstrap 4)


### Building and running locally:
1. Change username and password (optionally datasource url) in application.properties
2. *[Optional]* Run unit tests with `mvn test`
3. Start the application with `mvn spring-boot:run` (exploded form) or `mvn install && java -jar target/shop-1.0-SNAPSHOT.jar` (build & run separately)


### Accessing on Heroku:
An application instance is already deployed on [Heroku](https://www.heroku.com). You can access it here: https://shopstatistic.herokuapp.com.

### Available views:
* __Home page__: you can add new goods here
* __Navbar__: navigate between all existing views
    * __All goods__: nuff said
    * __Find goods__: find by date, you can click *"Delete all"* button to clear all records for the selected date
    * __Total profit__: get profit in selected currency on some date
 
