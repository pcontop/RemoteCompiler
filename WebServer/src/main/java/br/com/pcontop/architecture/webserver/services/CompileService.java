package br.com.pcontop.architecture.webserver.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by pcont_000 on 27/01/2016.
 */
public interface CompileService {

    public File compileProject(String idFile, MultipartFile inputFile);
}
