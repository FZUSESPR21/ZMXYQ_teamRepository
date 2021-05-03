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

  // 判断url的合法性
  public String urlLegal(String url) {
    if (url != null && url != "") {
      Pattern pattern = Pattern.compile(
          "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
      if (!pattern.matcher(url).matches()) {
        return "USER_NOT_URL";
      }
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
      if (sex != 0 && sex != 1) {
        return "USER_SEX_LEGAL";
      }
    }

    return "OK";
  }

  // 判断用户出生日期合法性
  public String birthdayLegal(Date date){

    // 判断出生日期是否为空
    if (date==null){
      return "USER_BIRTHDAY_NULL";
    }

    // 判断日期是否合法
    if (!isDate(date)){
      return "USER_NOT_BIRTHDAY";
    }

    return "OK";
  }

  // 日期合法性函数
  public boolean isDate(Date date) {

    SimpleDateFormat formater = new SimpleDateFormat();
    formater.applyPattern("yyyy-MM-dd");
    String time = formater.format(date);
    String[] dataSubs = time.split("-");    //以-号为标志，分隔年月日

    int year = Integer.valueOf(dataSubs[0]);
    int month = Integer.valueOf(dataSubs[1]);
    int day = Integer.valueOf(dataSubs[2]);

    ///计算闰年的2月份
    if (dataSubs[0].endsWith("00")) {         //如果年份为100的整数
      if (year % 400 == 0) {    //能被400整除，即闰年
        //月份
        if (month == 2) { //2月份有29号
          if (day > 0 && day < 30) {
            return true;
          }
        }
      }
    } else {
      if (year % 4 == 0) {  //能被4整除，即闰年
        //月份
        if (month == 2) { //2月份有29号
          if (day > 0 && day < 30) {
            return true;
          }
        }
      }
    }
    //计算平年的各月份
    if (month == 2) { //2月份无29号
      if (day > 0 && day < 29) {
        return true;
      }
    } else if (month == 4 || month == 6 || month == 9 || month == 11) {          //4,6,9,11月份
      if (day > 0 && day < 31) {
        return true;
      }
    } else {                         //剩余的月份
      if (day > 0 && day < 32) {
        return true;
      }
    }
    return false;
  }


}
