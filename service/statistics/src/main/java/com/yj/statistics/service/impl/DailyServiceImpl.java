package com.yj.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yj.statistics.client.UCenterClient;
import com.yj.statistics.entity.Daily;
import com.yj.statistics.mapper.DailyMapper;
import com.yj.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author yujun
 * @since 2023-03-23
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UCenterClient uCenterClient;

    @Override
    public void getDaily(String day) {
        baseMapper.delete(new QueryWrapper<Daily>().eq("date_calculated",day));

        //1.调用fiegn接口查数据
        R r = uCenterClient.getRegisterCount(day);
        int registerCount = (int) r.getData().get("registerCount");

        //2.向统计数据表添加数据
        Daily daily = new Daily();
        daily.setDateCalculated(day);
        daily.setRegisterNum(registerCount);
        daily.setLoginNum(1);
        daily.setCourseNum(1);
        daily.setVideoViewNum(1);
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String type, String begin, String end) {
        List<Daily> dailyList = baseMapper.selectList(new QueryWrapper<Daily>().between("date_calculated", begin, end)
                .select("date_calculated", type));
        ArrayList<String> xList = new ArrayList<>();
        ArrayList<Integer> yList = new ArrayList<>();
        for (int i = 0; i <dailyList.size() ; i++) {
            Daily daily = dailyList.get(i);
            xList.add(daily.getDateCalculated());
            switch (type){
                case "register_num":
                    yList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    yList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    yList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    yList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("xList",xList);
        map.put("yList",yList);
        return map;
    }
}
