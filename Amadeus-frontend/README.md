# Amadeus Flight App Frontend

## Overview 
This is the frontend of the Amadeus Flight App, developed using **React** and **TypeScript**, powered by **Vite** for fast development and build performance. The frontend consumes the backend API and provides a responsive user interface to show all of the information from the backend. 

## Features
- Search flight options providing certain parameters
- Search airport codes dynamically from a specified keyword  
- Filter and sort flights dynamically
- Pagináte flights in order to improve user experience 
- Responsive UI using Ant Design
- Client-side routing with React Router DOM
- API integration using Axios

## Technologies used
- **Vite:** Fast build tool and development server for React
- **React:** Library for building the user interface
- **TypeScript:** Enhances JavaScript with static typing
- **Ant Desing:** UI component library for a polished and responsive design
- **Reacr Router DOM:** Handles client-side routing
- **Axios:** Manages HTTP requests to the backend API
- **Vitest:** Framework used for unit testing

## Prerequisites
Before running the project, make sure you have **Node.js** installed.
This project was developed using Node v22.12.0

### Check if Node.js is installed:
```bash
node -v
```

## Running the frontend
1. Install dependencies:
```bash
npm install
```
2. Run the application
```bash
npm run dev
```
3. The frontend will be available at:
```bash
http://localhost:5173
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
docker build -t amadeus-frontend-image .
```
