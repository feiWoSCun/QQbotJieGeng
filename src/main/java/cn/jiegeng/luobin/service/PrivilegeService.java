package cn.jiegeng.luobin.service;

import net.mamoe.mirai.event.events.MessageEvent;

public interface PrivilegeService {
    void addUser(MessageEvent event,String[] nrArr);
}
