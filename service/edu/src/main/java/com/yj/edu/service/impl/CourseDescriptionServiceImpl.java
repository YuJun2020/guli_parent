package com.yj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yj.edu.entity.CourseDescription;
import com.yj.edu.mapper.CourseDescriptionMapper;
import com.yj.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author yujun
 * @since 2023-03-07
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

    @Override
    public void removeByCourseId(String courseId) {
        this.removeById(courseId);
    }
}
