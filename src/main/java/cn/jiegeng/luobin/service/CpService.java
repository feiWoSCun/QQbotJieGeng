package cn.jiegeng.luobin.service;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author feiwoscun
 */
public interface CpService {
    /**
     * @param key
     * @param value
     * @param number
     * @return
     * */
    int add(String key, String value, long number);

    public void Store(MessageEvent event, String[] nrArr);

    public void get(MessageEvent event, String[] nrArr);
}
