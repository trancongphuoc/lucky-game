/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.utils;

import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.IOException;

/**
 *
 * @author NhatNT2
 */
public class FileUploadCommon {

    public String doSongUpload(File tmpFile, String strFilePath) {

        File fileDesc = new File(strFilePath);
        try {
            if (fileDesc == null) {
                return "ERROR";
            } else {
                FileUtils.copyFile(tmpFile, fileDesc);
            }
        } catch (IOException ex) {
        }
        return "SUCCESS";
    }

    public String doConvertWav2Mulaw(String strWavFilePath, String strMulawFilePath) {

        try {
            String strBashCmd = "sox " + strWavFilePath
                    + " -t raw -r 8000 -c 1 -b 8 -U " + strMulawFilePath;
            exeBashCommandline(strBashCmd);
        } catch (IOException ex) {
            return "FAIL";
        }
        return "SUCCESS";
    }

    public String doConvertMulaw2Wav(String strWavFilePath, String strMulawFilePath) {

        try {
            String strBashCmd = "sox -t raw -r 8000 -U -b 8 -c 1 " + strMulawFilePath + " " + strWavFilePath;
//            System.out.println("doConvertMulaw2Wav: " + strBashCmd);
            exeBashCommandline(strBashCmd);
        } catch (IOException ex) {
            return "FAIL";
        }
        return "SUCCESS";
    }

    public String doDeleteFile(String strFilePath) {

        String strBashCmd = "rm -rf " + strFilePath;
        try {
            exeBashCommandline(strBashCmd);
        } catch (IOException ex) {
            return "FAIL";
        }
        return "SUCCESS";
    }
    public String doMakeFolder(String strFolderPath) {

        String strBashCmd = "mkdir -p " + strFolderPath;
        try {
            exeBashCommandline(strBashCmd);
        } catch (IOException ex) {
            return "FAIL";
        }
        return "SUCCESS";
    }

    public String exeBashCommandline(String strCommand) throws IOException {

//        Process proc = null;
        try {
            strCommand = strCommand.trim();
            if (strCommand.length() > 0) {
                Runtime.getRuntime().exec(strCommand);
//                proc = Runtime.getRuntime().exec(strCommand);
            }
        } catch (IOException e) {
        }
        return "";
    }

    public String exeCommandLinux(String[] strCommand) throws IOException {

        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(strCommand);
        } catch (IOException e) {
        }
        return "";
    }
}
