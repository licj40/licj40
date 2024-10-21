package com.k210.licj.k210.service;

import com.alibaba.fastjson.JSON;
import com.k210.licj.k210.face.Comparison;
import com.k210.licj.k210.face.Detection;
import com.k210.licj.k210.face.ImageWriter;
import com.k210.licj.k210.mapper.CtrlMapper;
import com.k210.licj.k210.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class CtrlService {

    @Value("${filePath}")
    private String dirPath;

    @Autowired
    private CtrlMapper mapper;

    public void ctrl() throws Exception {
        String fileName = UUID.randomUUID() + ".jpg";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("/yyyy/MM/dd/");
        String datePath = simpleDateFormat.format(new Date());
        File dirFile = new File(dirPath+datePath);
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }
        log.info("文件存储地址={}",datePath+fileName);
        ImageWriter.get(dirPath+datePath+fileName);
        Map detection = Detection.detection(dirPath+datePath+fileName);
        log.info("人脸检测结果={}",JSON.toJSONString(detection));
        if(R.getInt(detection,"error_code") != 0){
            log.info("不存在人脸信息");
            return;
        }
        Map result1 = (Map) detection.get("result");
        if(R.getStr(result1,"face_num").compareTo("0") <= 0){
            new File(dirPath+datePath+fileName).delete();
            return;
        }
        List<Map> userList = mapper.selectUser();
        log.info("用户信息={}", JSON.toJSONString(userList));
        Map mapResult = new HashMap();
        mapResult.put("picture",datePath+fileName);
        mapResult.put("openTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        for(int i=0;i<userList.size();i++){
            log.info("路径1={}",dirPath+ R.getStr(userList.get(i), "picture"));
            log.info("路径2={}",dirPath+datePath+fileName);
            Map map = Comparison.faceComparison(dirPath+R.getStr(userList.get(i), "picture"), dirPath+datePath+fileName);
            log.info("比对结果={}", JSON.toJSONString(map));
            if(R.getInt(map,"error_code")!=0){
                log.info("人脸比对服务异常");
                return;
            }
            Map result = (Map) map.get("result");
            int score = R.getInt(result,"score");
            log.info("当前用户比对结果为={}，用户={}",JSON.toJSONString(score),JSON.toJSON(userList.get(i)));
            if(score >= 60){
                if(R.getInt(userList.get(i),"open_auth")==1){
                    mapResult.put("openName",userList.get(i).get("name"));
                    mapResult.put("openNameId",userList.get(i).get("id"));
                    mapResult.put("type",1);
                }
                int rows = mapper.insert(mapResult);
                log.info("rows={}",rows);
                log.info("授权开门");
                return;
            }
        }
        mapResult.put("type",3);
        int rows = mapper.insertError(mapResult);
        log.info("rows={}",rows);
    }

    public void open(HttpServletRequest request) throws Exception {
        log.info("header={}",request.getHeader("Authorization"));
        long id = Long.parseLong(request.getHeader("Authorization"));
        String name = mapper.selectNameById(id);
        String fileName = UUID.randomUUID() + ".jpg";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("/yyyy/MM/dd/");
        String datePath = simpleDateFormat.format(new Date());
        File dirFile = new File(dirPath+datePath);
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }
        log.info("文件存储地址={}",datePath+fileName);
        ImageWriter.get(dirPath+datePath+fileName);
//        String path = datePath+fileName;
        Map mapResult = new HashMap();
        mapResult.put("openNameId",id);
        mapResult.put("openName",name);
        mapResult.put("type",2);
        mapResult.put("picture",datePath+fileName);
        mapResult.put("openTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        int rows = mapper.insert(mapResult);
        log.info("rows={}",rows);
    }

    public int check() throws ParseException {
        Map result = mapper.selectFirst();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = date.parse(R.getStr(result,"open_time"));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,-10);
        Date now = calendar.getTime();
        if(time.after(now)){
            return 1;
        }
        return 0;
    }
}
