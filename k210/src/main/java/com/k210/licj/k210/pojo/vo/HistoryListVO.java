package com.k210.licj.k210.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class HistoryListVO {
    private Long id;
    private String openName;
    private Long openNameId;
    private Integer type;
    private String picture;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date openTime;
}
