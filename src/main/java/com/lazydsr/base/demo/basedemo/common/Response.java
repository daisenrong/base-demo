package com.lazydsr.base.demo.basedemo.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Response<T> {
    private Integer code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PageInfo pageInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class PageInfo {
        private Integer pageSize;
        private Integer pageNum;
        private Integer total;
    }
}
