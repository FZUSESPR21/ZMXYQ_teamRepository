package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.BlackList;
import com.team.backend.model.PostEyeOn;
import com.team.backend.model.Result;
import com.team.backend.model.TreeHole;
import com.team.backend.mapper.TreeHoleMapper;
import com.team.backend.service.TreeHoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@Service
public class TreeHoleServiceImpl extends ServiceImpl<TreeHoleMapper, TreeHole> implements
    TreeHoleService {

  @Autowired
  TreeHoleMapper treeHoleMapper;

  // 查询树洞内容
  public Result<List<TreeHole>> queryTreeHole(Long id) {

    Result<List<TreeHole>> result = new Result<>();

    QueryWrapper<TreeHole> wrapper = new QueryWrapper<>();
    wrapper.eq("from_id", id);
    List<TreeHole> treeHoleList = treeHoleMapper.selectList(wrapper);

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(treeHoleList);
    return result;
  }

  // 新增树洞内容
  public Result<Integer> insertTreeHole(Long id, String content) {

    Result<Integer> result = new Result<>();

    if (content == null || content == "") {
      result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getMessage());
      result.setData(0);
      return result;
    }
    TreeHole treeHole = new TreeHole();
    treeHole.setFromId(id);
    treeHole.setMessage(content);
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(treeHoleMapper.insert(treeHole));
    return result;
  }

  // 更新树洞内容
  public Result<Integer> updateTreeHole(TreeHole treeHole) {

    Result<Integer> result = new Result<>();

    if (treeHole.getId() == null) {
      result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_ID_NULL").getMessage());
      result.setData(0);
      return result;
    }

    if (treeHole.getMessage() == null || treeHole.getMessage() == "") {
      result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getMessage());
      result.setData(0);
      return result;
    }

    // 判断数据库是否存在这条树洞
    TreeHole sqlTreeHole = treeHoleMapper.selectById(treeHole.getId());
    if (sqlTreeHole == null) {
      result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_DELETED").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_DELETED").getMessage());
      result.setData(0);
      return result;
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(treeHoleMapper.updateById(treeHole));
    return result;
  }

  // 删除树洞
  public Result<Integer> deleteTreeHole(Long id) {

    Result<Integer> result = new Result<>();

    if (id == null) {
      result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_ID_NULL").getMessage());
      result.setData(0);
      return result;
    }

    // 判断数据库是否存在这条树洞
    TreeHole sqlTreeHole = treeHoleMapper.selectById(id);
    if (sqlTreeHole == null) {
      result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_DELETED").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_DELETED").getMessage());
      result.setData(0);
      return result;
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(treeHoleMapper.deleteById(id));
    return result;

  }

}
