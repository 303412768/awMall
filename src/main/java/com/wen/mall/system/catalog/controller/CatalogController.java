package com.wen.mall.system.catalog.controller;


import com.wen.mall.config.bean.Result;
import com.wen.mall.system.catalog.entity.Catalog;
import com.wen.mall.system.catalog.service.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
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

    @PostMapping("/save")
    public Result<Catalog> save(Catalog catalog) {
        catalogService.save(catalog);
        return  Result.success();
    }
}
