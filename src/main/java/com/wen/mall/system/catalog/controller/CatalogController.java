package com.wen.mall.system.catalog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.mall.config.bean.PageResult;
import com.wen.mall.config.bean.Result;
import com.wen.mall.system.catalog.entity.Catalog;
import com.wen.mall.system.catalog.service.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public PageResult selectPage(@RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "10")Long pageSize) {
        Page<Catalog> page = new Page<>(pageNo, pageSize);

        QueryWrapper<Catalog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("uuid");
        page = (Page<Catalog>) catalogService.page(page, queryWrapper);
        System.out.println(page);
        return PageResult.instance(page);
    }

    @GetMapping("/{uuid}")
    public Result<Catalog> detail(@PathVariable String uuid) {
        return Result.success(catalogService.getById(uuid));
    }

    @PostMapping("/save")
    public Result save(Catalog catalog) {
        catalogService.save(catalog);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(Catalog catalog) {
        catalogService.updateById(catalog);
        return Result.success();
    }

    @PostMapping("/delete/{uuid}")
    public Result delete(@PathVariable List uuid) {
        catalogService.removeByIds(uuid);
        return Result.success();
    }
}
