FROM openjdk:11-jre-slim
COPY build/libs/task_management-0.0.1-SNAPSHOT.jar task_management-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-DSpring.profiles.active=prod", "-jar", "task_management-0.0.1-SNAPSHOT.jar"]