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
//注解不仅 能保留到class字节码文件中，还能在运行通过反射获取到，这也是我们最常用的。
@Retention(RetentionPolicy.RUNTIME)
//能修饰成员变量
@Target(ElementType.FIELD)
public @interface BindPort {
    String value() default "8080";
}
