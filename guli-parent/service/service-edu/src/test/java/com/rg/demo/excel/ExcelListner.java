package com.rg.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/21 9:20
 */
public class ExcelListner extends AnalysisEventListener<ReadData> {
   List<ReadData> list =  new ArrayList<ReadData>();

   //一行一行的去读取
    @Override
    public void invoke(ReadData readData, AnalysisContext analysisContext) {
        System.out.println("****"+readData);
        list.add(readData);
    }

    //读取Excel表头信息
    @Override
    public void invokeHeadMap(Map <Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息:"+headMap);
    }

    //读取完成之后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
