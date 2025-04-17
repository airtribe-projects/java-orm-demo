# Use an official OpenJDK runtime as a parent image
FROM localhost:5000/gw-base-image-java-21:gw-la-10

# Set the working directory in the container
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

# Copy the packaged jar file into the container
COPY server/target/server-0.0.1.jar app.jar
# Make port 9011 available to the world outside this container
EXPOSE 9011

ENV APP_JAR_FILE="app.jar"

# ENTRYPOINT ["java", "-jar", "/app/app.jar"]
ENTRYPOINT ["/startv2.sh"]