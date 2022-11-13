package cn.jiegeng.luobin.util;

import cn.jiegeng.luobin.annotation.CommandAnnotation;
import cn.jiegeng.luobin.event.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
@Component
public class ReflectUtil {
    private static GroupMessageEvent groupMessageEvent;

    @Autowired
    public ReflectUtil(GroupMessageEvent groupMessageEvent) {
        this.groupMessageEvent = groupMessageEvent;
    }

    public static void reflectGroupEvent(String command, String[] nrArr, MessageEvent event) {
        //拿到反射方法
            Method targetMethod = Arrays.stream(GroupMessageEvent.class.getDeclaredMethods()).filter(method -> method.
                    getDeclaredAnnotation(CommandAnnotation.class)!=null).filter(method -> method.
                getDeclaredAnnotation(CommandAnnotation.class).method().equals(command)).findFirst().get();
        try {
            targetMethod.invoke(groupMessageEvent, event, nrArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
