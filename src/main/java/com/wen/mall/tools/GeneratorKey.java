package com.wen.mall.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class GeneratorKey {

    private static final String BASE_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String getKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String orderNumber(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formatDateTime = localDateTime.format(formatter);
        return formatDateTime +
                "-" +
                getRandomString(4);
    }
    private static String getRandomString(int length){
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(BASE_STR.length());
            sb.append(BASE_STR.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(orderNumber());
    }
}
