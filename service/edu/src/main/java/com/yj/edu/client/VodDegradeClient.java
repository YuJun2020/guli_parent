package com.yj.edu.client;

import com.yj.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodDegradeClient implements VodClient {
    @Override
    public R removeVideo(String videoId) {
        return R.error().message("删除视频失败");
    }

    @Override
    public R removeVideos(List<String> videoIds) {
        return R.error().message("删除多个视频失败");
    }
}
