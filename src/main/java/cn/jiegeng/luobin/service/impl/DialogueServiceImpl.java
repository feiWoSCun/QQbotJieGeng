package cn.jiegeng.luobin.service.impl;

import cn.jiegeng.luobin.command.enums.HelloEnums;
import cn.jiegeng.luobin.domain.dto.Dialogue;
import cn.jiegeng.luobin.domain.vo.Command;
import cn.jiegeng.luobin.mapper.CommandMapper;
import cn.jiegeng.luobin.mapper.DialogueMapper;
import cn.jiegeng.luobin.service.DialogueService;
import cn.jiegeng.luobin.util.DateUtils;
import cn.jiegeng.luobin.util.MasterUtil;
import cn.jiegeng.luobin.util.MathUtils;
import cn.jiegeng.luobin.util.RedisUtil;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service("dialogueService")
@Transactional
public class DialogueServiceImpl implements DialogueService {
    @Resource(name = "pool")
    ExecutorService myPool;
    final CommandMapper commandMapper;
    final RedisUtil redisUtil;
    final DialogueMapper dialogueMapper;

    public DialogueServiceImpl(RedisUtil redisUtil, DialogueMapper dialogueMapper, CommandMapper commandMapper) {
        this.redisUtil = redisUtil;
        this.dialogueMapper = dialogueMapper;
        this.commandMapper = commandMapper;
    }

    /**
     * 得到当前时间可以用的独白
     * 针对主人的操作，用的很少.不存redis
     *
     * @return
     */
    @Override
    public String getDialogues() {
        //当前时间
        int nowTime = DateUtils.getNowTime();
        List<String> dialogues = dialogueMapper.getDialogues(nowTime);
        String[] temp = new String[dialogues.size()];
        String str[] = (String[]) dialogues.toArray(temp);
        double random = MathUtils.getRandom();
        //随机语录
        int i = (int) (random * str.length);
        return str[i];
    }

    /**
     * 添加命令
     *
     * @param event
     * @param nrArr
     */
    public void addC(MessageEvent event, String[] nrArr) {
        if (nrArr.length != 2) {
            event.getSubject().sendMessage(MasterUtil.commonSay(event).plus(HelloEnums.FORMATWRONG.getSayHello()));
            return;
        }
        Thread thread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());
            int l = Integer.valueOf(String.valueOf(System.currentTimeMillis()).substring(4));
            Command command = Command.builder().date(format).user(String.valueOf(event.getSubject().getId())).key(nrArr[0]).id(l).build();
            Dialogue dialogue = Dialogue.builder().dialogueTime(5).nr(nrArr[1]).answerId(l).build();
            int i = commandMapper.addCommand(command);
            int j = dialogueMapper.addDialogue(dialogue);
            if (i == 1 && j == 1) {
                event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("添加命令" + nrArr[0] + "成功"));
                redisUtil.sAdd(String.valueOf(l), nrArr[1]);
                redisUtil.sAdd("command", nrArr[0]);
            } else event.getSubject().sendMessage(MasterUtil.commonSay(event).plus(HelloEnums.DONTKNOW.getSayHello()));
        });
        thread.setName("添加指令的线程");
        myPool.execute(thread);
    }
}
