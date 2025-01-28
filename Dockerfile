FROM gradle:jdk17 as build
COPY . /sources
WORKDIR /sources
RUN gradle clean bootJar

FROM openjdk:17-jdk as runner
WORKDIR /app
COPY --from=build /sources/build/libs/* .
CMD java -jar "obmennik-1.0-SNAPSHOT.jar"