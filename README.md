## Overview

Simple CRUD application using Java EE, JPA and JSP.

## AWS URL
```sh
http://ec2-15-228-39-249.sa-east-1.compute.amazonaws.com:8080/manager/
```

### Running on docker

first generate the war file
```sh
mvn clean package
```

up the container
```sh
sudo docker-compose up -d
```

the application will be available on
```sh
http://localhost:8080/manager/
```

For run only the tests:
```sh
mvn test
```
