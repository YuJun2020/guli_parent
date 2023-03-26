package com.yj.oss.controller;

import com.yj.oss.service.OssService;
import com.yj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("uploadAvatar")
    public R upLoadAvatar(MultipartFile file){
        String url = ossService.upLoadAvator(file);
        return R.ok().data("url", url);
    }
}
