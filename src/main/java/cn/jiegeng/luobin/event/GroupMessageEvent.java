package cn.jiegeng.luobin.event;

import cn.jiegeng.luobin.annotation.CommandAnnotation;
import cn.jiegeng.luobin.command.enums.ApiUrl;
import cn.jiegeng.luobin.command.enums.HelloEnums;
import cn.jiegeng.luobin.configuration.bot.JieGengBot;
import cn.jiegeng.luobin.domain.dto.AnswerDto;
import cn.jiegeng.luobin.mapper.DialogueMapper;
import cn.jiegeng.luobin.mapper.PrivilegeMapper;
import cn.jiegeng.luobin.mapper.WeatherMapper;
import cn.jiegeng.luobin.quartz.Weather;
import cn.jiegeng.luobin.service.CpService;
import cn.jiegeng.luobin.service.DialogueService;
import cn.jiegeng.luobin.service.PrivilegeService;
import cn.jiegeng.luobin.util.MasterUtil;
import cn.jiegeng.luobin.util.RedisUtil;
import cn.jiegeng.luobin.util.apiutil.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.github.plexpt.chatgpt.Chatbot;
import lombok.Data;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 群消息事件,可接受私聊
 *
 * @author 罗彬的电脑
 * @date 2022/11/13
 */
@Component
@Data
public class GroupMessageEvent {
    private static CpService cpService;
    private static RedisUtil redisUtil;
    private static PrivilegeMapper privilegeMapper;

    private static DialogueMapper mapper;

    private static DialogueService dialogueService;
    private static PrivilegeService privilegeService;
    private static WeatherMapper weatherMapper;

