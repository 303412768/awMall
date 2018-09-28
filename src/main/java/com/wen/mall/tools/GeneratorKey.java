package com.wen.mall.tools;

import java.util.UUID;

public class GeneratorKey {

    public static String getKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
