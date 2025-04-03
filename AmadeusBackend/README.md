# Amadeus Flight App Backend

## Overview
This is the backend service for the Amadeus Flight App, built using **Spring Boot** to provide a RESTful API for managing flights.
The backend handles the business logic such as filtering, sorting and pagination, but also the connection with the Amadeus API, managing access token and creating requests to obtain information that will be displayed by the frontend. 

## Features
- REST API to manage the connection with Amadeus API
- Filtering and sorting capabilities
- Pagination support
- Unit tests with **Junit** and **Mockito**
- Architecture based on different layers: Controller, service and repository

## Technologies used
- **Spring Boot:** Framework for building the REST API
- **Spring Web:** Dependency that provides tools for handling HTTP requests
- **Spring validation:** Enables validation of user input
- **Junit:** For writing and running unit tests
- **Mockito:** For mocking dependencies in tests

## Prerequisites
Before running the project, make sure you have **JDK 17 or higher** installed.
This project was developed using JDK v17.0.1

### Check if JDK is installed:
```bash
java -version
```

## Running the backend
1. Install dependencies and build the project
```bash
mvn clean install
```
2. Run the application
```bash
mvn spring-boot:run
```
3. The backend will be available at:
```bash
http://localhost:8080
```

## How to set up your API secrets 

For Linux and MacOS we can use the terminal to define the environment variables.
If you want just for temporal session:

```bash
export API_SECRET_KEY="your_api_key"
```

If you want to store it permanently, we can added to ~./bashrc

```bash
export API_SECRET_KEY="your_api_key" >>  ~./bashrc
source  ~./bashrc
```

To check if the variable was successfully saved, you can run

```bash
echo $API_SECRET_KEY
```

Now in your program we can use the following command to obtain the value 

```bash
System.getenv("API_SECRET_KEY");
```

## For creating the image that will be used in the docker-compose.yaml
1. Check if Docker is installed:
```bash
docker -v
```
2. Check if Docker engine is running
```bash
docker ps
```
3. Build the image 
```bash
docker build -t amadeus-backend-image .
```
