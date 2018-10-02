package com.wen.mall.config.code.controller;


import com.wen.mall.config.bean.Result;
import com.wen.mall.config.code.entity.BaseCode;
import com.wen.mall.config.init.BaseCodeInit;
import com.wen.mall.config.init.BaseCodeProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author John
 * @since 2018-09-28
 */
@RestController
@RequestMapping("/api/1.0/baseCode")
public class BaseCodeController {

    @Autowired
    private BaseCodeInit baseCodeInit;

    @GetMapping("/{category}")
    public List<BaseCode> getListByCategory(@PathVariable String category) {
        return BaseCodeProperty.getListByCategory(category);
    }

    @GetMapping("/{category}/{code}")
    public String getName(@PathVariable String category,@PathVariable String code) {
        return BaseCodeProperty.getName(category,code);
    }

    @GetMapping("/fresh")
    public Result fresh() {
        baseCodeInit.initBaseCode();
        return Result.success();
    }
}
