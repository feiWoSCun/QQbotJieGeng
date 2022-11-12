package cn.jiegeng.luobin.event;

import cn.jiegeng.luobin.annotation.CommandAnnotation;
import cn.jiegeng.luobin.command.enums.ApiUrl;
import cn.jiegeng.luobin.command.enums.HelloEnums;
import cn.jiegeng.luobin.domain.dto.AnswerDto;
import cn.jiegeng.luobin.mapper.DialogueMapper;
import cn.jiegeng.luobin.service.DialogueService;
import cn.jiegeng.luobin.util.MasterUtil;
import cn.jiegeng.luobin.util.apiutil.HttpUtil;
import com.alibaba.fastjson.JSON;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class GroupMessageEvent {

    final DialogueMapper mapper;

    private static DialogueService dialogueService;

    @Autowired
    public GroupMessageEvent(DialogueMapper mapper, DialogueService dialogueService) {
        this.mapper = mapper;
        this.dialogueService = dialogueService;
    }

    /**
     * 打招呼
     * @param event
     * @param nrArr
     * @return
     */
    @CommandAnnotation(method = "桔梗桔梗 hi")
    public static ListeningStatus sayHello1(MessageEvent event, String[] nrArr) {
        long id = event.getSender().getId();
        //是不是master
        if (MasterUtil.isMaster(id)) {
            //拿到打招呼语录
            String dialogues = dialogueService.getDialogues();
            /**
             *
             */
            event.getSubject().sendMessage(new At(id).plus(MasterUtil.masterSay()).plus(",主人").plus("\n").plus(dialogues));
        } else {
            event.getSubject().sendMessage(new At(id).plus(HelloEnums.DOHELP.getSayHello()));
        }
        return ListeningStatus.LISTENING;
    }

    /**
     * 在不是命令的情况下自动回话
     * @param event
     * @param nrArr
     * @return
     */
    @CommandAnnotation(method = "桔梗桔梗 ")
    public static ListeningStatus getDialogue(MessageEvent event, String[] nrArr) {
        String url = ApiUrl.Chat.getUrl() + nrArr[0];
        String s = HttpUtil.get(url);
        AnswerDto answerDto = JSON.parseObject(s, AnswerDto.class);
        long id = event.getSender().getId();
        if ("未获取到相关信息".equals(answerDto.getContent())) {
            answerDto.setContent(HelloEnums.SORRY.getSayHello() + dialogueService.getDialogues());
        }
        event.getSubject().sendMessage(new At(id).plus(answerDto.getContent()));
        return ListeningStatus.LISTENING;
    }
}
