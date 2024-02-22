//package com.example.wiremock.extensions;
//
//import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
//import com.github.tomakehurst.wiremock.common.*;
//import com.github.tomakehurst.wiremock.extension.*;
//import com.github.tomakehurst.wiremock.http.*;
//import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
//
//import java.nio.file.*;
//
//public static class ExampleTransformer implements ResponseDefinitionTransformerV2 {
//
////    @Override
//    public ResponseDefinition transform(ServeEvent serveEvent) {
//        return new ResponseDefinitionBuilder()
//                .withHeader("MyHeader", "Transformed")
//                .withStatus(200)
//                .withBody("Transformed body")
//                .build();
//    }
//
//    @Override
//    public String getName() {
//        return "example";
//    }
//}