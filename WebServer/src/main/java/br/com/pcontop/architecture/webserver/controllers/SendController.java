package br.com.pcontop.architecture.webserver.controllers;

import br.com.pcontop.architecture.messageService.MessageService;
import br.com.pcontop.architecture.webserver.exception.ReturnFileException;
import br.com.pcontop.architecture.webserver.services.CompileService;
import br.com.pcontop.architecture.webserver.services.ReturnFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * Created by pcont_000 on 26/01/2016.
 */
@Controller
public class SendController {
    @Autowired private MessageService messageService;

    @Autowired private CompileService compileService;

    @Autowired private ReturnFileService returnFileService;

    @RequestMapping("/")
    String home() {
        return "sendPageNice";
    }

    @RequestMapping("/login")
    String login() {
        return "login";
    }

    @RequestMapping(value="/send", method= RequestMethod.POST)
    public void handleFileUpload(@RequestParam("projectFile") MultipartFile file, HttpServletRequest request, HttpServletResponse response){

        final String idFile = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();

        messageService.sendMessage(idFile,"Received File " + fileName +  " - Starting process...");

        File returnFile = compileService.compileProject(idFile, file);

        messageService.sendMessage(idFile, "Finished processing file " + fileName +  " without error.");

        returnFileService.returnFile(response, idFile, returnFile);

    }


}
