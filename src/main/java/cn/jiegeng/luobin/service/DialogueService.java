package cn.jiegeng.luobin.service;

import net.mamoe.mirai.event.events.MessageEvent;

public interface DialogueService {
 String getDialogues();
 public void addC(MessageEvent event, String[] nrArr);
}
