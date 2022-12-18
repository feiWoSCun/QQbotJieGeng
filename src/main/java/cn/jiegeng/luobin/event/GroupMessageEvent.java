package cn.jiegeng.luobin.event;

import cn.jiegeng.luobin.annotation.CommandAnnotation;
import cn.jiegeng.luobin.ashin.service.InteractService;
import cn.jiegeng.luobin.command.enums.ApiUrl;
import cn.jiegeng.luobin.command.enums.HelloEnums;
import cn.jiegeng.luobin.domain.dto.AnswerDto;
import cn.jiegeng.luobin.mapper.DialogueMapper;
import cn.jiegeng.luobin.mapper.PrivilegeMapper;
import cn.jiegeng.luobin.service.CpService;
import cn.jiegeng.luobin.service.DialogueService;
import cn.jiegeng.luobin.service.PrivilegeService;
import cn.jiegeng.luobin.util.MasterUtil;
import cn.jiegeng.luobin.util.RedisUtil;
import cn.jiegeng.luobin.util.apiutil.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 群消息事件,可接受私聊
 *
 * @author 罗彬的电脑
 * @date 2022/11/13
 */
@Component
@Data
public class GroupMessageEvent {
    private static InteractService interactService;
    private static CpService cpService;
    private static RedisUtil redisUtil;
    private static PrivilegeMapper privilegeMapper;

    private static DialogueMapper mapper;

    private static DialogueService dialogueService;
    private static PrivilegeService privilegeService;


    @Autowired
    public GroupMessageEvent(DialogueMapper mapper, DialogueService dialogueService, PrivilegeService privilegeService, PrivilegeMapper privilegeMapper, RedisUtil redisUtil, CpService cpService, InteractService interactService) {
        this.mapper = mapper;
        this.dialogueService = dialogueService;
        this.privilegeService = privilegeService;
        this.privilegeMapper = privilegeMapper;
        this.redisUtil = redisUtil;
        this.cpService = cpService;
        this.interactService = interactService;
    }

    public GroupMessageEvent() {
    }

    /**
     * 打招呼
     *
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
            event.getSubject().sendMessage(MasterUtil.commonSay(event).
                    plus(MasterUtil.masterSay()).plus(",主人").plus("\n").plus(dialogues));
        } else {
            event.getSubject().sendMessage(MasterUtil.commonSay(event).plus(HelloEnums.DOHELP.getSayHello()));
        }
        return ListeningStatus.LISTENING;
    }

    /**
     * 在不是命令的情况下自动回话
     *
     * @param event
     * @param nrArr
     * @return
     */
    @CommandAnnotation(method = "桔梗桔梗 ")
    public static ListeningStatus getDialogue(MessageEvent event, String[] nrArr) {
        StringBuilder builder = new StringBuilder();
        for (String s : nrArr) {
            builder.append(s);
        }
        String url = ApiUrl.Chat.getUrl() + builder;
        String s = HttpUtil.get(url);
        AnswerDto answerDto = JSON.parseObject(s, AnswerDto.class);
        long id = event.getSender().getId();
        if ("未获取到相关信息".equals(answerDto.getContent())) {
            answerDto.setContent(HelloEnums.SORRY.getSayHello() + dialogueService.getDialogues());
        }
        event.getSubject().sendMessage(MasterUtil.commonSay(event).plus(answerDto.getContent()));
        return ListeningStatus.LISTENING;
        /*StringBuilder builder=new StringBuilder();
        ChatBO chatBO = new ChatBO();
        chatBO.setSessionId(String.valueOf(event.getSender().getId()));
        StringBuilder builder=new StringBuilder();
        for (String s : nrArr) {
            builder.append(s);
        }

        chatBO.setQuestion(builder.toString());
        event.getSubject().sendMessage(interactService.chat(chatBO));
        return ListeningStatus.LISTENING;*/
    }

    @CommandAnnotation(method = "桔梗桔梗 add", privilege = 1)
    public static ListeningStatus addCommand(MessageEvent event, String[] nrArr) {
        privilegeService.addUser(event, nrArr);
        return ListeningStatus.LISTENING;
    }

    /**
     * 存储 cp
     *
     * @param event
     * @param nrArr
     * @return
     */
    @CommandAnnotation(method = "桔梗桔梗 cp")
    public static ListeningStatus rememberThings(MessageEvent event, String[] nrArr) {
        cpService.Store(event, nrArr);
        return ListeningStatus.LISTENING;
    }

    /**
     * 得到存储值
     *
     * @param event
     * @param nrArr
     * @return
     */
    @CommandAnnotation(method = "桔梗桔梗 get")
    public static ListeningStatus getKV(MessageEvent event, String[] nrArr) {
        cpService.get(event, nrArr);
        return ListeningStatus.LISTENING;
    }

    @CommandAnnotation(method = "桔梗桔梗 addC", privilege = 2)
    public static ListeningStatus addC(MessageEvent event, String[] nrArr) {

        dialogueService.addC(event, nrArr);
        return ListeningStatus.LISTENING;
    }

    @CommandAnnotation(method = "桔梗桔梗 weather")
    public static ListeningStatus subWeather(MessageEvent event, String[] nrArr) {
        redisUtil.lLeftPush("weather", "2097839141");
        redisUtil.lLeftPush("weather", "2698087831");
        redisUtil.lLeftPush("weather", "645680833");
        if (event instanceof GroupMessageEvent) {
            long id = ((net.mamoe.mirai.event.events.GroupMessageEvent) event).getGroup().getId();
            event.getSubject().sendMessage(MasterUtil.commonSay(event).plus(HelloEnums.ADDWEATHER.getSayHello()).plus("\n明天消息将会在8：00准时送达(/≧▽≦)/"));
            redisUtil.lLeftPush("weather", String.valueOf(id));
            return ListeningStatus.LISTENING;
        }
        List<String> weather = redisUtil.lRange("weather", 0, -1);
        redisUtil.lLeftPush("weather", String.valueOf(event.getSubject().getId()));
        cpService.get(event, nrArr);
        return ListeningStatus.LISTENING;
    }

}
