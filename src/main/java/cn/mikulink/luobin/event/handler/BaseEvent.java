package cn.mikulink.luobin.event.handler;

import cn.mikulink.luobin.domain.UserStore;
import cn.mikulink.luobin.util.MasterUtil;
import cn.mikulink.luobin.util.StringUtils;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Component;

@Component("helloEvent")
public class BaseEvent extends SimpleListenerHost {
    /**
     * 针对群消息的
     * @param event
     * @return
     */
    @EventHandler
    public ListeningStatus sayHello(GroupMessageEvent event) {
        String s=event.getMessage().contentToString();
        //判断是不是命令
        if(MasterUtil.judgeIfStartWith(s)&& StringUtils.isNotEmpty(s)) {
            //得到命令
            String command = MasterUtil.getCommand(s);
            //把命令清楚后,得到剩下的内容
            String[] strings = MasterUtil.clearBegin(s);
            //执行反射方法

        }
            return ListeningStatus.LISTENING;
        }

    /**
     *
     * @param event
     * @return
     */
 public ListeningStatus getCommandSay(MessageEvent event){
    //反射方法
        return ListeningStatus.LISTENING;
 }

}
