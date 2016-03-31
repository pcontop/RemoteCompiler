package br.com.pcontop.architecture.webserver.services;

import br.com.pcontop.architecture.messageService.MessageService;
import br.com.pcontop.architecture.webserver.exception.ReturnFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by pcont_000 on 27/01/2016.
 */
@Service
public class ReturnFileServiceImpl implements ReturnFileService {

    private static final int BUFFER_SIZE = 4096;

    @Autowired private MessageService messageService;

    @Override
    public void returnFile(HttpServletResponse response, String idFile, File downloadFile){

        messageService.sendMessage(idFile, "Beginning to send the compiled project file back to the user.");

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(downloadFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String mimeType = "application/octet-stream";

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",  downloadFile.getName());
        response.setHeader(headerKey, headerValue);


        // get output stream of the response
        OutputStream outStream = null;
        try {
            outStream = response.getOutputStream();
            FileCopyUtils.copy(inputStream, outStream);
            outStream.close();

        } catch (IOException e) {
            messageService.sendMessage(idFile, "Error in returning the file: " + e.getMessage());
            throw new ReturnFileException(idFile, "Error in returning the file: " + e.getMessage());
        } finally {
            if (outStream!=null) {
                try {
                    outStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        messageService.sendMessage(idFile, "File returned successfully.");

    }

}
