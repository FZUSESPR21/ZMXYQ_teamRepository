package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.*;
import com.team.backend.model.*;
import com.team.backend.service.PartyTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.lang.Long;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@Service
public class PartyTypeServiceImpl extends ServiceImpl<PartyTypeMapper, PartyType> implements
    PartyTypeService {

    @Autowired
    PartyCommentMapper partyCommentMapper;

    @Autowired
    PartyMapper partyMapper;

    @Autowired
    PartyTypeMapper partyTypeMapper;

    @Autowired
    PartyParticipantsMapper partyParticipantsMapper;

    public Result<List<Party>> PartylistByTypeID(int id) {// 根据TypeID获取组局

        Result<List<Party>> result = new Result<>();
        QueryWrapper<Party> wrapper = new QueryWrapper<>();
        List<Party> partylistByTypeID = new LinkedList<>();

        if (id == -1) {//当传入为"-1"时返回所有组局信息
            partylistByTypeID = partyMapper.selectList(null);
        } else if (id == -2) {//当传入"-2"时返回有空位的组局
            partylistByTypeID = partyMapper.selectByMyWrapper();
        } else if (id == 0 || id == 1 || id == 2 || id == 3 || id == 4 || id == 5 || id == 6 || id == 7 || id == 8) {
            //0-自习 1-电影 2-聚餐 3-拼车 4-拼单 5-运动 6-游戏 7-旅行 8-其他
            wrapper.eq("party_type_id", id);
            partylistByTypeID = partyMapper.selectList(wrapper);
        } else {
            result.setCode(ExceptionInfo.valueOf("PARTY_TYPE_NOT_EXISTED").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_TYPE_NOT_EXISTED").getMessage());
            result.setData(null);
            return result;
        }
        result.setCode(ExceptionInfo.valueOf("OK").getCode());
        result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
        result.setData(partylistByTypeID);
        return result;
    }

}
