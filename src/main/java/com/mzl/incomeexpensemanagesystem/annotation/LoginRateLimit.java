package com.mzl.incomeexpensemanagesystem.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRateLimit {

    /**
     * 默认每秒放入桶中的token，controller方法不设置就使用默认的10
     */
    int limitNum() default 10;

}
