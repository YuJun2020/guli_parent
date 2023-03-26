package com.yj.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.yj.base.exceptionHandler.GuliException;
import com.yj.utils.R;
import com.yj.vod.service.VodService;
import com.yj.vod.utils.ConstantPropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/videovod")
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) throws IOException {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }

    @DeleteMapping("removeVideo/{videoId}")
    public R removeVideo(@PathVariable String videoId){
        try {
            String regionId = "cn-shanghai";  // 点播服务接入地域
            DefaultProfile profile = DefaultProfile.getProfile(regionId, ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    @DeleteMapping("removeVideos")
    public R removeVideos(@RequestParam("videoIds") List<String> videoIds){
        try {
            String regionId = "cn-shanghai";  // 点播服务接入地域
            DefaultProfile profile = DefaultProfile.getProfile(regionId, ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds( StringUtils.join(videoIds.toArray(),","));
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    @GetMapping("getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId){
        try {
            String regionId = "cn-shanghai";  // 点播服务接入地域
            DefaultProfile profile = DefaultProfile.getProfile(regionId, ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse acsResponse = client.getAcsResponse(request);
            //播放凭证
            return R.ok().data("playAuth",acsResponse.getPlayAuth());
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"获取视频播放凭证失败");
        }
    }
}
