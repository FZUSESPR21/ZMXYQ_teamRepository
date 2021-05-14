package com.team.backend;

import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.User;
import com.team.backend.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.SQLException;

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

    }
}
