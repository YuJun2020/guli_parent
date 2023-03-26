package com.yj.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.edu.client.OrderClient;
import com.yj.edu.entity.Comment;
import com.yj.edu.entity.Course;
import com.yj.edu.entity.Teacher;
import com.yj.edu.entity.frontvo.CourseFrontVo;
import com.yj.edu.service.ChapterService;
import com.yj.edu.service.CommentService;
import com.yj.edu.service.CourseService;
import com.yj.edu.service.TeacherService;
import com.yj.utils.JwtUtils;
import com.yj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu/front")
public class FrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private OrderClient orderClient;

    @GetMapping("index")
    public R index() {
        List<Course> courseList = courseService.getHotCourses();
        List<Teacher> teacherList = teacherService.getHotTeachers();
        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }

    @GetMapping("queryTeacherList/{pageIndex}/{pageSize}")
    public R queryTeacherList(@PathVariable Long pageIndex, @PathVariable Long pageSize) {
        Page<Teacher> page = new Page<>(pageIndex, pageSize);
        Map<String, Object> map = teacherService.getTeacherList(page);
        return R.ok().data(map);
    }

    @GetMapping("getTeacherInfo/{teacherId}")
    public R getTeacherInfo(@PathVariable String teacherId) {
        Teacher teacher = teacherService.getById(teacherId);
        List<Course> courses = courseService.list(new QueryWrapper<Course>().eq("teacher_id", teacherId));
        return R.ok().data("teacher",teacher).data("courses",courses);
    }

    @PostMapping("queryCourseList/{pageIndex}/{pageSize}")
    public R queryCourseList(@PathVariable Long pageIndex, @PathVariable Long pageSize, @RequestBody(required = false)CourseFrontVo courseFrontVo) {
        Page<Course> page = new Page<>(pageIndex, pageSize);
        Map<String, Object> map = courseService.getCourseList(page,courseFrontVo);
        return R.ok().data(map);
    }

    @GetMapping("getCourseDetail/{courseId}")
    public R getCourseDetail(@PathVariable String courseId, HttpServletRequest req) {
        Map<String, Object> map = courseService.getCourseDetail(courseId);
        List<Map<String, Object>> chapterList = chapterService.queryChapterList(courseId);
        String memberId = JwtUtils.getMemberIdByJwtToken(req);
        boolean isBuy = false;
        if(!StringUtils.isEmpty(memberId)){
            isBuy = orderClient.isBuyCourse(courseId, memberId);
        }
        return R.ok().data("course",map).data("chapterList",chapterList).data("isBuy",isBuy);
    }

    @GetMapping("updateCourseViewCount/{courseId}")
    public R updateCourseViewCount(@PathVariable String courseId) {
        courseService.updateCourseViewCount(courseId);
        return R.ok();
    }

    @GetMapping("queryCommentList/{pageIndex}/{pageSize}/{courseId}")
    public R queryCommentList(@PathVariable Long pageIndex,@PathVariable Long pageSize,@PathVariable String courseId) {
        Page<Comment> page = new Page<>(pageIndex, pageSize);
        Map<String, Object> map = commentService.getCommentList(page,courseId);
        return R.ok().data(map);
    }

    @PostMapping("addComment")
    public R queryCommentList(@RequestBody Comment comment) {
        commentService.save(comment);
        return R.ok();
    }

}
