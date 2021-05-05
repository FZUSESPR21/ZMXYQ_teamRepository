package com.team.backend.util;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class Base64UtilTest {

  @Test
  void createFile() {
    try {
      File file = new File("1.txt");
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  @Test
  void decryptByBase64AndSave() {
      try {
          File file = new File("1.txt");
          InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
          BufferedReader bufferedReader = new BufferedReader(reader);
          StringBuilder stringBuilder = new StringBuilder();
          int temp;
          while ( (temp = bufferedReader.read()) != -1) {
            stringBuilder.append((char) temp);
          }
          String source = stringBuilder.toString();
          String[] sourceOrigin = source.split(",");
          System.out.println(sourceOrigin[0]);
        System.out.println(Base64Util.generateFileSuffixByBase64Prefix(sourceOrigin[0]));
//        System.out.println(sourceOrigin[1].substring(0,10));
        String fileName = UUID.randomUUID().toString().replace("-","");
        String suffix = Base64Util.generateFileSuffixByBase64Prefix(sourceOrigin[0]);
          int result = Base64Util.decryptByBase64AndSave(sourceOrigin[1], fileName+suffix);
          System.out.println(result);
      }catch (IOException e) {
          e.printStackTrace();
      }
  }

  @Test
  public void mimeTest() {
    Base64Util.generateFileSuffixByBase64Prefix("");
  }
}