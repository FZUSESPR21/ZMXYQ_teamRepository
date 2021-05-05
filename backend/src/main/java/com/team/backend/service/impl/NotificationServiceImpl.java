package com.team.backend.service.impl;

import com.team.backend.model.Notification;
import com.team.backend.mapper.NotificationMapper;
import com.team.backend.service.NotificationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification>
        implements NotificationService {

    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public List<Map<String, Object>> getLikeInformation(Long userId) {
        List<Map<String,Object>> likeNotificationList = new ArrayList<>();
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("is_read", 0).groupBy("id");
        return likeNotificationList;
    }

}
