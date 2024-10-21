package com.k210.licj.k210.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TimelineListVO {

    private String updateName1;
    private String updateName2;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private int lastAuth;
    private int nowAuth;
    private String type;

}
