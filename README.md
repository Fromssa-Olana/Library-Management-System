   # Project Overview

# Introduction 
A library management system is a piece of software to manage a small library whose functions are limited to that of lending books to its members, receiving them back, doing the associated operations such as querying, registering members, keep track of transactions. For now, the system will be managed by a librarian clerk and it will have only one user. 

# Tools and freamwork
- It is developed using Vaadin application for front end and Spring Boot for the backend.
- It contains all the necessary configuration and files to get started.
- The project is a standard Maven project, so it can imported to an IDE of choice.

## Running the Application
There are two ways to run the application:  
 - To run from the command line, use `mvn` and open [http://localhost:8080](http://localhost:8080) in your browser.
 - Another way is to  run the `Application` class directly from an IDE.


## Project structure

- `MainView.java` in `src/main/java` contains the navigation setup. 
- `views` package in `src/main/java` contains the server-side Java views of the application.
- `views` folder in `frontend/src/` contains the client-side JavaScript views of your application.

