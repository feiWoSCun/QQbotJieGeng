package cn.jiegeng.luobin.service.impl;

import cn.jiegeng.luobin.command.enums.HelloEnums;
import cn.jiegeng.luobin.domain.dto.City;
import cn.jiegeng.luobin.mapper.PrivilegeMapper;
import cn.jiegeng.luobin.service.PrivilegeService;
import cn.jiegeng.luobin.util.MasterUtil;
import cn.jiegeng.luobin.util.RedisUtil;
import cn.jiegeng.luobin.util.StringUtils;
import lombok.Data;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 28250
 */
@Component
@Data
public class PrivilegeServiceImpl implements PrivilegeService {
    @Resource(name = "pool")
    ExecutorService myPool;
    final RedisUtil redisUtil;
    final PrivilegeMapper privilegeMapper;

    @Autowired
    public PrivilegeServiceImpl(PrivilegeMapper privilegeMapper, RedisUtil redisUtil) {
        this.privilegeMapper = privilegeMapper;
        this.redisUtil = redisUtil;
    }

    @Override
    public void addUser(MessageEvent event, String[] nrArr) {
        /**
         * 校验数据
         */
        Thread thread = new Thread(() -> {
            int i = 0;
            if (StringUtils.isNull(nrArr) || !StringUtils.isNumber(nrArr)) {
                event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("阁下传入的数据格式有误,添加失败＞﹏＜"));
            }
            String number = nrArr[0];
            String privilege = nrArr[1];
            /**
             * 判断是否添加过了管理员
             */
            String s = redisUtil.get(number);
            if (s == null) {
                i = privilegeMapper.addPrivilege(number, Integer.valueOf(privilege));
                if (i >= 1) {
                    redisUtil.set(nrArr[0], nrArr[1]);
                    event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("添加管理员:" + nrArr[0] + " 成功" + "＞﹏＜"));
                } else {
                    event.getSubject().sendMessage(MasterUtil.commonSay(event).plus(HelloEnums.DONTKNOW.getSayHello()));
                }
            }
            /**
             * 执行更新操作
             */
            else {
                int j = privilegeMapper.changePrivilege(number, Integer.valueOf(privilege));
                if (j >= 1) {
                    redisUtil.set(nrArr[0], nrArr[1]);
                    event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("更新管理员:" + nrArr[0] + " 成功" + "＞﹏＜"));
                } else {
                    event.getSubject().sendMessage(MasterUtil.commonSay(event).plus(HelloEnums.DONTKNOW.getSayHello()));
                }

            }
        });
        thread.setName("添加用户线程");
        myPool.execute(thread);
    }

    /**
     * 添加城市
     * @param cities
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int addCity(List<City> cities) {
        AtomicInteger i= new AtomicInteger();
        Thread thread = new Thread(() ->{
            i.set(privilegeMapper.addCity(cities));
        });
        thread.setName("添加城市的线程");
        myPool.execute(thread);
        System.out.println(thread.getId()+thread.getName());
        return i.get();
    }

    @Override
    public String getSpell(String location) {
        return null;
    }


    /**
     * 添加用户，授予权限
     *
     * @param nrArr
     * @return
     */
}
