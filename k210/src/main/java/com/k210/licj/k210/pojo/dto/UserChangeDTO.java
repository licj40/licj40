package com.k210.licj.k210.pojo.dto;

import lombok.Data;

@Data
public class UserChangeDTO {
    private Long id;
    private String type;
    private int auth;
}
