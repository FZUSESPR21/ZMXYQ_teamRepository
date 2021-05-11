package com.team.backend.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.team.backend.model.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@Component
public interface PostMapper extends BaseMapper<Post> {
    List<Post> listPostPageOrderByGmtCreateDesc(IPage<?> page,Long currentUserId);
    List<Post> fuzzyListPostPageOrderByGmtCreateDesc(IPage<?> page, Map<String,Object> queryMap);
}
