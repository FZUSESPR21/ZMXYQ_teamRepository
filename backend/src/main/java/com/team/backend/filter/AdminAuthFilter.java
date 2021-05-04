package com.team.backend.filter;

import com.team.backend.model.Admin;
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

/**
 * @author : ccreater
 * @ClassName : com.team.backend.Filter.AdminAuthFilter
 * @Description : 类描述
 * @date : 2021-05-04 22:04 Copyright  2021 ccreater. All rights reserved.
 */

@WebFilter(urlPatterns = "/admin/*", filterName = "adminAuthFilter")
public class AdminAuthFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpSession session = request.getSession();
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String [] noAuth = new String[]{"login","register","changepsw"};
    String uri = request.getRequestURI();
    for(String path : noAuth){
      if(uri.indexOf(path)>=0){
        filterChain.doFilter(servletRequest, servletResponse);
        return;
      }
    }
    //need admin auth
    if(isAdmin(session)){
      filterChain.doFilter(servletRequest, servletResponse);
    }else{
      response.sendError(401);
      return;
    }
  }

  public boolean isAdmin(HttpSession session){
    Admin admin = (Admin) session.getAttribute("admin");
    if(admin == null){
      return false;
    }else{
      return true;
    }
  }
}
