package br.com.pcontop.architecture.compilerServer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pcont_000 on 27/01/2016.
 */
@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE, reason="The file does not contain a maven project.")
public class NotAMavenProjectException extends RuntimeException {
    public NotAMavenProjectException(String message){
        super(message);
    }

}
