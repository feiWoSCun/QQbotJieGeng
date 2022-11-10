package cn.mikulink.luobin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 反射指令，就不用每个方法都添加@handler了
 */
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface CommandAnnotation {
    String method() default "";
}
