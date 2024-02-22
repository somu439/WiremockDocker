package com.example.wiremock.extensions;

public class StringManipulation {
    public static void main(String[] args) {
        String s = "Sreeni 01012022 100_400";
//                String s = "Sreeni 01012022 100_400";

        // Split the string by space
        String[] parts = s.split(" ");

        // Extract min and max numbers
        String minMax = parts[2]; // Third part containing min_max
        String[] minMaxParts = minMax.split("_");

        // Assign min and max numbers
        int min = Integer.parseInt(minMaxParts[0]);
        int max = Integer.parseInt(minMaxParts[1]);

        // Output min and max numbers
        System.out.println("Part1: " +parts[0] );
        System.out.println("Part2: " +parts[1] );
        System.out.println("Min: " + min);
        System.out.println("Max: " + max);
    }
}