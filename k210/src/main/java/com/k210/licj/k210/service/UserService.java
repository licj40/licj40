package com.k210.licj.k210.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.k210.licj.k210.exception.ServiceException;
import com.k210.licj.k210.mapper.UserMapper;
import com.k210.licj.k210.pojo.dto.*;
import com.k210.licj.k210.pojo.entity.UserEntity;
import com.k210.licj.k210.pojo.vo.TimelineListVO;
import com.k210.licj.k210.pojo.vo.UserArrVO;
import com.k210.licj.k210.response.StatusCode;
import com.k210.licj.k210.util.Base;
import com.k210.licj.k210.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper mapper;

    @Value("${filePath}")
    private String dirPath;

    public void reg(UserRegDTO userRegDTO) {
        UserEntity user = mapper.selectUser(userRegDTO.getUsername());
        if(user!=null)
            throw new ServiceException(StatusCode.USERNAME_ALREADY_EXISTS);
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userRegDTO,userEntity);
        userEntity.setPassword(Base.encode(userEntity.getPassword()));
        int rows = mapper.insertUser(userEntity);
    }

    public UserEntity login(UserLoginDTO userLoginDTO,HttpServletRequest request) {
        UserEntity user = mapper.selectUser(userLoginDTO.getUsername());
        if(user==null)
            throw new ServiceException(StatusCode.USERNAME_ERROR);
        if(!Base.decode(user.getPassword()).equals(userLoginDTO.getPassword()))
            throw new ServiceException(StatusCode.PASSWORD_ERROR);
        if(user.getLoginAuth()==0)
            throw new ServiceException(StatusCode.NO_LOGIN_AUTH);
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        return user;
    }

    public PageInfo<UserEntity> select(UserSelectDTO userSelectDTO, HttpServletRequest request) {
        if(!checkLogin(request)){
            throw new ServiceException(StatusCode.NOT_LOGIN);
        }
        log.info("查询条件={}", JSONObject.toJSONString(userSelectDTO));
        if(userSelectDTO.getCreateTime()!=null && !userSelectDTO.getCreateTime().equals("")){
            userSelectDTO.setCreateTime(userSelectDTO.getCreateTime() + "%");
        }
        if(userSelectDTO.getUpdateTime()!=null && !userSelectDTO.getUpdateTime().equals("")){
            userSelectDTO.setUpdateTime(userSelectDTO.getUpdateTime() + "%");
        }
        PageHelper.startPage(userSelectDTO.getCurrentPage(), userSelectDTO.getPageSize());
        List<UserEntity> list =  mapper.select(userSelectDTO);
        PageInfo<UserEntity> page = new PageInfo<>(list);
        return page;
    }

    public void update(UserUpdateDTO userUpdateDTO, HttpServletRequest request) {
        if(!checkLogin(request)){
            throw new ServiceException(StatusCode.NOT_LOGIN);
        }
        int rows = mapper.update(userUpdateDTO);
        updateTime(userUpdateDTO.getId());
    }

    public void change(UserChangeDTO userChangeDTO, HttpServletRequest request) {
        if(!checkLogin(request)){
            throw new ServiceException(StatusCode.NOT_LOGIN);
        }
        UserEntity user1 = getUser(request);
        UserEntity user2 = selectUpdateName(userChangeDTO.getId());
        log.info("状态更改参数={}",JSONObject.toJSONString(userChangeDTO));
        Map map = R.asMap("updateName1",user1.getName(),"updateName1Id",user1.getId(),
                "updateName2",user2.getName(),"updateName2Id",user2.getId(),"type",userChangeDTO.getType(), "auth",userChangeDTO.getAuth());
        if(userChangeDTO.getAuth()==1){
            map.put("lastAuth",0);
            map.put("nowAuth",1);
        }else{
            map.put("lastAuth",1);
            map.put("nowAuth",0);
        }
        int row = mapper.change(map);
        int rows = mapper.insertHistory(map);
        log.info("eow = {}, rows = {}",row, rows);
        updateTime(userChangeDTO.getId());
    }

    public List<UserArrVO> selectUserName() {
        return mapper.selectUserName();
    }



    public PageInfo<TimelineListVO> selectTimeline(TimelineSelectDTO timelineSelectDTO, HttpServletRequest request) {
        if(!checkLogin(request)){
            throw new ServiceException(StatusCode.NOT_LOGIN);
        }
        log.info("查询条件={}", JSONObject.toJSONString(timelineSelectDTO));
        if(timelineSelectDTO.getUpdateTime()!=null && !timelineSelectDTO.getUpdateTime().equals("")){
            timelineSelectDTO.setUpdateTime(timelineSelectDTO.getUpdateTime() + "%");
        }
        PageHelper.startPage(timelineSelectDTO.getCurrentPage(), timelineSelectDTO.getPageSize());
        List<TimelineListVO> list =  mapper.selecttimeLine(timelineSelectDTO);
        PageInfo<TimelineListVO> page = new PageInfo<>(list);
        return page;
    }

    public void updateTime(Long id){
        int rows = mapper.updateTime(id);
    }

    public UserEntity selectUpdateName(Long id){
        UserEntity user = mapper.selectUpdateName(id);
        return user;
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


