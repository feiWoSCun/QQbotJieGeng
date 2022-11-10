package cn.mikulink.luobin.event;

import cn.mikulink.luobin.annotation.CommandAnnotation;
import cn.mikulink.luobin.mapper.DialogueMapper;
import cn.mikulink.luobin.service.DialogueService;
import cn.mikulink.luobin.util.MasterUtil;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class GroupEvent {

    final DialogueMapper mapper;

    private static DialogueService dialogueService;

    @Autowired
    public GroupEvent(DialogueMapper mapper, DialogueService dialogueService) {
        this.mapper = mapper;
        this.dialogueService = dialogueService;
    }

    @CommandAnnotation(method = "桔梗桔梗 hi")
    public static ListeningStatus sayHello1(MessageEvent event) {
        if (MasterUtil.judgeIfStartWith(event.getMessage().contentToString())) {
            long id = event.getSender().getId();
            //是不是master
            if (MasterUtil.isMaster(id)) {
                //拿到打招呼语录
                String dialogues = dialogueService.getDialogues();
                event.getSubject().sendMessage(new At(id).plus(MasterUtil.masterSay()).plus(",主人").plus("\n").plus(dialogues));
            } else {
                event.getSubject().sendMessage(new At(id).plus("原为阁下效劳"));
            }
        }
        return ListeningStatus.LISTENING;
    }
}
