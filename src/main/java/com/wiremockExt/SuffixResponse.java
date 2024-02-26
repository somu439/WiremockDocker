package com.wiremockExt;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;


public class SuffixResponse extends ResponseDefinitionTransformer {

//    @Override
public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
    String suffix = parameters.getString("suffix", "_default_suffix");
        String originalBody = responseDefinition.getBody();
        String modifiedBody = originalBody + suffix;
        return ResponseDefinitionBuilder.like(responseDefinition)
                .withBody(modifiedBody)
                .build();
    }

    @Override
    public String getName() {
        return "suffix-response";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
