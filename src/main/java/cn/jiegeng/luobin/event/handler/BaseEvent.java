package cn.jiegeng.luobin.event.handler;

import cn.jiegeng.luobin.util.MasterUtil;
import cn.jiegeng.luobin.util.RedisUtil;
import cn.jiegeng.luobin.util.ReflectUtil;
import cn.jiegeng.luobin.util.StringUtils;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Component;

/**
 * @author feiwoscun
 */
@Component("baseEvent")
public class BaseEvent extends SimpleListenerHost {
    //这个是要用的chatgpt

    final RedisUtil redisUtil;

    public BaseEvent(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }


    /**
     * 针对所有消息的
     * @param event
     * @return
     */
    @EventHandler
    public ListeningStatus sayHello(MessageEvent event) {
        long id = event.getSender().getId();
        String s = event.getMessage().contentToString();
        boolean b = redisUtil.setMembers("command").
                stream().anyMatch(t -> t.equals(s)&&!"桔梗桔梗 hi".equals(t)&&t.equals(s)&&!"桔梗桔梗 weather".equals(s));
        if(b){
            String member = redisUtil.sRandomMember(s);
            if (member!=null&&member.length()>0) {
                event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("").plus(member));
            }
            return ListeningStatus.LISTENING;
        }

        //判断是不是命令
        if (MasterUtil.judgeIfStartWith(s) && StringUtils.isNotEmpty(s)) {
            //得到命令
            String command = MasterUtil.getCommand(s);
            //把命令清除后,得到剩下的内容
            String[] strings = MasterUtil.clearBegin(s);
            //执行反射方法
            ReflectUtil.reflectGroupEvent(command, strings, event);
        }
        return ListeningStatus.LISTENING;
    }

    /**
     * @param event
     * @return
     */
    public ListeningStatus getCommandSay(MessageEvent event) {
        //反射方法
        return ListeningStatus.LISTENING;
    }

}
