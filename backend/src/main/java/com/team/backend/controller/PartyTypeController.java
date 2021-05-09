package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Party;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.service.impl.PartyCommentServiceImpl;
import com.team.backend.service.impl.PartyParticipantsServiceImpl;
import com.team.backend.service.impl.PartyServiceImpl;
import com.team.backend.service.impl.PartyTypeServiceImpl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/party-type")
public class PartyTypeController {

  User user;

  @Resource
  private PartyTypeServiceImpl partyTypeService;

  /**
   * 根据Type获取组局接口
   * <p>
   * 接口：/api/party-type/getparty
   * <p>
   *
   * @param partyTypeID
   * @return the result
   */
  @GetMapping("/getparty")
  public Result<List<Party>> getMyparty(int partyTypeID) {

    return partyTypeService.PartylistByTypeID(partyTypeID);

  }

}

