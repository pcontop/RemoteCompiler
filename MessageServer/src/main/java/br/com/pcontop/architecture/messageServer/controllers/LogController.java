package br.com.pcontop.architecture.messageServer.controllers;

import br.com.pcontop.architecture.messageBeans.LogMessageDTO;
import br.com.pcontop.architecture.messageServer.dao.LogMessageDAO;
import br.com.pcontop.architecture.messageServer.model.LogMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pcont_000 on 26/01/2016.
 */
@RestController
public class LogController {

    @Autowired
    LogMessageDAO logMessageDAO;

    @Transactional
    @RequestMapping("/logMessageSimpleTest")
    String logMessage(@RequestParam String idFile,
                      @RequestParam String originator,
                      @RequestParam String message) {
        LogMessage logMessage = new LogMessage();
        logMessage.setIdFile(idFile);
        logMessage.setOriginator(originator);
        logMessage.setText(message);

        logMessageDAO.save(logMessage);

        return "Message was sent.";
    }

    @Transactional
    @RequestMapping("/logMessage")
    String logMessage(@RequestBody LogMessageDTO logMessageDTO) {
        LogMessage logMessage = LogMessage.fromDTO(logMessageDTO);

        logMessageDAO.save(logMessage);

        return "Message was sent.";
    }

}
