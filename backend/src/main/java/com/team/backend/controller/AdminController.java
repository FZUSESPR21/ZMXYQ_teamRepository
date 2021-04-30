package com.team.backend.controller;


import com.team.backend.util.Response;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/test")
  public Response greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    Map<String,Object> data=new LinkedHashMap<>();
    Map<String,Object> data2=new LinkedHashMap<>();
    data2.put("fuck","fuck");
    data.put("name",data2);
    return new Response(0, name,data);
  }
}

