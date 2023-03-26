package com.yj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yj.base.exceptionHandler.GuliException;
import com.yj.edu.client.VodClient;
import com.yj.edu.entity.CourseDescription;
import com.yj.edu.entity.Video;
import com.yj.edu.mapper.VideoMapper;
import com.yj.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author yujun
 * @since 2023-03-07
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean deleteVideo(String videoId) {
        Video video = this.getById(videoId);
        String videoSourceId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            R r = vodClient.removeVideo(videoSourceId);
            if(r.getCode() == 20001){
                throw new GuliException(20001,"删除小节失败");
            }
        }
        return this.removeById(videoId);
    }

    @Override
    public void removeByCourseId(String courseId) {
        List<Video> videos = baseMapper.selectList(new QueryWrapper<Video>().eq("course_id", courseId).select("video_source_id"));
        ArrayList<String> list = new ArrayList<>();
        for(Video video : videos){
            String videoSourceId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                list.add(videoSourceId);
            }
        }
        if(list.size()>0){
            vodClient.removeVideos(list);
        }
        baseMapper.delete(new QueryWrapper<Video>().eq("course_id",courseId));
    }
}
