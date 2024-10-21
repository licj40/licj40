package com.k210.licj.k210.pojo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class HistorySelectDTO {
//    private String openName;
    private Long openNameId;
    private Integer type;
    private String openTime;

    private Integer currentPage;
    private Integer pageSize;
}
