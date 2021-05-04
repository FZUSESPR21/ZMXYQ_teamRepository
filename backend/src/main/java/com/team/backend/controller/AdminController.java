package com.team.backend.controller;


import com.team.backend.model.Admin;
import com.team.backend.model.User;
import com.team.backend.service.PostService;
import com.team.backend.service.impl.AdminServiceImpl;
import com.team.backend.service.impl.PostServiceImpl;
import com.team.backend.util.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  @Autowired
  private PostServiceImpl postServiceImpl;

  @PostMapping("/login")
  public Response login(@RequestBody Map<String, Object> map) {
    String nickname = (String) map.get("adminId");
    String password = (String) map.get("password");
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
  public Response changePwd(@RequestBody Map<String, Object> map) {
    String nickname = (String) map.get("adminId");
    String newPassword = (String) map.get("newPassword");
    String oldPassword = (String) map.get("oldPassword");
    int code = 0;
    if (nickname == "" || nickname == null) {
      if (admin != null) {
        nickname = admin.getNickname();
      } else {
        code = 1;
        return new Response(code, "", null);
      }
    }
    if (adminServiceImpl.changePwd(nickname, oldPassword, newPassword) && code == 0) {
      code = 0;
    } else {
      code = 1;
    }
    return new Response(code, "", null);

  }

  @PostMapping("/register")
  public Response register(@RequestBody Map<String, Object> map) {
    //(@RequestParam(value = "adminId") String nickname,
    //      @RequestParam(value = "password") String password)
    String nickname = (String) map.get("adminId");
    String password = (String) map.get("password");
    Admin admin = adminServiceImpl.register(nickname, password);
    int code = 0;
    if (admin == null) {
      code = 1;
    } else {
      session.setAttribute("admin", admin);

    }
    return new Response(code, "", null);
  }

  @RequestMapping("/logout")
  public Response logout() {
    session.setAttribute("admin", null);
    return new Response(0, "");
  }

  @PostMapping("/post")
  public Response getUncheckedPostList(@RequestBody Map<String, Object> map) {
    int pageIndex = 0;
    int order = 0;
    try {
      pageIndex = (int) map.get("pageIndex");
      order = (int) map.get("order");
    } catch (Exception e) {
      return new Response(1, "参数不合法");
    }
    List<Map<String, Object>> posts = adminServiceImpl.getUncheckedPostList(order);
    Map<String, Object> data = new HashMap<>();
    data.put("count", posts.size());
    int fromIndex = pageIndex * 6;
    int toIndex = (pageIndex + 1) * 6;
    if (fromIndex >= posts.size() || fromIndex < 0) {
      return new Response(1, "pageIndex非法");
    } else {
      if (toIndex >= posts.size()) {
        toIndex = posts.size() - 1;
      }
    }
    posts = posts.subList(fromIndex, toIndex+1);
    List<Map<String,Object>> mes = new ArrayList<>();

    for(Map<String,Object> post : posts){
      Map<String,Object> tmp = new HashMap<>();
      tmp.put("publisherId",post.get("publisher_id"));
      tmp.put("postID",post.get("id"));
      tmp.put("publisherName",postServiceImpl.getPostPublisher((long)post.get("publisher_id")).getUsername());
      tmp.put("message",post.get("message"));
      tmp.put("imageUrls",post.get("image_urls"));
      tmp.put("gmtCreate",post.get("gmt_create"));
      mes.add(tmp);
    }

    data.put("mes", mes);
    return new Response(0, "", data);

  }

}

