package com.yj.order.service.impl;

import com.yj.order.client.EduClient;
import com.yj.order.client.UCenterClient;
import com.yj.order.entity.Order;
import com.yj.order.mapper.OrderMapper;
import com.yj.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author yujun
 * @since 2023-03-20
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UCenterClient uCenterClient;

    @Override
    public String addOrder(String courseId, String memberId) {
        Map<String, Object> course = eduClient.getCourseDetail(courseId);
        Map<String, Object> member = uCenterClient.getMemberInfoById(memberId);
        Order order = new Order();
        //order.setId("1");
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle((String) course.get("title"));
        order.setCourseCover((String) course.get("cover"));
        order.setTeacherName((String) course.get("teacherName"));
        order.setTotalFee(new BigDecimal(course.get("price").toString()));
        order.setMemberId(memberId);
        order.setMobile((String) member.get("mobile"));
        order.setNickname((String) member.get("nickname"));
        order.setStatus(0);
        order.setPayType(1);
        order.setIsDeleted(false);
        int insert = baseMapper.insert(order);
        System.out.println("添加是否成功：" + insert);
        return order.getOrderNo();

    }
}
