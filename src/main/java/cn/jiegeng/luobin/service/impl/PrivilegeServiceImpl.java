package cn.jiegeng.luobin.service.impl;

import cn.jiegeng.luobin.mapper.PrivilegeMapper;
import cn.jiegeng.luobin.service.PrivilegeService;
import cn.jiegeng.luobin.util.MasterUtil;
import cn.jiegeng.luobin.util.StringUtils;
import lombok.Data;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@Data
public class PrivilegeServiceImpl implements PrivilegeService {
    final PrivilegeMapper privilegeMapper;

    @Autowired
    public PrivilegeServiceImpl(PrivilegeMapper privilegeMapper) {
        this.privilegeMapper = privilegeMapper;
    }

    @Override
    public void addUser(MessageEvent event, String[] nrArr) {
        new Thread(() -> {
            int i = 0;
            if (StringUtils.isNull(nrArr) || !StringUtils.isNumber(nrArr)) {
                event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("阁下传入的数据格式有误,添加失败＞﹏＜"));
            }
            String number = nrArr[0];
            String privilege = nrArr[1];
            i = privilegeMapper.addPrivilege(number, Integer.valueOf(privilege));
            if (i == 1) {
                event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("添加管理员:" + nrArr[0] + "成功" + "＞﹏＜"));
            } else {
                event.getSubject().sendMessage(MasterUtil.commonSay(event).plus("桔梗不知道发生什么事了,添加失败＞﹏＜"));
            }
        }).start();
    }


    /**
     * 添加用户，授予权限
     *
     * @param nrArr
     * @return
     */
}
