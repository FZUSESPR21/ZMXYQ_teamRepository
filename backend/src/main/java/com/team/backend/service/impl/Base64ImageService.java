package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.util.Base64Util;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class Base64ImageService {
  public ExceptionInfo saveImage(String source,String fileName) {
    ExceptionInfo info = ExceptionInfo.POST_IMAGE_STORE_FAIL;
    String[] sourceOrigin = source.split(",");
    if (sourceOrigin.length == 2) {
      String suffix = Base64Util.generateFileSuffixByBase64Prefix(sourceOrigin[0]);
      if (!StringUtils.isBlank(suffix)) {
        String uuid = UUID.randomUUID().toString().replace("-","");
        String backendFileName = uuid + suffix;
        int result = Base64Util.decryptByBase64AndSave(sourceOrigin[1],backendFileName);
        if (result == 0) {
          info = ExceptionInfo.OK;
          fileName = backendFileName;
        }else if (result == 1 ) {
          info = ExceptionInfo.POST_IMAGE_FOLDER_NOT_CREATED;
        }else if (result == 2) {
          info = ExceptionInfo.POST_IMAGE_CONTENT_EMPTY;
        }else if (result == 3) {
          info = ExceptionInfo.POST_IMAGE_STORE_PATH_NOT_FOUND;
        }
      }
    }
    return info;
  }
}
