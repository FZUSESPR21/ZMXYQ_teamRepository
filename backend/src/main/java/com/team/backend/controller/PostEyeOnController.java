package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.PostEyeOnMapper;
import com.team.backend.model.PostEyeOn;
import com.team.backend.model.User;
import com.team.backend.service.impl.PostEyeOnServiceImpl;
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
 * @author Tars
 * @since 2021-04-28
 */
@RestController
@RequestMapping("/alumnicycle/posts/collect")
public class PostEyeOnController {
    @Resource
    private PostEyeOnServiceImpl postEyeOnService;

    private User user;

    @PostMapping
    public Result eyeOnPost(@RequestBody PostEyeOn postEyeOn) {
        Result result;
        Long userId = user.getId();
        postEyeOn.setIdFrom(userId);
        try {
            if (postEyeOnService.collectPost(postEyeOn)) {
                result = Result.success();
            } else {
                result = Result.error(ExceptionInfo.POST_EYE_ON_FAIL.getCode()
                        , ExceptionInfo.POST_EYE_ON_FAIL.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = Result.error(ExceptionInfo.POST_EYE_ON_FAIL.getCode()
                    , ExceptionInfo.POST_EYE_ON_FAIL.getMessage());
        }
        return result;
    }
}

