package com.yj.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.edu.entity.Teacher;
import com.yj.edu.entity.frontvo.CourseFrontVo;
import com.yj.edu.entity.vo.CourseInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yujun
 * @since 2023-03-07
 */
public interface CourseService extends IService<Course> {

    String addCourse(CourseInfo courseInfo);

    CourseInfo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfo courseInfo);

    Map<String,String> getCoursePublishInfo(String courseId);

    void removeCourseById(String courseId);

    List<Course> getHotCourses();

    Map<String, Object> getCourseList(Page<Course> page, CourseFrontVo courseFrontVo);

    Map<String, Object> getCourseDetail(String courseId);

    void updateCourseViewCount(String courseId);
}
