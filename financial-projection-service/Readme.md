# Financial Projection App

## Overview

The Financial Projection App is designed to help analyzing the financial data and generate future projections. 
## Features

- **Upload Financial Data**: Users can upload their financial data in Excel format, making it easy to get started without manual data entry.
- **Future Projections**: The app calculates future financial projections based on historical data and specified growth rates. Users can specify how many months into the future they want predictions.
- **Customizable Output**: Users can receive output in a structured format, allowing them to easily interpret financial data and projections.

## How It Works

1. **Data Upload**: Users upload an Excel file containing their financial data. The app reads the data, including historical sales, expenses, and other relevant financial metrics.

2. **Data Parsing**: The app scans the uploaded Excel sheet to identify key financial categories and their corresponding values. It handles variations in data structure, such as empty rows or different formats.

3. **Calculating Projections**: Based on user-defined growth rates, the app calculates future projections for specified categories (e.g., Product Sales, Service Sales, Total Sales, Operating Expenses). Users can specify how many months into the future they want to see projections.

4. **Output Generation**: The app generates a structured JSON output that includes the calculated projections, formatted in a way that is easy to interpret.

## Technologies Used

- **Java**: The core programming language used for the application logic.
- **Spring Boot**: Framework for building the RESTful API and managing application components.
- **Apache POI**: Library for reading and writing Excel files, enabling seamless data upload and processing.
- **Jackson**: Library for JSON serialization and deserialization, ensuring structured output.
- **Maven**: Build automation tool used for managing project dependencies.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven
- An IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/NarayananRangarajan27/financial-projection-service.git
   cd financial-projection-service
2. Build the project using mvn clean install
3. Run the application using mvn spring-boot:run
4. Access the application at http://localhost:8080/api/projections/analyze

### Testing

Please use the postman collection attached in the resources folder to test the api. The input to the api is the excel sheet and the growth rate in percentage for each cateogory