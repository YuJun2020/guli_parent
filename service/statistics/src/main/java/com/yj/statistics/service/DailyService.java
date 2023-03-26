package com.yj.statistics.service;

import com.yj.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author yujun
 * @since 2023-03-23
 */
public interface DailyService extends IService<Daily> {

    void getDaily(String day);

    Map<String, Object> getChartData(String type, String begin, String end);
}
