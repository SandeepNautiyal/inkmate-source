# Start with a base image containing Java runtime
FROM ubuntu:17.10

RUN apt-get update

# Add a volume pointing to /tmp
VOLUME /tmp

# The application's jar file
ARG JAR_FILE=target/inkmate-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} inkmate-0.0.1-SNAPSHOT.jar

ADD . /target

RUN apt-get install curl -y openjdk-8-jdk

WORKDIR .

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/inkmate-0.0.1-SNAPSHOT.jar"]
