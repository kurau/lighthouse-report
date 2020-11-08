FROM openjdk:8-jdk-alpine
WORKDIR /home
COPY /target/lighthouse-report-1.0-SNAPSHOT-spring-boot.jar report.jar
COPY src/main/resources/templates templates
ENTRYPOINT ["java", "-jar", "report.jar"]