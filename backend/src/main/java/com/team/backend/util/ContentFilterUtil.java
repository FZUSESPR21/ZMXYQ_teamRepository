package com.team.backend.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huawei.ais.sdk.AisAccess;
import com.huawei.ais.sdk.util.HttpClientUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;

public class ContentFilterUtil {
    private static AisAccess service;
    private static String uri;

    public static int assumeContentStatus(String content){
        if(service==null){
            service = ServiceAccessBuilder.builder()
                .ak("YCAYWOS9LXLWVU7SVIJJ")
                .sk("0znCA3FKJvKwCIK6dz5Uk4F5sYPFIwg4aBaMcqZd")
                .region("cn-east-3")               // 内容审核服务目前支持华北-北京(cn-north-4)、华东上海一(cn-east-3)、亚太-新加坡(ap-southeast-3)以及亚太-香港(ap-southeast-1)
                .connectionTimeout(5000)            // 连接目标url超时限制
                .connectionRequestTimeout(1000)     // 连接池获取可用连接超时限制
                .socketTimeout(20000)               // 获取服务器响应数据超时限制
                .retryTimes(3)                      // 请求异常时的重试次数
                .build();
            uri = "/v1.0/moderation/text";
        }
        int result = 1;
        try {
            JSONObject json = new JSONObject();

            json.put("categories", new String[] {"porn","politics", "ad", "abuse", "contraband", "flood"});

            JSONObject text = new JSONObject();
            text.put("text", content);
            text.put("type", "content");

            JSONArray items = new JSONArray();
            items.add(text);

            json.put("items", items);

            StringEntity stringEntity = new StringEntity(json.toJSONString(), "utf-8");

            HttpResponse response = service.post(uri, stringEntity);

            ResponseProcessUtils.processResponseStatus(response);

            JSONObject jsonObject = JSON.parseObject(HttpClientUtils.convertStreamToString(response.getEntity().getContent()));

            String contentType = (String) jsonObject.getJSONObject("result").get("suggestion");
            if("pass".equals(contentType)) {
                result = 0;
            }else if ("block".equals(contentType)) {
                result = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.close();
        }
        return result;
    }
}
