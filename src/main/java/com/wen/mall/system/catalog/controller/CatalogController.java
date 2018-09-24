package com.wen.mall.system.catalog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.mall.config.bean.Result;
import com.wen.mall.system.catalog.entity.Catalog;
import com.wen.mall.system.catalog.service.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.sql.Wrapper;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author John
 * @since 2018-09-20
 */
@RestController
@RequestMapping("/catalogs")
public class CatalogController {

    @Autowired
    private ICatalogService catalogService;

    @GetMapping("")
    public Page<Catalog> selectPage(@RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "10")Long pageSize) {
        Page<Catalog> page = new Page<>(pageNo, pageSize);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("uuid");
        page = (Page<Catalog>) catalogService.page(page, queryWrapper);
        System.out.println(page);
        return page;
    }

    @PostMapping("/save")
    public Result<Catalog> save(Catalog catalog) {
        catalogService.save(catalog);
        return Result.success();
    }
}
