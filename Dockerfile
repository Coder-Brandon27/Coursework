FROM amazoncorretto:17
COPY ./target/devops-0.1.0.3-jar-with-dependencies.jar /tmp
ENTRYPOINT ["java", "-jar", "devops-0.1.0.3-jar-with-dependencies.jar", "db:3306", "30000"]