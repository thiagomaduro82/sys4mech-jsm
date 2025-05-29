package com.sys4business.sys4mech.utils;

public class Sys4MechUtil {
    
    public static String generateUuid() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    public static String formatUuid(String uuid) {
        if (uuid == null || uuid.length() != 32) {
            throw new IllegalArgumentException("Invalid UUID format");
        }
        return uuid.toUpperCase();
    }

    public static boolean isValidUuid(String uuid) {
        return uuid != null && uuid.matches("^[0-9a-fA-F]{32}$");
    }
}
