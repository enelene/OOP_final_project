package com.example.quizwebsite.userManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingManager {
    public static String generateHash(String targ) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(targ.getBytes());
        return hexToString(md.digest());
    }

    public static String hexToString(byte[] bytes) {
        StringBuilder buff = new StringBuilder();
        for (byte aByte : bytes) {
            int val = aByte & 0xff; // remove higher bits, sign
            if (val < 16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }
}
