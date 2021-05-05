package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team.backend.util.Base64Util;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class Base64ImageService {
  public String saveImage(String source) {
    String[] sourceOrigin = source.split(",");
    if (sourceOrigin.length == 2) {
      String suffix = Base64Util.generateFileSuffixByBase64Prefix(sourceOrigin[0]);
      if (!StringUtils.isBlank(suffix)) {
        String uuid = UUID.randomUUID().toString().replace("-","");
        String fileName = uuid + suffix;
        int result = Base64Util.decryptByBase64AndSave(sourceOrigin[1],fileName);

      }
    }
  }
}
