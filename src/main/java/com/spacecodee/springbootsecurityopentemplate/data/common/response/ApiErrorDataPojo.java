package com.spacecodee.springbootsecurityopentemplate.data.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ApiErrorDataPojo<E> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String backendMessage;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String path;
    private String method;
    private transient E data;
}