package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.util.Base64Util;
import java.util.UUID;

import javafx.util.Pair;
import org.springframework.stereotype.Service;

/**
 * Base64文件存储Service
 *
 * @author Tars
 * @since 2021-05-02
 */
@Service
public class Base64ImageServiceImpl {
  public Pair<ExceptionInfo,String> saveImage(String source, String fileName) {
    Pair<ExceptionInfo,String> info = new Pair<>(ExceptionInfo.POST_IMAGE_STORE_FAIL,"");
    String[] sourceOrigin = source.split(",");
    if (sourceOrigin.length == 2) {
      String suffix = Base64Util.generateFileSuffixByBase64Prefix(sourceOrigin[0]);
      if (!StringUtils.isBlank(suffix)) {
        String uuid = UUID.randomUUID().toString().replace("-","");
        String backendFileName = uuid + suffix;
        int result = Base64Util.decryptByBase64AndSave(sourceOrigin[1],backendFileName);
        if (result == 0) {
          info = new Pair<>(ExceptionInfo.OK,backendFileName);
        }else if (result == 1 ) {
          info = new Pair<>(ExceptionInfo.POST_IMAGE_FOLDER_NOT_CREATED,"");
        }else if (result == 2) {
          info = new Pair<>(ExceptionInfo.POST_IMAGE_CONTENT_EMPTY,"");
        }else if (result == 3) {
          info = new Pair<>(ExceptionInfo.POST_IMAGE_STORE_PATH_NOT_FOUND,"");
        }
      }
    }
    return info;
  }
}
