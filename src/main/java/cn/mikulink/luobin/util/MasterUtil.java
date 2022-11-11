package cn.mikulink.luobin.util;

import cn.mikulink.luobin.command.HelloCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 与主人有关的操作
 */
@Component
public class MasterUtil {
    private static HelloCommand helloCommand;
    @Value("${masterId}")
    static long masterId = 2825097536l;

    @Autowired
    public MasterUtil(HelloCommand helloCommand) {
        this.helloCommand = helloCommand;
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
        if (nowTime == 1) return "早上好";
        else return nowTime == 2 ? "中午好" : "晚上好";
    }

    /**
     * 判断是否以指令开头
     *
     * @param target
     * @return
     */
    public static Boolean judgeIfStartWith(String target) {
        if (StringUtils.isEmpty(target)) return false;
        else return helloCommand.getCommand().stream().anyMatch(t -> target.startsWith(t));
    }

    /**
     * 清除开头的指令
     *
     * @param target
     * @return
     */
    public static String[] clearBegin(String target) {
        String s = helloCommand.getCommand().stream().filter(t -> target.startsWith(t)).findFirst().get();
        String substring = target.substring(s.length());
        return substring.split(" ");
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
}
