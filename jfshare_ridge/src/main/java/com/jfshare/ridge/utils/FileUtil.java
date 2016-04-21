package com.jfshare.ridge.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <PRE>
 * 作用:
 *       文件操作工具类
 * 限制:
 *       无.
 * 注意事项:
 *       无.
 * 修改历史:
 * -----------------------------------------------------------------------------
 *         VERSION       DATE                BY              CHANGE/COMMENT
 * -----------------------------------------------------------------------------
 *          1.0        2011-07-15           null              create
 * -----------------------------------------------------------------------------
 * </PRE>
 */
public class FileUtil {
	
	protected static final Logger logger = LoggerFactory
            .getLogger(FileUtil.class);

	/**
	 * 写文件
	 * 
	 * @param filePathAndName
	 *            文件路径与名称
	 * @param fileContent
	 *            需要写入的文件内容
	 * @param characterSet
	 *            字符集编码
	 */
	public static void writeFile(String filePathAndName, String fileContent,
			String characterSet) {
		try {
			File file = new File(filePathAndName);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(file), characterSet);
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(fileContent);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件返回集合
	 * 
	 * @param filePathAndName
	 *            文件路径和名称
	 * @param characterSet
	 *            字符集编码
	 * @return 以行内容为单位的集合
	 */
	public static List<String> readFile(String filePathAndName,
			String characterSet) {
		File file = new File(filePathAndName);
		List<String> list = new ArrayList<String>();
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), characterSet);
			BufferedReader reader = new BufferedReader(read);
			String line = "";
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
			read.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 追加文件：使用FileWriter
	 * 
	 * @param filePathAndName
	 *            文件路径和名称
	 * @param content
	 *            需要追加的内容
	 */
	public static boolean appendWriter(String filePathAndName, String content) {
		try {
			File file = new File(filePathAndName);
			if (!file.exists()) {// 判断是否存在
				file.createNewFile();
			}
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(filePathAndName, true);
			writer.write(content + "\n");
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param folderPath
	 *            文件夹路径和名称
	 */
	public static void createFolder(String folderPathAndName) {
		try {
			
			File newFolder = new File(folderPathAndName);
			 if (newFolder.exists()) {
				 return;
			 }

			if (newFolder.getParentFile().exists()) {
				newFolder.mkdir();
			} else {
				createFolder(newFolder.getParentFile().getPath());
				newFolder.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            操作文件的路径和名称
	 */
	public static void delFile(String filePathAndName) {
		try {
			File delFile = new File(filePathAndName);
			delFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param file
	 */
	public static void deleteFileFolder(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					FileUtil.deleteFileFolder(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
			System.out.println("this filePath is not exist！" + '\n');
		}
	}

	/**
	 * 复制文件夹与文件夹下的文件
	 * 
	 * @param rawFolderPathAndName
	 *            原始文件夹路径和名称
	 * @param newFolderPathAndName
	 *            新文件夹和名称
	 */
	public static void copyFolder(String rawFolderPathAndName,
			String newFolderPathAndName) {
		try {
			(new File(newFolderPathAndName)).mkdirs();
			File a = new File(rawFolderPathAndName);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (rawFolderPathAndName.endsWith(File.separator)) {
					temp = new File(rawFolderPathAndName + file[i]);
				} else {
					temp = new File(rawFolderPathAndName + File.separator
							+ file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(
							newFolderPathAndName + "/"
									+ (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {
					copyFolder(rawFolderPathAndName + "/" + file[i],
							newFolderPathAndName + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	
	
	public static void writePropertiesFile(String filePathAndName , Map<String,String> keyValueMap) throws Exception{
		if(filePathAndName==null || keyValueMap==null){
			return;
		}
		File file = new File(filePathAndName);
        if(!file.exists()){
			file.createNewFile();
		}
		
		
		FileOutputStream fos = null;
		FileInputStream fis = null;
		Properties prop = new Properties();
		
		try {
			fis = new FileInputStream(file);
			prop.load(fis);
			fos = new FileOutputStream(file);
		
			for(Entry<String,String> entry : keyValueMap.entrySet()){
				prop.setProperty(entry.getKey(), entry.getValue());
			}
			prop.store(fos,null);
			fos.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}
				if (null!=fis){
					fis.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}
	
	
	/**
	 * 更新properties中某一个文件, 多线程下操作会有问题
	 * @param filePathAndName
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	@Deprecated
	public static void writePropertiesFileSingleKeyValue(String filePathAndName,String key, String value) throws Exception{
		if(filePathAndName==null || key==null || value==null){
			return;
		}
		 File file = new File(filePathAndName);
         if(!file.exists()){
 			file.createNewFile();
 		}
		
		FileOutputStream fos = null;
		FileInputStream fis = null;
		Properties prop = new Properties();
		try {
			fis = new FileInputStream(file);
			synchronized(file){
				prop.load(fis);
				prop.setProperty(key, value);
				fos = new FileOutputStream(file);
			
				prop.store(fos,null);
				fos.flush();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}
				if (null!=fis){
					fis.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		
//		logger.debug(Constants.formatLogMsg("Write snapshot:[" +filePathAndName+ "] key:[" + key + "] value:["+ value +"] successfully !"));
	}

	public static void main(String[] args) {
		String path = "/data/test.properties";
//		Map<String,String> keyValueMap = new HashMap<String,String>();
//		for(int i=0;i<10;i++){
//			String key = "redis.ip."+i;
//			String value = "192.168.10.1"+i;
//			keyValueMap.put(key, value);
//		}
//		try {
//			FileUtil.writePropertiesFile(path, keyValueMap);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			FileUtil.writePropertiesFileSingleKeyValue(path, "redis.ip.0", "hahah");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// FileUtil.createFolder("d:\\test1");
	}

}