package com.wen.mall.config.bean;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private List<T> rows;
    private Long total;

    public static PageResult instance(IPage iPage) {
        PageResult pageResult = new PageResult();
        pageResult.setRows(iPage.getRecords());
        pageResult.setTotal(iPage.getTotal());
        return pageResult;
    }
}
