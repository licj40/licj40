package com.k210.licj.k210.pojo.dto;

import lombok.Data;


@Data
public class TimelineSelectDTO {
    private String updateName1Id;
    private String updateName2Id;
    private String updateTime;

    private Integer currentPage;
    private Integer pageSize;
}
