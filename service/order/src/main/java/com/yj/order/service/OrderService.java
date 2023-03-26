package com.yj.order.service;

import com.yj.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author yujun
 * @since 2023-03-20
 */
public interface OrderService extends IService<Order> {

    String addOrder(String courseId, String memberIdByJwtToken);
}
