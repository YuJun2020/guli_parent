package com.yj.statistics.client;

import com.yj.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "ucenter")
public interface UCenterClient {
    @GetMapping("/ucenter/member/getRegisterCount/{day}")
    public R getRegisterCount(@PathVariable String day);
}
