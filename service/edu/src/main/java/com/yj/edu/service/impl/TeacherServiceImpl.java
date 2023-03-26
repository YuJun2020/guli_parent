package com.yj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.edu.entity.Course;
import com.yj.edu.entity.Teacher;
import com.yj.edu.mapper.TeacherMapper;
import com.yj.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author yujun
 * @since 2023-02-28
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Cacheable(value = "teacher",key = "'selectHotTeacherList'")
    @Override
    public List<Teacher> getHotTeachers() {
        return baseMapper.selectList(new QueryWrapper<Teacher>().orderByDesc("id").last("limit 4"));
    }

    @Override
    public Map<String, Object> getTeacherList(Page page) {
        baseMapper.selectPage(page, new QueryWrapper<Teacher>().orderByDesc("id"));
        List<Teacher> records = page.getRecords();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
