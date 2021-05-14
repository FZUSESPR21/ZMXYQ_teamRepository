package com.team.backend;

import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.PartyComment;
import com.team.backend.model.PersonalBlackItem;
import com.team.backend.model.PersonalCollection;
import com.team.backend.model.Post;
import com.team.backend.model.PostComment;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.service.impl.UserServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.UserServiceImplTest
 * @Description : UserService的测试类
 * @date : 2021-05-03 12:07:36 Copyright  2021 user. All rights reserved.
 * <p> 采用条件覆盖进行测试
 */
@SpringBootTest
public class UserServiceImplTest {

//  @Autowired
//  UserServiceImpl userService;
//
//  @Test
//  void identifyUserTest() {
//
//    Result<Integer> result = new Result<>();
//    User user = new User();
//
//    user.setId(null);
//    result.setCode(ExceptionInfo.valueOf("USER_ID_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_ID_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setId(-123456L);
//    result.setCode(ExceptionInfo.valueOf("USER_ID_BELOW0").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_ID_BELOW0").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setId(9999999999L);// 数据库不存在此条记录
//    result.setCode(ExceptionInfo.valueOf("USER_NOT_EXISTED").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NOT_EXISTED").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setId(123456L);
//
//    user.setUsername("");
//    result.setCode(ExceptionInfo.valueOf("USER_NAME_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NAME_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setUsername("会飞的abc是可爱的小猪猪猪猪猪猪呀");
//    result.setCode(ExceptionInfo.valueOf("USER_NAME_LENGTH").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NAME_LENGTH").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setUsername("会飞的abc是可爱的小猪猪呀");
//
//    user.setUserIconUrl("");
//    result.setCode(ExceptionInfo.valueOf("USER_ICON_URL_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_ICON_URL_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setUserIconUrl("w.xx.com");
//    result.setCode(ExceptionInfo.valueOf("USER_NOT_URL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NOT_URL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setUserIconUrl("https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG");
//
//    user.setCertificateImageUrl("");
//    result.setCode(ExceptionInfo.valueOf("USER_IMG_URL_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_IMG_URL_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setCertificateImageUrl("w.xx.com");
//    result.setCode(ExceptionInfo.valueOf("USER_NOT_IMG_URL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NOT_IMG_URL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setCertificateImageUrl("https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG");
//
//    user.setSchool("");
//    result.setCode(ExceptionInfo.valueOf("USER_SCHOOL_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_SCHOOL_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setSchool("xx大学");
//    result.setCode(ExceptionInfo.valueOf("USER_SCHOOL_CHINESE").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_SCHOOL_CHINESE").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setSchool("福州大学");
//
//    user.setSex(3); // 0~女, 1~男, 2~无
//    result.setCode(ExceptionInfo.valueOf("USER_SEX_LEGAL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_SEX_LEGAL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//
//    user.setSex(2);
//
//    result.setCode(ExceptionInfo.valueOf("OK").getCode());
//    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
//    result.setData(1);
//    Assertions.assertEquals(result, userService.identifyUser(user));
//  }
//
//  @Test
//  void identifyStatusTest() {
//
//    Result<Integer> result = new Result<>();
//
//    result.setCode(ExceptionInfo.valueOf("OK").getCode());
//    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.identifyStatus(123456L));
//  }
//
//  @Test
//  void postListTest() {
//
//    Result<List<Post>> result = userService.postList(123456L);
//    System.out.println(result);
//  }
//
//  @Test
//  void postDeletedTest() {
//
//    Result<Integer> result = new Result<>();
//
//    result.setCode(ExceptionInfo.valueOf("USER_POST_ID_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_POST_ID_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.postDeleted(123456L, null));
//
//    result.setCode(ExceptionInfo.valueOf("USER_POST_DELETED").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_POST_DELETED").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.postDeleted(123456L, 999999999L));
//
//    result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.postDeleted(12L, 1L));
//
//    result.setCode(ExceptionInfo.valueOf("OK").getCode());
//    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
//    result.setData(1);
//    Assertions.assertEquals(result, userService.postDeleted(123456L, 3L));
//
//  }
//
//  @Test
//  void PostCommentListTest() {
//
//    Result<List<PostComment>> result = userService.PostCommentList(123456L);
//    System.out.println(result);
//
//  }
//
//  @Test
//  void PartyCommentListTest() {
//
//    Result<List<PartyComment>> result = userService.PartyCommentList(123456L);
//    System.out.println(result);
//
//  }
//
//  @Test
//  void PostCommentDeletedTest() {
//
//    Result<Integer> result = new Result<>();
//
//    result.setCode(ExceptionInfo.valueOf("USER_POST_COMMENT_ID_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_POST_COMMENT_ID_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.PostCommentDeleted(123456L, null));
//
//    result.setCode(ExceptionInfo.valueOf("USER_POST_COMMENT_DELETED").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_POST_COMMENT_DELETED").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.PostCommentDeleted(123456L, 999999999L));
//
//    result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.PostCommentDeleted(12L, 3L));
//
//    result.setCode(ExceptionInfo.valueOf("OK").getCode());
//    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
//    result.setData(1);
//    Assertions.assertEquals(result, userService.PostCommentDeleted(123456L, 4L));
//
//  }
//
//  @Test
//  void PartyCommentDeletedTest() {
//
//    Result<Integer> result = new Result<>();
//
//    result.setCode(ExceptionInfo.valueOf("USER_PARTY_COMMENT_ID_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_PARTY_COMMENT_ID_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.PartyCommentDeleted(123456L, null));
//
//    result.setCode(ExceptionInfo.valueOf("USER_PARTY_COMMENT_DELETED").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_PARTY_COMMENT_DELETED").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.PartyCommentDeleted(123456L, 999999999L));
//
//    result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.PartyCommentDeleted(12L, 1L));
//
//    result.setCode(ExceptionInfo.valueOf("OK").getCode());
//    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
//    result.setData(1);
//    Assertions.assertEquals(result, userService.PartyCommentDeleted(123456L, 2L));
//
//  }
//
//  @Test
//  void queryUserTest() {
//
//    Result<User> result = userService.queryUser(123456L);
//    System.out.println(result);
//  }
//
//  @Test
//  void updateUserTest() throws ParseException {
//
//    Result<Integer> result = new Result<>();
//    User user = new User();
//
//    user.setId(null);
//    result.setCode(ExceptionInfo.valueOf("USER_ID_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_ID_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.updateUser(user));
//
//    user.setId(-123456L);
//    result.setCode(ExceptionInfo.valueOf("USER_ID_BELOW0").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_ID_BELOW0").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.updateUser(user));
//
//    user.setId(99999999L);// 数据库不存在一条ID为99999999L的记录
//    result.setCode(ExceptionInfo.valueOf("USER_NOT_EXISTED").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NOT_EXISTED").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.updateUser(user));
//
//    user.setId(1L);
//
//    user.setUsername("");
//    result.setCode(ExceptionInfo.valueOf("USER_NAME_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NAME_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.updateUser(user));
//
//    user.setUsername("会飞的abc是可爱的小猪猪猪猪猪猪呀");
//    result.setCode(ExceptionInfo.valueOf("USER_NAME_LENGTH").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NAME_LENGTH").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.updateUser(user));
//
//    user.setUsername("会飞的xyz是可爱的小猪猪呀");
//
//    user.setUserIconUrl("");
//    result.setCode(ExceptionInfo.valueOf("USER_ICON_URL_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_ICON_URL_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.updateUser(user));
//
//    user.setUserIconUrl("w.xx.com");
//    result.setCode(ExceptionInfo.valueOf("USER_NOT_URL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NOT_URL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.updateUser(user));
//
//    user.setUserIconUrl("https://www.fzu.edu.cn/attach/2021/04/29/419363.JPG");
//
//    user.setSchool("");
//    result.setCode(ExceptionInfo.valueOf("USER_SCHOOL_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_SCHOOL_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.updateUser(user));
//
//    user.setSchool("xx大学");
//    result.setCode(ExceptionInfo.valueOf("USER_SCHOOL_CHINESE").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_SCHOOL_CHINESE").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.updateUser(user));
//
//    user.setSchool("福州大学");
//
//    user.setSex(3); // 0~女, 1~男, 2~无
//    result.setCode(ExceptionInfo.valueOf("USER_SEX_LEGAL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_SEX_LEGAL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.updateUser(user));
//
//    user.setSex(1);
//    String dateString = "2000-1-1";
//    Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
//    user.setBirthday(birthday);
//
//    result.setCode(ExceptionInfo.valueOf("OK").getCode());
//    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
//    result.setData(1);
//    Assertions.assertEquals(result, userService.updateUser(user));
//
//  }
//
//  @Test
//  void listCollectionTest() {
//
//    Result<List<PersonalCollection>> result = userService.listCollection(123456L);
//    System.out.println(result);
//  }
//
//  @Test
//  void deleteCollectionTest() {
//
//    Result<Integer> result = new Result<>();
//
//    result.setCode(ExceptionInfo.valueOf("USER_COLLECTION_ID_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_COLLECTION_ID_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.deleteCollection(123456L, null));
//
//    result.setCode(ExceptionInfo.valueOf("USER_COLLECTION_DELETED").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_COLLECTION_DELETED").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.deleteCollection(123456L, 999999999L));
//
//    result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.deleteCollection(12L, 4L));
//
//    result.setCode(ExceptionInfo.valueOf("OK").getCode());
//    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
//    result.setData(1);
//    Assertions.assertEquals(result, userService.deleteCollection(123456L, 5L));
//
//  }
//
//  @Test
//  void listBlackTest() {
//
//    Result<List<PersonalBlackItem>> result = userService.listBlack(123456L);
//    System.out.println(result);
//
//  }
//
//  @Test
//  void deleteBlackTest() {
//
//    Result<Integer> result = new Result<>();
//
//    result.setCode(ExceptionInfo.valueOf("USER_BLACK_ID_NULL").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_BLACK_ID_NULL").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.deleteBlack(123456L, null));
//
//    result.setCode(ExceptionInfo.valueOf("USER_BLACK_DELETED").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_BLACK_DELETED").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.deleteBlack(123456L, 999999999L));
//
//    result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
//    result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
//    result.setData(0);
//    Assertions.assertEquals(result, userService.deleteBlack(999999999L, 1L));
//
//    result.setCode(ExceptionInfo.valueOf("OK").getCode());
//    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
//    result.setData(1);
//    Assertions.assertEquals(result, userService.deleteBlack(123456L, 2L));
//  }
//

}
