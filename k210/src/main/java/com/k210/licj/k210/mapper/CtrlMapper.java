package com.k210.licj.k210.mapper;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CtrlMapper {

    List<Map> selectUser();

    int insert(@Param("mapResult") Map mapResult);

    int insertError(@Param("mapResult") Map mapResult);

    String selectNameById(long id);

    Map selectFirst();
}
