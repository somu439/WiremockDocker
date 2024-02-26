package com.wiremockExt;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.PostServeAction;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.jayway.jsonpath.JsonPath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonPathQueryExtension extends PostServeAction {

    private static final Pattern JSON_PATH_PATTERN = Pattern.compile("/jsonpath/query\\?expression=(.*?)&content=(.*)");

//    @Override
    public Response apply(Request request, Response response, Parameters parameters) {
        String requestPath = request.getUrl();
        Matcher matcher = JSON_PATH_PATTERN.matcher(requestPath);

        if (matcher.matches()) {
            String jsonPathExpression = matcher.group(1);
            String jsonContent = matcher.group(2);

            // Query value using JSONPath
            Object result = JsonPath.read(jsonContent, jsonPathExpression);

            // Create a new response with the queried value
            return Response.Builder.like(response)
                    .but()
                    .body(String.valueOf(result))
                    .build();
        }

        return response;
    }

    @Override
    public String getName() {
        return "Json-Path-Query-Extension";
    }
}
