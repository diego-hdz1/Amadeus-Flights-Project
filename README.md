# Flight Search Application

## Overview
This project is a flight search application that consumes the Amadeus for Developers API to retrieve flight information. Users can enter their search criteria, and the application will dynamically load the results from the API

## Features
- **IATA Code Lookup**: If users don't know the IATA code of an airport, they can type part of the name, and the application will dynamically suggest results.
- **Pagination**: The application dislays a limited number of results per page to enhance usability and performance
- **Sorting Options**: Users can sort results based on price, flight duration or a combination of both.
- **Docker integration**: The project uses Docker to create images for both the frontend and backend. A Docker Compose configuration is included to facilitate communication and execution of both images.

  ## Technologies used

- **Backend** Java, Spring Boot, Junit, Mockito
- **Frontend** React, Typescript, React, Ant Design, Axios, Vitest, React Router DOM
- **Amadeus API**: Used to fetch flight information
- **Docker and Docker Compose**: For containerization and service orchestration

Follow the instructions in each subdirectory (backend and frontend to run the respective commands)

## Build ad start the application using Docker compose

```bash
docker-compose up
```

## Instalation and set up
1. Clone the repository:
```bash
git clone https://github.com/diego-hdz1/Amadeus-FlightsProject.git
```
