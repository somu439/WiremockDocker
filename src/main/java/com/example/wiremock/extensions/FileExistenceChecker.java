package com.example.wiremock.extensions;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.*;
import com.github.tomakehurst.wiremock.extension.*;
import com.github.tomakehurst.wiremock.http.*;
import java.nio.file.*;

public class FileExistenceChecker extends ResponseDefinitionTransformer {

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
//        String bodyFileNameExpression = parameters.getString("bodyFileName", null);
//        System.out.println("bodyfileNameExperssion:"+bodyFileNameExpression);
//        if (bodyFileNameExpression == null) {
//            return responseDefinition;
//        }

        String fileName = responseDefinition.getBodyFileName();
        System.out.println("getbodyfilename:"+fileName);

        if (fileName != null) {
            Path filePath = Paths.get("__files", "india.json");
            System.out.println("filepath:"+filePath);
            if (Files.exists(filePath)) {
                return responseDefinition;
            } else {
                return ResponseDefinitionBuilder.like(responseDefinition)
                        .withStatus(400)
                        .withBody("Response File not found")
                        .build();
            }
        } else {
            return responseDefinition;
        }
    }

    @Override
    public String getName() {
        return "file-existence-checker";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
