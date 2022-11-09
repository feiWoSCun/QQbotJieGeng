package cn.mikulink.luobin.event;

import cn.mikulink.luobin.bot.JieGengBot;
import io.ktor.http.ContentType;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class Test{
    public void onEnable() {
        JieGengBot.getBot().getEventChannel().registerListenerHost(new HelloTest());
    }
}
