package br.com.pcontop.architecture.compilerServer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pcont_000 on 27/01/2016.
 */
@ResponseStatus(value= HttpStatus.INSUFFICIENT_STORAGE, reason="File is of an unsupported type.")
public class InsufficientStorageException extends RuntimeException {
    public InsufficientStorageException(String message){
        super(message);
    }
}
