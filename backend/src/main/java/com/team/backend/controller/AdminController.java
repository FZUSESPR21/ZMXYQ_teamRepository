package com.team.backend.controller;


import com.team.backend.model.Admin;
import com.team.backend.model.User;
import com.team.backend.service.impl.AdminServiceImpl;
import com.team.backend.service.impl.PartyServiceImpl;
import com.team.backend.service.impl.PostServiceImpl;
import com.team.backend.util.Response;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("${server.api-path}/admin")
public class AdminController {

  private Admin admin;
  @Autowired
  private HttpSession session;
  @Autowired
  private AdminServiceImpl adminServiceImpl;
  @Autowired
  private PostServiceImpl postServiceImpl;
  @Autowired
  private PartyServiceImpl partyServiceImpl;

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
      return new Response(1, "pageIndex非法或没有待审核Post");
    } else {
      if (toIndex >= posts.size()) {
        toIndex = posts.size() - 1;
      }
    }
    posts = posts.subList(fromIndex, toIndex);
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
  @PostMapping("/postconfirm")
  public Response postConfirm(@RequestBody Map<String, Object> map) {
    String id = (String)map.get("postID");
    int pass = (int)map.get("pass");
    if(pass!=1&&pass!=0){
      return new Response(1,"pass值不正确");
    }
    try{
      adminServiceImpl.confirmPost(Long.parseLong(id),pass);
      return new Response(0,"");
    }catch (Exception e){
      return new Response(1,e.getMessage());
    }
  }

  @PostMapping("/party")
  public Response getUncheckedPartyList(@RequestBody Map<String, Object> map) {
    int pageIndex = 0;
    int order = 0;
    try {
      pageIndex = (int) map.get("pageIndex");
      order = (int) map.get("order");
    } catch (Exception e) {
      return new Response(1, "参数不合法");
    }
    List<Map<String, Object>> parties = adminServiceImpl.getUncheckedParty(order);
    Map<String, Object> data = new HashMap<>();
    data.put("count", parties.size());
    int fromIndex = pageIndex * 6;
    int toIndex = (pageIndex + 1) * 6;
    if (fromIndex >= parties.size() || fromIndex < 0) {
      return new Response(1, "pageIndex非法或没有待审核Party");
    } else {
      if (toIndex >= parties.size()) {
        toIndex = parties.size() - 1;
      }
    }
    parties = parties.subList(fromIndex, toIndex);
    List<Map<String,Object>> mes = new ArrayList<>();

    for(Map<String,Object> party : parties){
      Map<String,Object> tmp = new HashMap<>();
      tmp.put("publisherId",party.get("publisher_id"));
      tmp.put("postID",party.get("id"));
      tmp.put("publisherName",partyServiceImpl.getPartyPublisher((long)party.get("publisher_id")).getUsername());
      tmp.put("message",party.get("description"));
      tmp.put("imageUrls",party.get("image_urls"));
      tmp.put("gmtCreate",party.get("gmt_create"));
      mes.add(tmp);
    }

    data.put("mes", mes);
    return new Response(0, "", data);

  }
  @PostMapping("/partyconfirm")
  public Response partyConfirm(@RequestBody Map<String, Object> map) {
    String id = (String)map.get("partyID");
    int pass = (int)map.get("pass");
    if(pass!=1&&pass!=0){
      return new Response(1,"pass值不正确");
    }
    try{
      adminServiceImpl.confirmParty(Long.parseLong(id),pass);
      return new Response(0,"");
    }catch (Exception e){
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      return new Response(1,pw.toString());
    }
  }

  @PostMapping("/authority")
  public Response getUncheckedUserList(@RequestBody Map<String, Object> map) {
    int pageIndex = 0;
    int order = 0;
    try {
      pageIndex = (int) map.get("pageIndex");
      order = (int) map.get("order");
    } catch (Exception e) {
      return new Response(1, "参数不合法");
    }
    List<Map<String, Object>> parties = adminServiceImpl.getUncheckedUser(order);
    Map<String, Object> data = new HashMap<>();
    data.put("count", parties.size());
    int fromIndex = pageIndex * 6;
    int toIndex = (pageIndex + 1) * 6;
    if (fromIndex >= parties.size() || fromIndex < 0) {
      return new Response(1, "pageIndex非法或没有待审核User");
    } else {
      if (toIndex >= parties.size()) {
        toIndex = parties.size() - 1;
      }
    }
    parties = parties.subList(fromIndex, toIndex);
    List<Map<String,Object>> mes = new ArrayList<>();

    for(Map<String,Object> party : parties){
      Map<String,Object> tmp = new HashMap<>();
      tmp.put("userID",party.get("id"));
      tmp.put("userName",party.get("username"));
      tmp.put("imageUrls",party.get("certificate_image_url"));
      tmp.put("gmtCreate",party.get("gmt_create"));
      mes.add(tmp);
    }

    data.put("mes", mes);
    return new Response(0, "", data);

  }
  @PostMapping("/authorityconfirm")
  public Response userConfirm(@RequestBody Map<String, Object> map) {
    String id = (String)map.get("userID");
    int pass = (int)map.get("pass");
    if(pass!=1&&pass!=0){
      return new Response(1,"pass值不正确");
    }
    try{
      adminServiceImpl.confirmUser(Long.parseLong(id),pass);
      return new Response(0,"");
    }catch (Exception e){
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      return new Response(1,sw.toString());
    }
  }


}

