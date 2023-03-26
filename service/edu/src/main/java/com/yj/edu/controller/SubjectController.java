package com.yj.edu.controller;


import com.yj.edu.service.SubjectService;
import com.yj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author yujun
 * @since 2023-03-06
 */
@RestController
@RequestMapping("/edu/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        System.out.println("aaa");
        subjectService.addSubject(file);
        return R.ok();
    }

    @PostMapping("queryList")
    public R queryList(){
        List<Map<String,Object>> list = subjectService.queryList();
        return R.ok().data("list",list);
    }

}

