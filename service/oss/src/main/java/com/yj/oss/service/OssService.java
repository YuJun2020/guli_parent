package com.yj.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String upLoadAvator(MultipartFile file);
}
