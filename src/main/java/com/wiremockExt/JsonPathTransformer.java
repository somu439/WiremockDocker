package com.wiremockExt;

import com.github.tomakehurst.wiremock.common.*;
import com.github.tomakehurst.wiremock.extension.*;
import com.github.tomakehurst.wiremock.http.*;
import com.jayway.jsonpath.JsonPath;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.jayway.jsonpath.JsonPath;


//public class JsonPathTransformer extends ResponseDefinitionTransformerV2 {
    public class JsonPathTransformer extends ResponseDefinitionTransformer {
//    public static void main(String[] args) {
//        String respDef= transform()
//    }

        //    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
        String jsonPathExpression = parameters.getString("bodyFileName", null);
//        System.out.println("bodyfileNameExperssion:"+bodyFileNameExpression);
//        if (bodyFileNameExpression == null) {
//            return responseDefinition;
//        }

        String body = request.getBodyAsString();
//        String jsonPathExpression = responseDefinition.getBodyFileName();

        if (body != null && jsonPathExpression != null) {
            try {
                String result = JsonPath.read(body, jsonPathExpression);
                return ResponseDefinitionBuilder.like(responseDefinition).withBody(result).build();
            } catch (Exception e) {
                return ResponseDefinitionBuilder.like(responseDefinition)
                        .withStatus(500)
                        .withBody("Failed to evaluate JSONPath expression: " + e.getMessage())
                        .build();
            }
        } else {
            return responseDefinition;
        }
    }

    @Override
    public String getName() {
        return "json-path-transformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
