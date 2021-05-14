package com.team.backend.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Base64图片解码工具类
 *
 * <p>
 * 用于解析前端发送的Base64图片数据，并保存到程序根目录Base64Decoded/文件夹下
 * </p>
 *
 * @author Tars
 * @since 2021-5-2
 */
public class Base64Util {

    private static boolean isFolderExist;
    private static String folderName="static";//存放在根目录该文件夹下
    private static Map<String,String> mimeTypeMap;

    static {
        Map<String,String> typeMap = new HashMap<>();
        typeMap.put("data:image/png;base64",".png");//png格式图片
        typeMap.put("data:image/jpeg;base64",".jpeg");//jpeg格式图片
        typeMap.put("data:image/gif;base64",".gif");//gif格式图片
        typeMap.put("data:image/bmp;base64",".bmp");//bmp格式图片
        typeMap.put("data:image/x-icon;base64",".ico");//ico格式图片
        setMimeTypeMap(typeMap);

        try {
            folderName = Paths.get(Paths.get(Base64Util.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString(),folderName).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File file = new File(folderName);
        if (!file.exists()) {
            isFolderExist = file.mkdir();
            if (isFolderExist) {
                System.out.println("文件夹创建成功");
            }else {
                System.out.println("文件夹创建失败");
            }
        }else {
            isFolderExist = true;
        }
    }

    /**
     * 解码Base64字符串，并以指定文件名存储到Base64图片存储位置
     *
     * @param source   要解码的Base64字符串
     * @param saveName 存储文件名
     * @return the int 结果代码：0：成功，1：文件夹未创建，：2：Base64字符串或存储文件名为空
     *                      ，3：未找到存储位置，4：文件写入失败
     */
    public static String getFolderName(){
        return Base64Util.folderName;
    }
    public static int decryptByBase64AndSave(String source,String saveName) {
        int result = 0;
        if (!isFolderExist) {
         result = 1;
        }else if (StringUtils.isBlank(source) || StringUtils.isBlank(saveName)) {
            result = 2;
        }else {
            Decoder decoder = Base64.getDecoder();
            try (OutputStream outputStream = new FileOutputStream(Paths.get(folderName,saveName).toString())) {
                byte[] bytes = decoder.decode(source);
                for (int i = 0;i < bytes.length;i++) {
                    if (bytes[i] < 0) {
                        bytes[i] += 256;
                    }
                }
                outputStream.write(bytes);
            }catch (FileNotFoundException e) {
                result = 3;
                e.printStackTrace();
            }catch (IOException e) {
                result = 4;
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据Base64字符串前缀生成文件后缀名
     *
     * @param prefix Base64字符串前缀，类似data:image/png;base64
     * @return 文件后缀名
     */
    public static String generateFileSuffixByBase64Prefix(String prefix) {
        String suffix = "";
        if (prefix != null) {
            if(mimeTypeMap.containsKey(prefix)){
                suffix = mimeTypeMap.get(prefix);
            }
            for (Map.Entry<String,String> mimePair : mimeTypeMap.entrySet()) {
                if (prefix.equals(mimePair.getKey())) {
                    suffix = mimePair.getValue();
                    break;
                }
            }
        }
        return suffix;
    }

    public static void setMimeTypeMap(Map<String, String> mimeTypeMap) {
        Base64Util.mimeTypeMap = mimeTypeMap;
    }
}
