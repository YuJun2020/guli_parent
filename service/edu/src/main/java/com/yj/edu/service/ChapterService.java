package com.yj.edu.service;

import com.yj.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface ChapterService extends IService<Chapter> {

    List<Map<String, Object>> queryChapterList(String courseId);

    boolean deleteChapter(String chapterId);

    void removeByCourseId(String courseId);
}
