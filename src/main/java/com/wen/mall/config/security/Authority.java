package com.wen.mall.config.security;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authority {

    String[] value() default {};

    String[] authorities() default {};

    String[] roles() default {};

}
