package cn.jiegeng.luobin.listener;

import cn.jiegeng.luobin.annotation.CommandAnnotation;
import cn.jiegeng.luobin.domain.dto.City;
import cn.jiegeng.luobin.service.PrivilegeService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: feiWoSCun
 * @description:
 * @email: 2825097536@qq.com
 * @date: 2023/2/5
 */
@Component
public class MyExcelListener extends AnalysisEventListener<City> {
    @Resource
     PrivilegeService privilegeService;
    private static final int ROW = 50;
    List<City> city = new ArrayList<City>();
    int count = 0;

    public MyExcelListener() {
    }

    @Override
    public void invoke(City data, AnalysisContext context) {
        city.add(data);
        count++;
        if (count >= ROW) {
            privilegeService.addCity(city);
            city.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
