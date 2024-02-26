FROM openjdk:11-jre-alpine

WORKDIR /wiremock

# Copy WireMock standalone JAR and your main JAR with extensions to the container
COPY wiremock-standalone-3.3.1.jar .
COPY WiremockDocker.main.jar .

# Copy your custom extension JAR to the container
COPY your-extension.jar .

# Run WireMock with the desired classpath and extension
CMD ["java", "-cp", "wiremock-standalone-3.3.1.jar:WiremockDocker.main.jar:your-extension.jar", "wiremock.Run", "--extensions", "com.wiremockExt.JsonFileResponseValidator"]
