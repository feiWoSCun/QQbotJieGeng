package cn.jiegeng.luobin.ashin.util;

import com.github.plexpt.chatgpt.Chatbot;
import net.mamoe.mirai.Bot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * chatbot工具类
 *
 * @author ashinnotfound
 * @date 2022/12/10
 */
@Component
public class ChatbotUtil {

    private static final Map<String,Chatbot> chatbotMap = new HashMap<>();
    private static String sessionToken;
    private static Long qq;
    private static String password;
    private static Bot qqBot;

    @Value("${sessionToken}")
    public void setSessionToken(String sessionToken) {
        ChatbotUtil.sessionToken = sessionToken;
    }

    @Value("${qq}")
    public void setQq(Long qq) {
        ChatbotUtil.qq = qq;
    }

    @Value("${password}")
    public void setPassword(String password) {
        ChatbotUtil.password = password;
    }


    /**
     * 根据sessionId获取对应的Chatbot实体类
     *
     * @param sessionId 会话id
     * @return {@link Chatbot}
     */
    public static Chatbot getChatbot(String sessionId){
        if(chatbotMap.containsKey(sessionId)) return chatbotMap.get(sessionId);
        Chatbot chatbot = new Chatbot(sessionToken);
        chatbotMap.put(sessionId,chatbot);
        return chatbot;
    }
}
