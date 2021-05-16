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
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.service.impl.PartyServiceImpl;
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
@RequestMapping("${server.api-path}/party")
public class PartyController {
  @Resource
  private PartyServiceImpl partyService;
  User user;



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
  public com.team.backend.util.Result insertParty(@RequestBody Map<String, Object> requestMap
      , HttpServletRequest request) {
    Number userIdNum = (Number) requestMap.get("userId");
    Long userId = userIdNum.longValue();
    String description = (String) requestMap.get("description");
    List<String> imageUrls = (List<String>) requestMap.get("images");
    int peopleCnt = (int) requestMap.get("peopleCnt");
    Number partyTypeNum = (Number) requestMap.get("partyTypeId");
    Long partyTypeId = partyTypeNum.longValue();
    ExceptionInfo exceptionInfo = partyService
        .insertParty(userId, description, imageUrls, peopleCnt, partyTypeId);
    return com.team.backend.util.Result.error(exceptionInfo.getCode(), exceptionInfo.getMessage());
  }

  /**
   * 修改组局信息接口
   * <p>
   * 接口：/api/party/update
   * <p>
   *
   * @param requestMap the request map
   * @param request    the request
   */
  @PostMapping("/update")
  public Result<Integer> updateParty(@RequestBody Map<String, Object> requestMap
      , HttpServletRequest request) {
    Number partyIdNum = (Number) requestMap.get("partyId");
    Long partyId = partyIdNum.longValue();
    String description = (String) requestMap.get("description");
    List<String> imageUrls = (List<String>) requestMap.get("images");
    int peopleCnt = (int) requestMap.get("peopleCnt");
    Number partyTypeNum = (Number) requestMap.get("partyTypeId");
    Long partyTypeId = partyTypeNum.longValue();
    return partyService.updateParty(partyId, description, imageUrls, peopleCnt, partyTypeId);
  }

  /**
   * 查看我的组局接口
   * <p>
   * 接口：/api/party/myparty
   * <p>
   *
   * @return
   */
  @GetMapping("/myparty")
  public Result<List<Map<String, Object>>> getMyparty() {

    if (user == null) {
      Result<List<Map<String, Object>>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return partyService.GetMyPartyList(user.getId());
//    return partyService.GetMyPartyList(id);//测试用
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
  public Result<Map<String, Object>> getPartymes(Long partyId) {

    return partyService.getPartymes(partyId);

  }

  /**
   * 参加组局接口
   * <p>
   * 接口：/api/party/join
   * <p>
   *
   * @param partyId
   * @return the result
   */

  @PostMapping("/join")
  public Result<Integer> joinParty(Long partyId) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }
    return partyService.joinParty(user.getId(), partyId);
//    return partyService.joinParty(userId,partyId);//测试
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
  public Result<Integer> exitParty(Long partyId) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return partyService.exitParty(user.getId(), partyId);
//    return partyService.exitParty(userId, partyId);//测试
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

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return partyService.deleteParty(user.getId(), partyId);
//    return partyService.deleteParty(userId, partyId);//测试
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
  public Result<List<Map<String, Object>>> searchParty(String massage) {

    return partyService.searchParty(massage);
  }
}

