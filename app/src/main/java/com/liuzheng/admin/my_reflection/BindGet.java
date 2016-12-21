package com.liuzheng.admin.my_reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：刘正
 * 时间：2016/12/21 0021
 * 作用：
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BindGet {
    String value() default "";
}