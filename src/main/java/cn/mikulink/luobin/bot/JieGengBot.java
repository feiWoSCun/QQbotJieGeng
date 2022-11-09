package cn.mikulink.luobin.bot;


import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Description: \兔子万岁/
 * @author: MikuLink
 * @date: 2020/12/14 13:46
 **/
@Component
public class JieGengBot implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(JieGengBot.class);

    //一个实例只给一个bot，暂时不考虑一个实例允许部署多个bot
    private static Bot bot;

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

    /**
     * 启动BOT
     */
    private void startBot() {
        if (null == botAccount || null == botPwd) {
            System.err.println("*****未配置账号或密码*****");
        }

        bot = BotFactory.INSTANCE.newBot(botAccount, botPwd, new BotConfiguration() {
            {
                //保存设备信息到文件deviceInfo.json文件里相当于是个设备认证信息
                fileBasedDeviceInfo(deviceInfo);
                setProtocol(MiraiProtocol.IPAD); // 切换协议
            }
        });
        bot.login();

        //设置https协议，已解决SSL peer shut down incorrectly的异常
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");

        // 这个和picbotx 还是不太一样 那个不会占用主线程
        // 这里必须要启新线程去跑bot 不然会占用主线程
        new Thread(() -> {
            bot.join();
        }).start();
    }
}
