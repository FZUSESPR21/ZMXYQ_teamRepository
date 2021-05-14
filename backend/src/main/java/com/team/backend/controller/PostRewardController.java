package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.PostReward;
import com.team.backend.model.User;
import com.team.backend.service.impl.PostRewardServiceImpl;
import com.team.backend.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@RestController
@RequestMapping("${server.api-path}/alumnicycle/posts/reward")
public class PostRewardController {
    @Resource
    private PostRewardServiceImpl postRewardService;

    User user;

    @PostMapping
    public Result rewardPost(@RequestBody PostReward postReward) {
        Long userId = user.getId();
        postReward.setIdFrom(userId);
        Result result;
        try {
            result = postRewardService.rewardPost(postReward);
        }catch (Exception e) {
            e.printStackTrace();
            result = Result.error(ExceptionInfo.POST_REWARD_INFO_NOT_VALID.getCode()
                    ,ExceptionInfo.POST_REWARD_INFO_NOT_VALID.getMessage());
        }
        return result;
    }
}

