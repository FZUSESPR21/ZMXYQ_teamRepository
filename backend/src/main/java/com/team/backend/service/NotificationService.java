package com.team.backend.service;

import com.team.backend.model.Notification;

import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangyu
 * @author Tars
 * @since 2021-04-28
 */
public interface NotificationService extends IService<Notification> {
    /**
     * 获取用户点赞消息
     * 
     * @param userId 用户ID
     * @return 点赞消息信息映射
     */
    List<Map<String,Object>> getLikeInformation(Long userId);
}
