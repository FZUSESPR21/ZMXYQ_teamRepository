package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.service.impl.PartyParticipantsServiceImpl;
import com.team.backend.service.impl.PartyServiceImpl;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("${server.api-path}/party-participants")
public class PartyParticipantsController {

  @Resource
  private PartyServiceImpl partyService;

  @Resource
  private PartyParticipantsServiceImpl partyParticipantsService;

  /**
   * 将参与者移出组局接口
   * <p>
   * 接口：/api/party-participants/moveoff
   * <p>
   *
   * @param partyId
   * @return the result
   */

  @PostMapping("/moveoff")
  public Result<Integer> exitParty(Long partyId, Long userId) {

    return partyParticipantsService.moveOffParticipant(partyId, userId);

  }

}

