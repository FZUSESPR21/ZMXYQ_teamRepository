package com.team.backend.filter;

import com.team.backend.model.Admin;
import com.team.backend.model.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author : ccreater
 * @ClassName : com.team.backend.filter.UserAuthFilter
 * @Description : 类描述
 * @date : 2021-05-07 21:04 Copyright  2021 ccreater. All rights reserved.
 */
@WebFilter(urlPatterns = "/*", filterName = "userAuthFilter")
public class UserAuthFilter implements Filter {
  @Value("${server.servlet.context-path}")
  String contextValue;
  @Value("${test.istest}")
  int isTest;
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    if(isTest==1){
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpSession session = request.getSession();
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String [] noAuth = new String[]{"/user/upload/img","/user/login"};
    String uri = request.getRequestURI();
    for(String path : noAuth){
      if(uri.equals(contextValue+path)){
        filterChain.doFilter(servletRequest, servletResponse);
        return;
      }
    }
    //need admin auth
    if(isLogin(session)){
      filterChain.doFilter(servletRequest, servletResponse);
    }else{
      response.sendError(401);
      return;
    }
  }

  public boolean isLogin(HttpSession session){
    User user = (User) session.getAttribute("user");
    if(user == null){
      return false;
    }else{
      return true;
    }
  }
}
