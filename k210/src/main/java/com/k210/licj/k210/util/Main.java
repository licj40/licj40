package com.k210.licj.k210.util;

import org.python.util.PythonInterpreter;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        PythonInterpreter interpreter = new PythonInterpreter();
        PrintStream oldPrintStream = System.out; //将原来的System.out交给printStream 对象保存
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos)); //设置新的out
        System.out.println("this is the text to output"); //此行用于测试,这一行的输出被新的out截获并保存在bos中(执行这一行时,控制台没有输出内容)

        String python = "for i in range(10): print(i)";
        StringBuilder result = new StringBuilder();
//        System.out.println(python);
        interpreter.exec(python);

        System.setOut(oldPrintStream); //恢复原来的System.out
        System.out.println("**********");
        System.out.println("result" + bos.toString()); //将bos中保存的信息输出,这就是我们上面准备要输出的内容

        Process pro = Runtime.getRuntime().exec("./test.exe");

        BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));

//
//        String str = in.readLine();
//        while(str != null){
//            result.append(str).append("\n");
//            str = in.readLine();
//        }
//        System.out.println("str = " + result.toString());
    }
}

