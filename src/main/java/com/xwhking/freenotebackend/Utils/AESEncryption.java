package com.xwhking.freenotebackend.Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESEncryption {

    private static final String SECRET_KEY = "ThisIsASecretKey"; // 密钥，需要保密

    public static String encrypt(String plainText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {
            String originalString = "Hello, World!"; // 要加密的字符串

            String encryptedString = encrypt(originalString);
            System.out.println("Encrypted: " + encryptedString);

            String decryptedString = decrypt(encryptedString);
            System.out.println("Decrypted: " + decryptedString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
