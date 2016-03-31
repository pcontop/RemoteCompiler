package br.com.pcontop.architecture.messageService;

import br.com.pcontop.architecture.messageBeans.LogMessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by pcont_000 on 26/01/2016.
 */
@Service
public class MessageService {

    private static String LOG_MESSAGE="/logMessage";

    @Value("${server.name}")
    private String ORIGINATOR;


    @Value("${logserver.url}")
    private String logServerUrl;

    public void sendMessage(String idFile, String message){

        System.out.println(idFile + ": " + message);

        LogMessageDTO logMessageDTO = new LogMessageDTO();
        logMessageDTO.setIdFile(idFile);
        logMessageDTO.setOriginator(ORIGINATOR);
        logMessageDTO.setText(message);

        String uri = logServerUrl + LOG_MESSAGE;

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.postForObject(uri, logMessageDTO, String.class);
    }

}
