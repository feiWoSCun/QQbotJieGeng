package cn.jiegeng.luobin.controller;

import cn.jiegeng.luobin.domain.dto.City;
import cn.jiegeng.luobin.listener.MyExcelListener;
import cn.jiegeng.luobin.service.PrivilegeService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import net.dreamlu.mica.core.result.R;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.Reader;
import java.net.URISyntaxException;

/**
 * @author: feiWoSCun
 * @description:
 * @email: 2825097536@qq.com
 * @date: 2023/2/4
 */
//todo: 导入各个城市
@RestController
public class ExcelController {
    @Resource
    PrivilegeService privilegeService;

    @RequestMapping("/get/{count}")
    public void importExcel(@PathVariable Long count) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String template = null;
        try {
            template = Thread.currentThread().getContextClassLoader().getResource("template").toURI().getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String path = template + File.separator + "城市列表.xls";
        File file = new File(path);
        if (file.exists()) {
            ExcelReaderBuilder read = EasyExcel.read(path, City.class, new MyExcelListener(privilegeService,count));
            ExcelReader build = read.build();
            build.read();
            build.close();
            stopWatch.stop();
            System.out.println(stopWatch.getTotalTimeSeconds());
        } else {
            System.out.println("1找不到文件");
        }
    }


}
