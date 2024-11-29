package com.spacecodee.springbootsecurityopentemplate.data.common.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ApiResponseDataPojo<E> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private transient E data;
    @Setter(AccessLevel.PRIVATE)
    private String status;
    @Setter(AccessLevel.PRIVATE)
    private int statusCode;
    @Setter(AccessLevel.PRIVATE)
    private LocalDate localDate = LocalDate.now();

    public void setHttpStatus(@NotNull HttpStatus status) {
        this.statusCode = status.value();
        this.status = status.name();
    }
}
