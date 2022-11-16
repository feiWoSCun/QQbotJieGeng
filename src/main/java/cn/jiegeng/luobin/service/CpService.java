package cn.jiegeng.luobin.service;

import net.mamoe.mirai.event.events.MessageEvent;

public interface CpService {
    int add(String key, String value, long number);

    public void Store(MessageEvent event, String[] nrArr);

    public void get(MessageEvent event, String[] nrArr);
}
