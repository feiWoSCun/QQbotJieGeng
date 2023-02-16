package cn.jiegeng.luobin.quartz;/**
 * 舞台再大，不上去永远是观众，静下心沉住气慢慢来
 * @email: 2825097536@qq.com
 * @author: feiWoSCun
 * @creat: 2022/12/17
 */

import cn.jiegeng.luobin.configuration.bot.JieGengBot;
import cn.jiegeng.luobin.command.enums.ApiUrl;
import cn.jiegeng.luobin.domain.vo.WeatherVo;
import cn.jiegeng.luobin.util.RedisUtil;
import cn.jiegeng.luobin.util.apiutil.HttpUtil;
import com.alibaba.fastjson.JSON;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 舞台再大，不上去永远是观众，静下心沉住气慢慢来
 *
 * @author feiWoSCun
 * @email 2825097536@qq.com
 * @creat 2022/12/17
 */
@Component
@EnableScheduling
public class Weather {

    private static RedisUtil redisUtil;

    public Weather() {
    }

    @Autowired
    public Weather(RedisUtil redisUtil) {
        Weather.redisUtil = redisUtil;
    }

    public static WeatherVo getWeather(String a) {
        Map<String, String> hashMap = new HashMap<>(4);
        String url = ApiUrl.Wheather.getUrl() + a;
        String s = null;
        s = HttpUtil.get(url);
        return JSON.parseObject(s, WeatherVo.class);

    }

    //todo：群订阅消息有问题 ，正则表达式
    @Scheduled(cron = "* * 8/14 * * *")
    public void configureTasks() {
        //订阅的朋友 redis
        Map<String, String> SubFriend = redisUtil.hGetAll("weather", String.class, String.class);
        Set<String> id = SubFriend.keySet();


        System.out.println(new Date());

        List<Long> allMember = JieGengBot.getBot().getFriends().stream().map(t -> t.getId()).collect(Collectors.toList());
        allMember.addAll(JieGengBot.getBot().getGroups().stream().map(t -> t.getId()).collect(Collectors.toList()));
        //拿到所有的订阅朋友
        List<Long> SubFri = allMember.stream()
                .filter(t -> id.contains(String.valueOf(t))).collect(Collectors.toList());
        for (Long s : SubFri) {
            Friend friend = JieGengBot.getBot().getFriend(s);
            Group group = JieGengBot.getBot().getGroup(Long.valueOf(s));
            jump(SubFriend.get(String.valueOf(s)),friend);
            jump(SubFriend.get(String.valueOf(s)),group);
        }
    }

    public void tellWeatherDes(String s, Contact contact) {
        WeatherVo weather = getWeather(s);
        StringBuilder builder = new StringBuilder();
        builder.append("我希望每天阳光明媚，也希望你心情也是这样(/≧▽≦)/" + "\n");
        builder.append(weather.getResults().get(0).getLocation().getName() + "\n"
                + weather.getResults().get(0).getNow().getText()
                + "\n" + "现在的温度:" + weather.getResults().get(0).getNow().getTemperature() + "℃");
        WeatherVo.ResultsDTO.NowDTO now = weather.getResults().get(0).getNow();
        contact.sendMessage(builder.toString());
        StringBuilder builder1 = new StringBuilder();
        builder1.append("桔梗温馨提示:" + "\n");
        if (Float.valueOf(now.getTemperature()) < 10.0) {
            builder1.append("今天天气较冷，请注意保暖哦");
        }
        if (Integer.valueOf(now.getCode()) <= 19 && Integer.valueOf(now.getCode()) >= 10) {
            builder1.append("\n");
            builder1.append("今天可能会有小雨，出行请注意带伞(*/ω＼*)");
        }
        if (builder1.length() >= 14) {
            contact.sendMessage(builder1.toString());
        }
    }
    public void jump(String s,Contact contact){
        if(Objects.nonNull(contact)){
            tellWeatherDes(s,contact);
        }
    }
}
