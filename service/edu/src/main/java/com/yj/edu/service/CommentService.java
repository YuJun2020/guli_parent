package com.yj.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.edu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author yujun
 * @since 2023-03-20
 */
public interface CommentService extends IService<Comment> {

    Map<String, Object> getCommentList(Page<Comment> page, String courseId);
}
