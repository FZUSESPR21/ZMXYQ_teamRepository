package com.team.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.exception.ExceptionInfo
 * @Description : 错误信息枚举类
 * @date : 2021-04-29 12:50:33 Copyright  2021 user. All rights reserved.
 */

/**
 * 60: 用户异常 61: 帖子异常 62: 组局异常
 */
@AllArgsConstructor
@Getter
public enum ExceptionInfo {

  OK(200, "ok"),

  USER_IMG_NULL(6000, "图片不存在"),
  USER_NOT_IMG(6001, "用户上传文件非图片"),
  USER_ID_NULL(6002, "用户ID为空"),
  USER_ID_BELOW0(6003, "用户ID小于0"),
  USER_ID_EXISTED(6004, "用户ID已存在"),
  USER_NAME_NULL(6005, "用户名为空"),
  USER_NAME_LENGTH(6005, "用户名长度大于20个字节"),
  USER_NOT_URL(6006, "url不合法"),
  USER_SCHOOL_NULL(6007, "学校为空"),
  USER_SCHOOL_LENGTH(6008, "学校名字大于50个字节"),
  USER_SCHOOL_CHINESE(6009, "学校名字包含非中文字符"),
  USER_SEX_LEGAL(6010, "性别不合法，只能为0或1"),
  USER_BIRTHDAY_NULL(6011, "用户出生日期为空"),
  USER_NOT_BIRTHDAY(6012, "用户出生日期不合法"),
  USER_POST_ID_NULL(6013, "个人帖子ID为空"),
  USER_POST_DELETED(6014, "不存在此条帖文"),
  USER_POST_COMMENT_ID_NULL(6015, "个人帖文评论ID为空"),
  USER_POST_COMMENT_DELETED(6016, "不存在此条帖文评论"),
  USER_PARTY_COMMENT_ID_NULL(6017, "个人组局评论ID为空"),
  USER_PARTY_COMMENT_DELETED(6018, "不存在此条组局评论"),
  USER_NOT_EXISTED(6019, "用户不存在"),
  USER_COLLECTION_ID_NULL(6020,"收藏ID为空"),
  USER_COLLECTION_DELETED(6021,"不存在此条个人收藏"),
  POST_IMAGE_FOLDER_NOT_CREATED(6501,"文件夹未创建"),
  POST_IMAGE_CONTENT_EMPTY(6502,"Base64字符串或存储文件名为空"),
  POST_IMAGE_STORE_PATH_NOT_FOUND(6503,"未找到图片存储位置"),
  POST_IMAGE_STORE_FAIL(6504,"图片文件存储失败"),
  ;

  private Integer code;
  private String message;


}
