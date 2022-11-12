package cn.jiegeng.luobin.aop;

import cn.jiegeng.luobin.annotation.CommandAnnotation;
import cn.jiegeng.luobin.command.HelloCommand;
import cn.jiegeng.luobin.command.enums.HelloEnums;
import cn.jiegeng.luobin.util.RedisUtil;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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

    @Pointcut("@annotation(cn.jiegeng.luobin.annotation.CommandAnnotation)")
    public void targetTar() {
    }


    @Before("targetTar() && @annotation(annotation)&&args(joinPoint)")
    public void EventAop(CommandAnnotation annotation, ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MessageEvent event = (MessageEvent) Arrays.stream(args).filter(temp -> temp instanceof MessageEvent).findFirst().get();
        int i = annotation.privilege();
        //用户id
        long id = (event.getSender().getId());
        //用户的权限
        Integer pri = Integer.valueOf(redisUtil.get(String.valueOf(id)));
        if (114514 == i) {
        } else if (pri == null || pri == 0 || pri > i) {
            event.getSender().sendMessage(new At(id).plus(HelloEnums.NOPRIVILEGES.getSayHello()));
        }
    }
}
