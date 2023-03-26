package com.yj.statistics.controller;


import com.yj.statistics.service.DailyService;
import com.yj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author yujun
 * @since 2023-03-23
 */
@RestController
@RequestMapping("/statistics")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @GetMapping("getDaily/{day}")
    public R getDaily(@PathVariable String day){
        dailyService.getDaily(day);
        return R.ok();
    }

    @GetMapping("getChartData/{type}/{begin}/{end}")
    public R getChartData(@PathVariable String type, @PathVariable String begin, @PathVariable String end){
        Map<String,Object> map = dailyService.getChartData(type,begin,end);
        return R.ok().data(map);
    }

}

