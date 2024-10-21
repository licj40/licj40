package com.k210.licj.k210.mapper;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EchartsMapper {


    int selectNumber(@Param("typeName") String typeName, int value);

    int selectAll();

    int selectType(String datetime, int i);

    List<Map> selectName();

    int selectOpen();

    List<Map> selectGroup();

    List<Map> selectInfo();
}
