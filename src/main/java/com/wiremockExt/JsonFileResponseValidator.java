package com.wiremockExt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.jayway.jsonpath.JsonPath;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import org.apache.commons.io.FileUtils;
//import com.github.tomakehurst.wiremock.http.ResponseDefinitionBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFileResponseValidator extends ResponseDefinitionTransformer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
        String originalBody = responseDefinition.getBodyFileName();
        System.out.println("resopnse body:"+originalBody);

        String bodyFileNameExpression = parameters.getString("bodyFileName", null);
        String responseBody="";


//        System.out.println("bodyfileNameExperssion:"+bodyFileNameExpression);
        if (bodyFileNameExpression == null) {
            return responseDefinition;
        }

        String requestBody = request.getBodyAsString();
//        System.out.println("requestbody:"+requestBody);
        String currentDirectory = Paths.get("").toAbsolutePath().toString();
        String bodyFileName = evaluateBodyFileNameExpression(requestBody, bodyFileNameExpression);
        System.out.println("bodyfilename:"+bodyFileName);
//        byte[] responseBodyBytes = files.getBinaryFileNamed(bodyFileName).readContents();
//
//        responseBody = new String(responseBodyBytes, StandardCharsets.UTF_8);
//        System.out.println("resonsebody:"+responseBody);

        if (bodyFileName == null) {
            return responseDefinition;
        }

//        String filePath = "__files/" + bodyFileName;
        String filePath = bodyFileName;
        Path file = Paths.get("__files/"+filePath);

        if (Files.exists(file)) {
            byte[] responseBodyBytes = files.getBinaryFileNamed(bodyFileName).readContents();

            responseBody = new String(responseBodyBytes, StandardCharsets.UTF_8);
//            System.out.println("resonsebody:"+responseBody);
            // JSON file exists, return the original response
//            return responseDefinition;
            return ResponseDefinitionBuilder.like(responseDefinition)
                    .withStatus(200)
                    .withBody(responseBody)
                    .withHeader("content", "application/json")
                    .build();
        }
        else {
            // JSON file does not exist, return a 400 status code with a custom response body
            return ResponseDefinitionBuilder
                    .like(responseDefinition)
                    .withStatus(400)
                    .withBody("{\"Error\": \"record not found\"}")
                    .build();
        }
    }

    private String evaluateBodyFileNameExpression(String requestBody, String expression) {
        Object extractedValue = JsonPath.read(requestBody, expression);

//            JsonNode jsonNode = objectMapper.readTree(requestBody);
        String firstName = extractedValue.toString(); //JsonPath.read(jsonNode, "firstname");
        System.out.println("evaulatebodyfile:"+firstName);
        return firstName + ".json";

    }

    @Override
    public String getName() {
        return "json-file-response-validator";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
