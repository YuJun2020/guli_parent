package com.yj.edu.service;

import com.yj.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author yujun
 * @since 2023-03-07
 */
public interface VideoService extends IService<Video> {

    boolean deleteVideo(String videoId);

    void removeByCourseId(String courseId);
}
