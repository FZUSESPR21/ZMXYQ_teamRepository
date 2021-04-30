package com.team.backend.util;

import com.team.backend.model.Admin;
import com.team.backend.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
/**
 * @author : ccreater
 * @ClassName : com.team.backend.util.AuthAspect
 * @Description : 类描述
 * @date : 2021-04-29 15:44 Copyright  2021 ccreater. All rights reserved.
 */
@Aspect
@Component
public class AuthAspect {
  @Autowired
  private HttpSession session;
  @Before("execution(* com.team.backend.controller.*.*(..))")
  public void setRoleId(JoinPoint pjp){
    Object obj = pjp.getTarget();
    Class clazz=obj.getClass();
    try {
      Field f=clazz.getDeclaredField("user");
      f.setAccessible(true);
      User user=(User)session.getAttribute("user");
      f.set(obj, user);

    } catch (NoSuchFieldException | IllegalAccessException e) {}
  }
  @Before("execution(* com.team.backend.controller.AdminController.*(..))")
  public void setAdmin(JoinPoint pjp){
    Object obj = pjp.getTarget();
    Class clazz=obj.getClass();
    try {
      Field f=clazz.getDeclaredField("admin");
      f.setAccessible(true);
      Admin user=(Admin)session.getAttribute("admin");
      f.set(obj, user);

    } catch (NoSuchFieldException | IllegalAccessException e) {}
  }

}
