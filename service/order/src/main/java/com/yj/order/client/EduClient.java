package com.yj.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Component
@FeignClient(name = "edu")
public interface EduClient {
    @GetMapping("/edu/course/getCourseDetail/{courseId}")
    public Map<String,Object> getCourseDetail(@PathVariable("courseId") String courseId);
}
