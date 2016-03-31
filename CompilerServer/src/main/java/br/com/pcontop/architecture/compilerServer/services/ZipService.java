package br.com.pcontop.architecture.compilerServer.services;

import br.com.pcontop.architecture.compilerServer.config.Config;
import br.com.pcontop.architecture.compilerServer.exceptions.InsufficientStorageException;
import br.com.pcontop.architecture.compilerServer.exceptions.UnsupportedFileException;
import br.com.pcontop.architecture.messageService.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by pcont_000 on 27/01/2016.
 */
@Service
public class ZipService {

    @Autowired
    Config config;

    @Autowired
    MessageService messageService;

    private static final int BUFFER_SIZE = 4096;

    public String unzipFile(String idFile, File file){
        checkZip(idFile, file);

        String tmpDir = buildTmpDir(idFile);

        unzipFile(idFile, file, tmpDir);

        return tmpDir;
    }

    private void unzipFile(String idFile, File file, String tmpDir) {
        messageService.sendMessage(idFile, "Unzipping file: " + file.getName());
        try {
            unzip(file.getAbsolutePath(), tmpDir);
        } catch (IOException e) {
            messageService.sendMessage(idFile, "Error unzipping file: " + file.getName() + ": "  + e.getMessage());
            throw new InsufficientStorageException("Error unzipping file: " + file.getName() + ".");
        }
        messageService.sendMessage(idFile, "File unzipped successfully: " + file.getName());

    }

    private String buildTmpDir(String idFile) {
        String dirFilePath = getTempPath(idFile);
        File dirFile = new File (dirFilePath);
        dirFile.mkdirs();
        return dirFilePath;
    }

    private String getTempPath(String idFile) {
        return config.TEMP_DIR + idFile + "/";
    }


    private void checkZip(String idFile, final File file) {
        messageService.sendMessage(idFile, "Checking zip file status of " + file.getName());
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(file);
        } catch (IOException e) {
            messageService.sendMessage(idFile, "File is not a healthy zip!");
            throw new UnsupportedFileException("File is not a healthy zip!");
        } finally {
            try {
                if (zipfile != null) {
                    zipfile.close();
                    zipfile = null;
                }
            } catch (IOException e) {
                messageService.sendMessage(idFile, "File is not a healthy zip!");
                throw new UnsupportedFileException("File is not a healthy zip!");
            }
        }
        messageService.sendMessage(idFile, "Zipfile ok!");

    }

    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    public File zipPath(String idFile) {
        messageService.sendMessage(idFile, "Preparing to generate return zip file.");
        File dirTemp = new File(getTempPath(idFile));
        File projectDir = dirTemp.listFiles()[0];
        String projectName = projectDir.getName();
        String zipName = projectName + ".zip";
        File zip = new File(dirTemp.getAbsolutePath() + File.separator +  zipName);
        if (zip.exists()){
            zip.delete();
        }
        ZipUtil.pack(projectDir, zip);
        messageService.sendMessage(idFile, "Zip file generated.");
        return zip;
    }
}
