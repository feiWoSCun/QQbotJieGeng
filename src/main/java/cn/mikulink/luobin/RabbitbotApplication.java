package cn.mikulink.luobin;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={ DataSourceAutoConfiguration.class })
@MapperScan("cn.mikulink.luobin.mapper")
public class RabbitbotApplication {
    public static void main(String[] args) {
        //启动后业务入口在cn.mikulink.rabbitbot.bot.RabbitBot中
        SpringApplication.run(RabbitbotApplication.class, args);
        System.out.println("=============桔梗 ready=============");

    }
}
