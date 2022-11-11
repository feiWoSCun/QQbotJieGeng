package cn.mikulink.luobin.event.handler;

import cn.mikulink.luobin.domain.UserStore;
import cn.mikulink.luobin.util.MasterUtil;
import cn.mikulink.luobin.util.ReflectUtil;
import cn.mikulink.luobin.util.StringUtils;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import org.springframework.stereotype.Component;

@Component("baseEvent")
public class BaseEvent extends SimpleListenerHost {
    /**
     * 针对群消息的
     *
     * @param event
     * @return
     */
    @EventHandler
    public ListeningStatus sayHello(GroupMessageEvent event) {
        long id=event.getSender().getId();
        if(id==3256586077l){
            event.getSubject().sendMessage(new At(id).plus("芳娃闭嘴"));
             return ListeningStatus.LISTENING;
        }

        String s = event.getMessage().contentToString();
        //判断是不是命令
        if (MasterUtil.judgeIfStartWith(s) && StringUtils.isNotEmpty(s)) {
            //得到命令
            String command = MasterUtil.getCommand(s);
            //把命令清除后,得到剩下的内容
            String[] strings = MasterUtil.clearBegin(s);
            //执行反射方法
            ReflectUtil.reflectGroupEvent(command,strings,event);
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
