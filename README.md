#Environment
* Java 8+
* Gradle

#Additional plugins 
* Lombok
* Rest-assured
* TestNG
* Allure
* AssertJ

#Execution
* locally via IDE
* using Gradle
```
gradle test
```

#How to generate report
Get test report after test execution
will be located here jsonplaceholder-tests/api-tests/build/reports/allure-report

```
 gradle allureReport 
```
 or 
 ```
 allure serve allure-results
```

#TODO
* add BDD support (e.g Cucumber)
* extend existing coverage

#Troubleshoot

Tests cannot execute 
- make sure that JDK at least java8
- clean gradle daemon
```
gradle --stop
```
- invalidate cache and restart
- try 
```gradle build
```

