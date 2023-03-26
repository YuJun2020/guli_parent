package com.yj.oss.service.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.yj.oss.service.OssService;
import com.yj.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class OssServiceImpl implements OssService{
    @Override
    public String upLoadAvator(MultipartFile file) {
        try {
            ClientConfiguration configuration = new ClientConfiguration();
            configuration.setSupportCname(false);
            OSSClient ossClient = new OSSClient(ConstantPropertiesUtils.END_POINT, ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET,configuration);
            if(!ossClient.doesBucketExist(ConstantPropertiesUtils.BUCKET_NAME)){
                ossClient.createBucket(ConstantPropertiesUtils.BUCKET_NAME);
                ossClient.setBucketAcl(ConstantPropertiesUtils.BUCKET_NAME, CannedAccessControlList.PublicRead);
            }
            String path = new DateTime().toString("yyyy/MM/dd");
            String uuid = UUID.randomUUID().toString();
            String fileName = path+"/"+uuid+file.getOriginalFilename();
            PutObjectRequest putObjectRequest = new PutObjectRequest(ConstantPropertiesUtils.BUCKET_NAME, fileName, file.getInputStream());
            putObjectRequest.setProcess("true");
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            //https://edu-yujun.oss-cn-beijing.aliyuncs.com/%E5%93%88%E5%A3%AB%E5%A5%87.jpeg
            return result.getResponse().getUri();
        }catch (Exception e){}
        return null;
    }
}
