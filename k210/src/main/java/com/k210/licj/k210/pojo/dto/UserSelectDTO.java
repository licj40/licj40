package com.k210.licj.k210.pojo.dto;

import lombok.Data;

@Data
public class UserSelectDTO {
    private String username;
    private String name;
    private Integer openAuth;
    private Integer loginAuth;
    private String createTime;
    private String updateTime;

    private Integer currentPage;
    private Integer pageSize;
}
