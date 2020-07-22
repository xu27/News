package com.example.xcxlibrary.Util;

import java.io.File;

public class FileUtil {
    public static void isfileexists(String filepath){
        File file =new File(filepath);
        if (!file.exists()) {
            //不存在创建路径
            file.mkdir();
        }
    };
    public static void isfileexists(File file){
        if (!file.exists()) {
            //不存在创建路径
            file.mkdir();
        }
    };
}
