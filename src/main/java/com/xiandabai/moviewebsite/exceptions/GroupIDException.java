package com.xiandabai.moviewebsite.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GroupIDException extends RuntimeException{
    public GroupIDException(String message) {
        super(message);
    }
}
