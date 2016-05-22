package com.jfshare.product.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lenovo on 2016/5/20.
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 下载远程文件到本地
     * @param remoteFilePath 远程文件url
     * @param localFilePath 本地文件路径
     */
    public static boolean downloadFile(String remoteFilePath, String localFilePath, String fileName) {
        logger.info(">>>> downloadFile ---- remoteFilePath : {}, localFilePath : {}", remoteFilePath, localFilePath);
        URL urlFile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File filePath = new File(localFilePath);
        File file = new File(localFilePath + fileName);
        try {
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            urlFile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection) urlFile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(file));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
        } catch (Exception e) {
            logger.error("<<<<<<<< downloadFile error !!! remoteFilePath : " + remoteFilePath + ", localFilePath : " + localFilePath, e);
            return false;
        } finally {
            try {
                bis.close();
                bos.close();
                httpUrl.disconnect();
//                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
