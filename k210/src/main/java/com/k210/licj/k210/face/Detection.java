package com.k210.licj.k210.face;

import com.alibaba.fastjson.JSON;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.k210.licj.k210.face.Comparison.HTTP_CLIENT;

public class Detection {

    public static final String API_KEY = "r4ba7QTyKiSRdTq5Vo7MGOkd";
    public static final String SECRET_KEY = "yBW8Rckyw85puFbWlxy85nnxRORKaSWB";

    private static final String path = "E:/stm32 project/k210/k210-web/public";

    private static final String face1 = "/2024/02/17/a5a35922-be7c-474d-a9bf-6a7ae6402be3.jpg";

    public static Map detection(String facePath) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Map<String,String> map = new HashMap<>();
        map.put("image",ImageUtil.convertImageToBase64Str(facePath));
        map.put("image_type","BASE64");
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(map));
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/detect?access_token=" + getAccessToken())
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

    public static void main(String[] args) throws IOException {
        System.out.println(detection(path+face1));
    }
}
