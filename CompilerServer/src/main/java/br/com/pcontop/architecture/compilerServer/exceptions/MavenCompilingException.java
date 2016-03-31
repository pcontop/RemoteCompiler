package br.com.pcontop.architecture.compilerServer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pcont_000 on 27/01/2016.
 */
@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE, reason="Error in compiling the Maven Project.")
public class MavenCompilingException extends RuntimeException {
    public MavenCompilingException(String message){
        super(message);
    }

}
