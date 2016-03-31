package br.com.pcontop.architecture.compilerServer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by pcont_000 on 27/01/2016.
 */
@Service
public class Config {


    @Value("${server.name}")
    public String ORIGINATOR;

    @Value("${logserver.url}")
    public String LOG_SERVER_URL;

    @Value("${server.tmpdir}")
    public String TEMP_DIR;

    @Value("${server.maven_home}")
    public String MAVEN_HOME;

    @Value("${server.java_home}")
    public String JAVA_HOME;

    public static String LOG_MESSAGE_SERVER="/logMessage";


}
