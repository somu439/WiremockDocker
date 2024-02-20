package com.example.wiremock.extensions;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Body;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

public class RandomResponseExtension extends ResponseDefinitionTransformer {

    @Override
    public ResponseDefinition transform(
            Request request,
            ResponseDefinition responseDefinition,
            FileSource files,
            Parameters parameters) {
        return ResponseDefinitionBuilder.like(responseDefinition)
                .withBody("HI there:")
                .withHeader("X-Hello", "World")
                .build();
    }

    @Override
    public String getName() {
        return "test.wiremockExt.Sreeni";
    }
}
