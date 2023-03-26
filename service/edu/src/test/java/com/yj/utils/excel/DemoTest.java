package com.yj.utils.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoTest {
    public static void main(String[] args){
//        EasyExcel.write("E:\\demo.xlsx",DemoData.class)
//                .sheet("学生信息")
//                .doWrite(getData());
//        EasyExcel.read("E:\\demo.xlsx",DemoData.class,new ExcelListener())
//                .sheet().doRead();
        int i=1;
        i=i++;

        System.out.println(i);
        ;
    }

    public static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<DemoData>();
        for(int i=0;i<10;i++){
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("yj"+i);
            list.add(data);
        }
        return list;
    }
}
