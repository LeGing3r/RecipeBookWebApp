package com.example.utils;

public class NumberUtils {

    public static double getNumberFromString(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            if (s.contains("/")) {
                var nums = s.split("/");
                return Double.parseDouble(nums[0]) / Double.parseDouble(nums[1]);
            }
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
