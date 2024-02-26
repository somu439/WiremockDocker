package com.wiremockExt;

import com.jayway.jsonpath.JsonPath;

import java.nio.file.Paths;

public class JsonPathExample {
    public static void main(String[] args) {
        // Sample JSON string
        String jsonString = "{\"store\":{\"book\":[{\"title\":\"Book 1\",\"price\":10},{\"title\":\"Book 2\",\"price\":20}]}}";

        // JSONPath expression to extract the price of the second book
        String jsonPathExpression = "$.store.book[1].price";

        // Extracting value using JSONPath
        Object extractedValue = JsonPath.read(jsonString, jsonPathExpression);

        // Printing the extracted value
        System.out.println("Extracted value: " + extractedValue);
        // Get the current working directory
        String currentDirectory = Paths.get("").toAbsolutePath().toString();

        // Print the current directory
        System.out.println("Current directory: " + currentDirectory);
    }
}