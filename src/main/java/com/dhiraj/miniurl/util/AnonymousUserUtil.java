package com.dhiraj.miniurl.util;

import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class AnonymousUserUtil {
    public static String generateAnonKey(HttpServletRequest request){
        try{
            String ip = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");

            String raw = ip + ":" + userAgent;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for(byte b:hash){
                hex.append(String.format("%02x",b));
            }

            return hex.toString();
        }catch(Exception e){
            throw new RuntimeException("Failed to generate anonymous key");
        }
    }
}
