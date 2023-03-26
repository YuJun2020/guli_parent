package com.yj.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VodService {
    String uploadVideo(MultipartFile file) throws IOException;
}
