package com.rg.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/21 8:53
 */
public class TestEasyExcel {
    public static void main(String[] args) {

        //testWrite();
        testRead();
    }

    public static void testRead(){
        String filename = "F:\\write.xlsx";
        EasyExcel.read(filename,ReadData.class,new ExcelListner()).sheet().doRead();
    }

    public static void testWrite(){
        //实现excel的写操作
        //1.设置写入文件夹地址和excel名称
        String filename = "F:\\write.xlsx";
        //2.调用easyexcel里面的方法实现写操作
        EasyExcel.write(filename, WriteData.class).sheet("学生列表").doWrite(getData());
    }

    public static List <WriteData> getData(){
        List <WriteData> list = new ArrayList <>();
        for(int i = 0;i < 10;i++){
            String sname = "lucy"+i;
            list.add(new WriteData(i, sname));
        }
        return list;
    }
}
