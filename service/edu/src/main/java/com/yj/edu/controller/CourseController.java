package com.yj.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.edu.entity.Chapter;
import com.yj.edu.entity.Course;
import com.yj.edu.entity.Teacher;
import com.yj.edu.entity.Video;
import com.yj.edu.entity.vo.CourseInfo;
import com.yj.edu.entity.vo.CourseQuery;
import com.yj.edu.entity.vo.TeacherQuery;
import com.yj.edu.service.ChapterService;
import com.yj.edu.service.CourseService;
import com.yj.edu.service.VideoService;
import com.yj.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yujun
 * @since 2023-03-07
 */
@RestController
@RequestMapping("/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;

    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfo courseInfo){
        String courseId = courseService.addCourse(courseInfo);
        return R.ok().data("courseId",courseId);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfo CourseInfo = courseService.getCourseInfo(courseId);
        return R.ok().data("CourseInfo",CourseInfo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfo courseInfo){
        courseService.updateCourseInfo(courseInfo);
        return R.ok();
    }

    @PostMapping("queryChapterList/{courseId}")
    public R queryChapterList(@PathVariable String courseId){
        List<Map<String,Object>> list = chapterService.queryChapterList(courseId);
        return R.ok().data("list",list);
    }

    @PostMapping("addChapter")
    public R addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    @GetMapping("getChapter/{chapterId}")
    public R getChapter(@PathVariable String chapterId){
        Chapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }

    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean isDeleted = chapterService.deleteChapter(chapterId);
        return R.ok().data("isDeleted",isDeleted);
    }

    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video){
        videoService.save(video);
        return R.ok();
    }

    @GetMapping("getVideo/{videoId}")
    public R getVideo(@PathVariable String videoId){
        Video video = videoService.getById(videoId);
        return R.ok().data("video",video);
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody Video chapter){
        videoService.updateById(chapter);
        return R.ok();
    }

    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        boolean isDeleted = videoService.deleteVideo(videoId);
        return R.ok().data("isDeleted",isDeleted);
    }

    @GetMapping("getCoursePublishInfo/{courseId}")
    public R getCoursePublishInfo(@PathVariable String courseId){
        Map<String, String> courseInfo = courseService.getCoursePublishInfo(courseId);
        return R.ok().data("courseInfo",courseInfo);
    }

    @PostMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        Course course = new Course();
        course.setId(courseId);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    @PostMapping("queryList/{pageIndex}/{pageSize}")
    public R queryList(@PathVariable Long pageIndex, @PathVariable Long pageSize, @RequestBody(required = false) CourseQuery courseQuery){
        Page<Course> page = new Page<>(pageIndex,pageSize);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        String teacherId = courseQuery.getTeacherId();
        String title = courseQuery.getTitle();
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.like("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        wrapper.orderByDesc("gmt_modified");
        IPage<Course> result = courseService.page(page, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",result.getRecords());
        map.put("total",result.getTotal());
        return R.ok().data(map);
    }

    @PostMapping("removeCourseById/{courseId}")
    public R removeCourseById(@PathVariable String courseId){
        courseService.removeCourseById(courseId);
        return R.ok();
    }

    @GetMapping("getCourseDetail/{courseId}")
    public Map<String,Object> getCourseDetail(@PathVariable String courseId){
        return courseService.getCourseDetail(courseId);
    }

}

