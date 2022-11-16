package cn.jiegeng.luobin.bot;


import cn.jiegeng.luobin.command.HelloCommand;
import cn.jiegeng.luobin.domain.dto.Dialogue;
import cn.jiegeng.luobin.domain.vo.UserPri;
import cn.jiegeng.luobin.event.handler.BaseEvent;
import cn.jiegeng.luobin.mapper.CommandMapper;
import cn.jiegeng.luobin.mapper.DialogueMapper;
import cn.jiegeng.luobin.mapper.PrivilegeMapper;
import cn.jiegeng.luobin.util.RedisUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 桔梗bot
 */
@Component
public class JieGengBot implements ApplicationRunner {

    final DialogueMapper dialogueMapper;

    final RedisUtil redisUtil;
    final HelloCommand helloCommand;
    final BaseEvent baseEvent;
    final CommandMapper commandMapper;
    final PrivilegeMapper privilegeMapper;
    //一个实例只给一个bot，暂时不考虑一个实例允许部署多个bot
    private static Bot bot;

    @Autowired
    public JieGengBot(BaseEvent baseEvent, CommandMapper commandMapper,
                      HelloCommand helloCommand, PrivilegeMapper privilegeMapper,
                      RedisUtil redisUtil, DialogueMapper dialogueMapper) {
        this.baseEvent = baseEvent;
        this.commandMapper = commandMapper;
        this.helloCommand = helloCommand;
        this.privilegeMapper = privilegeMapper;
        this.redisUtil = redisUtil;
        this.dialogueMapper = dialogueMapper;
    }

    public static Bot getBot() {
        return bot;
    }

    //账号
    @Value("${bot.account:}")
    private Long botAccount;
    //密码
    @Value("${bot.pwd:}")
    private String botPwd;
    //设备认证信息文件
    private static final String deviceInfo = "deviceInfo.json";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.startBot();
    }


    private void startBot() {
        if (null == botAccount || null == botPwd) {
            System.err.println("*****未配置桔梗账号或密码*****");
        }
        bot = BotFactory.INSTANCE.newBot(botAccount, botPwd, new BotConfiguration() {
            {
                //保存设备信息到文件deviceInfo.json文件里相当于是个设备认证信息
                fileBasedDeviceInfo(deviceInfo);
                setProtocol(MiraiProtocol.ANDROID_PAD); // 切换协议
            }
        });
        bot.login();
        //注册打招呼语录
        List<Dialogue> dialogsDto = dialogueMapper.getDialogsDto();
        dialogsDto.stream().filter(t->t.getAnswerId()!=0&&t.getDialogueTime()==5).forEach(t->redisUtil.sAdd(String.valueOf(t.getAnswerId()),t.getNr()));
        //注册指令
        Set<String> command = commandMapper.getCommand();
        helloCommand.setCommand(command);
        redisUtil.SAddSet("command",command);
        //注册事件
        List<ListenerHost> events = Arrays.asList(
                baseEvent
        );
        /**
         * 注册事件给桔梗
         */
        for (ListenerHost event : events) {
            GlobalEventChannel.INSTANCE.registerListenerHost(event);
        }
        //注册用户权限
        List<UserPri> itsPri = privilegeMapper.getAllQQNumberAndItsPri();
        helloCommand.setUserPris(itsPri);
        for (UserPri userPri : itsPri) {
            redisUtil.set(userPri.getNumber(), String.valueOf(userPri.getPrivilege()));
        }
        //设置https协议，已解决SSL peer shut down incorrectly的异常
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");

        // 这个和picbotx 还是不太一样 那个不会占用主线程
        // 这里必须要启新线程去跑bot 不然会占用主线程
      new Thread(() -> {
            bot.join();
        }).start();
    }
}
