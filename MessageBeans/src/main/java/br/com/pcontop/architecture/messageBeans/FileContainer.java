package br.com.pcontop.architecture.messageBeans;

import java.io.File;

/**
 * Created by pcont_000 on 28/01/2016.
 */
public class FileContainer {
    File file;
    String errorMessage;
    String idFile;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getIdFile() {
        return idFile;
    }

    public void setIdFile(String idFile) {
        this.idFile = idFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileContainer that = (FileContainer) o;

        return !(idFile != null ? !idFile.equals(that.idFile) : that.idFile != null);

    }

    @Override
    public int hashCode() {
        return idFile != null ? idFile.hashCode() : 0;
    }
}
