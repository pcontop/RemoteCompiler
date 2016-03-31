package br.com.pcontop.architecture.webserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pcont_000 on 27/01/2016.
 */
@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Could not read file returned from server.")
public class ReturnFileException extends IdContainerException {
    private String idFile;
    public ReturnFileException(String idFile, String message){
        super(message);
        this.idFile = idFile;
    }

    @Override
    public String getIdFile() {
        return idFile;
    }
}
