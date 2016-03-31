package br.com.pcontop.architecture.compilerServer.controllers;

import br.com.pcontop.architecture.compilerServer.config.Config;
import br.com.pcontop.architecture.compilerServer.services.*;
import br.com.pcontop.architecture.messageBeans.FileContainer;
import br.com.pcontop.architecture.messageService.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by pcont_000 on 26/01/2016.
 */
@RestController
public class CompilerController {

    @Autowired
    Config config;

    @Autowired private MessageService messageService;
    @Autowired private ZipService zipService;
    @Autowired private FileService fileService;
    @Autowired private MavenService mavenService;

    //@RequestMapping(value = "/compileProject", method = RequestMethod.POST)
    File compileProject(@RequestParam(value="file") MultipartFile file , @RequestParam String idFile) {
        String fileName = file.getOriginalFilename() + "[" + idFile + "]";
        messageService.sendMessage(idFile, "Starting processing on file " + fileName + "...");

        File receivedFile = fileService.buildFile(file);

        String pomPath = zipService.unzipFile(idFile, receivedFile);

        mavenService.compile(idFile, pomPath);

        File compiledProjectZip = zipService.zipPath(idFile);

        //TODO - Cleaner service.
        //cleanerService.clear(pomPath, file);

        messageService.sendMessage(idFile, "Finished successfully processing on file " + fileName + "...");

        return compiledProjectZip;
    }


    @RequestMapping(value = "/compileProject", method = RequestMethod.POST)
    FileContainer compileProjectContainer(@RequestParam(value="file") MultipartFile file , @RequestParam String idFile) {
        FileContainer fileContainer = new FileContainer();
        fileContainer.setIdFile(idFile);
        String fileName = file.getOriginalFilename() + "[" + idFile + "]";
        try {
            messageService.sendMessage(idFile, "Starting processing on file " + fileName + "...");

            File receivedFile = fileService.buildFile(file);

            String pomPath = zipService.unzipFile(idFile, receivedFile);

            mavenService.compile(idFile, pomPath);

            File compiledProjectZip = zipService.zipPath(idFile);

            //TODO - Cleaner service.
            //cleanerService.clear(pomPath, file);

            messageService.sendMessage(idFile, "Finished successfully processing on file " + fileName + "...");

            fileContainer.setFile(compiledProjectZip);
        } catch (RuntimeException ex){
            String message = ex.getMessage();
            fileContainer.setErrorMessage(message);
        }

        return fileContainer;

    }

}
