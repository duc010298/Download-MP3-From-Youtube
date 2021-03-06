/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import view.main;

/**
 *
 * @author Đỗ Trung Đức
 */
public class MyUtils {

    private main mainFrame = null;

    public void downloadMP3(String strURL, String folderSave, javax.swing.JFrame frame) {
        mainFrame = (main) frame;
        String command = "";
        command += getCurrentWorkingDir()
                + "\\youtubeDL\\youtube-dl --extract-audio --audio-format mp3 --audio-quality 320k --add-metadata "
                + "--metadata-from-title " + '"' + "%(artist)s - %(title)s" + '"' + " "
                + "--output " + '"' + folderSave + "%(title)s.%(ext)s" + '"' + " "
                + '"' + strURL + '"';
        executeCommand(command);
    }

    private String getCurrentWorkingDir() {
        return System.getProperty("user.dir");
    }

    private void executeCommand(String command) {
        String temp = null;
        boolean isFirst = true;
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream(), "CP1258"));
            String line;
            while ((line = r.readLine()) != null) {
                if(line.contains("[download]")) {
                    if(isFirst) {
                        mainFrame.writeToConsole(line);
                        temp = mainFrame.getConsoleContent() + "\n";
                        isFirst = false;
                        continue;
                    }
                    mainFrame.rewriteConsole(temp + line);
                    continue;
                }
                mainFrame.writeToConsole(line);
            }
        } catch (IOException ex) {
        }
    }

    public boolean validateURL(String URL) {
        String regex = "^(https\\:\\/\\/)(www\\.youtube\\.com\\/watch\\?v=).+$";
        return URL.matches(regex);
    }
}
