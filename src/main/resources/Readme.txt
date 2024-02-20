To run wiremock in standalone + extensions along with classpath
java -cp "wiremock-standalone-3.3.1.jar:/Users/sreeni/IdeaProjects/Wiremock/out/artifacts/RandomResponseExtension.main.jar" wiremock.Run --extensions com.example.wiremock.extensions.JsonFileResponseValidator


