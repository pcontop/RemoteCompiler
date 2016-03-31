package br.com.pcontop.architecture.webserver.exception;

/**
 * Created by pcont_000 on 28/01/2016.
 */
public abstract class IdContainerException extends RuntimeException {
    public abstract String getIdFile();

    public IdContainerException(String message){
        super(message);
    }
}
