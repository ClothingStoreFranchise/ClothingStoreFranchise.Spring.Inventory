FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 8084

ADD target/inventory.jar inventory.jar

ENTRYPOINT ["java", "-jar", "inventory.jar"]