package com.wen.mall.config.init;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wen.mall.config.code.entity.BaseCode;
import com.wen.mall.config.code.service.IBaseCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(value = 9)
public class BaseCodeInit implements ApplicationRunner {

    private static final String CATEGORY = "category";

    @Autowired
    private IBaseCodeService baseCodeService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        initBaseCode();
    }

    public void initBaseCode() {
        BaseCodeProperity.CODE_MAP.clear();
        QueryWrapper<BaseCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(CATEGORY);
        queryWrapper.groupBy(CATEGORY);
        List<BaseCode> list=baseCodeService.list(queryWrapper);
        list.forEach(obj-> {
            QueryWrapper<BaseCode> wrapper = new QueryWrapper<>();
            wrapper.eq(CATEGORY, obj.getCategory());
            List<BaseCode> listByCategory=baseCodeService.list(wrapper);
            BaseCodeProperity.CODE_MAP.put(obj.getCategory(), listByCategory);
        });
    }
}
