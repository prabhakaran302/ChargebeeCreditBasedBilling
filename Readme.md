# BillingApplication

## Features

- **Dynamic Service Configuration**: Add services with associated costs.
- **Package Management**: Configure packages with predefined plans and costs.
- **Purchase Handling**: Customers can subscribe to packages.
- **Usage Tracking**: Record usage of services by customers.
- **Transaction Summary**: Generate and export summaries of all transactions.

## Prerequisites

To run this application, ensure you have the following installed:

- **Java 8 or later**
- **Maven** (for dependency management and build)

## Installation

1. Clone this repository:
   ```bash
   git clone <repository-url>
   cd BillingApplication
   ```
2. Build the application using Maven:
   ```bash
   mvn clean install
   ```

## Usage

1. Run the application:
   ```bash
   java -jar target/BillingApplication.jar
   ```
2. Follow the prompts:

    - Enter services and their costs in the format:
      ```
      services=<service_name>:<cost>, e.g. services=S1:1,S2:2,S3:3
      ```

    - Enter packages and their details in the format:
      ```
      packages=<plan>:<name>:<quantity>:<cost>, e.g. packages=Basic:100:100.00,Standard:250:225.00,Premium:500:450.00
      ```

    - Choose an option:
        - `1` for customer purchases: Enter purchase details as:
          ```
          <customer>,<plan>, e.g. C1,Standard
          ```
        - `2` for usage tracking: Enter usage details as:
          ```
          <customer>,<service>,<quantity>, e.g. C1,S1,5
          ```

3. The application generates a transaction summary and writes it to:
   ```
   src/main/resources/output.txt
   ```

## File Structure

```
src/main/java/com/upwork
├── model       # Data models (Customer, Package, Purchase, Service, Usage)
├── service     # Core services (BillingService, CustomerService)
├── util        # Utility classes (FileUtil, Parser)
└── BillingApplication.java # Main application logic
```

## Example Workflow

1. Start the application.
2. Define services:
   ```
   services=S1:1,S2:2,S3:3
   ```
3. Define packages:
   ```
   packages=Basic:100:100.00,Standard:250:225.00,Premium:500:450.00
   ```
4. Record a purchase:
   ```
   C1,Standard
   ```
5. Record service usage:
   ```
   C1,S1,5
   ```
6. Check the generated transaction summary in `src/main/resources/output.txt`.

## Key Classes

- **`BillingApplication`**: Entry point of the application.
- **`CustomerService`**: Manages customer data.
- **`BillingService`**: Processes purchases and usage, generates transaction summaries.
- **`FileUtil`**: Handles file operations.
- **`Parser`**: Parses input strings into application models.