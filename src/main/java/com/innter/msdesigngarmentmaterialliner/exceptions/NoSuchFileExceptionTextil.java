package com.innter.msdesigngarmentmaterialliner.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NoSuchFileExceptionTextil extends NotFoundTextil{
    private String code;
    private HttpStatus status;

    public NoSuchFileExceptionTextil(String code, HttpStatus status, String message) {

        super(code, status, message);
        this.code = code;
        this.status = status;
    }
}
