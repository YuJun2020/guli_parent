package com.yj.cms.controller;


import com.yj.cms.entity.Banner;
import com.yj.cms.service.BannerService;
import com.yj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author yujun
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/cms/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @GetMapping("queryList")
    public R queryList(){
        List<Banner> list = bannerService.queryList();
        return R.ok().data("list",list);
    }
}

