package com.yj.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author yujun
 * @since 2023-02-28
 */
public interface TeacherService extends IService<Teacher> {

    List<Teacher> getHotTeachers();

    Map<String, Object> getTeacherList(Page page);
}
