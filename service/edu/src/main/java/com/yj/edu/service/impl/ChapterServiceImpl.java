package com.yj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yj.base.exceptionHandler.GuliException;
import com.yj.edu.client.VodClient;
import com.yj.edu.entity.Chapter;
import com.yj.edu.entity.CourseDescription;
import com.yj.edu.entity.Subject;
import com.yj.edu.entity.Video;
import com.yj.edu.mapper.ChapterMapper;
import com.yj.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yujun
 * @since 2023-03-07
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;

    @Override
    public List<Map<String, Object>> queryChapterList(String courseId) {
        List<Chapter> chapters = this.list(new QueryWrapper<Chapter>().eq("course_id", courseId));
        List<Video> videos = videoService.list(new QueryWrapper<Video>().eq("course_id", courseId));
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i=0,len=chapters.size();i<len;i++){
            HashMap<String, Object> map = new HashMap<>();
            Chapter chapter = chapters.get(i);
            map.put("id",chapter.getId());
            map.put("title",chapter.getTitle());
            List<Map<String, Object>> children = new ArrayList<>();
            for(int j=0,len2=videos.size();j<len2;j++){
                Video child = videos.get(j);
                if(child.getChapterId().equals(chapter.getId())){
                    HashMap<String, Object> childMap = new HashMap<>();
                    childMap.put("id",child.getId());
                    childMap.put("title",child.getTitle());
                    childMap.put("videoSourceId",child.getVideoSourceId());
                    children.add(childMap);
                    map.put("children",children);
                }
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        int count = videoService.count(new QueryWrapper<Video>().eq("chapter_id", chapterId));
        if(count>0){
            throw new GuliException(20001,"当前章节下有小节，不能删除");
        }else{
            return this.removeById(chapterId);
        }
    }

    @Override
    public void removeByCourseId(String courseId) {
        baseMapper.delete(new QueryWrapper<Chapter>().eq("course_id",courseId));
    }
}
