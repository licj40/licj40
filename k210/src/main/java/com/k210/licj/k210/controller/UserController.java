package com.k210.licj.k210.controller;

import com.alibaba.fastjson.JSONObject;
import com.k210.licj.k210.pojo.dto.*;
import com.k210.licj.k210.response.JsonResult;
import com.k210.licj.k210.response.Rsp;
import com.k210.licj.k210.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/login")
    public JsonResult login(@RequestBody @Validated UserLoginDTO userLoginDTO,HttpServletRequest request){
        return JsonResult.ok(service.login(userLoginDTO,request));
    }

    @PostMapping("/reg")
    public JsonResult reg(@RequestBody @Validated UserRegDTO userRegDTO){
        log.info("长度 ={}",userRegDTO.getName().length());
        service.reg(userRegDTO);
        return JsonResult.ok();
    }

    @PostMapping("/update")
    public JsonResult update(@RequestBody UserUpdateDTO userUpdateDTO,HttpServletRequest request){
        log.info("修改信息为={}", JSONObject.toJSONString(userUpdateDTO));
        service.update(userUpdateDTO,request);
        return JsonResult.ok();
    }

    @PostMapping("/change")
    public JsonResult change(@RequestBody UserChangeDTO userChangeDTO,HttpServletRequest request){
        log.info("修改权限信息={}",userChangeDTO);
        service.change(userChangeDTO,request);
        return JsonResult.ok();
    }

    @PostMapping("/list")
    public Rsp userList(@RequestBody UserSelectDTO userSelectDTO,HttpServletRequest request){
        return Rsp.success(service.select(userSelectDTO,request));
    }

    @GetMapping("/arr")
    public Rsp userArr(){
        return Rsp.success(service.selectUserName());
    }

    @PostMapping("/timeline/list")
    public Rsp timelineList(@RequestBody TimelineSelectDTO timelineSelectDTO,HttpServletRequest request){
        return Rsp.success(service.selectTimeline(timelineSelectDTO,request));
    }
}


