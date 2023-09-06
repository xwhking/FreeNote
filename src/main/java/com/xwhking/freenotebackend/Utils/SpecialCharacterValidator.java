package com.xwhking.freenotebackend.Utils;

public class SpecialCharacterValidator {

    public static boolean containsSpecialCharacters(String input) {
        // 使用正则表达式检查字符串中是否包含特殊字符
        return input.matches(".*[\\p{P}\\p{S}].*");
    }

    public static void main(String[] args) {
        String testString = "HelloWorld"; // 要检查的字符串
        boolean containsSpecialChars = containsSpecialCharacters(testString);

        if (containsSpecialChars) {
            System.out.println("The string contains special characters.");
        } else {
            System.out.println("The string does not contain special characters.");
        }
    }
}