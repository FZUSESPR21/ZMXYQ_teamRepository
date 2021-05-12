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
  USER_CODE_NULL(6030, "微信授权码为空"),
  USER_ICON_URL_NULL(6031, "用户头像url为空"),

  POST_IMAGE_FOLDER_NOT_CREATED(6101, "文件夹未创建"),
  POST_IMAGE_CONTENT_EMPTY(6102, "Base64字符串或存储文件名为空"),
  POST_IMAGE_STORE_PATH_NOT_FOUND(6103, "未找到图片存储位置"),
  POST_IMAGE_STORE_FAIL(6104, "图片文件存储失败"),
  POST_PUBLISH_INFO_LOST(6105, "发帖信息缺失"),
  POST_PUBLISH_INSERT_FAIL(6106, "发帖信息存储失败"),
  POST_LIKE_POST_FAIL(6107, "点赞帖子失败，请检查用户ID和帖子ID有效性"),
  POST_LIKE_ALREADY(6108, "帖子点赞记录已存在，请勿重复点赞"),
  POST_ID_NULL(6109, "帖子ID为空"),
  POST_NOT_EXISTED(6110, "不存在该条帖子"),
  POST_COMMENT_INFO_LOST(6111,"帖子评论信息缺失"),
  POST_COMMENT_POST_FAIL(6112,"评论帖子失败，请检查参数有效性"),
  POST_LIST_INFO_LOST(6113,"获取帖子参数缺失"),
  POST_LIST_SEARCH_INFO_LOST(6114,"搜索帖子参数缺失"),
  POST_LIST_TYPE_INFO_LOST(6115,"获取主题帖子参数缺失"),
  POST_LIST_QUERY_FAIL(6116,"查询帖子失败，请检查参数有效性或稍后重试"),
  POST_LIST_TOP_TEN_INFO_LOST(6117,"获取最多赞的十个帖子参数缺失"),
  POST_EYE_ON_FAIL(6118,"关注失败请检查参数完整性及有效性"),
  POST_BLOCK_INFO_LOST(6119,"拉黑用户参数缺失或拉黑请求逻辑已存在"),
  POST_BLOCK_FAIL(6120,"拉黑用户失败，请检查参数有效性或稍后再试"),
  POST_REWARD_RP_NOT_ENOUGH(6121,"赞赏失败，人品值不足"),
  POST_REWARD_INFO_LOST(6122,"赞赏参数缺失"),
  POST_REWARD_INFO_NOT_VALID(6123,"赞赏失败，请检查参数有效性"),
  POST_PUBLISHER_INFO_LOST(6124,"查询发帖用户失败，请求参数缺失"),
  POST_PUBLISHER_INFO_INVALID(6125,"查询发帖用户失败，请检查参数有效性或稍后再试"),


  PARTY_DESCRIPTION_NULL(6200, "组局描述为空"),
  PARTY_IMAGEURLS_NULL(6201, "组局图片为空"),
  PARTY_PEOPLECNT_NULL(6202, "组局人数限制为空"),
  PARTY_PARTYTYPEID_NULL(6203, "组局类型ID为空"),
  PARTY_ID_NULL(6204, "组局ID为空"),
  PARTY_NOT_EXISTED(6205, "该组局记录不存在"),
  PARTY_TYPE_NOT_EXISTED(6206, "不存在该组局类型"),
  PARTY_NOT_AVAILABLE(6207, "该组局已满员"),
  PARTY_NOTCONTAIN_USER(6208, "当前用户没有参与该组局"),
  PARTY_NOTBELONGTO_USER(6209, "该组局不属于当前用户"),
  PARTY_SEARCHMAS_NULL(6210, "组局搜索条件为空"),
  ;

  private Integer code;
  private String message;


}
