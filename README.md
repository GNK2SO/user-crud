## Overview

Simple CRUD application using mainly Java EE, JPA and JSP.

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
localhost:8085/manager/
```

For run only the tests:
```sh
mvn test
```
