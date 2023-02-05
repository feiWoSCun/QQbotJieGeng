package cn.jiegeng.luobin.service;

import cn.jiegeng.luobin.domain.dto.City;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.List;

public interface PrivilegeService {
    void addUser(MessageEvent event,String[] nrArr);

    /**
     * @param cities
     * @return 添加的数目
     */
    int addCity(List<City> cities);

    String getSpell(String location);
}
