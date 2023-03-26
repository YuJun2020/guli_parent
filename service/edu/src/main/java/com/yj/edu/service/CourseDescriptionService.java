package com.yj.edu.service;

import com.yj.edu.entity.CourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author yujun
 * @since 2023-03-07
 */
public interface CourseDescriptionService extends IService<CourseDescription> {

    void removeByCourseId(String courseId);
}
