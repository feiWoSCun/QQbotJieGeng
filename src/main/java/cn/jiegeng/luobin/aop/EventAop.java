/*

package cn.jiegeng.luobin.aop;

import cn.jiegeng.luobin.annotation.CommandAnnotation;
import cn.jiegeng.luobin.command.HelloCommand;
import cn.jiegeng.luobin.command.enums.HelloEnums;
import cn.jiegeng.luobin.util.RedisUtil;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
*/
/**
 * aop事件,对所有事件进行权限判断
 *
 * @author 罗彬的电脑
 * @date 2022/11/13
 *//*



@Component
@Aspect
public class EventAop {
    final HelloCommand helloCommand;
    final RedisUtil redisUtil;

    @Autowired
    public EventAop(HelloCommand helloCommand, RedisUtil redisUtil) {
        this.helloCommand = helloCommand;
        this.redisUtil = redisUtil;
    }

    @Pointcut("@execution(* cn.jiegeng.luobin.event.*.*(..))")
    public void targetTar() {
    }

    @Before("targetTar()")
    public void EventAop( JoinPoint joinPoint) {
        System.out.println("切面方法开始执行");
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        int privilege = method.getAnnotation(CommandAnnotation.class).privilege();
        MessageEvent event = (MessageEvent) Arrays.stream(args).filter(temp -> temp instanceof MessageEvent).findFirst().get();

        //用户id
        long id = (event.getSender().getId());
        //用户的权限
        Integer pri = Integer.valueOf(redisUtil.get(String.valueOf(id)));
        if (114514 == privilege) {
        } else if (pri == null || pri == 0 || pri > privilege) {
            event.getSender().sendMessage(new At(id).plus(HelloEnums.NOPRIVILEGES.getSayHello()));
            throw  new RuntimeException("用户无权限");
        }
    }
}*/
