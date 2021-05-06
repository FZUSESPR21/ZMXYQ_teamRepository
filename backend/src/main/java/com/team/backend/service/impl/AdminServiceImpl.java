package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.mapper.PartyMapper;
import com.team.backend.mapper.PostMapper;
import com.team.backend.mapper.UserMapper;
import com.team.backend.model.Admin;
import com.team.backend.mapper.AdminMapper;
import com.team.backend.model.Party;
import com.team.backend.model.Post;
import com.team.backend.model.User;
import com.team.backend.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.backend.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.annotation.Resource;
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

  @Resource
  private UserMapper userMapper;
  @Autowired
  private AdminMapper adminMapper;
  @Autowired
  private PostMapper postMapper;
  @Autowired
  private PartyMapper partyMapper;

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
    return getOne(new QueryWrapper<Admin>().allEq(condition));
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

  public List<Map<String,Object>> getUncheckedPostList(int order){
    QueryWrapper<Post> wrapper = new QueryWrapper<Post>();
    Map<String,Object> map = new HashMap<>();
    map.put("status",0);
    map.put("deleted",0);
    wrapper.allEq(map);
    if(order==0){
      wrapper.orderByAsc("gmt_create");
    }else{
      wrapper.orderByDesc("gmt_create");
    }

    return postMapper.selectMaps(wrapper);

  }
  public void confirmPost(long id,int pass){
    if(pass==0){
      Post post = postMapper.selectById(id);
      post.setStatus(1);
      postMapper.updateById(post);
    }
  }

  public List<Map<String,Object>> getUncheckedParty(int order){
    QueryWrapper<Party> wrapper = new QueryWrapper<>();
    Map<String,Object> map = new HashMap<>();
    map.put("status",0);
    map.put("deleted",0);
    wrapper.allEq(map);
    if(order==0){
      wrapper.orderByAsc("gmt_create");
    }else{
      wrapper.orderByDesc("gmt_create");
    }

    return partyMapper.selectMaps(wrapper);
  }

  public void confirmParty(long id,int pass){
    if(pass==0){
      Party party = partyMapper.selectById(id);
      party.setStatus(1);
      partyMapper.updateById(party);
    }
  }

  public List<Map<String,Object>> getUncheckedUser(int order){
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    Map<String,Object> map = new HashMap<>();
    map.put("status",0);
    map.put("deleted",0);
    wrapper.allEq(map);
    if(order==0){
      wrapper.orderByAsc("gmt_create");
    }else{
      wrapper.orderByDesc("gmt_create");
    }

    return userMapper.selectMaps(wrapper);
  }

  public void confirmUser(long id,int pass){
    if(pass==0){
      User user = userMapper.selectById(id);
      user.setStatus(1);
      userMapper.updateById(user);
    }
  }

}
