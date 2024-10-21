package com.k210.licj.k210.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.k210.licj.k210.exception.ServiceException;
import com.k210.licj.k210.mapper.HistoryMapper;
import com.k210.licj.k210.pojo.dto.HistorySelectDTO;
import com.k210.licj.k210.pojo.entity.UserEntity;
import com.k210.licj.k210.pojo.vo.HistoryListVO;
import com.k210.licj.k210.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Service
@Slf4j
public class HistoryService {

    @Autowired
    private HistoryMapper mapper;

    public PageInfo<HistoryListVO> select(HistorySelectDTO historySelectDTO, HttpServletRequest request) {
        if(!checkLogin(request)){
            throw new ServiceException(StatusCode.NOT_LOGIN);
        }
        log.info("查询条件={}", JSONObject.toJSONString(historySelectDTO));
        PageHelper.startPage(historySelectDTO.getCurrentPage(), historySelectDTO.getPageSize());
        if(historySelectDTO.getOpenTime()!=null && !historySelectDTO.getOpenTime().equals("")){
            historySelectDTO.setOpenTime(historySelectDTO.getOpenTime() + "%");
        }
        List<HistoryListVO> list =  mapper.select(historySelectDTO);
        PageInfo<HistoryListVO> page = new PageInfo<>(list);
        return page;
    }

    public UserEntity getUser(HttpServletRequest request){
        String userJson = request.getHeader("token");
        log.info("userJson={}", userJson);
        UserEntity user = JSONObject.parseObject(userJson, UserEntity.class);
        return user;
    }

    public boolean checkLogin(HttpServletRequest request){
        String userJson = request.getHeader("token");
        log.info("userJson={}", userJson);
        UserEntity user = JSONObject.parseObject(userJson, UserEntity.class);
        if(user==null){
            return false;
        }
        log.info("user = {}", user);
        return true;
    }
}
