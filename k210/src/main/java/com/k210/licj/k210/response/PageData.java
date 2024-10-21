package com.k210.licj.k210.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
public class PageData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer total;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String value1;

}
