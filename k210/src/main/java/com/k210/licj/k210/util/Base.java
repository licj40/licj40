package com.k210.licj.k210.util;


import java.util.Base64;

/**
 * Created by Liuxd on 2018-11-02.
 */
public class Base {


    public static void main(String[] args) throws Exception {
        String str = "青锋剑，偃月刀";
        System.out.println(encode(str));
        System.out.println(decode((encode(str))));

    }
    /**
     * Base64
     *
     */
    public static String encode(String str) {
        byte[] bytes = str.getBytes();
        //Base64 加密
        String encoded = Base64.getEncoder().encodeToString(bytes);
        return encoded;
    }


    public static String decode(String str) {
        //Base64 解密
        byte[] decoded = Base64.getDecoder().decode(str);
        String decodeStr = new String(decoded);
        return decodeStr;
    }
}