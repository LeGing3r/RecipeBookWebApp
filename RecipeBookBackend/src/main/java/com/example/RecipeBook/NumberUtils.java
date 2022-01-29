package com.example.RecipeBook;

public class NumberUtils {
    public static double getNumberFromString(String s) {
        if (s.matches("\\d*(.)\\d+")) {
            var nums = s.split("(.)");
            return Double.parseDouble(nums[0]) + (Double.parseDouble(nums[1]) / Math.pow(10, nums[1].length()));
        }
        if (s.matches("\\d+/\\d+")) {
            var nums = s.split("/");
            return Double.parseDouble(nums[0]) / Double.parseDouble(nums[1]);
        }
        if (s.matches("\\d+")) {
            return Double.parseDouble(s);
        }
        return 0;
    }

    public static String findNumberInString(String s) {
        int index = 0;
        while (s.substring(0, index).matches("\\w+[.]+[/]+\\w*")) {
            index++;
        }
        if (index != 0) {
            return s.substring(0, index - 1);
        }
        return "";
    }
}
