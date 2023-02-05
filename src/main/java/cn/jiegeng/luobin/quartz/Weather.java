package cn.jiegeng.luobin.quartz;/**
 * 舞台再大，不上去永远是观众，静下心沉住气慢慢来
 *
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
import net.mamoe.mirai.contact.Friend;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 舞台再大，不上去永远是观众，静下心沉住气慢慢来
 *
 * @email: 2825097536@qq.com
 * @author: feiWoSCun
 * @creat: 2022/12/17
 */
@Component
@EnableScheduling
public class Weather {

    private static RedisUtil redisUtil;

    public Weather(RedisUtil redisUtil) {
        Weather.redisUtil = redisUtil;
    }

    public static WeatherVo getWeather() {

        String s = HttpUtil.get(ApiUrl.Wheather.getUrl());

        return JSON.parseObject(s, WeatherVo.class);

    }

    @Scheduled(cron = "50 8,14 * * * ?")
    public void configureTasks() {
        System.out.println(new Date());
        WeatherVo weather = getWeather();
        StringBuilder builder = new StringBuilder();
        builder.append("我希望每天阳光明媚，也希望你心情也是这样(/≧▽≦)/" + "\n");
        builder.append(weather.getResults().get(0).getLocation().getName() + "\n"
                + weather.getResults().get(0).getNow().getText()
                + "\n" + "现在的温度:" + weather.getResults().get(0).getNow().getTemperature() + "℃");

        System.out.println(weather.getResults().get(0).getLocation());
        System.out.println(weather.getResults().get(0).getNow().getText());
        //订阅的朋友 redis
        List<String> id = redisUtil.lRange("weather", 0, -1);
        //拿到所有的订阅朋友
        List<String> collect = JieGengBot.getBot().getFriends().stream().map(t -> String.valueOf(t.getId()))
                .filter(t -> id.contains(t)).collect(Collectors.toList());
        for (String s : collect) {
            Friend friend = JieGengBot.getBot().getFriend(Long.valueOf(s));
            if (Objects.nonNull(friend)) {
                friend.sendMessage(weather.getResults().get(0).getLocation().getName() + "\n"
                        + weather.getResults().get(0).getNow().getText()
                        + "\n" + weather.getResults().get(0).getNow().getTemperature());
                WeatherVo.ResultsDTO.NowDTO now = weather.getResults().get(0).getNow();
                StringBuilder builder1 = new StringBuilder();
                builder1.append("桔梗温馨提示:" + "\n");
                if (Float.valueOf(now.getTemperature()) < 10.0) {
                    builder1.append("今天天气较冷，请注意保暖哦");
                }
                if (Integer.valueOf(now.getCode()) <= 19 && Integer.valueOf(now.getCode()) >= 10) {
                    builder1.append("\n");
                    builder1.append("今天可能会有小雨，出行请注意带伞(*/ω＼*)");
                }
                if (builder1.toString().length() >= 8) {
                    friend.sendMessage(builder.toString());
                }

            }
        }
    }
}
