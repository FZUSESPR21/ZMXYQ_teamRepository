package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Report;
import com.team.backend.model.User;
import com.team.backend.service.ReportService;
import com.team.backend.service.impl.ReportServiceImpl;
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
@RequestMapping("${server.api-path}/alumnicycle/posts/tipoff")
public class ReportController {
    private User user;

    @Resource
    private ReportServiceImpl reportService;

    @PostMapping
    public Result reportPost(@RequestBody Map<String,Number> requestMap) {
        Result result;

        if(requestMap.containsKey("postId") && requestMap.get("postId") != null) {
            Long postId = requestMap.get("postId").longValue();
            Long userId = user.getId();

            Report report = new Report();
            report.setUserId(userId);
            report.setPostId(postId);
            try {
                if (reportService.reportPost(report)) {
                    result = Result.success();
                } else {
                    result = Result.error(ExceptionInfo.POST_REPORT_INFO_INVALID.getCode()
                            ,ExceptionInfo.POST_REPORT_INFO_INVALID.getMessage());
                }
            }catch (Exception e) {
                e.printStackTrace();
                result = Result.error(ExceptionInfo.POST_REPORT_INFO_INVALID.getCode()
                        ,ExceptionInfo.POST_REPORT_INFO_INVALID.getMessage());
            }
        }else {
            result = Result.error(ExceptionInfo.POST_REPORT_INFO_LOST.getCode()
                    ,ExceptionInfo.POST_REPORT_INFO_LOST.getMessage());
        }
        return result;
    }
}

