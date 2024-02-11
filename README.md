# Real Estate Management System

This project is a real estate management system that allows property owners to list their properties and customers to place offers on them. The system also includes user management, property management, search and filter functionality, and an admin dashboard.

## Prerequisites

- Java 11 or higher
- Maven
- in-memory database (H2)
- An IDE (like IntelliJ IDEA)

## Getting Started

1. Clone the repository to your local machine using `https://github.com/WAA-Group-9/real-estate-manager-back-end.git`.
2. Open the project in your IDE.
3. Update the `application.properties` file with your database connection details. If you're using an in-memory database like H2, make sure to set the `spring.datasource.url` to `jdbc:h2:mem:testdb`.

## Running the Application

1. Navigate to the root directory of the project in your terminal.
2. Run `mvn clean install` to build the project and install dependencies.
3. Run `mvn spring-boot:run` to start the application.

The application will start running at `http://localhost:8080`.

## Using the Application

Refer to the API endpoints and system flow mentioned in the previous section.

## Testing

Run `mvn test` in the project directory to run the unit tests.

## Documentation

API documentation is available at `https://documenter.getpostman.com/view/31257683/2sA2r3YRLr`.


## Data Directory

The data directory contains the in-memory database file when using an in-memory database like H2. This file is usually located in the root directory of the project and is named `testdb.mv.db`. Please note that this file is temporary and will be deleted when the application stops running.