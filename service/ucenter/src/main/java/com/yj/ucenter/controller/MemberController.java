package com.yj.ucenter.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yj.ucenter.entity.Member;
import com.yj.ucenter.entity.vo.RegisterVo;
import com.yj.ucenter.service.MemberService;
import com.yj.utils.JwtUtils;
import com.yj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yujun
 * @since 2023-03-15
 */
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("login")
    public R login(@RequestBody Member member){
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest req){
        String memberId = JwtUtils.getMemberIdByJwtToken(req);
        Member member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    @GetMapping("getMemberInfoById/{memberId}")
    public Map<String, Object> getMemberInfoById(@PathVariable String memberId){
        Member member = memberService.getById(memberId);
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(member), new TypeReference<Map<String, Object>>() {
        });
        return map;
    }

    @GetMapping("getRegisterCount/{day}")
    public R getRegisterCount(@PathVariable String day){
        int count = memberService.getRegisterCount(day);
        return R.ok().data("registerCount",count);
    }



}

