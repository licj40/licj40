package com.k210.licj.k210.face;

import cn.hutool.core.util.IdUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.Loader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

@Component
@Slf4j
public class ImageWriter {


    public static void get(String filename) throws Exception {
        URL url = new URL("http://" + "192.168.0.150" + ":8080");
        log.info("url = {}", url);
        // 打开连接
        URLConnection con = url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        File file = new File(filename);
        FileOutputStream os = new FileOutputStream(file, true);
        // 开始读取
        len = is.read(bs);
        while(len != -1) {
            os.write(bs, 0, len);
            len = is.read(bs);
            log.info("len={}",len);
        }
        log.info("finish");
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }


    public static String modifyResolution(
            String imagePath, String outputDir, Integer width, Integer height) throws Exception {
        List<String> paths = Splitter.on(".").splitToList(imagePath);
        String ext = paths.get(paths.size() - 1);
        if (!Arrays.asList("jpg", "png").contains(ext)) {
            throw new Exception("format error");
        }
        String resultPath =
                Joiner.on(File.separator).join(Arrays.asList(outputDir, IdUtil.simpleUUID() + "." + ext));
        String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
        ProcessBuilder builder =
                new ProcessBuilder(
                        ffmpeg,
                        "-i",
                        imagePath,
                        "-vf",
                        MessageFormat.format("scale={0}:{1}", String.valueOf(width), String.valueOf(height)),
                        resultPath);
        builder.inheritIO().start().waitFor();
        return resultPath;
    }
}