# spices-catalogue-microservice

### Commands for docker

1. mvn clean package
2. docker build -t catalogue ./spices-application/web/
3. docker-compose up

### Commands to execute database integration tests

mvn clean -P integration-test verify

If we want to start Postgresql for a fast feedback while running the tests
from the IDE, we can execute mvn docker:start.

### Commands to execute functional tests

mvn clean -P functional-test verify

If we want a fast feedback while executing our functional tests from the
IDE we can execute docker-compose up.