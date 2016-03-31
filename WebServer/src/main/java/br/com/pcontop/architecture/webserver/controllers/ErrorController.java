package br.com.pcontop.architecture.webserver.controllers;

import br.com.pcontop.architecture.messageService.MessageService;
import br.com.pcontop.architecture.webserver.exception.CompilingException;
import br.com.pcontop.architecture.webserver.exception.IdContainerException;
import br.com.pcontop.architecture.webserver.exception.ReturnFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pcont_000 on 27/01/2016.
 */
@ControllerAdvice
public class ErrorController {

    @Autowired private MessageService messageService;

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAnyException(HttpServletRequest request, Exception ex){
        String idFile = "";
        if (ex instanceof IdContainerException){
            IdContainerException idContainerException = (IdContainerException) ex;
            idFile = idContainerException.getIdFile();
        }
        messageService.sendMessage(idFile, "Requested URL="+request.getRequestURL());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());

        modelAndView.setViewName("sendPageNice");
        return modelAndView;
    }
}
