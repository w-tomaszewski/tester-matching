##Requirements:
````
JDK 8
Maven 3.6.2
Node 12.14.0
````

##How to install locally
````
cd <app_folder>
mvn clean verify
````

##How to run

Backend
````
cd <app_folder>
java -jar target/tester-matching-1.0.0.jar
````
Frontend
````
cd <app_folder>/src/main/javascript/dist
ng serve
````

##How to build using Docker

````
docker build -t frontend-image -f docker/Dockerfile-frontend ./
docker build -t backend-image -f docker/Dockerfile-backend ./
````

##How to run using Docker

````
docker run --rm -it -p 4200:80 frontend-image
docker run --rm -it -p 8080:8080 backend-image
````

Open browser and enter URL: http://localhost:4200


