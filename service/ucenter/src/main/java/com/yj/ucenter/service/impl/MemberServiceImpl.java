package com.yj.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yj.base.exceptionHandler.GuliException;
import com.yj.ucenter.entity.Member;
import com.yj.ucenter.entity.vo.RegisterVo;
import com.yj.ucenter.mapper.MemberMapper;
import com.yj.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.utils.JwtUtils;
import com.yj.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yujun
 * @since 2023-03-15
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(Member member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登陆失败");
        }
        Member user = baseMapper.selectOne(new QueryWrapper<Member>().eq("mobile", mobile));
        if(user==null){
            throw new GuliException(20001,"登陆失败");
        }
        if(user.getIsDisabled() || user.getIsDeleted() || !MD5.encrypt(password).equals(user.getPassword())){
            throw new GuliException(20001,"登陆失败");
        }
        return JwtUtils.getJwtToken(user.getId(),user.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname)
        || StringUtils.isEmpty(code) || StringUtils.isEmpty(password)
        || !code.equals(redisTemplate.opsForValue().get(mobile))){
            throw new GuliException(20001,"注册失败");
        }
        Integer count = baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile", mobile));
        if(count > 0){
            throw new GuliException(20001,"注册失败");
        }
        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        member.setIsDisabled(false);
        baseMapper.insert(member);
    }

    @Override
    public Member getMemberByOpenId(String openid) {
        return baseMapper.selectOne(new QueryWrapper<Member>().eq("openid",openid));
    }

    @Override
    public int getRegisterCount(String day) {
        return baseMapper.getRegisterCount(day);
    }
}
