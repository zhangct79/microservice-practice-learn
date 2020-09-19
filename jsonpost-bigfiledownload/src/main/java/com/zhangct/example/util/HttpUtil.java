package com.zhangct.example.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpUtil {

    public static String doPost(String uri, String param) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost(uri);
        post.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(param, StandardCharsets.UTF_8);
        post.setEntity(entity);

        HttpResponse response = httpClient.execute(post);

//        int statusCode = response.getStatusLine().getStatusCode();
//        if (statusCode != 200) {
//            throw new RuntimeException("http请求异常" + statusCode);
//        }

        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }
}