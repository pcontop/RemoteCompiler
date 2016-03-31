package br.com.pcontop.architecture.messageServer.dao;

import br.com.pcontop.architecture.messageServer.model.LogMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by pcont_000 on 26/01/2016.
 */
@org.springframework.stereotype.Repository
public interface LogMessageDAO extends CrudRepository<LogMessage, String> {
    public List<LogMessage> findByIdFile(@Param("idFile") String idFile);
}
