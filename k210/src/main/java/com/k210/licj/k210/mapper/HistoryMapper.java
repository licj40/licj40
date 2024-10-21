package com.k210.licj.k210.mapper;

import com.k210.licj.k210.pojo.dto.HistorySelectDTO;
import com.k210.licj.k210.pojo.vo.HistoryListVO;

import java.util.List;

public interface HistoryMapper {

    List<HistoryListVO> select(HistorySelectDTO historySelectDTO);

}
