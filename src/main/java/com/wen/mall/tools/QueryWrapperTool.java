package com.wen.mall.tools;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.google.common.base.CaseFormat;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class QueryWrapperTool<T> {

    public QueryWrapper<T> getQueryWrapper(HttpServletRequest request) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String attributeName = names.nextElement();
            setWrapper(request, attributeName, queryWrapper);

        }
        setSort(request, queryWrapper);
        return queryWrapper;
    }

    private void setWrapper(HttpServletRequest request, String attributeName, QueryWrapper<T> queryWrapper) {
        if (attributeName.startsWith("query-")) {
            String[] arr = attributeName.split("-");
            if (arr.length == 3) {
                String queryType = arr[1];
                String columnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, arr[2]);
                Object value = request.getParameter(attributeName);
                if (StringUtils.isEmpty(value)) {
                    return;
                }
                switch (queryType) {
                    case "eq":
                        queryWrapper.eq(columnName, value);
                        break;
                    case "like":
                        queryWrapper.like(columnName, value);
                        break;
                    case "likeRight":
                        queryWrapper.likeRight(columnName, value);
                        break;
                    case "likeLeft":
                        queryWrapper.likeLeft(columnName, value);
                        break;
                    case "ge": //大于等于
                        queryWrapper.ge(columnName, value);
                    case "gt": //大于
                        queryWrapper.gt(columnName, value);
                        break;
                    case "le": //小于等于
                        queryWrapper.le(columnName, value);
                        break;
                    case "lt": //小于
                        queryWrapper.lt(columnName, value);
                        break;
                }
            }
        }
    }

    private void setSort(HttpServletRequest request, QueryWrapper<T> queryWrapper) {
        if (null != request.getParameter("sortName")) {
            String sortName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getParameter("sortName"));
            String sortType = request.getParameter("sortOrder");
            if (!StringUtils.isEmpty(sortName)) {
                if ("desc".equals(sortType)) {
                    queryWrapper.orderByDesc(sortName);
                } else {
                    queryWrapper.orderByAsc(sortName);
                }
            }
        }

    }

    public static void main(String[] args) {
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));//testData
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));//testData
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));//TestData

        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testdata"));//testdata
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testData"));//test_data
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "testData"));//test-data
    }
}
