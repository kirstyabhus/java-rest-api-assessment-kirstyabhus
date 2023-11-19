# Multi Investment Portfolio Manager API
## Description
A Investment Portfolio Manager API powered by Spring Boot. This appllication allows the management of multiple portfolios each containing multiple investments

## Key Features:
- **Portfolio and Investment Management API Operations**: Users perform actions for both portfolios and investments. Operations for portfolios include creating, reading, updating, deleting and sorting portfolios. Users can also move investments between portfolios. For Investments, users can perform create, read, update, delete and sort operations. Investment operations also include filtering (by name, symbol, totalValue, purchasePrice, currentPrice).
- **Automatic calculations**: The total value of investments are automatically calculated on insertion into the portfolio. Additionally, the total value of the portfolio is calculated from the total of the investments, regardless of whether investments are added or deleted, the total value will always be up-to-date.

## Documentation
The API documentation has been generated using Swagger and is accessible at http://localhost:8080/swagger-ui/index.html once the application is running. To access this documentation, the application must be running on port 8080.
The Swagger API documentation shows the available endpoints (and their usage), request parameters, response formats, and error codes.

## Installation
### 

## Running the Application
After installing, you can run the application using the following Maven command.

  ```bash
  mvn spring-boot:run
  ```

Upon running this command, the application can be accessed with the API endpoints defined.

![Swagger Documentation of Endpoints](/SwaggerDoc.PNG)
