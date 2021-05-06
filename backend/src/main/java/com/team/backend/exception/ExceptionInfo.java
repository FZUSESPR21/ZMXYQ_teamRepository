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
  USER_NAME_LENGTH(6006, "用户名超过15个字符"),
  USER_NOT_URL(6007, "头像url不合法"),
  USER_SCHOOL_NULL(6008, "学校为空"),
  USER_SCHOOL_LENGTH(6009, "学校名字大于50个字节"),
  USER_SCHOOL_CHINESE(6010, "学校名字包含非中文字符"),
  USER_SEX_LEGAL(6011, "性别不合法，只能为0或1"),
  USER_IMG_URL_NULL(6012, "证件照url为空"),
  USER_NOT_IMG_URL(6013, "证件照url不合法"),
  USER_POST_ID_NULL(6014, "个人帖子ID为空"),
  USER_POST_DELETED(6015, "不存在此条帖文"),
  USER_POST_COMMENT_ID_NULL(6016, "个人帖文评论ID为空"),
  USER_POST_COMMENT_DELETED(6017, "不存在此条帖文评论"),
  USER_PARTY_COMMENT_ID_NULL(6018, "个人组局评论ID为空"),
  USER_PARTY_COMMENT_DELETED(6019, "不存在此条组局评论"),
  USER_NOT_EXISTED(6020, "用户不存在"),
  USER_COLLECTION_ID_NULL(6021, "收藏ID为空"),
  USER_COLLECTION_DELETED(6022, "不存在此条个人收藏"),
  USER_BLACK_ID_NULL(6023, "黑名单ID为空"),
  USER_BLACK_DELETED(6024, "不存在此条黑名单记录"),
  USER_TREE_HOLE_NULL(6025, "树洞内容为空"),
  USER_TREE_HOLE_DELETED(6026, "不存在此条树洞记录"),
  USER_TREE_HOLE_ID_NULL(6027, "树洞ID为空"),
  USER_NOT_BELONG(6028, "用户不存在此条记录"),
  USER_NOT_LOGIN(6029, "用户未登录"),
  POST_IMAGE_FOLDER_NOT_CREATED(6101, "文件夹未创建"),
  POST_IMAGE_CONTENT_EMPTY(6102, "Base64字符串或存储文件名为空"),
  POST_IMAGE_STORE_PATH_NOT_FOUND(6103, "未找到图片存储位置"),
  POST_IMAGE_STORE_FAIL(6104, "图片文件存储失败"),
  POST_PUBLISH_INFO_LOST(6105, "发帖信息缺失"),
  POST_PUBLISH_INSERT_FAIL(6106, "发帖信息存储失败"),
  ;

  private Integer code;
  private String message;


}
