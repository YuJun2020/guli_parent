package com.yj.ucenter.mapper;

import com.yj.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author yujun
 * @since 2023-03-15
 */
public interface MemberMapper extends BaseMapper<Member> {

   int getRegisterCount(String day);

}
