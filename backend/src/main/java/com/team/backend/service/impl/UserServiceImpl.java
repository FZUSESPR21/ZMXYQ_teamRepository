package com.team.backend.service.impl;

import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.mapper.UserMapper;
import com.team.backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

  // 上传身份验证图片
  public Result<String> identifyImg(File file) throws IOException {

    Result<String> result = new Result<>();

    // 判断用户上传图片是否存在
    if (!file.exists() || file.length() < 1) {
      result.setCode(ExceptionInfo.valueOf("USER_IMG_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_IMG_NULL").getMessage());
      result.setData("");
      return result;
    }

    //判断用户上传照片是否为图片
    ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
    Iterator iterator = ImageIO.getImageReaders(imageInputStream);
    if (!iterator.hasNext()) {
      result.setCode(ExceptionInfo.valueOf("USER_NOT_IMG").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_IMG").getMessage());
      result.setData("");
      return result;
    }

    // 上传图片至服务器，返回图片地址  等待后续until类中处理图片方法写好后再做修改
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData("图片的url");
    return result;
  }
}
