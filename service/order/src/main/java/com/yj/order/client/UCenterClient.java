package com.yj.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Component
@FeignClient(name = "ucenter")
public interface UCenterClient {
    @GetMapping("/ucenter/member/getMemberInfoById/{memberId}")
    public Map<String, Object> getMemberInfoById(@PathVariable("memberId") String memberId);
}
