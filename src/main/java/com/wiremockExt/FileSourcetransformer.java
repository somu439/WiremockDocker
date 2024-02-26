package com.wiremockExt;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSourcetransformer extends ResponseDefinitionTransformer {

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
        // Read the content of response_template.json file
        String responseTemplatePath = "response_template.json"; // Adjust the path to match the location of your file
        String responseTemplateContent;
        try {
            Path filePath = (Path) files.getBinaryFileNamed(responseTemplatePath);
            responseTemplateContent = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            // Handle file reading error
            e.printStackTrace();
            return null; // Return null response definition in case of error
        }

        // Replace placeholder with request parameter value
        String name = request.queryParameter("name").firstValue();
        String responseBody = responseTemplateContent.replace("{{name}}", name != null ? name : "Guest");

        // Create a new ResponseDefinition with the transformed body
        return ResponseDefinitionBuilder
                .like(responseDefinition)
                .but()
                .withBody(responseBody)
                .build();
    }

    @Override
    public String getName() {
        return "custom-response-transformer";
    }
}
