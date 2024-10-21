package com.k210.licj.k210.controller;

import com.k210.licj.k210.response.JsonResult;
import com.k210.licj.k210.service.CtrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

@RestController
@Slf4j
public class CtrlController {

    @Autowired
    private CtrlService ctrlService;

    @GetMapping("/test")
    public String test() throws ParseException {
        log.info("已调用");
        int ans = ctrlService.check();
        String result = ans==1?"SUCCESS":"ERROR";
//        int n = (int) (Math.random() * 2);
//        String result = n==1?"SUCCESS":"ERROR";
        log.info("检测结果为 = {}", result);
        return result;
    }

    @PostMapping("/open")
    public JsonResult open(HttpServletRequest request) throws Exception {
        log.info("open");
        ctrlService.open(request);
        return JsonResult.ok("已开启");
    }
}
