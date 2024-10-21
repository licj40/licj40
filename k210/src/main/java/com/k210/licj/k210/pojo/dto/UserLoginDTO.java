package com.k210.licj.k210.pojo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginDTO {
    @NotBlank(message = "用户名不能为空白|空字符串|null")
    @Length(min =1, max = 20, message = "用户名必须在1-10位之间")
    private String username;
    @NotBlank(message = "密码名不能为空白|空字符串|null")
    @Length(min = 6, max = 20, message = "密码必须在6-10位之间")
    private String password;
}
