package com.team.backend.util;


import com.team.backend.exception.ExceptionInfo;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.util.UserLegal
 * @Description : 用户信息合法性验证类
 * @date : 2021-04-29 16:21:25 Copyright  2021 user. All rights reserved.
 */
public class UserLegal {

  // 判断用户ID的合法性
  public String idLegal(Long id) {

    // 用户ID为空
    if (id == null) {
      return "USER_ID_NULL";
    }

    // 用户ID < 0
    if (id < 0) {
      return "USER_ID_BELOW0";
    }

    return "OK";
  }

  // 判断用户名的合法性
  public String usernameLegal(String username) {

    // 用户名为空
    if (username == null || username == "") {
      return "USER_NAME_NULL";
    }
    // 用户名长度过长
    if (username.length() > 15) {
      return "USER_NAME_LENGTH";
    }

    return "OK";
  }

  // 判断上传图片的合法性
  public String imgLegal(File file) throws IOException {
    // 判断用户上传文件是否存在
    if (!file.exists() || file.length() < 1) {
      return "USER_IMG_NULL";
    }

    //判断用户上传文件是否为图片
    ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
    Iterator iterator = ImageIO.getImageReaders(imageInputStream);
    if (!iterator.hasNext()) {
      return "USER_NOT_IMG";
    }

    return "OK";
  }

  // 判断头像url的合法性
  public String iconUrlLegal(String url) {
    if (url != null && url != "") {
      Pattern pattern = Pattern.compile(
          "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
      if (!pattern.matcher(url).matches()) {
        return "USER_NOT_URL";
      }
    }

    return "OK";
  }

  // 判断证件照url的合法性
  public String imgUrlLegal(String url) {
    if (url == null || url == "") {
      return "USER_IMG_URL_NULL";
    }
    Pattern pattern = Pattern.compile(
        "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
    if (!pattern.matcher(url).matches()) {
      return "USER_NOT_IMG_URL";
    }

    return "OK";
  }

  // 判断用户学校名合法性
  public String schoolLegal(String school) {

    // 判断学校名是否为空
    if (school == null || school == "") {
      return "USER_SCHOOL_NULL";
    }

    // 判断学校名的长度是否过长
    if (school.length() > 50) {
      return "USER_SCHOOL_LENGTH";
    }

    //判断学校名是否存在非中文字符
    int n = 0;
    for (int i = 0; i < school.length(); i++) {
      n = (int) school.charAt(i);

      if (!(19968 <= n && n < 40869)) {
        return "USER_SCHOOL_CHINESE";
      }
    }
    return "OK";
  }

  //判断用户性别的合法性
  public String sexLegal(Integer sex) {

    // 判断性别值是否为0或1
    if (sex != null) {
      if (sex != 0 && sex != 1 && sex != 2) {
        return "USER_SEX_LEGAL";
      }
    }

    return "OK";
  }

}
