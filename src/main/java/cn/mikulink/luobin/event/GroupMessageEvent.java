package cn.mikulink.luobin.event;

import cn.mikulink.luobin.annotation.CommandAnnotation;
import cn.mikulink.luobin.domain.dto.AnswerDto;
import cn.mikulink.luobin.mapper.DialogueMapper;
import cn.mikulink.luobin.service.DialogueService;
import cn.mikulink.luobin.util.MasterUtil;
import cn.mikulink.luobin.util.apiutil.HttpUtil;
import com.alibaba.fastjson.JSON;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GroupMessageEvent {

    final DialogueMapper mapper;

    private static DialogueService dialogueService;

    @Autowired
    public GroupMessageEvent(DialogueMapper mapper, DialogueService dialogueService) {
        this.mapper = mapper;
        this.dialogueService = dialogueService;
    }

    @CommandAnnotation(method = "桔梗桔梗 hi")
    public static ListeningStatus sayHello1(MessageEvent event,String[] nrArr) {
            long id = event.getSender().getId();
            //是不是master
            if (MasterUtil.isMaster(id)) {
                //拿到打招呼语录
                String dialogues = dialogueService.getDialogues();
                event.getSubject().sendMessage(new At(id).plus(MasterUtil.masterSay()).plus(",主人").plus("\n").plus(dialogues));
            } else {
                event.getSubject().sendMessage(new At(id).plus("原为阁下效劳"));
            }
        return ListeningStatus.LISTENING;
    }
    @CommandAnnotation(method = "桔梗桔梗 ")
    public static  ListeningStatus getDialogue(MessageEvent event,String[] nrArr){
        String url="http://api.qingyunke.com/api.php?key=free&appid=0&msg="+nrArr[0];
            String s = HttpUtil.get(url);
        AnswerDto answerDto = JSON.parseObject(s, AnswerDto.class);
        long id = event.getSender().getId();
       if( "未获取到相关信息".equals(answerDto.getContent())) {
           answerDto.setContent("原谅桔梗现在还不是很聪明\n" + dialogueService.getDialogues());
       }
        event.getSubject().sendMessage(new At(id).plus(answerDto.getContent()));
        return ListeningStatus.LISTENING;
    }
}
