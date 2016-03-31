package br.com.pcontop.architecture.webserver.services;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by pcont_000 on 27/01/2016.
 */
public interface ReturnFileService {
    public void returnFile(HttpServletResponse response, String idFile, File downloadFile);
}
