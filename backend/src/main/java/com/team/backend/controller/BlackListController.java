package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.BlackList;
import com.team.backend.model.User;
import com.team.backend.service.impl.BlackListServiceImpl;
import com.team.backend.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@RestController
@RequestMapping("${server.api-path}/block")
public class BlackListController {
    private User user;

    @Resource
    private BlackListServiceImpl blackListService;

    @PostMapping
    public Result blockUser(@RequestBody Map<String,Object> requestMap) {
        Number userIdToNum = (Number) requestMap.get("userIdTo");
        Integer isBlockInteger = (Integer) requestMap.get("isBlock");
        Long userId = user.getId();
        BlackList blackList = new BlackList();
        Result result;
        if (userIdToNum != null && isBlockInteger != null) {
            Long userIdTo = userIdToNum.longValue();
            boolean isBlock = isBlockInteger != 0;
            blackList.setBeUserId(userIdTo);
            blackList.setUserId(userId);
            try {
                if(blackListService.blockUser(blackList, isBlock)){
                    result = Result.success();
                }else {
                    result = Result.error(ExceptionInfo.POST_BLOCK_INFO_LOST.getCode()
                            ,ExceptionInfo.POST_BLOCK_INFO_LOST.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = Result.error(ExceptionInfo.POST_BLOCK_FAIL.getCode()
                        ,ExceptionInfo.POST_BLOCK_FAIL.getMessage());
            }
        }else {
            result = Result.error(ExceptionInfo.POST_BLOCK_INFO_LOST.getCode()
                    ,ExceptionInfo.POST_BLOCK_INFO_LOST.getMessage());
        }
        return result;
    }
}

