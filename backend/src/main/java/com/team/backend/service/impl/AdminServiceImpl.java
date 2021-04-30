package com.team.backend.service.impl;

import com.team.backend.model.Admin;
import com.team.backend.mapper.AdminMapper;
import com.team.backend.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.nio.charset.StandardCharsets;
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
  private String nickname;
  private String password;

  private boolean processInput(String nickname, String password) {
    this.nickname = nickname.trim();
    this.password = password.trim();
    if (StringUtils.isBlank(nickname) || StringUtils.isBlank(password)) {
      return false;
    }
    try {
      this.password += "AA546ADF546safd35444sfd";
      byte[] bytesOfPassword = password.getBytes("UTF-8");
      MessageDigest md = MessageDigest.getInstance("MD5");
      StringBuffer buf = new StringBuffer("");
      byte[] b=md.digest(bytesOfPassword);
      int i;
      for (int offset = 0; offset < b.length; offset++) {
        i = b[offset];
        if (i < 0)
          i += 256;
        if (i < 16)
          buf.append("0");
        buf.append(Integer.toHexString(i));
      }
      this.password = buf.toString();
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public Admin login(String nickname, String password) {
    if (!processInput(nickname, password)) {
      return null;
    }
    Map<String, String> condition = new HashMap<>();
    condition.put("nickname", this.nickname);
    condition.put("password", this.password);
    return adminMapper.selectOne(query().allEq(condition));
  }

  public Admin register(String nickname, String password) {
    if (!processInput(nickname, password)) {
      return null;
    }
    List<Admin> adminList = adminMapper.selectList(null);
    if (adminList.size() > 0) {
      return null;
    } else {
      Admin admin = new Admin(this.nickname, this.password);
      adminMapper.insert(admin);
      return admin;
    }
  }

  public boolean changePwd(String nickname, String oldPassword, String newPassword) {
    Admin admin = login(nickname, oldPassword);
    if (admin == null) {
      // user not exist
      return false;
    }
    if (!processInput(nickname, newPassword)) {
      // new Password format error
      return false;
    }
    admin.setPassword(this.password);
    updateById(admin);
    return false;
  }
}
