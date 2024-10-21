package com.k210.licj.k210.mapper;

import com.k210.licj.k210.pojo.dto.TimelineSelectDTO;
import com.k210.licj.k210.pojo.dto.UserSelectDTO;
import com.k210.licj.k210.pojo.dto.UserUpdateDTO;
import com.k210.licj.k210.pojo.entity.UserEntity;
import com.k210.licj.k210.pojo.vo.HistoryListVO;
import com.k210.licj.k210.pojo.vo.TimelineListVO;
import com.k210.licj.k210.pojo.vo.UserArrVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    
    UserEntity selectUser(String username);

    int insertUser(UserEntity userEntity);

    int updateTime(Long id);

    UserEntity selectUpdateName(Long id);

    int update(UserUpdateDTO userUpdateDTO);

    List<UserEntity> select(UserSelectDTO userSelectDTO);

    int change(@Param("map") Map map);

    int insertHistory(@Param("map") Map map);

    List<UserArrVO> selectUserName();

    List<TimelineListVO> selecttimeLine(TimelineSelectDTO timelineSelectDTO);
}
