package com.example.xcxlibrary.Util.HttpUtil;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadHelper {

    /**
     *保存到传入的文件路径下
     * @param url
     * @param filename
     * @param filePath 文件路径
     * @param listener 回调接口
     */
    public static void downloadFile(String url, String filename,String filePath , DownloadFileListener listener) {
        DownloadFileAsyncTask asyncTask=new DownloadFileAsyncTask(url,filename,filePath,listener);
        asyncTask.execute();
    }

    /**
     *
     * @param url
     * @param listener 回调接口
     */
    public static void downloadHttpdata(String url, DownloadHttpDataListener listener) {
        DownloadHttpDataAsyncTask asyncTask = new DownloadHttpDataAsyncTask(url,listener);
        asyncTask.execute();
    }

    /**
     * 专门用来下载重服务器获取的短文本
     * @param surl
     * @param listener
     */
    public static void downloadShortText(String surl,ShortTextListener listener){
        String data = "";
        try {
            URL url=new URL(surl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setConnectTimeout(5*1000);
            InputStream inputStream = null;
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK){
                inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    data+=line;
                }
            }else {
                listener.onFail(-1,"code="+code);
            }
            listener.onSuccess(1,data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            listener.onFail(-1,e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            listener.onFail(-1,e.getMessage());
        }
    }

    public static String downloadShortText(String surl){
        String data = "";
        try {
            URL url=new URL(surl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setConnectTimeout(5*1000);
            InputStream inputStream = null;
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK){
                inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    data+=line;
                }
            }else {
                return "code="+code;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return data;
    }

    /**
     * onProgress 更新进度
     * onstart 开始下载
     * onSuccess下载成功
     * onFail 下载失败
     */
    public interface DownloadFileListener{
        void onProgress(int value);
        void onstart();
        void onSuccess(int code, File file);
        void onFail(int code, File file, String message);
        void onfinish();
        abstract class CustomListener implements DownloadFileListener {
            @Override
            public void onProgress(int value) { }

            @Override
            public void onstart() { }

            @Override
            public void onSuccess(int code, File file) { }

            @Override
            public void onFail(int code, File file, String message) { }
        }

    }

    /**
     * downloadFolderName 默认文件路径
     * documentname 传入文件路径
     */
    public static class DownloadFileAsyncTask extends AsyncTask<String,Integer,Boolean>{

        private String url;
        private String filename;
        private DownloadFileListener listener;
        private String filePath;

        public DownloadFileAsyncTask(String url, String filename, String filePath, DownloadFileListener listener) {
            this.url = url;
            this.filename = filename;
            this.listener = listener;
            this.filePath= filePath;
        }

        //准备
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (listener!=null){
                listener.onstart();
            }

            //判断文件路径是否存在
            File file = new File(filePath);
            if (!file.exists()) {
                //不存在创建路径
                file.mkdir();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String murl = url;
            String fileName = filePath + "/" + filename;
            try {
                URL getdata = new URL(murl);
                HttpURLConnection connection = (HttpURLConnection) getdata.openConnection();
                InputStream inputStream = connection.getInputStream();
                /**
                 * 获取文件的总长度
                 */
                int contentLength = connection.getContentLength();
                File apkFile = new File(fileName);
                //判断有无重名文件
                if(apkFile.exists()){
                    //尝试删除重名文件
                    boolean result = apkFile.delete();
                    if(!result){
                        if(listener != null){
                            listener.onFail(-1, apkFile, "文件重复且删除失败");
                        }
                        return false;
                    }
                }
                int downloadSize = 0;
                //缓冲
                byte[] bytes = new byte[1024];
                int length;
                FileOutputStream outputStream = new FileOutputStream(fileName);
                while ((length = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, length);
                    downloadSize += length;
                    /**
                     * update UI
                     */
                    publishProgress(downloadSize * 100 / contentLength);
                }
                //关闭流
                inputStream.close();
                outputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (listener!=null){
                listener.onSuccess(1,new File(fileName));
            }
            return true;
        }

        //更新
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values!=null&&values.length>0){
                if (listener!=null){
                    listener.onProgress(values[0]);
                }
            }
        }

        //结束
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            listener.onfinish();
        }
    }


    public interface DownloadHttpDataListener{
        void onProgress(int value);
        void onstart();
        void onSuccess(int code, Object data);
        void onFail(int code, String message);
        void onfinish();
        abstract class HttpDataListener implements DownloadHelper.DownloadHttpDataListener {
            @Override
            public void onProgress(int value) { }
            @Override
            public void onstart() { }
            @Override
            public void onSuccess(int code, Object data) { }
            @Override
            public void onFail(int code, String message) { }
            @Override
            public void onfinish() { }
        }

    }


    /**
     * 用于从网络上下载临时加载的数据 如：json img
     */
    public static class DownloadHttpDataAsyncTask extends AsyncTask<String,Integer,Boolean>{

        private String url;
        private DownloadHttpDataListener listener;

        public DownloadHttpDataAsyncTask(String url, DownloadHttpDataListener listener) {
            this.url = url;
            this.listener = listener;
        }

        //准备
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (listener!=null){
                listener.onstart();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String murl = url;
            HttpURLConnection connection = null;
            InputStream inputStream=null;
            try {
                URL url = new URL(murl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5*1000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setRequestProperty("Charset", "UTF-8");
                connection.setRequestProperty("Accept-Charset", "UTF-8");
                connection.connect();
                if (connection.getResponseCode()==200){
                    inputStream = connection.getInputStream();
                    int size = connection.getContentLength();//无法获取get的长度
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0,downloadSize = 0;
                    while ((len = inputStream.read(buffer))!=-1){
                        bao.write(buffer,0,len);
                        /**
                         * update UI
                         */
                        downloadSize += len;
                        publishProgress(downloadSize);
                    }
                    byte[] bytedata = bao.toByteArray();
                    listener.onSuccess(1,bytedata);
                }else {
                    listener.onFail(-1,"httpNot200");
                    return false;
                }
            } catch (MalformedURLException e) {
                listener.onFail(-1,e.getMessage());
                return false;
            } catch (IOException e) {
                listener.onFail(-1,e.getMessage());
                return false;
            }finally {
                connection.disconnect();
                if (inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        listener.onFail(-1,e.getMessage());
                    }
                }
            }
            return true;
        }

        //更新
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values!=null&&values.length>0){
                if (listener!=null){
                    listener.onProgress(values[0]);
                }
            }
        }

        //结束
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            listener.onfinish();
        }
    }

    /**
     * 下载短文本的监听器
     */
    public interface ShortTextListener{
        void onstart();
        void onSuccess(int code, Object data);
        void onFail(int code, String message);
        abstract class XCXShortTextListener implements ShortTextListener{
            @Override
            public void onstart() { }

            @Override
            public void onSuccess(int code, Object data) { }

            @Override
            public void onFail(int code, String message) { }
        }
    }
}
