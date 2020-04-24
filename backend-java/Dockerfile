#build
FROM maven:3-openjdk-14 as backend-builder
COPY pom.xml /app/
COPY src /app/src/
WORKDIR /app/
RUN mvn package

#run
FROM openjdk:14-alpine
COPY --from=backend-builder /app/target/wms-app*.jar ./wms-app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/wms-app.jar"]
HEALTHCHECK --interval=1m --timeout=3s CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

