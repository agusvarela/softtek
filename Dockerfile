FROM openjdk:17
WORKDIR /app
COPY build/libs/softtek-0.0.1-SNAPSHOT.jar softtek.jar
CMD ["java", "-jar", "softtek.jar"]
EXPOSE 8080
