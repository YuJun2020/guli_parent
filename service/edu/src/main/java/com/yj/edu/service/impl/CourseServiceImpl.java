package com.yj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.base.exceptionHandler.GuliException;
import com.yj.edu.entity.Course;
import com.yj.edu.entity.CourseDescription;
import com.yj.edu.entity.Teacher;
import com.yj.edu.entity.frontvo.CourseFrontVo;
import com.yj.edu.entity.vo.CourseInfo;
import com.yj.edu.mapper.CourseMapper;
import com.yj.edu.service.ChapterService;
import com.yj.edu.service.CourseDescriptionService;
import com.yj.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Override
    public String addCourse(CourseInfo courseInfo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo,course);
        boolean save = this.save(course);
        if(!save){
            throw new GuliException(20001,"添加课程失败");
        }
        CourseDescription description = new CourseDescription();
        description.setId(course.getId());
        description.setDescription(courseInfo.getDescription());
        boolean saveDesc = courseDescriptionService.save(description);
        if(!saveDesc){
            throw new GuliException(20001,"添加课程描述失败");
        }
        return course.getId();
    }

    @Override
    public CourseInfo getCourseInfo(String courseId) {
        Course course = this.getById(courseId);
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(course,courseInfo);
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfo.setDescription(courseDescription.getDescription());
        return courseInfo;
    }

    @Override
    public void updateCourseInfo(CourseInfo courseInfo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo,course);
        boolean updated = this.updateById(course);
        if(!updated){
            throw new GuliException(20001,"修改课程基本信息失败");
        }
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfo.getId());
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public Map<String, String> getCoursePublishInfo(String courseId) {
        return this.baseMapper.getCoursePublishInfo(courseId);
    }

    @Override
    public void removeCourseById(String courseId) {
        videoService.removeByCourseId(courseId);
        chapterService.removeByCourseId(courseId);
        courseDescriptionService.removeByCourseId(courseId);
        this.removeById(courseId);
    }

    @Cacheable(value = "course",key = "'selectHotCourseList'")
    @Override
    public List<Course> getHotCourses() {
        return baseMapper.selectList(new QueryWrapper<Course>().orderByDesc("id").last("limit 8"));
    }

    @Override
    public Map<String, Object> getCourseList(Page<Course> page, CourseFrontVo courseFrontVo) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            queryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(page, queryWrapper);
        List<Course> records = page.getRecords();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public Map<String, Object> getCourseDetail(String courseId) {
        return baseMapper.getCourseDetail(courseId);
    }

    @Override
    public void updateCourseViewCount(String courseId) {
        Course course = baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount()+1);
        baseMapper.updateById(course);
    }
}
