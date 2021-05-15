package com.team.backend;

import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Result;
import com.team.backend.model.TreeHole;
import com.team.backend.service.impl.TreeHoleServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.TreeHoleServiceImplTest
 * @Description : TreeHoleService的测试类
 * @date : 2021-05-03 13:32:46 Copyright  2021 user. All rights reserved.
 */
@SpringBootTest
public class TreeHoleServiceImplTest {

  @Autowired
  TreeHoleServiceImpl treeHoleService;

  @Test
  void queryTreeHoleTest() {

    Result<List<TreeHole>> result = treeHoleService.queryTreeHole(123456L);
    System.out.println(result);
  }

  @Test
  void insertTreeHoleTest() {

    Result<Integer> result = new Result<>();

    result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getCode());
    result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getMessage());
    result.setData(0);
    Assertions.assertEquals(result, treeHoleService.insertTreeHole(123456L, null));

    result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getCode());
    result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getMessage());
    result.setData(0);
    Assertions.assertEquals(result, treeHoleService.insertTreeHole(123456L, ""));

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(1);
    Assertions.assertEquals(result, treeHoleService.insertTreeHole(123456L, "再小的帆也能远航"));
  }

  @Test
  void updateTreeHoleTest() {

    Result<Integer> result = new Result<>();
    TreeHole treeHole = new TreeHole();

    treeHole.setId(null);
    result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_ID_NULL").getCode());
    result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_ID_NULL").getMessage());
    result.setData(0);
    Assertions.assertEquals(result, treeHoleService.updateTreeHole(treeHole));

    treeHole.setId(999999999L);
    result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_DELETED").getCode());
    result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_DELETED").getMessage());
    result.setData(0);
    Assertions.assertEquals(result, treeHoleService.updateTreeHole(treeHole));

    treeHole.setId(2L);

    treeHole.setMessage(null);
    result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getCode());
    result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getMessage());
    result.setData(0);
    Assertions.assertEquals(result, treeHoleService.updateTreeHole(treeHole));

    treeHole.setMessage("");
    result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getCode());
    result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_NULL").getMessage());
    result.setData(0);
    Assertions.assertEquals(result, treeHoleService.updateTreeHole(treeHole));

    treeHole.setMessage("今天天气真好");

    treeHole.setFromId(99999999L);
    result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
    result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
    result.setData(0);
    Assertions.assertEquals(result, treeHoleService.updateTreeHole(treeHole));

    treeHole.setFromId(123456L);

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(1);
    Assertions.assertEquals(result, treeHoleService.updateTreeHole(treeHole));
  }

//  @Test
//  void deleteTreeHoleTest() {
//
//    Result<Integer> result = new Result<>();
//
//    result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_ID_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_ID_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, treeHoleService.deleteTreeHole(123456L, null));
//
//    result.setCode(ExceptionInfo.valueOf("USER_TREE_HOLE_DELETED").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_TREE_HOLE_DELETED").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, treeHoleService.deleteTreeHole(123456L, 99999999L));
//
//    result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, treeHoleService.deleteTreeHole(99999999L, 1L));
//
//    result.setCode(ExceptionInfo.valueOf("OK").getCode());
//    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
//    result.setData(1);
//    Assertions.assertEquals(result, treeHoleService.deleteTreeHole(123456L, 1L));
//  }
}
