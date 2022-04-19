package com.rg.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/21 9:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadData {
    //设置excel表头名称
    @ExcelProperty(index = 0)
    private Integer sno;

    @ExcelProperty(index = 1)
    private String sname;
}
