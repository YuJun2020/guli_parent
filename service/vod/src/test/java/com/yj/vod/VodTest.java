package com.yj.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class VodTest {
    public static void main(String[] args) throws ClientException {
//        getPlayUrl();
//        getPlayAuth();
        testUploadVideo();
    }

    /**
     * 本地文件上传接口
     */
    public static void testUploadVideo() {
        UploadVideoRequest request = new UploadVideoRequest("LTAI5t7DUzitah3RNt2myiZT", "iexD6oLudwtCqbbTFvh5OB95sIpI2C"
                , "aaa", "E:\\What If I Want to Move Faster.mp4");
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        request.setApiRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /*
    获取播放地址
     */
    public static void getPlayUrl() throws ClientException {
        DefaultAcsClient client = initVodClient("LTAI5t7DUzitah3RNt2myiZT", "iexD6oLudwtCqbbTFvh5OB95sIpI2C");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("4c268030c07d71edab296733a68f0102");
        GetPlayInfoResponse response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
    /*
    获取播放凭证
     */
    public static void getPlayAuth() throws ClientException {
        DefaultAcsClient client = initVodClient("LTAI5t7DUzitah3RNt2myiZT", "iexD6oLudwtCqbbTFvh5OB95sIpI2C");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("4c268030c07d71edab296733a68f0102");
        GetVideoPlayAuthResponse acsResponse = client.getAcsResponse(request);
        //播放凭证
        System.out.print("PlayAuth = " + acsResponse.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + acsResponse.getVideoMeta().getTitle() + "\n");
    }

    //填入AccessKey信息
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入地域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
