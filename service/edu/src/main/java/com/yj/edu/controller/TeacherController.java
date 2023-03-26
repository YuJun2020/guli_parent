package com.yj.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.base.exceptionHandler.GuliException;
import com.yj.edu.entity.Teacher;
import com.yj.edu.entity.vo.TeacherQuery;
import com.yj.edu.service.TeacherService;
import com.yj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yujun
 * @since 2023-02-28
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "查询全部讲师")
    @GetMapping("queryAll")
    public R queryAll(){
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "查询列表")
    @PostMapping("queryList/{pageIndex}/{pageSize}")
    public R queryList(@PathVariable Long pageIndex, @PathVariable Long pageSize, @RequestBody(required = false) TeacherQuery teacherQuery){
//        try{
//            int aaa = 10/0;
//        }catch (Exception e){
//            throw new GuliException(2001,"自定义异常");
//        }
        Page<Teacher> page = new Page<>(pageIndex,pageSize);
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        wrapper.orderByDesc("gmt_modified");
        IPage<Teacher> result = teacherService.page(page, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",result.getRecords());
        map.put("total",result.getTotal());
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("deleteTeacher/{id}")
    public R deleteTeacher(@PathVariable String id){
        boolean b = teacherService.removeById(id);
        return b ? R.ok() : R.error();
    }

    @ApiOperation(value = "添加/编辑讲师")
    @PostMapping("saveOrUpdate")
    public R saveOrUpdate(@RequestBody Teacher teacher){
        String id = teacher.getId();
        boolean b = StringUtils.isEmpty(id) ? teacherService.save(teacher) : teacherService.updateById(teacher);
        return b ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher){
        boolean b = teacherService.updateById(teacher);
        return b ? R.ok() : R.error();
    }

}

