FROM maven:3.6.3-adoptopenjdk-14 AS package
COPY src /usr/src/application/src
COPY pom.xml /usr/src/application
RUN mvn -f /usr/src/application/pom.xml package

FROM adoptopenjdk:14-jre-hotspot as builder
WORKDIR application
COPY --from=package /usr/src/application/target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk:14-jre-hotspot
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

ENTRYPOINT ["java","-XX:+UseContainerSupport", "org.springframework.boot.loader.JarLauncher"]
