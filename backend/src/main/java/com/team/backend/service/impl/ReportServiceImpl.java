package com.team.backend.service.impl;

import com.team.backend.model.Report;
import com.team.backend.mapper.ReportMapper;
import com.team.backend.service.ReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangyu
 * @author Tars 
 * @since 2021-04-28
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

  @Resource
  private ReportMapper reportMapper;

  @Override
  public boolean reportPost(Report report) {
    boolean result = false;
    if (report.getUserId() != null && report.getPostId() != null) {
      if(reportMapper.insert(report) == 1) {
        result = true;
      }
    }
    return result;
  }
}
