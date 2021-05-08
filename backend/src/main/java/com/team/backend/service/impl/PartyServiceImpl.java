package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.*;
import com.team.backend.model.Party;
import com.team.backend.model.PartyParticipants;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.service.PartyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.backend.util.UserLegal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@Service
public class PartyServiceImpl extends ServiceImpl<PartyMapper, Party> implements PartyService {
    @Autowired
    PartyMapper partyMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PartyCommentMapper partyCommentMapper;
    @Autowired
    PartyParticipantsMapper partyParticipantsMapper;
    @Autowired
    PartyTypeMapper partyTypeMapper;

    public User getPartyPublisher(Long publisherId) {
        User user = new User();
        if (publisherId != null) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("username", "birthday", "is_graduated", "province", "city", "school"
                    , "user_icon_url");
            queryWrapper.eq("id", publisherId);
            user = userMapper.selectOne(queryWrapper);
        }
        return user;
    }

    // 新建组局
    public Result<Integer> insertParty(Long id, String description, String imageUrls, int peopleCnt, Long partyTypeId) {

        Result<Integer> result = new Result<>();
        UserLegal userLegal = new UserLegal();
        if (description == null || description == "") {
            result.setCode(ExceptionInfo.valueOf("PARTY_DESCRIPTION_NULL").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_DESCRIPTION_NULL").getMessage());
            result.setData(0);
            return result;
        }
        if (imageUrls == null || imageUrls == "") {
            result.setCode(ExceptionInfo.valueOf("PARTY_IMAGEURLS_NULL").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_IMAGEURLS_NULL").getMessage());
            result.setData(0);
            return result;
        }
//        if (imageUrls != null && !userLegal.iconUrlLegal(imageUrls).equals("OK")) {//引用图片URL判断方法
//            result.setCode(ExceptionInfo.valueOf("PARTY_NOT_URL").getCode());
//            result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_URL").getMessage());
//            result.setData(0);
//            return result;
//        }
        if (peopleCnt == 0) {
            result.setCode(ExceptionInfo.valueOf("PARTY_PEOPLECNT_NULL").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_PEOPLECNT_NULL").getMessage());
            result.setData(0);
            return result;
        }
        if (partyTypeId == null) {
            result.setCode(ExceptionInfo.valueOf("PARTY_PARTYTYPEID_NULL").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_PARTYTYPEID_NULL").getMessage());
            result.setData(0);
            return result;
        }
        Party party = new Party();
        party.setPublisherId(id);
        party.setDescription(description);
        party.setImageUrls(imageUrls);
        party.setPeopleCnt(peopleCnt);
        party.setPartyTypeId(partyTypeId);
        result.setCode(ExceptionInfo.valueOf("OK").getCode());
        result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
        result.setData(partyMapper.insert(party));
        return result;
    }

    // 获取组局审核状态
//    public Result<Integer> PartyIdentifyStatus(long id) {
//
//        Result<Integer> result = new Result<>();
//        result.setCode(ExceptionInfo.valueOf("OK").getCode());
//        result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
//        Party party = partyMapper.selectById(id);
//        result.setData(party.getStatus());
//        return result;
//    }

    // 修改组局信息
    public Result<Integer> updateParty(Long id, String description, String imageUrls, int peopleCnt, Long partyTypeId) {
        Result<Integer> result = new Result<>();
        Party party = partyMapper.selectById(id);
        UserLegal userLegal = new UserLegal();
        if (party.getId() == null) {
            result.setCode(ExceptionInfo.valueOf("PARTY_ID_NULL").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_ID_NULL").getMessage());
            result.setData(0);
            return result;
        }
        if (description == null || description == "") {
            result.setCode(ExceptionInfo.valueOf("PARTY_DESCRIPTION_NULL").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_DESCRIPTION_NULL").getMessage());
            result.setData(0);
            return result;
        }
        if (imageUrls == null || imageUrls == "") {
            result.setCode(ExceptionInfo.valueOf("PARTY_IMAGEURLS_NULL").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_IMAGEURLS_NULL").getMessage());
            result.setData(0);
            return result;
        }
//        if (imageUrls != null && !userLegal.iconUrlLegal(imageUrls).equals("OK")) {//引用图片URL判断方法
//            result.setCode(ExceptionInfo.valueOf("PARTY_NOT_URL").getCode());
//            result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_URL").getMessage());
//            result.setData(0);
//            return result;
//        }
        if (peopleCnt == 0) {
            result.setCode(ExceptionInfo.valueOf("PARTY_PEOPLECNT_NULL").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_PEOPLECNT_NULL").getMessage());
            result.setData(0);
            return result;
        }
        if (partyTypeId == null) {
            result.setCode(ExceptionInfo.valueOf("PARTY_PARTYTYPEID_NULL").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_PARTYTYPEID_NULL").getMessage());
            result.setData(0);
            return result;
        }
        // 判断数据库是否存在这个组局
        Party aqlParty = partyMapper.selectById(party.getId());
        if (aqlParty == null) {
            result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
            result.setData(0);
            return result;
        }
        result.setCode(ExceptionInfo.valueOf("OK").getCode());
        result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
        result.setData(partyMapper.updateById(party));
        return result;
    }

    //查看我的组局
    public Result<List<Party>> GetMyPartyList(Long id) {//使用个人ID查询

        Result<List<Party>> result = new Result<>();
        QueryWrapper<Party> wrapper = new QueryWrapper<>();
        wrapper.eq("publisher_id", id);
        List<Party> MyPartyList = partyMapper.selectList(wrapper);

        result.setCode(ExceptionInfo.valueOf("OK").getCode());
        result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
        result.setData(MyPartyList);
        return result;
    }

    //获取组局详情
    public Result<Party> getPartymes(Long id) { //根据组局ID获取组局详情

        Result<Party> result = new Result<>();

        result.setCode(ExceptionInfo.valueOf("OK").getCode());
        result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
        Party party = partyMapper.selectById(id);
        result.setData(party);
        return result;
    }


    //参加组局
    public Result<Integer> joinParty(long userid, long id) {
        Result<Integer> result = new Result<>();
        Party jionParty = partyMapper.selectById(id);
        // 判断数据库是否存在该组局
        if (jionParty == null) {
            result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
            result.setData(0);
            return result;
        }
        QueryWrapper<PartyParticipants> wrapper = new QueryWrapper<>();
        wrapper.eq("party_id", id);
        List<PartyParticipants> partyParticipantsList = partyParticipantsMapper.selectList(wrapper);
        if (partyParticipantsList.size() < jionParty.getPeopleCnt()) {// 判断该组局是否已满员
            PartyParticipants partyParticipants = new PartyParticipants();
            partyParticipants.setPartyId(id);
            partyParticipants.setParticipantId(userid);
            partyParticipants.setDeleted(0);//设置删除逻辑为0
            java.util.Date date = new java.util.Date();
            partyParticipants.setGmtCreate(date);//设置时间
            partyParticipants.setGmtModified(date);
            result.setCode(ExceptionInfo.valueOf("OK").getCode());
            result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
            result.setData(partyParticipantsMapper.insert(partyParticipants));
        } else {
            result.setCode(ExceptionInfo.valueOf("PARTY_NOT_AVAILABLE").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_AVAILABLE").getMessage());
            result.setData(0);
        }
        return result;
    }

    //退出组局
    public Result<Integer> exitParty(long id) {//缺少请求退出组局的用户ID
        Result<Integer> result = new Result<>();
        Party exitParty = partyMapper.selectById(id);
        // 判断数据库是否存在该组局
        if (exitParty == null) {
            result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
            result.setData(0);
            return result;
        }
        QueryWrapper<PartyParticipants> wrapper = new QueryWrapper<>();
        wrapper.eq("party_id", id);//这会直接把所有参与该组局的参与者删除
//                .eq("participant_id",userid);
        result.setData(partyParticipantsMapper.delete(wrapper));
        return result;
    }


    //删除组局
    public Result<Integer> deleteParty(Long userId, Long id) {//比接口文档加了个userID的参数

        Result<Integer> result = new Result<>();
        if (id == null) {
            result.setCode(ExceptionInfo.valueOf("PARTY_ID_NULL").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_ID_NULL").getMessage());
            result.setData(0);
            return result;
        }

        // 判断数据库是否存在该组局
        Party dlParty = partyMapper.selectById(id);
        if (dlParty == null) {
            result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
            result.setData(0);
            return result;
        }
        // 判断该组局是否属于当前用户
        if (!dlParty.getPublisherId().equals(userId)) {
            result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
            result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
            result.setData(0);
            return result;
        }

        result.setCode(ExceptionInfo.valueOf("OK").getCode());
        result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
        result.setData(partyMapper.deleteById(id));
        return result;

    }

    // 根据massage模糊搜索组局
    public Result<List<Party>> searchParty(String massage) {//发布者具体信息也要返回

        Result<List<Party>> result = new Result<>();

        QueryWrapper<Party> wrapper = new QueryWrapper<>();
        //用massage进行组局搜索
        wrapper.like("id", massage).or()
                .like("description", massage).or()
                .like("publisher_id", massage).or()
                .like("party_type_id", massage).or()
                .like("people_cnt", massage).or()
                .like("image_urls", massage).or()
                .like("gmt_create", massage);
        List<Party> partyList = partyMapper.selectList(wrapper);
        result.setCode(ExceptionInfo.valueOf("OK").getCode());
        result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
        result.setData(partyList);
        return result;
    }


}