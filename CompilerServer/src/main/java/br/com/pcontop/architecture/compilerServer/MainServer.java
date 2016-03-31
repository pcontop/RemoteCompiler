package br.com.pcontop.architecture.compilerServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("br.com.pcontop.architecture")
public class MainServer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainServer.class, args);
    }


}
