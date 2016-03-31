package br.com.pcontop.architecture.messageBeans;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by pcont_000 on 26/01/2016.
 */
public class LogMessageDTO {
    private String id;
    private String idFile;
    private String text;
    private Calendar date;
    private String originator;

    public LogMessageDTO(){
        setId(UUID.randomUUID().toString());
        setDate(Calendar.getInstance());
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdFile() {
        return idFile;
    }

    public void setIdFile(String idFile) {
        this.idFile = idFile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }
}
