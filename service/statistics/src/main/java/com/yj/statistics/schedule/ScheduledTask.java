package com.yj.statistics.schedule;

import com.yj.statistics.service.DailyService;
import com.yj.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private DailyService dailyService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        dailyService.getDaily(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
