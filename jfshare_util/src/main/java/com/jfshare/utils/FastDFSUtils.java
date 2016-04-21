package com.jfshare.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;

public class FastDFSUtils {
	private static Log logger = LogFactory.getLog(FastDFSUtils.class);
	
	// 接超时的时限，单位为毫秒
	private int connect_timeout = 30000;
	// 网络超时的时限，单位为毫秒 
	private int network_timeout = 30000;
	// 字符集
	private String charset = "UTF-8";
	
	public FastDFSUtils(String host, int port){
		try {
			ClientGlobal.setG_connect_timeout(connect_timeout);
			ClientGlobal.setG_network_timeout(network_timeout);
			ClientGlobal.setG_charset(charset);
			
			// 单机
			if(!host.trim().equals("") && port != 0){
				InetSocketAddress[] tracker_servers = new InetSocketAddress[1];  
				tracker_servers[0] = new InetSocketAddress(host, port);
				ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FastDFSUtils(String host, int port, int connectTimeout, int networkTimeout, String charSet){
		try {
			ClientGlobal.setG_connect_timeout(connectTimeout);
			ClientGlobal.setG_network_timeout(networkTimeout);
			ClientGlobal.setG_charset(charSet);
			
			// 单机
			if(!host.trim().equals("") && port != 0){
				InetSocketAddress[] tracker_servers = new InetSocketAddress[1];  
				tracker_servers[0] = new InetSocketAddress(host, port);
				ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FastDFSUtils(List<HostAndPort> trackerServers){
		try {
			ClientGlobal.setG_connect_timeout(connect_timeout);
			ClientGlobal.setG_network_timeout(network_timeout);
			ClientGlobal.setG_charset(charset);
			
			// 集群
			if(trackerServers.size() > 0){
				InetSocketAddress[] tracker_servers = new InetSocketAddress[trackerServers.size()];
				for(int i = 0; i < trackerServers.size(); i++){
					HostAndPort host_port = trackerServers.get(i);
					tracker_servers[i] = new InetSocketAddress(host_port.getHost(), host_port.getPort());
				}
				ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers)); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FastDFSUtils(List<HostAndPort> trackerServers, int connectTimeout, int networkTimeout, String charSet){
		try {
			ClientGlobal.setG_connect_timeout(connectTimeout);
			ClientGlobal.setG_network_timeout(networkTimeout);
			ClientGlobal.setG_charset(charSet);
			
			// 集群
			if(trackerServers.size() > 0){
				InetSocketAddress[] tracker_servers = new InetSocketAddress[trackerServers.size()];
				for(int i = 0; i < trackerServers.size(); i++){
					HostAndPort host_port = trackerServers.get(i);
					tracker_servers[i] = new InetSocketAddress(host_port.getHost(), host_port.getPort());
				}
				ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers)); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传文件到fastDFS
	 * @param inputStream
	 * @param uploadFileName
	 * @param fileExtName
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(InputStream inputStream, String uploadFileName, String fileExtName) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();  
		
        byte[] buff = new byte[1024];  
        int rc = 0;  
        while ((rc = inputStream.read(buff, 0, 1024)) > 0) {  
            bos.write(buff, 0, rc);  
        }
        
		return this.uploadFile(bos.toByteArray(), uploadFileName, fileExtName);
	}
	
	/**
	 * 上传文件到fastDFS
	 * @param file
	 * @param uploadFileName
	 * @param fileExtName
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(File file, String uploadFileName, String fileExtName) throws Exception {
		return this.uploadFile(FileUtils.readFileToByteArray(file), uploadFileName, fileExtName);
	}
	
	/**
	 * 上传文件到fastDFS
	 * @param fileBuff
	 * @param uploadFileName
	 * @param fileLength
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(byte[] fileBuff, String uploadFileName, String fileExtName) throws Exception {
		TrackerServer trackerServer = null;
		String fileId = "";

		try {
			// 建立连接
			trackerServer = new TrackerClient().getConnection();
			StorageClient1 client = new StorageClient1(trackerServer, null);

			// 设置元信息
			NameValuePair[] metaList = new NameValuePair[3];
			metaList[0] = new NameValuePair("fileName", uploadFileName);
			metaList[1] = new NameValuePair("fileExtName", fileExtName);
			metaList[2] = new NameValuePair("fileLength", String.valueOf(fileBuff.length));

			// 上传文件
			fileId = client.upload_file1(fileBuff, fileExtName, metaList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传文件：" + uploadFileName + "到fastDFS失败！");
			throw e;
		} finally {
			if(trackerServer != null){
				trackerServer.close();
			}
		}

		return fileId;
	}
	
	/**
	 * 下载文件
	 * @param fastDFS的key
	 * @return
	 */
	public byte[] downuplaodFile(String filePath) {
		TrackerServer trackerServer = null;
		byte[] fileBytes = null;
		
		try {
			// 建立连接
			trackerServer = new TrackerClient().getConnection();
			StorageClient1 client = new StorageClient1(trackerServer, null);

			// 上传文件
			fileBytes = client.download_file1(filePath);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下载文件：" + filePath + "失败！");
			throw e;
		} finally { 
			try {
				if(trackerServer != null){
					trackerServer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return fileBytes;
		}
	}
	
	public static void main(String[] args) {
		FastDFSUtils util = new FastDFSUtils("192.168.10.82", 22122);
		byte[] bytes = util.downuplaodFile("group2/M00/00/46/wKgKVFTzxoKAJn_6AAAWAOQ-fBc265.xls");
		util.getFile(bytes, "E:\\test.xls");
	}
	
	 private void getFile(byte[] bfile, String filePath) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;
        
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    }

}
