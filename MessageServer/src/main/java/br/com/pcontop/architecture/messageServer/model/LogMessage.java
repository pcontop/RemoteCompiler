package br.com.pcontop.architecture.messageServer.model;

import br.com.pcontop.architecture.messageBeans.LogMessageDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by pcont_000 on 26/01/2016.
 */
@Entity
public class LogMessage {
    @Id
    private String id;
    private String idFile;
    private String text;
    private Calendar date;
    private String originator;

    public LogMessage(){
        this.setId(UUID.randomUUID().toString());
        this.setDate(Calendar.getInstance());
    }

    public String getIdFile() {
        return idFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogMessage that = (LogMessage) o;

        if (idFile != null ? !idFile.equals(that.idFile) : that.idFile != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return !(originator != null ? !originator.equals(that.originator) : that.originator != null);

    }

    @Override
    public int hashCode() {
        int result = idFile != null ? idFile.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (originator != null ? originator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
                "idFile='" + idFile + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", originator='" + originator + '\'' +
                '}';
    }

    public LogMessageDTO getDTO(){
        LogMessageDTO logMessageDTO = new LogMessageDTO();
        logMessageDTO.setId(getId());
        logMessageDTO.setIdFile(getIdFile());
        logMessageDTO.setOriginator(getOriginator());
        logMessageDTO.setText(getText());
        return logMessageDTO;
    }

    public static LogMessage fromDTO(LogMessageDTO logMessageDTO){
        LogMessage logMessage = new LogMessage();
        logMessage.setId(logMessageDTO.getId());
        logMessage.setDate(logMessageDTO.getDate());
        logMessage.setIdFile(logMessageDTO.getIdFile());
        logMessage.setOriginator(logMessageDTO.getOriginator());
        logMessage.setText(logMessageDTO.getText());
        return logMessage;
    }
}
