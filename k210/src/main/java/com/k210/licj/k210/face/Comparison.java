package com.k210.licj.k210.face;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comparison {

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static final String API_KEY = "r4ba7QTyKiSRdTq5Vo7MGOkd";
    public static final String SECRET_KEY = "yBW8Rckyw85puFbWlxy85nnxRORKaSWB";

    private static final String path = "E:/stm32 project/k210/k210-web/public";

    private static final String face1 = "/2024/02/17/a5a35922-be7c-474d-a9bf-6a7ae6402be3.jpg";
    private static final String face2 = "/2024/02/20/de933c6d-ad5c-4528-8b8a-ef1684aac1bc.jpg";

    public static void main(String[] args) throws IOException {

//        System.out.println(getAccessToken());
        System.out.println(faceComparison(path+face1,path+face2));
    }

    public static Map faceComparison(String path1,String path2) throws IOException{
        MediaType mediaType = MediaType.parse("application/json");
        String base1 = ImageUtil.convertImageToBase64Str(path1);
        String base2 = ImageUtil.convertImageToBase64Str(path2);
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map1 = new HashMap<>();
        map1.put("image",base1);
        map1.put("image_type","BASE64");
        list.add(map1);
        Map<String,String> map2 = new HashMap<>();
        map2.put("image",base2);
        map2.put("image_type","BASE64");
        list.add(map2);
        String content = JSON.toJSONString(list);
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/match?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return JSON.parseObject(response.body().string(),Map.class);
    }

    public static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
}

