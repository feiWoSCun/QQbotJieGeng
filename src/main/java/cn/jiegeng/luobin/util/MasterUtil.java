package cn.jiegeng.luobin.util;

import cn.jiegeng.luobin.command.HelloCommand;
import cn.jiegeng.luobin.command.enums.HelloEnums;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 与主人有关的操作
 */
@Component
public class MasterUtil {
    public static RedisUtil redisUtil;
    private static HelloCommand helloCommand;
    @Value("${masterId}")
    static long masterId = 2825097536l;

    @Autowired
    public MasterUtil(HelloCommand helloCommand, RedisUtil redisUtil) {
        this.helloCommand = helloCommand;
        this.redisUtil = redisUtil;
    }


    public static String getUUID(MessageEvent event) {
        boolean b = true;
        String substring = null;
        while (b) {
            System.out.println("oooo");
            substring = UUID.nameUUIDFromBytes(String.valueOf(System.currentTimeMillis()).getBytes()).toString().substring(0, 3);
            Set<String> uuid = redisUtil.setMembers("uuid");
            String finalSubstring = substring;
            b = uuid.stream().anyMatch(t -> finalSubstring.equals(t));
        }
        redisUtil.sAdd("uuid", substring);
        return substring;
    }

    /**
     * 判断是否主人id
     *
     * @param targetId
     * @return
     */
    public static Boolean isMaster(long targetId) {
        return masterId == targetId;
    }

    public static String masterSay() {
        int nowTime = DateUtils.getNowTime();
        if (nowTime == 1) return HelloEnums.MORNING.getSayHello();
        else return nowTime == 2 ? HelloEnums.AFTERNOON.getSayHello() : HelloEnums.EVENING.getSayHello();
    }

    /**
     * 判断是否以指令开头
     *
     * @param target
     * @return
     */
    public static Boolean judgeIfStartWith(String target) {
        if (StringUtils.isEmpty(target)) {
            return false;}
        else{
            Set<String> command = redisUtil.setMembers("command");
            return redisUtil.setMembers("command").stream().anyMatch(t -> target.startsWith(t));
        }
    }

    /**
     * 清除开头的指令
     *
     * @param target
     * @return
     */
    public static String[] clearBegin(String target) {
        String s = getCommand(target);
        String substring = target.substring(s.length());
        String[] temp = substring.split(" ");
        List<String> collect = Arrays.stream(temp).filter(t -> !"".equals(t)).collect(Collectors.toList());
        String[] strings = new String[(collect.size())];
        return collect.toArray(strings);
    }

    /**
     * 得到指令
     *
     * @param target
     * @return
     */
    public static String getCommand(String target) {
        List<String> collect = helloCommand.getCommand().stream().filter(t -> target.startsWith(t)).collect(Collectors.toList());
        if (collect.size() == 1) return collect.get(0);
        else return collect.stream().max((a, b) -> a.length() - b.length()).get();
    }

    public static MessageChain commonSay(MessageEvent event) {
        if (event instanceof FriendMessageEvent) {
        } else {
            return new At(event.getSender().getId()).plus("\n");
        }
        //生成一个消息链
        return new PlainText("").plus("");
    }
}
