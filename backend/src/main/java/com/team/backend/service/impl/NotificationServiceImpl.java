package com.team.backend.service.impl;

import com.team.backend.model.Notification;
import com.team.backend.mapper.NotificationMapper;
import com.team.backend.service.NotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangyu
 * @since 2021-05-02
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {

}
