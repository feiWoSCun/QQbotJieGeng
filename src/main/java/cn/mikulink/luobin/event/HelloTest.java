package cn.mikulink.luobin.event;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.SingleMessage;
import org.springframework.stereotype.Component;

@Component
public class HelloTest extends SimpleListenerHost{
    @EventHandler
    public ListeningStatus sayHello(MessageEvent event){
        System.out.println(event.getSubject());
        System.out.println(event.getMessage().contains("hi"));
        System.out.println(event.getMessage().get(1));
        System.out.println(event.getSource());
        System.out.println(event.getSenderName());
        long id = event.getSender().getId();
        System.out.println(event.getSender());
        event.getSource().getFromId();
        event.getSource().getTargetId();
        event.getSubject().sendMessage("io");
        System.out.println(event.getSender().getRemark());
        if(event.getMessage().contentToString().equals("hi")) {
            event.getSubject().sendMessage(new At(id).plus( " 你好啊，我是桔梗q(≧▽≦q)"));
        }
        return ListeningStatus.LISTENING;
    }
}