    @Autowired
    public GroupMessageEvent(DialogueMapper mapper, DialogueService dialogueService,
                             PrivilegeService privilegeService, PrivilegeMapper privilegeMapper,
                             RedisUtil redisUtil, CpService cpService, WeatherMapper weatherMapper) {
        GroupMessageEvent.mapper = mapper;
        GroupMessageEvent.dialogueService = dialogueService;
        GroupMessageEvent.privilegeService = privilegeService;
        GroupMessageEvent.privilegeMapper = privilegeMapper;
        GroupMessageEvent.redisUtil = redisUtil;
        GroupMessageEvent.cpService = cpService;
        GroupMessageEvent.weatherMapper = weatherMapper;
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

        Chatbot chatbot = new Chatbot("eyJhbGciOiJkaXIiLCJlbmMiOiJBMjU2R0NNIn0..MR9Rk_tlAbnGVxqP.5XMi3e0hpOBfUNYyv6_nFwzREZoRN5nzJNv7DH4PUSHAw_aV--p6VEbKiCzWbpBNhuYEFumt0DUV-mmHb6IVdo51nvx6JLktBpn-w7Wjam8uCFkicTCAZ1VtKm4TzYkN336tGmlMCSqoiqkUCOm-onYhC7BIfKExku1oinv8HU4TSrDo39n6JJb16LnqfnEanji7OEbrGkJpApSHaJp982vZdRPOov4uf8ke0JZWQmHB9mXl8uDoAW7rLJOeynYafAHPL7lr4T-ANBhlrltdZVTvHYcEqxv08-HSJbnh_0IvCrQEn5x3eclxySZRzHdBWwSLF_13EpbOvk4_lfazphwujC-xtt6_y8Ggm_F11Nu8NurrVZqecobmKe1Jfs6twOdBmem9swol7TCdn7inCaR2gcU4vSJrn4_7RkqTUWm5SKk8yFFcBwyB2tXP-FpDqTCI4HeDOPQsIicbpVg2LG_8Y4lBqjNlXSyiDHaGShkM_DcHnkahbFrLuSnCcLiS0YEPT78OPMNL1aTUnQ4L0vbNCib_q44C7Yzxpj2bwWI3P1tFuIUUxJdplAON0sFI0zBkdgZF-vqkwdeDduBzQdUAUs4pivcpeSfNHIyNVPNMAoHV9qivylD8fzdiYMy-xvh_1bNB3ZdRKCfhTOu2yX4W6AmtrTS9wKtYARJmsnh8eSDHBscBqpLiT4nt4YQGDKZcZklBLwA643PtlTlfEMEX-YTn0eEnx5ptJ00wZsot_jD7mNvhWr1PZHLLiNvNpbSrazx4XykmW74iGNIhBs9NJdyq4js2M2INTNBEbadwPN4zf9NZwaQI4gQyjFdwdsuLg2LFizqoQOWhitnYOuO42YbTSiqjPLMj3-RS4cUg9_kZ1QORETSsml3mFB4jGc3Dl-4NAepZkKuz1U3d0fXECxlhXX5je71REaYp_iZUfIcZg2HDA79YJnZaTGoZ2IXbuueG-jSdZG-KbvN7bH7AQxEvkcKkByQaqEOFOI2XESfJ7iMrJvNCgzaBOh2qUCb2F85BAVP6uURWxgcuOfbXbO9A2Gz1abafnCuhZyiwT-r0GRYJwOqcv3d523MK6uwZfe1CExY2fvC9iEYqzfGuwl04oJdd9kI0qOIuyKBDEAznPfSesopQ5i0sM6Wn62r1m-PhGTeGeOfSqeyWb_dCC2VrjVb2YE4DuYcGUIwLLmVtLcoZH9YvtuYh8RKse6Q2jzFU5-430Ws5UoxYSBnPPuCM9MbtLLZSk2vmbF49Bl_nlM2lAnEl5w1llxxC2EmVBbaPFWn9U1F8N9rNugIB_FrgvNIE8Mtx_Ylmr_Z4twuUPs87Vsg8FIjtWuNaV_VWpcKfo6duUCc-A2mtdN68ezSSz0-FXbfhYkcLYOLokyCVeR7xwbPGpgdT23heDNbf5WSInC6Bd4RpOTj9BKNOlm3kPAUXWgcsMezTwNW_J7PbG1knJvFffYUDOaPdBwyuoX0s8gDDtxHU--707JMKORVimKsctR91zwzLwBWa659B9qb7So558QEc6F05QMTIDF6YpHh1hfIamqmlF0v3t05DJQO91NzT4HRRAJRPKjIyCArwVe-s-6zmbeAbcG-kVPdhnf4sydfeCuf9hcDFci0bi5kjn053fbmjtW_0PqhXOEWCbNYfod1QJSkBfbyprGdWxk3Jdbmt1A3ORrPytBtzexYcR-q0wP5ry9k76FK7WuMJOKPrczfbLKk2GUVgkyeag15h0Hw6UyKjY_xU8B0asSVTlpBYekbOUrI2O80VbjXN73r58iMxE4wAY1tTMi4pg2YeQY1ifPuPf1YivfGd1IGHJrODA1BPV1U1zp7kCe7N7CMI4d19G58vQ4GCeKnKPwXky2vqlKPSVSTxK36IGw28tgcohhCFC55cyytw-x-442jqktJ-Dvk8QoSP_uUc8eL51dPI8AnGz9bOSE4A-T8yBax9Imp5e7atTcNXPkRKqn0IJgPbvI8ZlK-0BACl3bc7Q8xwV7MVA0CB5XAFmGGPCtR6oDiZnWum2vQKlQmnx2I27UeWXLVAXC5W8CNrMF3quR0P_jwNlXDjow1xSL9EzTh0n3q9J_gRUaDMS3iWTM7ED0SeceYE-o0QY5LSsx259a3TQupZVCxP-4aNPiNr_dHkR07lja7t_JArNY0KRm-VqU8ZNWORmUOIcqgto6gdDbp_kpAhIay-FHGRYwQ68FZplAj5TQkXOkQr27y8Nf-3PhLNApdNE0InYYoSLDZ3OLokLz91uf8VVJhUC34xm8zfe4-XsAhSmRtSqy1NalDyBUgOB7uejuEx--VDpbGeT7zJ600Sv6JnbMLLXHkx_vN7hfaci4C9eglLfTnkCE-b6RQPW67qesTzpNXt2PB6Oxgj78gPP_R13ed1T4kQwkfzumePIOmhRLDqQmK2vnEDtztGv5zqqnDj7FJiU2Q8gniBSEvDeXT07dUOg5bAOZwcMGkchq4vtlUplkFIWnAns7iC4vU.vJzteuW5i1OgLkRNAsiKDw",
                "QsPLXXksp_XfqPnQLWASD3BWyajGLYkrQoL4b1BIEFs-1676450095-0-1-50aae9d4.9ae0732f.794c3f0f-160",
                "user-agent");
        chatbot.setHost("http://proxyaddr:5000");

        Map<String, Object> chatResponse = chatbot.getChatResponse("hello");
        System.out.println(chatResponse.get("message"));


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
        String city = weatherMapper.getCity(nrArr[0]);
        /*redisUtil.lLeftPush("weather", "2097839141");
        redisUtil.lLeftPush("weather", "2698087831");
        redisUtil.lLeftPush("weather", "645680833");*/
        //群号码
        long groupId = -1L;
        long qq = -1L;
        Weather weather = new Weather();
        if (event instanceof net.mamoe.mirai.event.events.GroupMessageEvent) {
            groupId = ((net.mamoe.mirai.event.events.GroupMessageEvent) event).getGroup().getId();
            event.getSubject().sendMessage(MasterUtil.commonSay(event).plus(HelloEnums.ADDWEATHER.getSayHello()).plus("\n明天消息将会在8：00准时送达(/≧▽≦)/"));
            redisUtil.hPut("weather", String.valueOf(groupId), city);

            Group group = JieGengBot.getBot().getGroup(groupId);

            weather.jump(nrArr[0], group);
            return ListeningStatus.LISTENING;
        }
        //todo:修改成hash key->账号，value->城市
      /*  List<String> weather = redisUtil.lRange("weather", 0, -1);
        redisUtil.lLeftPush("weather", String.valueOf(event.getSubject().getId()));
        cpService.get(event, nrArr);*/
        qq = event.getSubject().getId();
        redisUtil.hPut("weather", String.valueOf(qq), city);
        // Map<String, String> weather = redisUtil.hGetAll("weather",String.class,String.class);
        Friend friend = JieGengBot.getBot().getFriend(qq);
        weather.jump(nrArr[0], friend);
        return ListeningStatus.LISTENING;
    }

}
