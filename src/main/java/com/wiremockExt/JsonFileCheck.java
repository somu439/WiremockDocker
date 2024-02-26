package com.wiremockExt;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JsonFileCheck extends ResponseDefinitionTransformer {

    //    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
        String firstName = extractFirstNameFromRequestBody(request);
        System.out.println("firstname"+firstName);
        String filePath = "__files/" + firstName + ".json";
        Path file = Paths.get(filePath);
        System.out.println("Path"+file);

        if (Files.exists(file)) {
            System.out.println("file found");
            // JSON file exists, return the original response
//            return responseDefinition;
            return ResponseDefinitionBuilder.like(responseDefinition)
                    .withStatus(200)
                    .withBodyFile( firstName+".json")
                    .withHeader("content", "application/json")
                    .build();
        } else {
            System.out.println("file not found");
            // JSON file does not exist, return a 400 status code with a custom response body
//            return ResponseDefinitionBuilder
            return ResponseDefinitionBuilder.like(responseDefinition)
//                    .like(responseDefinition)
                    .withStatus(400)
                    .withBody("{\"status\": \"Error\", \"message\": \"File not found\"}")
                    .build();
        }
    }

    private String extractFirstNameFromRequestBody(Request request) {
        String body = request.getBodyAsString();

        // Assuming the request body is in JSON format and contains a "firstname" field
        // Parse the JSON body and extract the value of the "firstname" field
        // Adjust this logic according to the actual format of your request body
        // Here's a simple example assuming the body is {"firstname":"John","lastname":"Doe"}
        Map<String, String> jsonBody = parseJsonBody(body);
        System.out.println("jsonbody:"+jsonBody);
        return jsonBody.get("firstname");
    }

    private Map<String, String> parseJsonBody(String body) {
        // Implement JSON parsing logic here
        // This is a simplified example and might not work for all JSON formats
        // You can use libraries like Jackson or Gson for robust JSON parsing
        // For simplicity, this example assumes a simple JSON format without nested objects or arrays
        // Adjust this logic based on the actual structure of your JSON body
        // This example assumes the body is in the format {"key":"value","key2":"value2",...}
        // Splitting by comma and then by colon to extract key-value pairs
        String[] keyValuePairs = body.split(",");
        Map<String, String> jsonBody = new HashMap<>();
        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            // Removing quotes and extra spaces from keys and values
            String key = entry[0].trim().replaceAll("\"", "");
            String value = entry[1].trim().replaceAll("\"", "");
            jsonBody.put(key, value);
        }
        return jsonBody;
    }

    //    @Override
    public String getName() {
        return "json-file-check";
    }

    //    @Override
    public boolean applyGlobally() {
        return false;
    }
//public ResponseDefinition transform(Request request, ResponseDefinition rd,
//                                    FileSource fileSource, Parameters parameters) {
//
//    if (rd.getBodyFileName().startsWith("{{")) {
//        return new ResponseDefinitionBuilder()
//                .withBody("this is atest")
//                .(request.getUrl().substring(1))
//                .withStatus(rd.getStatus())
//                .withHeaders(rd.getHeaders())
//                .withTransformers(
//                        rd.getTransformers().toArray(new String[rd.getTransformers().size()]))
//                .build();
//    }
//
//    return rd;
//}
//
//    public String getName() {
//        return "BodyFileNameTransformer";
//    }
}
