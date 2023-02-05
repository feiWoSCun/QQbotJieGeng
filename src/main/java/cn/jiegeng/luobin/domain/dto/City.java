package cn.jiegeng.luobin.domain.dto;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author feiwoscun
 */
@Data
public class City {
    @ExcelProperty(index = 0)
    String code;
    @ExcelProperty(index = 1)

    String government;
    @ExcelProperty(index = 2)

    String location;
    @ExcelProperty(index = 3)

    String spell;
}
