package com.example.xcxlibrary.parser;


import com.example.xcxlibrary.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

public class FliePrarser extends BaseParser {

    private String filePath;
    private String fileName;


    public FliePrarser(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    @Override
    public File parse(Response response) {
        if (response.isSuccessful()) {
            File file = null;
            byte[] bytes = new byte[4096];
            FileOutputStream fileOutputStream = null;
            InputStream is = null;
            File download = new File(file,fileName);
            try {
                is = response.body().byteStream();
                File fpath = new File(filePath);
                FileUtil.isfileexists(file);
                fileOutputStream =new FileOutputStream(download);
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    fileOutputStream.write(bytes,0,len);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (is!=null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileOutputStream!=null){
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //下载内容
            return file;
        }
        return null;
    }
}
