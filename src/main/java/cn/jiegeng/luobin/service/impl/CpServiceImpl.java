package cn.jiegeng.luobin.service.impl;

import cn.jiegeng.luobin.command.enums.HelloEnums;
import cn.jiegeng.luobin.mapper.CpMapper;
import cn.jiegeng.luobin.service.CpService;
import cn.jiegeng.luobin.util.MasterUtil;
import cn.jiegeng.luobin.util.RedisUtil;
import cn.jiegeng.luobin.util.StringUtils;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

@Service
public class CpServiceImpl implements CpService {
    @Resource(name = "pool")
    ExecutorService myPool;
    final RedisUtil redisUtil;
    final CpMapper cpMapper;

    public CpServiceImpl(CpMapper cpMapper, RedisUtil redisUtil) {
        this.cpMapper = cpMapper;
        this.redisUtil = redisUtil;
    }

    /**
     * 添加kv
     * @param key
     * @param value
     * @param number
     * @return
     */
    @Override
    public int add(String key, String value, long number) {
        Thread thread = new Thread(() -> {
            int b = cpMapper.addCp(key, value, number);
        });
        thread.setName("添加存储kv线程");
        myPool.execute(thread);
        return 1;
    }
    public void Store(MessageEvent event,String[] nrArr){
        if(StringUtils.isNull(nrArr)||nrArr.length>=3){
            event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("存储的格式有误"));
             return;
        }
        if(nrArr.length==2){
            if(! StringUtils.isNumber(nrArr[nrArr.length - 1]))
                event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("时间格式有误"));
        }
        String uuid = MasterUtil.getUUID(event);
        Thread thread = new Thread(() -> redisUtil.hPut(String.valueOf(event.getSender().getId())+"lb", uuid, nrArr[0]));
        thread.setName("存储用户的kv--redis线程");
        myPool.execute(thread);
        event.getSubject().sendMessage(MasterUtil.commonSay(event).plus(HelloEnums.ADDSUCCESS.getSayHello()).plus(uuid));
    }

    /**
     * 获取存储的数据
     * @param event
     * @param nrArr
     */
    public void get(MessageEvent event,String[] nrArr){
        if (nrArr.length >= 2) {
            event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("参数格式错误（＞人＜）"));
            return;
        }
        long id = event.getSender().getId();
        Thread thread = new Thread(() -> {
            String o = (String) redisUtil.hGet(String.valueOf(id)+"lb", nrArr[0]);
            if (!StringUtils.isEmpty(o)) {
                event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("数据读取成功!给~\n").plus(o));
            } else {
                event.getSubject().sendMessage(MasterUtil.commonSay(event).plus(HelloEnums.GETWRONG.getSayHello()));
            }
        });
        thread.setName("用户读取线程");
        myPool.execute(thread);
    }
}
