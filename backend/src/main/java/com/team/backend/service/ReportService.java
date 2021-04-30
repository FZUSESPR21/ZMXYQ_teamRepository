package com.team.backend.service;

import com.team.backend.model.Report;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangyu
 * @author Tars
 * @since 2021-04-28
 */
public interface ReportService extends IService<Report> {

  /**
   * 举报帖子
   *
   * @param report the report 举报信息，需保证postId，userId有值
   * @return the boolean 是否处理成功
   */
  boolean reportPost(Report report);

}
