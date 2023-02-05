package cn.jiegeng.luobin.controller;

import cn.jiegeng.luobin.domain.dto.City;
import cn.jiegeng.luobin.listener.MyExcelListener;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: feiWoSCun
 * @description:
 * @email: 2825097536@qq.com
 * @date: 2023/2/4
 */
//todo: 导入各个城市
@RestController
public class ExcelController {
@RequestMapping("/get")
    public void importExcel(){
    String path="/home/feiwoscun/下载/firefox/城市列表.xls";
    ExcelReaderBuilder read = EasyExcel.read(path, City.class, new MyExcelListener());
}
}
