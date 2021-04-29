package com.team.backend.service.impl;

import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.mapper.UserMapper;
import com.team.backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.backend.util.UserLegal;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
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

  // 用户上传图片
  public Result<String> identifyImg(File file) throws IOException {

    Result<String> result = new Result<>();
    UserLegal userLegal = new UserLegal();
    String msg = userLegal.imgLegal(file);
    if (!msg.equals("OK")){
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData("");
      return result;
    }

    // 上传图片至服务器，返回图片地址  等待后续until类中处理图片方法写好后再做修改
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData("图片的url");
    return result;
  }

  public Result<Integer> identifyUser(User user) {

    Result<Integer> result = new Result<>();
    UserLegal userLegal = new UserLegal();
    String msg = userLegal.idLegal(user.getId());

    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户是否已存在
    User sqlUser = userMapper.selectById(user.getId());
    if (sqlUser.getId().equals(user.getId())){
      result.setCode(ExceptionInfo.valueOf("USER_ID_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_ID_EXISTED").getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户名是否合法
    msg = userLegal.usernameLegal(user.getUsername());
    if (!msg.equals("OK")){
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    msg = userLegal.urlLegal(user.getUserIconUrl());
    if (!msg.equals("OK")){
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断学校名是否合法
    msg = userLegal.schoolLegal(user.getSchool());
    if (!msg.equals("OK")){
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户性别合法性
    msg = userLegal.sexLegal(user.getSex());
    if (!msg.equals("OK")){
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户出生日期合法性
    msg = userLegal.birthdayLegal(user.getBirthday());
    if (!msg.equals("OK")){
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    user.setStatus(0);// 用户状态默认为未审核
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(userMapper.insert(user));
    return result;
  }

  // 获取用户审核状态
  public Result<Integer> identifyStatus(Long id){

    Result<Integer> result = new Result<>();

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    User user = userMapper.selectById(id);
    result.setData(user.getStatus());
    return result;
  }
}
