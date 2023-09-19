FROM openjdk:17-jdk-slim-buster as BUILD

WORKDIR /app

COPY ./target/rinha-backend-1.0.0.jar ./rinha.jar

#COPY src /app/src
#COPY pom.xml /app
#COPY .mvn /app/.mvn
#COPY mvnw /app/mvnw

#RUN ./mvnw clean package -DskipTests

#COPY --from=BUILD /app/target/rinha-backend-1.0.0.jar ./rinha.jar

EXPOSE 8080

CMD ["java", "-jar", "./rinha.jar"]