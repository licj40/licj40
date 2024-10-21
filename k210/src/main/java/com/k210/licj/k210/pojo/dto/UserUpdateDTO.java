package com.k210.licj.k210.pojo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDTO {
    private Long id;
    @NotBlank(message = "姓名不能为空白|空字符串|null")
    @Length(min = 1, max = 20, message = "姓名必须在1-20位之间")
    private String name;
    private String picture;
    @NotBlank(message = "密码名不能为空白|空字符串|null")
    @Length(min = 6, max = 20, message = "密码必须在6-10位之间")
    private String password;
}
