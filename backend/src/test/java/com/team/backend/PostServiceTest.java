package com.team.backend;

import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.User;
import com.team.backend.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class PostServiceTest {
    @Resource
    private PostServiceImpl postService;

    @Test
    public void getPostPublisherTest0 () {
        User postPublisher = postService.getPostPublisher(123456L);
        Assertions.assertNotEquals(postPublisher.getUsername(),null);
        Assertions.assertNotEquals(postPublisher.getBirthday(),null);
        Assertions.assertNotEquals(postPublisher.getIsGraduated(),null);
        Assertions.assertNotEquals(postPublisher.getProvince(),null);
        Assertions.assertNotEquals(postPublisher.getCity(),null);
        Assertions.assertNotEquals(postPublisher.getSchool(),null);
        Assertions.assertNotEquals(postPublisher.getUserIconUrl(),null);
    }

    @Test
    public void getPostPublisherTest1 () {
        User postPublisher = postService.getPostPublisher(null);
        Assertions.assertNull(postPublisher.getUsername());
        Assertions.assertNull(postPublisher.getBirthday());
        Assertions.assertNull(postPublisher.getIsGraduated());
        Assertions.assertNull(postPublisher.getProvince());
        Assertions.assertNull(postPublisher.getCity());
        Assertions.assertNull(postPublisher.getSchool());
        Assertions.assertNull(postPublisher.getUserIconUrl());
    }

    @Test
    public void publishPost0() {
        ExceptionInfo exceptionInfo = postService.publishPost(123456L, 1L, "danyuanceshi", "");
        Assertions.assertEquals(exceptionInfo,ExceptionInfo.OK);
    }
    @Test
    public void publishPost1() {
        ExceptionInfo exceptionInfo = postService.publishPost(null, 1L, "danyuanceshi", "");
        Assertions.assertEquals(exceptionInfo,ExceptionInfo.POST_PUBLISH_INFO_LOST);
    }
    @Test
    public void publishPost2() {
        ExceptionInfo exceptionInfo = postService.publishPost(123456L, null, "danyuanceshi", "");
        Assertions.assertEquals(exceptionInfo,ExceptionInfo.POST_PUBLISH_INFO_LOST);
    }
    @Test
    public void publishPost3() {
        ExceptionInfo exceptionInfo = postService.publishPost(123456L, 1L, null, "");
        Assertions.assertEquals(exceptionInfo,ExceptionInfo.POST_PUBLISH_INFO_LOST);
    }
    @Test
    public void publishPost4() {
        ExceptionInfo exceptionInfo = postService.publishPost(123456L, 1L, "danyuanceshi", null);
        Assertions.assertEquals(exceptionInfo,ExceptionInfo.OK);
    }

    @Test
    public void publishPost5() {
        ExceptionInfo exceptionInfo = postService.publishPost(123456L, 1L, "danyuanceshi", "");
        Assertions.assertEquals(exceptionInfo,ExceptionInfo.OK);
    }

    @Test
    public void publishPost6() {
        Assertions.assertThrows(Exception.class,()->{
            postService.publishPost(123456L, 6L, "danyuanceshi", "");
        });
    }

    @Test
    public void listPostPageOrderByGmtCreateIdDescTest0(){
        List<Map<String, Object>> mapList = postService.listPostPageOrderByGmtCreateIdDesc(1L, 10L, 123456L);
        System.out.println(mapList);
        Assertions.assertEquals(mapList.size(),10);
        for (Map<String,Object> info :mapList) {
            Assertions.assertNotNull(info.get("postId"));
            Assertions.assertNotNull(info.get("publisherId"));
            Assertions.assertNotNull(info.get("publisherName"));
            Assertions.assertNotNull(info.get("postType"));
            Assertions.assertNotNull(info.get("message"));
            Assertions.assertNotNull(info.get("imageUrls"));
            Assertions.assertNotNull(info.get("gmtCreate"));
            Assertions.assertNotNull(info.get("isEyeOn"));
            Assertions.assertNotNull(info.get("eyeOnNum"));
            Assertions.assertNotNull(info.get("isLike"));
            Assertions.assertNotNull(info.get("likeNum"));
            Assertions.assertNotNull(info.get("rewardNum"));
            Assertions.assertNotNull(info.get("comments"));
        }
    }

    @Test
    public void listPostPageOrderByGmtCreateIdDescTest1(){
        List<Map<String, Object>> mapList = postService.listPostPageOrderByGmtCreateIdDesc(100L, 10L, 123456L);
        System.out.println(mapList);
        for (Map<String,Object> info :mapList) {
            Assertions.assertEquals(mapList.size(), 0);
            Assertions.assertNotNull(info.get("postId"));
            Assertions.assertNotNull(info.get("publisherId"));
            Assertions.assertNotNull(info.get("publisherName"));
            Assertions.assertNotNull(info.get("postType"));
            Assertions.assertNotNull(info.get("message"));
            Assertions.assertNotNull(info.get("imageUrls"));
            Assertions.assertNotNull(info.get("gmtCreate"));
            Assertions.assertNotNull(info.get("isEyeOn"));
            Assertions.assertNotNull(info.get("eyeOnNum"));
            Assertions.assertNotNull(info.get("isLike"));
            Assertions.assertNotNull(info.get("likeNum"));
            Assertions.assertNotNull(info.get("rewardNum"));
            Assertions.assertNotNull(info.get("comments"));
        }
    }

    @Test
    public void listPostPageOrderByGmtCreateIdDescTest2(){
        List<Map<String, Object>> mapList = postService.listPostPageOrderByGmtCreateIdDesc(1L, 10000L, 123456L);
        System.out.println(mapList);
        for (Map<String,Object> info :mapList) {
            Assertions.assertNotNull(info.get("postId"));
            Assertions.assertNotNull(info.get("publisherId"));
            Assertions.assertNotNull(info.get("publisherName"));
            Assertions.assertNotNull(info.get("postType"));
            Assertions.assertNotNull(info.get("message"));
            Assertions.assertNotNull(info.get("imageUrls"));
            Assertions.assertNotNull(info.get("gmtCreate"));
            Assertions.assertNotNull(info.get("isEyeOn"));
            Assertions.assertNotNull(info.get("eyeOnNum"));
            Assertions.assertNotNull(info.get("isLike"));
            Assertions.assertNotNull(info.get("likeNum"));
            Assertions.assertNotNull(info.get("rewardNum"));
            Assertions.assertNotNull(info.get("comments"));
        }
    }
    @Test
    public void fuzzyListPostPageOrderByGmtCreateDescTest0(){
        List<Map<String, Object>> mapList = postService.fuzzyListPostPageOrderByGmtCreateDesc("这",1
                , 10000, 123456L);
        System.out.println(mapList.toString());
        for (Map<String,Object> info :mapList) {
            Assertions.assertNotNull(info.get("postId"));
            Assertions.assertNotNull(info.get("publisherId"));
            Assertions.assertNotNull(info.get("publisherName"));
            Assertions.assertNotNull(info.get("postType"));
            Assertions.assertNotNull(info.get("message"));
            Assertions.assertNotNull(info.get("imageUrls"));
            Assertions.assertNotNull(info.get("gmtCreate"));
            Assertions.assertNotNull(info.get("isEyeOn"));
            Assertions.assertNotNull(info.get("eyeOnNum"));
            Assertions.assertNotNull(info.get("isLike"));
            Assertions.assertNotNull(info.get("likeNum"));
            Assertions.assertNotNull(info.get("rewardNum"));
            Assertions.assertNotNull(info.get("comments"));
        }
    }

    @Test
    public void listPostPageByTypeId0() {
        List<Map<String, Object>> mapList = postService.listPostPageByTypeId(1L
                , 123456L, 1, 10);
        System.out.println(mapList.toString());
        for (Map<String, Object> info : mapList) {
            Assertions.assertNotNull(info.get("postId"));
            Assertions.assertNotNull(info.get("publisherId"));
            Assertions.assertNotNull(info.get("publisherName"));
            Assertions.assertNotNull(info.get("postType"));
            Assertions.assertNotNull(info.get("message"));
            Assertions.assertNotNull(info.get("imageUrls"));
            Assertions.assertNotNull(info.get("gmtCreate"));
            Assertions.assertNotNull(info.get("isEyeOn"));
            Assertions.assertNotNull(info.get("eyeOnNum"));
            Assertions.assertNotNull(info.get("isLike"));
            Assertions.assertNotNull(info.get("likeNum"));
            Assertions.assertNotNull(info.get("rewardNum"));
            Assertions.assertNotNull(info.get("comments"));
        }
    }

    @Test
    public void selectTopTenLikePost() {
        List<Map<String, Object>> mapList = postService.selectTopTenLikePost(123456L);
        System.out.println(mapList.toString());
        for (Map<String, Object> info : mapList) {
            Assertions.assertNotNull(info.get("postId"));
            Assertions.assertNotNull(info.get("publisherId"));
            Assertions.assertNotNull(info.get("publisherName"));
            Assertions.assertNotNull(info.get("postType"));
            Assertions.assertNotNull(info.get("message"));
            Assertions.assertNotNull(info.get("imageUrls"));
            Assertions.assertNotNull(info.get("gmtCreate"));
            Assertions.assertNotNull(info.get("isEyeOn"));
            Assertions.assertNotNull(info.get("eyeOnNum"));
            Assertions.assertNotNull(info.get("isLike"));
            Assertions.assertNotNull(info.get("likeNum"));
            Assertions.assertNotNull(info.get("rewardNum"));
            Assertions.assertNotNull(info.get("comments"));
        }
    }
}
