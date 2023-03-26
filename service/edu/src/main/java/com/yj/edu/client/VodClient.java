package com.yj.edu.client;

import com.yj.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "vod",fallback = VodDegradeClient.class)
public interface VodClient {
    @DeleteMapping("/videovod/removeVideo/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/videovod/removeVideos")
    public R removeVideos(@RequestParam("videoIds") List<String> videoIds);
}
