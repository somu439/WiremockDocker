//package com.example.wiremock.extensions;
//
//import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
//import com.github.tomakehurst.wiremock.extension.Parameters;
//import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
//import com.github.tomakehurst.wiremock.http.Request;
//import com.github.tomakehurst.wiremock.http.Response;
//import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class CustomExtension extends ResponseTransformer {
//
//    private static final String NAME = "customExtension";
//    private static final Map<String, String> CUSTOM_VALUES = new ConcurrentHashMap<>();
//
//    @Override
//    public Response transform(Request request, Response response, FileSource files, Parameters parameters,
//                              RajawaliMetrics rajawaliMetrics) {
//        String customValue = CUSTOM_VALUES.computeIfAbsent("customValue", key -> "RandomValue_" + System.nanoTime());
//        LOGGER.info("Added custom value: {}", customValue);
//
//        return Response.Builder.like(response)
//                .but()
//                .header("X-Custom-Header", customValue)
//                .build();
//    }
//
//    @Override
//    public String getName() {
//        return NAME;
//    }
//
//    public static void main(String[] args) {
//        Slf4jNotifier notifier = new Slf4jNotifier(true);
//        WireMockServerRunner runner = new WireMockServerRunner(notifier);
//        runner.runCommand(new String[]{"--port", "8080", "--extensions", "com.emirates.wiremock.extensions.CustomExtension"});
//    }
//}
