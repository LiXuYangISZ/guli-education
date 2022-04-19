package com.rg.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/21 8:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteData {

    //设置excel表头名称
    @ExcelProperty("学生编号")
    private Integer sno;

    @ExcelProperty("学生姓名")
    private String sname;
}
