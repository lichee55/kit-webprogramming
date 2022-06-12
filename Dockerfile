FROM openjdk:11
EXPOSE 8080
ADD build/libs/kumovie-0.0.1-SNAPSHOT.jar kumovie.jar
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "kumovie.jar"]