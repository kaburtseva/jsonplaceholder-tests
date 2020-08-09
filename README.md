# Environment
* Java 8+
* Gradle

# Additional plugins 
* Lombok
* Rest-assured
* TestNG
* Allure
* AssertJ

# Execution
* locally via IDE
* using Gradle
```
gradle test
```

# How to generate report
Get test report after test execution
will be located here jsonplaceholder-tests/api-tests/build/reports/allure-report

```
 gradle allureReport 
```
 or 
 ```
 allure serve allure-results
```
Latest report:
![Screenshot 2020-08-09 at 22 14 34](https://user-images.githubusercontent.com/8155318/89740434-4e7ab880-da91-11ea-83e2-52b736ec7cfe.png)
![Screenshot 2020-08-09 at 22 15 07](https://user-images.githubusercontent.com/8155318/89740437-5175a900-da91-11ea-88eb-afaf2a548c0d.png)

# TODO
* add BDD support (e.g Cucumber)
* extend existing coverage

# Troubleshooting

- make sure that JDK at least java8
- clean gradle daemon
```
gradle --stop
```
- invalidate cache and restart
- try 
```gradle build
```


