package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Result;
import com.team.backend.service.impl.PartyParticipantsServiceImpl;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;


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
  private PartyParticipantsServiceImpl partyParticipantsService;

  /**
   * 将参与者移出组局接口
   * <p>
   * 接口：/api/party-participants/moveoff
   * <p>
   *
   * @param partyId,userId
   * @return the result
   */

  @PostMapping("/moveoff")
  public Result<Integer> moveOffParticipant(@RequestParam Number partyId,
      @RequestParam Number userId) {
    if (partyId == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.PARTY_ID_NULL.getCode());
      result.setMessage(ExceptionInfo.PARTY_ID_NULL.getMessage());
      return result;
    }
    if (userId == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.USER_ID_NULL.getCode());
      result.setMessage(ExceptionInfo.USER_ID_NULL.getMessage());
      return result;
    }
    Long partyIdLong = partyId.longValue();
    Long userIdLong = userId.longValue();
    return partyParticipantsService.moveOffParticipant(partyIdLong, userIdLong);

  }

}

