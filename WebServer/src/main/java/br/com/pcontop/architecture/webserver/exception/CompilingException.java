package br.com.pcontop.architecture.webserver.exception;

/**
 * Created by pcont_000 on 28/01/2016.
 */
public class CompilingException extends IdContainerException {
    private String idFile;
    public CompilingException(String idFile, String message){
        super(message);
        this.idFile = idFile;
    }

    @Override
    public String getIdFile() {
        return idFile;
    }

}
