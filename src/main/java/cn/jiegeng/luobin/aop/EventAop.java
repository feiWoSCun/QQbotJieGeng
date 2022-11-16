/*
package cn.jiegeng.luobin.aop;
import cn.jiegeng.luobin.annotation.CommandAnnotation;
import cn.jiegeng.luobin.command.HelloCommand;
import cn.jiegeng.luobin.command.enums.HelloEnums;
import cn.jiegeng.luobin.util.RedisUtil;
import net.mamoe.mirai.event.Event;10
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 @Pointcut("@annotation(cn.jiegeng.luobin.annotation.CommandAnnotation)")
    public void targetTar() {
    }

    @Before("targetTar() && @annotation(annotation)&&args(joinPoint)")
    public void EventAop(CommandAnnotation annotation, ProceedingJoinPoint joinPoint) {
        System.out.println("切面方法开始执行");
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
    @Pointcut(value = "execution(public * cn.jiegeng.luobin.event.*.*(..)))")
    private void test() {
    }
    @Before(value = "test()")
    public void doAround(JoinPoint pjp){
        System.out.println("切面方法开始执行");
        Object[] args = pjp.getArgs();
        MessageEvent event = (MessageEvent) Arrays.stream(args).filter(temp -> temp instanceof MessageEvent).findFirst().get();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        signerCheck(targetMethod, event);
    }
*/
/**
     * 签名检查
     *//*


    private void signerCheck(Method targetMethod, MessageEvent event) {
        // 业务逻辑中需要进行增强的操作
        int privilege = targetMethod.getDeclaredAnnotation(CommandAnnotation.class).privilege();
        long id = (event.getSender().getId());
        //用户的权限
        Integer pri = Integer.valueOf(redisUtil.get(String.valueOf(id)));
        if (114514 == privilege) {
        } else if (pri == null || pri == 0 || pri > privilege) {
            event.getSender().sendMessage(new At(id).plus(HelloEnums.NOPRIVILEGES.getSayHello()));
            throw new RuntimeException("错误,没有权限");
        }
    }
}
*/
