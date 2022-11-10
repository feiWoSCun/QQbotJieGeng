package cn.mikulink.luobin.service.impl;

import cn.mikulink.luobin.mapper.DialogueMapper;
import cn.mikulink.luobin.service.DialogueService;
import cn.mikulink.luobin.util.DateUtils;
import cn.mikulink.luobin.util.MathUtils;
import cn.mikulink.luobin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service("dialogueService")
@Transactional
public class DialogueServiceImpl implements DialogueService {
    final RedisUtil redisUtil;
    final DialogueMapper dialogueMapper;

    public DialogueServiceImpl(RedisUtil redisUtil, DialogueMapper dialogueMapper) {
        this.redisUtil = redisUtil;
        this.dialogueMapper = dialogueMapper;
    }

    /**
     * 得到当前时间可以用的独白
     * 针对主人的操作，用的很少.不存redis
      * @return
     */
    @Override
    public String getDialogues() {
        //当前时间
        int nowTime = DateUtils.getNowTime();
        List<String> dialogues = dialogueMapper.getDialogues(nowTime);
        String[] temp=new String[dialogues.size()];
       String str[]=(String[]) dialogues.toArray(temp);
        double random = MathUtils.getRandom();
        //随机语录
        int i=(int)(random*str.length);
        return str[i];
    }
}
