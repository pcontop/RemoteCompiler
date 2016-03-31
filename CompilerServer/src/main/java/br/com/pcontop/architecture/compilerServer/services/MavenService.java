package br.com.pcontop.architecture.compilerServer.services;

import br.com.pcontop.architecture.compilerServer.config.Config;
import br.com.pcontop.architecture.compilerServer.exceptions.MavenCompilingException;
import br.com.pcontop.architecture.compilerServer.exceptions.NotAMavenProjectException;
import br.com.pcontop.architecture.messageService.MessageService;
import org.apache.maven.shared.invoker.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;

/**
 * Created by pcont_000 on 27/01/2016.
 */
@Service
public class MavenService {

    @Autowired
    Config config;

    @Autowired
    MessageService messageService;

    public void compile(String idFile, String projectPath) {
        messageService.sendMessage(idFile, "Starting Maven processing for project.");

        String pathToPom = getPathToPom (idFile, projectPath, 0);
        if (pathToPom==null){
            messageService.sendMessage(idFile, "Pom from project not found!");
            throw new NotAMavenProjectException("Pom from project not found!");
        }
        messageService.sendMessage(idFile, "Pom found at:" + pathToPom);

        mavenCompile(idFile, pathToPom);

        messageService.sendMessage(idFile, "Finished compiling the project with Maven.");

    }


    private String getPathToPom(String idFile, String projectPath, int depth) {
        String fileName=null;
        File dir = new File(projectPath);
        File[] poms = dir.listFiles(new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().equals("pom.xml");
            }
        });
        if (poms==null){
            return null;
        }

        if (poms.length!=1){
            if (depth==0) {
                for (File subDir: dir.listFiles()){
                    if (subDir.isDirectory()) {
                        fileName = getPathToPom(idFile, projectPath + subDir.getName(), 1);
                    }
                }
            }
        } else {
            fileName = poms[0].getAbsolutePath();
        }
        return fileName;
    }

    private void mavenCompile(String idFile, String projectPath) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile( new File( projectPath ) );
        request.setGoals( Collections.singletonList( "package" ) );
        request.setJavaHome(new File(config.JAVA_HOME));

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(config.MAVEN_HOME));
        try {
            InvocationResult result = invoker.execute( request );
            if (result.getExitCode()!=0){
                messageService.sendMessage(idFile, "Exception on generating Maven Package.");
                throw new MavenCompilingException("Exception on generating Maven Package.");
            }
        } catch (MavenInvocationException e) {
            e.printStackTrace();
            messageService.sendMessage(idFile, "Error on compiling Maven: " + e.getMessage());
            throw new MavenCompilingException("Error on compiling Maven: " + e.getMessage());
        }

    }
}
