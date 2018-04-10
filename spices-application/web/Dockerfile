FROM openjdk:8u151-jre-alpine
MAINTAINER Bill Soumakis

WORKDIR /var/spices-catalogue

ADD target/catalogue-1.0-SNAPSHOT.jar /var/spices-catalogue/catalogue.jar
ADD src/main/resources/local.yml /var/spices-catalogue/config.yml

EXPOSE 8990 8991

ENTRYPOINT ["java", "-jar", "catalogue.jar", "server", "config.yml"]