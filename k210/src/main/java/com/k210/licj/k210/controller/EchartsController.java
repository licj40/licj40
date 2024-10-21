package com.k210.licj.k210.controller;


import com.k210.licj.k210.mapper.EchartsMapper;
import com.k210.licj.k210.response.JsonResult;
import com.k210.licj.k210.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private EchartsMapper mapper;

    @Value("${value}")
    private boolean value;

    @GetMapping("/top")
    public JsonResult getEcharts() {
        String[] typeName = {"open_auth","login_auth"};
        List<Integer> ans = new ArrayList<>();
        List<String> percent = new ArrayList<>();
        int countNum = mapper.selectAll();
        for(int i=0;i<typeName.length;i++) {
            int count = mapper.selectNumber(typeName[i],1);
            ans.add(count);
            percent.add(String.format("%.2f", count * 1.0 / countNum * 100));
            count = mapper.selectNumber(typeName[i],0);
            ans.add(count);
            percent.add(String.format("%.2f", count * 1.0 / countNum * 100));
        }
        return JsonResult.ok(R.asMap("num",ans,"percent",percent));
    }


    @GetMapping("/graph")
    public JsonResult getEchartsGraph() {
        List<String> date = new ArrayList<>();
        List<Integer> shibie = new ArrayList<>();
        List<Integer> admin = new ArrayList<>();
        List<Integer> total = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        if(value){
            cal.set(2024,8,6);
            log.info(cal.getTime().toString());
        }
        for(int i=1;i<=15;i++) {
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            date.add(0,day + "day");
            String datetime = year + "-";
            if(month<10)
                datetime += "0";
            datetime += month+"-";
            if(day<10)
                datetime += "0";
            datetime += day+" %";
            int type1 = mapper.selectType(datetime,1);
            int type2 = mapper.selectType(datetime,2);
            shibie.add(0,type1);
            admin.add(0,type2);
            total.add(0,type1+type2);
            cal.add(Calendar.DAY_OF_MONTH,-1);
        }
        return JsonResult.ok(R.asMap("date",date,"shibie",shibie,"admin",admin,"total",total));
    }

    @GetMapping("/name")
    public JsonResult getEchartsName() {
        List<Map> name = mapper.selectName();
        int total = mapper.selectOpen();
        for(Map m:name) {
            int percent = R.getInt(m,"percent");
            m.put("percent", String.format("%.2f", (percent * 1.0 / total * 100)) + "%");
        }
        return JsonResult.ok(name);
    }

    @GetMapping("/bing")
    public JsonResult getEchartsBing() {
        List<Map> list = mapper.selectGroup();
        return JsonResult.ok(list);
    }

    @GetMapping("/table")
    public JsonResult getEchartsTable() {
        List<Map> list = mapper.selectInfo();
        return JsonResult.ok(list);
    }
}
