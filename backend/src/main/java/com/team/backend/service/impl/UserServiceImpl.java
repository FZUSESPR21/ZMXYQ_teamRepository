package com.team.backend.service.impl;

import com.team.backend.model.User;
import com.team.backend.mapper.UserMapper;
import com.team.backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Autowired
  UserMapper userMapper;

  // 上传身份验证图片
  public String identifyImg(File file) {
    //等待后续until类中处理图片方法写好后再做修改
    return "";
  }
}
