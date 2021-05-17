package com.team.backend.controller;


import com.team.backend.model.Result;
import com.team.backend.service.impl.PartyTypeServiceImpl;
import java.util.List;
import java.util.Map;
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
@RequestMapping("${server.api-path}/party-type")
public class PartyTypeController {

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
  public Result<List<Map<String, Object>>> getMyparty(@RequestParam int partyTypeID) {

    return partyTypeService.PartylistByTypeID(partyTypeID);

  }

}

