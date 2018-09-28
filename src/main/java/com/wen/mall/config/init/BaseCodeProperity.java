package com.wen.mall.config.init;

import com.wen.mall.config.code.entity.BaseCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class BaseCodeProperity {

    public static Map<String, List<BaseCode>> CODE_MAP = new HashMap<>();


    public static List<BaseCode> getListByCategory(String category) {
        return CODE_MAP.get(category);
    }

    public static String getName(String category, String code) {
        List<BaseCode> list = CODE_MAP.get(category);
        for (BaseCode obj : list) {
            if (code.equals(obj.getCode())) {
                return obj.getName();
            }
        }
        return null;
    }


}
