package br.com.pcontop.architecture.webserver.services;

import br.com.pcontop.architecture.messageBeans.FileContainer;
import br.com.pcontop.architecture.messageService.MessageService;
import br.com.pcontop.architecture.webserver.exception.CompilingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by pcont_000 on 26/01/2016.
 */
@Service
public class CompileServiceImpl implements CompileService{

    @Autowired
    MessageService messageService;

    private static String COMPILE_PROJECT ="/compileProject";

    @Value("${server.name}")
    private String ORIGINATOR;


    @Value("${compileserver.url}")
    private String compileServerUrl;

    @Value("${server.tempdir}")
    private String TEMP_DIR;

    public File compileProject(String idFile, MultipartFile inputFile){

        String projectDescription=inputFile.getOriginalFilename() + "[" + idFile + "]";

        messageService.sendMessage(idFile, "Starting to send project: " + projectDescription);
        String uri = compileServerUrl + COMPILE_PROJECT;

        String fileName = inputFile.getOriginalFilename();

        File outputFile = new File(TEMP_DIR + fileName);

        try {
            inputFile.transferTo(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("file", new FileSystemResource(outputFile));
        parts.add("idFile", idFile);

        RestTemplate restTemplate = new RestTemplate();

        FileContainer result = restTemplate.postForObject(uri, parts, FileContainer.class);

        if (result.getErrorMessage()!=null){
            throw new CompilingException(idFile, result.getErrorMessage());
        }

        messageService.sendMessage(idFile, "Project sent successfully: " + projectDescription);
        return result.getFile();
    }

}
