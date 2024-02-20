package com.example.wiremock.extensions;

//package com.example.wiremock.extensions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFileResponseValidator1 extends ResponseDefinitionTransformer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
        String bodyFileNameExpression = parameters.getString("bodyFileName", null);
        String responseBody = "";

        if (bodyFileNameExpression == null) {
            return responseDefinition;
        }

        String requestBody = request.getBodyAsString();
        String bodyFileName = "__files/" + evaluateBodyFileNameExpression(requestBody, bodyFileNameExpression);

        if (bodyFileName == null) {
            return responseDefinition;
        }

        Path file = Paths.get(bodyFileName);

        if (Files.exists(file)) {
            byte[] responseBodyBytes = files.getBinaryFileNamed(bodyFileName).readContents();
            responseBody = new String(responseBodyBytes, StandardCharsets.UTF_8);

            return ResponseDefinitionBuilder.like(responseDefinition)
                    .withStatus(200)
                    .withBody(responseBody)
                    .withHeader("Content-Type", "application/json")
                    .build();
        } else {
            return ResponseDefinitionBuilder
                    .like(responseDefinition)
                    .withStatus(400)
                    .withBody("Response File not found")
                    .build();
        }
    }

    private String evaluateBodyFileNameExpression(String requestBody, String expression) {
        Object extractedValue = JsonPath.read(requestBody, expression);
        return extractedValue.toString() + ".json";
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

