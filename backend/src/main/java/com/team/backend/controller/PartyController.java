package com.team.backend.controller;


import com.team.backend.service.PartyService;
import com.team.backend.util.Response;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Party;
import com.team.backend.model.PersonalCollection;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.service.impl.PartyCommentServiceImpl;
import com.team.backend.service.impl.PartyParticipantsServiceImpl;
import com.team.backend.service.impl.PartyServiceImpl;
import com.team.backend.service.impl.PartyTypeServiceImpl;
import com.team.backend.util.Response;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
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

  User user;

  @Resource
  private PartyServiceImpl partyService;

  /**
   * 新建组局接口
   * <p>
   * 接口：/api/party/insert
   * <p>
   *
   * @param requestMap the request map
   * @param request    the request
   * @return the result
   */
  @PostMapping("/insert")
  public Result<Integer> insertParty(@RequestBody Map<String, Object> requestMap
      , HttpServletRequest request) {

    Number partyTypeNum = (Number) requestMap.get("partyTypeId");
    Long partyTypeId = partyTypeNum.longValue();
    String description = (String) requestMap.get("description");
    String imageUrls = (String) requestMap.get("images");
    int peopleCnt = (int) requestMap.get("peopleCnt");
    com.team.backend.model.Result<Integer> result = partyService
        .insertParty(user.getId(), description, imageUrls, peopleCnt, partyTypeId);
    return result;
  }

  /**
   * 修改组局信息接口
   * <p>
   * 接口：/api/party/update
   * <p>
   *
   * @param id, String description, String imageUrls, int peopleCnt, Long partyTypeId
   * @return the result
   */
  @PostMapping("/update")
  public Result<Integer> updateParty(Long id, String description, String imageUrls, int peopleCnt,
      Long partyTypeId) {
    return partyService.updateParty(id, description, imageUrls, peopleCnt, partyTypeId);
  }

  /**
   * 查看我的组局接口
   * <p>
   * 接口：/api/party/myparty
   * <p>
   */
  @GetMapping("/myparty")
  public Result<List<Party>> getMyparty() {

    User user = null;

    if (user == null) {
      Result<List<Party>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return partyService.GetMyPartyList(user.getId());
  }

  /**
   * 组局详情接口
   * <p>
   * 接口：/api/party/partymes
   * <p>
   *
   * @param partyId
   * @return the result
   */
  @GetMapping("/partymes")
  public Result<Party> getPartymes(long partyId) {

    return partyService.getPartymes(partyId);

  }

  /**
   * 参加组局接口
   * <p>
   * 接口：/api/party/jion
   * <p>
   *
   * @param partyId
   * @return the result
   */
  @PostMapping("/jion")
  public Result<Integer> joinParty(long partyId) {

    User user = null;

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return partyService.joinParty(user.getId(), partyId);
  }

  /**
   * 退出组局接口
   * <p>
   * 接口：/api/party/exit
   * <p>
   *
   * @param partyId
   * @return the result
   */
  @PostMapping("/exit")
  public Result<Integer> exitParty(long partyId) {

    User user = null;

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return partyService.exitParty(user.getId(), partyId);
  }

  /**
   * 删除组局接口
   * <p>
   * 接口：/api/party/delete
   * <p>
   *
   * @param partyId
   * @return the result
   */
  @PostMapping("/delete")
  public Result<Integer> deleteParty(Long partyId) {

    User user = null;

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return partyService.deleteParty(user.getId(), partyId);
  }

  /**
   * 组局搜索接口
   * <p>
   * 接口：/api/party/search
   * <p>
   *
   * @param massage
   * @return the result
   */
  @PostMapping("/search")
  public Result<Integer> searchParty(String massage) {
    Result result = new Result();
    result.setData(1);
    if (partyService.searchParty(massage) != null) {
      result.setData(0);
    }
    return result;
  }
  //PARTY部分的controller层
}

