package com.yj.cms.service;

import com.yj.cms.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author yujun
 * @since 2023-03-14
 */
public interface BannerService extends IService<Banner> {

    List<Banner> queryList();
}
