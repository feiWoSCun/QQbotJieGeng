package cn.mikulink.luobin.util;

import cn.mikulink.luobin.annotation.CommandAnnotation;
import cn.mikulink.luobin.event.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectUtil {
    private static GroupMessageEvent groupMessageEvent;
    @Autowired
public ReflectUtil(GroupMessageEvent groupMessageEvent){
    this.groupMessageEvent = groupMessageEvent;
}
    public static void reflectGroupEvent(String command, String[] nrArr, net.mamoe.mirai.event.events.GroupMessageEvent event) {
        //拿到反射方法
        Method targetMethod = Arrays.stream(GroupMessageEvent.class.getDeclaredMethods()).filter(method -> method.
                getDeclaredAnnotation(CommandAnnotation.class).method().equals(command)).findFirst().get();
        try {
            targetMethod.invoke(groupMessageEvent,event,nrArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
}
