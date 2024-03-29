package com.yj.edu.entity.subject;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {
    @ExcelProperty(value = "一级分类",index = 0)
    private String oneSubject;
    @ExcelProperty(value = "二级分类",index = 1)
    private String twoSubject;
}
