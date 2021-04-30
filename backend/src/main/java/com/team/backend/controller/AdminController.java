package com.team.backend.controller;


import com.team.backend.model.Admin;
import com.team.backend.service.impl.AdminServiceImpl;
import com.team.backend.util.Response;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ccreater
 * @since 2021-04-28
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

  private Admin admin;
  @Autowired
  private HttpSession session;
  @Autowired
  private AdminServiceImpl adminServiceImpl;

  @PostMapping("/login")
  public Response login(@RequestParam(value = "adminId") String nickname,
      @RequestParam(value = "password") String password) {
    int code = 0;
    if (admin == null) {
      admin = adminServiceImpl.login(nickname, password);
      session.setAttribute("admin", admin);
    }

    if (admin == null) {
      code = 1;
    }

    return new Response(code, "", null);
  }

  @PostMapping("/changepsw")
  public Response changePwd(
      @RequestParam(value = "adminId", required = false, defaultValue = "") String nickname,
      @RequestParam String oldPassword, @RequestParam String newPassword) {
    int code = 0;
    if (nickname == "" && admin != null) {
      nickname = admin.getNickname();
    } else {
      code = 1;
      return new Response(code, "", null);
    }
    if (adminServiceImpl.changePwd(nickname, oldPassword, newPassword) && code == 0) {
      code = 0;
    } else {
      code = 1;
    }
    return new Response(code, "", null);

  }

  @PostMapping("/register")
  public Response register(@RequestParam(value = "adminId") String nickname,
      @RequestParam(value = "password") String password) {
    Admin admin = adminServiceImpl.register(nickname,password);
    int code = 0;
    if(admin == null){
      code = 1;
    }else{
      session.setAttribute("admin",admin);

    }
    return new Response(code, "", null);
  }

}

