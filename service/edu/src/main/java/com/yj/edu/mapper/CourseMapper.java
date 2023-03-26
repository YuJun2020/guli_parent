package com.yj.edu.mapper;

import com.yj.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author yujun
 * @since 2023-03-07
 */
public interface CourseMapper extends BaseMapper<Course> {
    Map<String,String> getCoursePublishInfo(String courseId);

    Map<String, Object> getCourseDetail(String courseId);
}
