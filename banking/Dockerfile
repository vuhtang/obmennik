FROM gradle:jdk17 as build
COPY . /sources
WORKDIR /sources
RUN gradle clean bootJar

FROM openjdk:17-jdk as runner
WORKDIR /app
COPY --from=build /sources/build/libs/* .
CMD java -jar "banking-0.0.1-SNAPSHOT.jar"