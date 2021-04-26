package com.team.backend.service.impl;

import com.team.backend.model.Report;
import com.team.backend.mapper.ReportMapper;
import com.team.backend.service.ReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-26
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

}
