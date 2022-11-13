package cn.jiegeng.luobin.event;

import cn.jiegeng.luobin.annotation.CommandAnnotation;
import cn.jiegeng.luobin.command.enums.ApiUrl;
import cn.jiegeng.luobin.command.enums.HelloEnums;
import cn.jiegeng.luobin.domain.dto.AnswerDto;
import cn.jiegeng.luobin.mapper.DialogueMapper;
import cn.jiegeng.luobin.mapper.PrivilegeMapper;
import cn.jiegeng.luobin.service.DialogueService;
import cn.jiegeng.luobin.service.PrivilegeService;
import cn.jiegeng.luobin.util.MasterUtil;
import cn.jiegeng.luobin.util.StringUtils;
import cn.jiegeng.luobin.util.apiutil.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.ToString;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 群消息事件,可接受私聊
 *
 * @author 罗彬的电脑
 * @date 2022/11/13
 */
@Component
@Data
public class GroupMessageEvent {
    private static PrivilegeMapper privilegeMapper;

    private static DialogueMapper mapper;

    private static DialogueService dialogueService;
    private static PrivilegeService privilegeService;


    @Autowired
    public GroupMessageEvent(DialogueMapper mapper, DialogueService dialogueService, PrivilegeService privilegeService, PrivilegeMapper privilegeMapper) {
        this.mapper = mapper;
        this.dialogueService = dialogueService;
        this.privilegeService = privilegeService;
        this.privilegeMapper = privilegeMapper;
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
        String url = ApiUrl.Chat.getUrl() + nrArr[0];
        String s = HttpUtil.get(url);
        AnswerDto answerDto = JSON.parseObject(s, AnswerDto.class);
        long id = event.getSender().getId();
        if ("未获取到相关信息".equals(answerDto.getContent())) {
            answerDto.setContent(HelloEnums.SORRY.getSayHello() + dialogueService.getDialogues());
        }
        event.getSubject().sendMessage(MasterUtil.commonSay(event).plus(answerDto.getContent()));
        return ListeningStatus.LISTENING;
    }

    @CommandAnnotation(method = "桔梗桔梗 add")
    public static ListeningStatus addCommand(MessageEvent event, String[] nrArr) {
        if (StringUtils.isNull(nrArr) || !StringUtils.isNumber(nrArr)) {
            event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("阁下传入的数据格式有误,添加失败＞﹏＜"));
            return ListeningStatus.LISTENING;
        }
        String number = nrArr[0];
        String privilege = nrArr[1];
        int i=1;
        new Thread(()->privilegeMapper.addPrivilege(number, Integer.valueOf(privilege))).start();
        if (i == 1) {
            event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("添加管理员+" + nrArr[0] + "成功" + "＞﹏＜"));
            return ListeningStatus.LISTENING;
        } else {
            event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("桔梗不知道发生什么事了,添加失败＞﹏＜"));
            return ListeningStatus.LISTENING;
        }
    }
}
