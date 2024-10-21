package com.k210.licj.k210.pojo.entity;

import lombok.Data;

@Data
public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private String name;
    private int openAuth;
    private int loginAuth;
    private String createTime;
    private String updateTime;
    private String picture;

}
