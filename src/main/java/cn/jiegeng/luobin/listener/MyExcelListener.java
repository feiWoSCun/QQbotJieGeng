package cn.jiegeng.luobin.listener;

import cn.jiegeng.luobin.domain.dto.City;
import cn.jiegeng.luobin.service.PrivilegeService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.stereotype.Component;

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
    private int ROW;
    List<City> city = new ArrayList<City>();
    int count = 0;
    //这里注入不进来？？？
/*    @Resource
    PrivilegeService privilegeService;*/
    PrivilegeService privilegeService;

    public MyExcelListener() {
    }

    public MyExcelListener(PrivilegeService privilegeService, Long count) {
        this.privilegeService = privilegeService;
        ROW = Math.toIntExact(count);
        System.out.println("构造方法调用成功");

    }

    @Override
    public void invoke(City data, AnalysisContext context) {
        city.add(data);
        count++;
        if (count >= ROW) {
            int i = privilegeService.addCity(city);
            count = 0;
            city.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
