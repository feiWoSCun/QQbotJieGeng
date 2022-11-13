package cn.jiegeng.luobin.service.impl;

import cn.jiegeng.luobin.mapper.PrivilegeMapper;
import cn.jiegeng.luobin.service.PrivilegeService;
import cn.jiegeng.luobin.util.StringUtils;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    final PrivilegeMapper privilegeMapper;

    @Autowired
    public PrivilegeServiceImpl(PrivilegeMapper privilegeMapper) {
        this.privilegeMapper = privilegeMapper;
    }

    /**
     * 添加用户，授予权限
     *
     * @param nrArr
     * @return
     */
}
