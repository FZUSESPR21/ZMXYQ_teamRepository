package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.model.Admin;
import com.team.backend.mapper.AdminMapper;
import com.team.backend.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.backend.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * AdminService，实现功能：登入，改密
 * </p>
 *
 * @author ccreater
 * @since 2021-04-28
 */

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

  @Autowired
  private AdminMapper adminMapper;

  private Admin processInput(String nickname, String password) {
    nickname = nickname.trim();
    password = password.trim();
    if (StringUtils.isBlank(nickname) || StringUtils.isBlank(password)) {
      return null;
    }
    try {
      password += "AA546ADF546safd35444sfd";
      byte[] bytesOfPassword = password.getBytes("UTF-8");
      MessageDigest md = MessageDigest.getInstance("MD5");
      password = Hex.encodeHexString(md.digest(bytesOfPassword));
    } catch (Exception e) {
      Log.error(e);
      return null;
    }
    return new Admin(nickname,password);
  }

  public Admin login(String nickname, String password) {
    Admin input = processInput(nickname, password);
    if (input==null) {
      log.debug("nickname or password is null");
      return null;
    }
    nickname = input.getNickname();
    password = input.getPassword();
    Map<String, String> condition = new HashMap<>();
    condition.put("nickname", nickname);
    condition.put("password", password);
    return getOne((Wrapper<Admin>) new QueryWrapper().allEq(condition));
  }

  public Admin register(String nickname, String password) {
    Admin input = processInput(nickname, password);
    if (input==null) {
      log.debug("nickname or password is null");
      return null;
    }
    nickname = input.getNickname();
    password = input.getPassword();
    List<Admin> adminList = adminMapper.selectList(null);
    if (adminList.size() > 0) {
      log.debug("Admin already exists");
      return null;
    } else {
      Admin admin = new Admin(nickname, password);
      adminMapper.insert(admin);
      return admin;
    }
  }

  public boolean changePwd(String nickname, String oldPassword, String newPassword) {
    Admin admin = login(nickname, oldPassword);
    if (admin == null) {
      // user not exist
      log.debug("user not exist");
      return false;
    }
    Admin input = processInput(nickname, newPassword);
    if (input==null) {
      // new Password format error
      log.debug("new Password format error");
      return false;
    }
    nickname = input.getNickname();
    newPassword = input.getPassword();
    admin.setPassword(newPassword);
    updateById(admin);
    return true;
  }

}
