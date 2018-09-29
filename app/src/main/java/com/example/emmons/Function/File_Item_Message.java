package com.example.emmons.Function;

/**
 * Created by Emmons on 2018/9/7 0007.
 */

public class File_Item_Message {
    private String FileName;
    private String FileSize;
    private String FileOB;
    private String FileDate;

    public File_Item_Message(String filename, String filesize, String fileob, String filedate){
        super();
        this.FileName = filename;
        this.FileDate = filedate;
        this.FileOB = fileob;
        this.FileSize = filesize;
    }

    public String getFileName(){
        return this.FileName;
    }

    public String getFileSize(){
        return this.FileSize;
    }

    public String getFileOB(){
        return this.FileOB;
    }

    public String getFileDate(){
        return this.FileDate;
    }
}
