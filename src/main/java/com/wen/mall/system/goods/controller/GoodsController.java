package com.wen.mall.system.goods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.mall.config.bean.PageResult;
import com.wen.mall.config.bean.Result;
import com.wen.mall.system.goods.entity.Goods;
import com.wen.mall.system.goods.service.IGoodsService;
import com.wen.mall.tools.GeneratorKey;
import com.wen.mall.tools.QueryWrapperTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
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
@RequestMapping("/api/1.0/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @GetMapping("")
    public PageResult selectPage(@RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "10")Long pageSize, HttpServletRequest request) {
        Page<Goods> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Goods> queryWrapper= new QueryWrapperTool<Goods>().getQueryWrapper(request);
        queryWrapper.orderByDesc("update_time");
        page = (Page<Goods>) goodsService.page(page, queryWrapper);
        return PageResult.instance(page);
    }

    @GetMapping("/list")
    public Result list(HttpServletRequest request) {
        QueryWrapper<Goods> queryWrapper= new QueryWrapperTool<Goods>().getQueryWrapper(request);
        List<Goods> list=  goodsService.list(queryWrapper);
        return Result.success(list);
    }

    @GetMapping("/{uuid}")
    public Result<Goods> detail(@PathVariable String uuid) {
        return Result.success(goodsService.getById(uuid));
    }

    @PostMapping("/save")
    public Result save(Goods goods) {
        goods.setUuid(GeneratorKey.getKey());
        goods.setUpdateTime(LocalDateTime.now());
        goodsService.save(goods);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(Goods goods) {
        goods.setUpdateTime(LocalDateTime.now());
        goodsService.updateById(goods);
        return Result.success();
    }

    @PostMapping("/delete/{uuid}")
    public Result delete(@PathVariable List uuid) {
        goodsService.removeByIds(uuid);
        return Result.success();
    }

}
