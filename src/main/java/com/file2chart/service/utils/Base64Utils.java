package com.file2chart.service.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Utils {

    public static String encrypt(byte[] data) {
        return Base64.getEncoder().withoutPadding().encodeToString(data);
    }

    public static byte[] decryptToBytes(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static String decryptToString(String data) {
        byte[] decodedBytes = decryptToBytes(data);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
