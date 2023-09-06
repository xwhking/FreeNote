package com.xwhking.freenotebackend.Utils;

import java.security.MessageDigest;

public class MD5Encryption {

    public static String encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b & 0xFF));
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String originalString = "xwhking" + "admin123"; // 要加密的字符串

        String encryptedString = encrypt(originalString);
        System.out.println("MD5 Encrypted: " + encryptedString);
    }
}
