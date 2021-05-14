package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.PartyComment;
import com.team.backend.model.Result;
import com.team.backend.service.impl.PartyCommentServiceImpl;
import com.team.backend.service.impl.PartyServiceImpl;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("${server.api-path}/party-comment")
public class PartyCommentController {

  @Resource
  private PartyServiceImpl partyService;

  @Resource
  private PartyCommentServiceImpl partyCommentService;

  /**
   * 评论组局接口Controller
   * <p>
   * 接口：/api/party-comment/comment
   * <p>
   *
   * @param requestMap
   * @return the result
   */
  @PostMapping
  public com.team.backend.util.Result commentParty(@RequestBody Map<String, Object> requestMap,
      HttpServletRequest request) {
    PartyComment partyComment = new PartyComment();
//    Number IdNumber = (Number) requestMap.get("Id");
//    Long Id = IdNumber.longValue();
    String content = (String) requestMap.get("information");
    Number userIdNumber = (Number) requestMap.get("userId");
    Long userId = userIdNumber.longValue();
    Number partyIdNumber = (Number) requestMap.get("partyId");
    Long partyId = partyIdNumber.longValue();
    Number preIdIdNumber = (Number) requestMap.get("preId");
    Long preId = preIdIdNumber.longValue();

    partyComment.setInformation(content);
    partyComment.setIdFrom(userId);
    partyComment.setPartyId(partyId);
    partyComment.setPreId(preId);
    partyComment.setStatus(0);
    partyComment.setDeleted(0);

    boolean isComSuccess = false;

    com.team.backend.util.Result result;
    try {
      isComSuccess = partyCommentService.commentParty(partyComment);
      if (isComSuccess) {
        result = com.team.backend.util.Result.success();
      } else {
        result = com.team.backend.util.Result.error(ExceptionInfo.PARTY_COMMENT_INFO_LOST.getCode()
            , ExceptionInfo.PARTY_COMMENT_INFO_LOST.getMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
      result = com.team.backend.util.Result.error(ExceptionInfo.PARTY_COMMENT_POST_FAIL.getCode()
          , ExceptionInfo.PARTY_COMMENT_POST_FAIL.getMessage());
    }
    return result;
  }

  /**
   * 获取组局评论列表接口Controller
   * <p>
   * 接口：/api/party-comment/commentlsit
   * <p>
   *
   * @param partyId
   * @return the result
   */
  @PostMapping("/commentlsit")
  public Result<List<Map<String, Object>>> getCommentList(Long partyId) {

    return partyCommentService.PartyCommentList(partyId);
  }


}

