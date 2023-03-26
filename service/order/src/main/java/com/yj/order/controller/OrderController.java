package com.yj.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yj.order.entity.Order;
import com.yj.order.service.OrderService;
import com.yj.order.service.PayLogService;
import com.yj.utils.JwtUtils;
import com.yj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author yujun
 * @since 2023-03-20
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayLogService payLogService;

    @PostMapping("addOrder/{courseId}")
    public R addOrder(@PathVariable String courseId, HttpServletRequest req){
        String orderId = orderService.addOrder(courseId, JwtUtils.getMemberIdByJwtToken(req));
        return R.ok().data("orderId",orderId);
    }

    @GetMapping("getOrder/{orderNo}")
    public R getOrder(@PathVariable String orderNo){
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        return R.ok().data("order",order);
    }

    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map<String,Object> map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if(map == null){
            return R.error().message("支付失败");
        }else if(map.get("trade_state").equals("SUCCESS")){
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }

    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        int count = orderService.count(new QueryWrapper<Order>()
                .eq("course_id", courseId)
                .eq("member_id", memberId)
                .eq("status", 1));
        return count>0;
    }
}

