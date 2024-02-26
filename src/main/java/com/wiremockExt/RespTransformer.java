package com.wiremockExt;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RespTransformer extends ResponseDefinitionTransformer {

//    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
        String jsonPathExpression = parameters.getString("jsonPathExpression");
        String responseFilePath = parameters.getString("responseFilePath");

        try {
            // Extract value from request using JsonPath
            String requestBody = request.getBodyAsString();
            String extractedValue = JsonPath.read(requestBody, jsonPathExpression);

            // Check if file exists
            if (!Files.exists(Paths.get("__files", responseFilePath))) {
                return ResponseDefinitionBuilder.like(responseDefinition)
                        .withStatus(400)
                        .withBody("File not found for the given JSONPath expression")
                        .build();
            }

            // Read contents of the file
            byte[] fileContent = Files.readAllBytes(Paths.get("__files", responseFilePath));

            return ResponseDefinitionBuilder.like(responseDefinition)
                    .withStatus(200)
                    .withBody(fileContent)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseDefinitionBuilder.like(responseDefinition)
                    .withStatus(500)
                    .withBody("Internal Server Error")
                    .build();
        }
    }

    @Override
    public String getName() {
        return "RespTransformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
