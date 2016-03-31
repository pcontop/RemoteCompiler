package br.com.pcontop.architecture.compilerServer.services;

import br.com.pcontop.architecture.compilerServer.config.Config;
import br.com.pcontop.architecture.compilerServer.exceptions.InsufficientStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by pcont_000 on 27/01/2016.
 */
@Service
public class FileService {
    @Autowired
    Config config;

    public File buildFile(MultipartFile file){
        File localFile = new File(config.TEMP_DIR + file.getOriginalFilename());
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            throw new InsufficientStorageException("Insufficient Storage on Compiler Server for project.");
        }

        return localFile;

    }
}
