# RSO: Image Handling microservice

## Prerequisites

```bash
docker run -d --name pg-image-data -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=image-data -p 65432:5432 postgres:13
```

#### network rso:
```bash
docker run -d --name pg-image-data -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=image-data -p 65432:5432 --network rso postgres:13
docker run -p 8080:8080 --network rso -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-image-data:5432/image-data imghandling2
```