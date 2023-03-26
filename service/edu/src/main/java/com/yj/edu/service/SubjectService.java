package com.yj.edu.service;

import com.yj.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author yujun
 * @since 2023-03-06
 */
public interface SubjectService extends IService<Subject> {

    void addSubject(MultipartFile file);

    List<Map<String,Object>> queryList();
}
