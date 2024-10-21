package com.k210.licj.k210.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Python {

    public static void main(String[] args) throws IOException, InterruptedException {
        // python代码存储路径【绝对路径】
        String pythonCodePath = "E:\\python project\\pythonProtect\\爬虫\\xmw\\test.py";
        // 前端传来的python代码
        String python = "<pre><code >for i in range(10):\\n    print(i)\\n    for j in range(5):\\n        print(j)</code></pre><p><br></p >";
        // python编译器位置【绝对路径】
        String exe = "E:\\Python313\\python.exe";
        Map map = run(exe, pythonCodePath, python);
        for (Object key : map.keySet()) {
            List<String> list = (List) map.get(key);
            for(String o : list) {
                System.out.println(o);
            }
        }
    }

    public static Map run(String exe, String pythonCodePath, String python) throws IOException, InterruptedException {
        Map map = new HashMap<String, String>();
        Document document = Jsoup.parse(python);
        Elements element = document.getElementsByTag("code");
        String pythonCode = element.text();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(pythonCodePath)));
        String[] codes = pythonCode.split("\\\\n");
        for(String code : codes) {
            out.println(code);
        }
        out.flush();
        String py = pythonCodePath;
//            String pram = "单个传递参数，若参数为基本类型，转化为String；若为数组等类型，也是将其转换为String型";
        String [] args = new String[] {exe, py};
        ProcessBuilder builder = new ProcessBuilder(args);
        Process process = builder.start();
        BufferedReader success = new BufferedReader(new InputStreamReader(process.getInputStream(), "GB2312"));//获取字符输入流对象
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream(), "GB2312"));//获取错误信息的字符输入流对象
        String line = null;
        List<String> success_result = new ArrayList<>();
        List<String> error_result = new ArrayList<>();
        //记录输出结果
        while ((line = success.readLine()) != null) {
            success_result.add(line);
        }
        //记录错误信息
        while ((line = error.readLine()) != null) {
            error_result.add(line);
        }
        success.close();
        error.close();
        process.waitFor();
        map.put("reslut", success_result);
        map.put("error", error_result);
        return map;
    }
}
