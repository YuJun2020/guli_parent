package com.yj.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yj.cms.entity.Banner;
import com.yj.cms.mapper.BannerMapper;
import com.yj.cms.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author yujun
 * @since 2023-03-14
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Cacheable(key = "'selectBannerList'",value = "banner")
    @Override
    public List<Banner> queryList() {
        return baseMapper.selectList(new QueryWrapper<Banner>().orderByDesc("id").last("limit 2"));
    }
}
