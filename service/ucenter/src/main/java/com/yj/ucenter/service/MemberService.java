package com.yj.ucenter.service;

import com.yj.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yujun
 * @since 2023-03-15
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getMemberByOpenId(String openid);

    int getRegisterCount(String day);
}
