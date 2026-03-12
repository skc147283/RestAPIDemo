# RestAPIDemo

Demo project for REST Assured API Test Automation using Java, Maven, and TestNG.

## Overview

This project demonstrates how to write and run automated REST API tests using the
[REST Assured](https://rest-assured.io/) library against the public
[JSONPlaceholder](https://jsonplaceholder.typicode.com) fake API.

It covers the four main HTTP operations:

| HTTP Method | Endpoint          | Test Class            |
|-------------|-------------------|-----------------------|
| GET         | `/posts`          | `GetRequestTest`      |
| POST        | `/posts`          | `PostRequestTest`     |
| PUT / PATCH | `/posts/{id}`     | `PutRequestTest`      |
| DELETE      | `/posts/{id}`     | `DeleteRequestTest`   |

## Project Structure

```
RestAPIDemo/
├── pom.xml                        # Maven build configuration
├── testng.xml                     # TestNG test suite definition
└── src/
    └── test/
        └── java/
            └── com/restapi/demo/
                ├── base/
                │   └── BaseTest.java          # Shared request specification
                ├── pojo/
                │   └── Post.java              # POJO for JSON deserialization
                └── tests/
                    ├── GetRequestTest.java    # GET request tests
                    ├── PostRequestTest.java   # POST request tests
                    ├── PutRequestTest.java    # PUT/PATCH request tests
                    └── DeleteRequestTest.java # DELETE request tests
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Running the Tests

```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=GetRequestTest

# Run a specific test method
mvn test -Dtest=GetRequestTest#testGetPostById
```

## Dependencies

| Library           | Version | Purpose                          |
|-------------------|---------|----------------------------------|
| REST Assured      | 5.3.2   | API testing library              |
| TestNG            | 7.8.0   | Test framework                   |
| Jackson Databind  | 2.15.2  | JSON serialization/deserialization|
| Hamcrest          | 2.2     | Assertion matchers               |

## Key Concepts Demonstrated

- **Request Specification**: Centralized configuration via `RequestSpecBuilder` in `BaseTest`
- **Response Validation**: Status codes, headers, and JSON body assertions using `then()` chaining
- **JSON Deserialization**: Mapping API responses to POJOs with Jackson
- **Query Parameters**: Filtering resources with `queryParam()`
- **Path Parameters**: Accessing specific resources with `{id}` placeholders
- **Request Body**: Sending JSON payloads with POST and PUT requests
- **Response Time Validation**: Asserting API performance thresholds

