package com.example.smartroomsimulationspring.Operations;

import java.io.*;

public class WrappedReader {
    private String fileName;
    private BufferedReader br;
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public WrappedReader(String argument, BufferedReader br , String filePath) {
        this.setFileName(argument);
        this.setBr(br);
        this.setFilePath(filePath);
    }
}
