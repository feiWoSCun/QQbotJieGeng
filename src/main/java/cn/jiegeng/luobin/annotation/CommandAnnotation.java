package cn.jiegeng.luobin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 反射指令，就不用每个方法都添加@handler了
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAnnotation {
    /**
     * 需要反射的方法
     * @return 需要反射的方法名字
     */
    String method() default "";
    /**
     * 权限校验
     * @return 用于权限判断
     */
    int privilege() default 114514;
}
