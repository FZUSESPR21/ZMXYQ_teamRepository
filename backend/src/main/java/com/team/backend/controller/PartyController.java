package com.team.backend.controller;


import com.team.backend.service.PartyService;
import com.team.backend.util.Response;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import javax.annotation.Resource;
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
 * @author yangyu
 * @since 2021-04-28
 */
@RestController
@RequestMapping("/party")
public class PartyController {
  @Autowired
  PartyService partyService;
  @PostMapping("/add")
  public Response addParty(@RequestBody Map<String, Object> map) {
    String id = (String)map.get("userID");
    String desc = (String)map.get("description");
    //@TODO: img list

    int peopleCnt = (int)map.get("peopleCnt");
    long partyTypeID = (long) map.get("partyTypeID");

    return new Response(0,"");
  }

}

