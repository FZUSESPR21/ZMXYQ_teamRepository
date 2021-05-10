package com.team.backend.controller;


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
@RequestMapping("/party-comment")
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
  public Result<Integer> commentParty(@RequestBody Map<String, Object> requestMap,
      HttpServletRequest request) {

    String content = (String) requestMap.get("content");
    Number userIdNumber = (Number) requestMap.get("userId");
    Long userId = userIdNumber.longValue();
    Number partyIdNumber = (Number) requestMap.get("partyId");
    Long partyId = partyIdNumber.longValue();
    Number preIdIdNumber = (Number) requestMap.get("preId");
    Long preId = preIdIdNumber.longValue();

    PartyComment partyComment = new PartyComment();
    partyComment.setInformation(content);
    partyComment.setIdFrom(userId);
    partyComment.setPartyId(partyId);
    partyComment.setPreId(preId);

    return partyCommentService.commentParty(partyComment);
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
  public com.team.backend.model.Result<List<PartyComment>> getCommentList(Long partyId) {

    return partyCommentService.PartyCommentList(partyId);
  }


}

