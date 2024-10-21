package com.k210.licj.k210.controller;

import com.k210.licj.k210.pojo.dto.HistorySelectDTO;
import com.k210.licj.k210.response.Rsp;
import com.k210.licj.k210.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService service;


    @PostMapping("/list")
    public Rsp userList(@RequestBody HistorySelectDTO historySelectDTO, HttpServletRequest request){
        return Rsp.success(service.select(historySelectDTO,request));
    }
}
