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
  public void mimeTest() {
    Base64Util.generateFileSuffixByBase64Prefix("");
  }
}