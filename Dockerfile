FROM openjdk

WORKDIR /app

COPY build/libs/obmennik-1.0-SNAPSHOT-plain.jar obmennik.jar

CMD ["java", "-jar", "obmennik.jar"]