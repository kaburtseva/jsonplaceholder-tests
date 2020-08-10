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
![Screenshot 2020-08-09 at 22 44 54](https://user-images.githubusercontent.com/8155318/89740527-060fca80-da92-11ea-9852-e9773ec8be8a.png)

# TODO
* add BDD support (e.g Cucumber)
* extend existing coverage
* add Spring
* add more detailed logging by @Slf4j

# Troubleshooting

- make sure that JDK at least java8
- clean gradle daemon
```
gradle --stop
```
- invalidate cache and restart
- try 
```
gradle build
```

# CircleCI run results

Please note: build failed such as there is a bug in jsonplaceholder.

![Screenshot 2020-08-09 at 22 58 54](https://user-images.githubusercontent.com/8155318/89755849-7c8be700-dae9-11ea-92e0-65b29d49b4c4.png)
![Screenshot 2020-08-09 at 22 59 07](https://user-images.githubusercontent.com/8155318/89755852-7eee4100-dae9-11ea-86e2-927af37969d1.png)




