/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.exception.InvalidFormatException;
import com.legendshop.util.AppUtils;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class FileProcessor {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(FileProcessor.class);

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		/*
		 * FileProcessor fp = new FileProcessor(); String filename = "111.txt";
		 * String dir1 = "D:\\tomcat\\webapps\\ezmp"; String dir2 =
		 * "\\upload\\admin\\cms\\info\\news"; String dir3 = "\\466_2004-02-17";
		 * String storeDir = dir1 + dir2; File file = new File("c:\\boot.ini");
		 */

		// fp.debug(getFileSuffixName(filename));
		// fp.debug(addNameBeforeSuffix(filename, "222"));
		// fp.writeFile("", storeDir,dir3,true,false,true);
		// fp.uploadFile(file, storeDir,true,false);
		// String storeDir = "E:\\test";
		// uploadFile(FormFile uploadedFile,storeDir, true,true);
		// System.out.println(fp.readFile(storeDir + dir3, true));
	}

	/**
	 * Instantiates a new file processor.
	 */
	public FileProcessor() {
	}

	/**
	 * 删除文件.
	 * 
	 * @param fileName
	 *            文件名称
	 * @return
	 *  0： 删除成功，
	 * -1：文件存在，但删除失败
	 * 1： 没有文件
	*	2： 未知原因失败
	 */
	public static int deleteFile(String fileName) {
		return deleteFile(fileName, true);
	}
	
	/**
	 * 删除文件.
	 * 
	 * @param fileName
	 *            文件名称
	 * @param backup 是否备份大图片
	 * @return
	 *  0： 删除成功，
	 * -1：文件存在，但删除失败
	 * 1： 没有文件
	*	2： 未知原因失败
	 */
	public static int deleteFile(String filename, boolean backup) {
		try {
			File f = new File(filename);
			if (f.exists()) {// 检查是否存在
				// 备份文件
				if (backup) {
					String backupPath = PropertiesUtil.getBackupFilesAbsolutePath();
					if (AppUtils.isNotBlank(backupPath)) {
						String bigPath = PropertiesUtil.getBigFilesAbsolutePath();
						if (AppUtils.isNotBlank(bigPath)) {
							if (filename.indexOf(bigPath) > -1) {// 大图片才会备份
								// copy file
								String destFilePath = filename;
								destFilePath = destFilePath.replace(bigPath, backupPath);
								File destFile = new File(destFilePath);
								FileUtils.copyFile(f, destFile);
							}
						}
					}
				}
				boolean result = f.delete();// 删除文件
				if(result){
					return 0; // 删除成功
				}else{
					return -1; // 删除失败
				}
				
			} else {
				log.warn("没有该文件：{}", filename);
				return 1;// 没有文件
			}
		} catch (Exception e) {
			log.warn("删除文件 {} 失败", filename);
			return 2;// 失败
		}
	}

	/**
	 * 删除目录和下面的所有的文件.
	 * 
	 * @param path
	 *            the path
	 */
	public static void deleteDirectory(File path) {
		if (!path.exists())
			return;
		if (path.isFile()) {
			path.delete();
			return;
		}
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteDirectory(files[i]);
		}
		path.delete();
	}

	/**
	 * Upload file.
	 * 
	 * @param is
	 *            the is
	 * @param storeDir
	 *            the store dir
	 * @param prefix
	 *            the prefix
	 * @param uploadedFileName
	 *            the uploaded file name
	 * @param nameChanged
	 *            the name changed
	 * @param overwrited
	 *            the overwrited
	 * @return the string
	 */
	public static String uploadFile(InputStream is, String storeDir, String prefix, String uploadedFileName, boolean nameChanged,
			boolean overwrited) {
		FileOutputStream fos = null;
		try {
			String extName = getFileExtName(uploadedFileName);
			if (nameChanged) {
				uploadedFileName = prefix + System.currentTimeMillis() + randomNumeric(new Random(), 6) + extName;
			}
			File parentPath = new File(storeDir);
			if (!parentPath.exists()) {
				parentPath.mkdirs();
			}
			File f = new File(storeDir + "/" + uploadedFileName);
			if (f.exists() && nameChanged) {
				uploadedFileName = System.currentTimeMillis() + "_" + uploadedFileName;
				f = new File(storeDir + "/" + uploadedFileName);
			} else if (f.exists() && !overwrited) {
				return uploadedFileName;
			}
			fos = new FileOutputStream(f);
			int len = 0;
			byte[] buf = new byte[8192];
			while ((len = is.read(buf)) > 0) {
				fos.write(buf, 0, len);
			}

		} catch (Exception e) {
			return null;
		} finally {
			try {
				fos.close();

			} catch (Exception e) {
			}
			try {
				is.close();
			} catch (Exception e2) {
			}
		}

		return uploadedFileName;
	}

	/**
	 * Random numeric.
	 * 
	 * @param random
	 *            the random
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String randomNumeric(Random random, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			int val = random.nextInt(10);
			sb.append(String.valueOf(val));
		}
		return sb.toString();
	}

	/**
	 * Copy file.
	 * 
	 * @param srcFile
	 *            the src file
	 * @param destFile
	 *            the dest file
	 * @return true, if successful
	 */
	public static boolean copyFile(File srcFile, File destFile) {
		try {
			FileInputStream fSrc = new FileInputStream(srcFile);
			FileOutputStream fDest = new FileOutputStream(destFile);
			int len = 0;
			byte[] buf = new byte[8192];
			while ((len = fSrc.read(buf)) > 0) {
				fDest.write(buf, 0, len);
			}
			fDest.close();
			fSrc.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * Write file. 默认写文件方法
	 * 
	 * @param fileContent
	 *            the file content
	 * @param parentFilePath
	 *            the parent file path
	 * @param uploadedFileName
	 *            the uploaded file name
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String writeFile(String fileContent, String parentFilePath, String uploadedFileName) throws IOException {
		return writeFile(fileContent, parentFilePath, uploadedFileName, false, true, false);
	}

	/**
	 * Write file.
	 * 
	 * @param fileContent
	 *            the file content
	 * @param parentFilePath
	 *            the parent file path
	 * @param uploadedFileName
	 *            the uploaded file name
	 * @param nameChanged
	 *            the name changed
	 * @param overwrited
	 *            the overwrited
	 * @param formatText
	 *            the format text
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String writeFile(String fileContent, String parentFilePath, String uploadedFileName, boolean nameChanged,
			boolean overwrited, boolean formatText) throws IOException {
		File parentPath = null;
		File outFile = null;
		FileWriter outFileWriter = null;
		OutputStreamWriter outputStreamWriter = null;
		try {

			parentPath = new File(parentFilePath);
			if (!parentPath.exists()) {
				parentPath.mkdirs();
			}

			outFile = new File(parentFilePath + "/" + uploadedFileName);
			if (outFile.exists() && nameChanged) {
				uploadedFileName = System.currentTimeMillis() + "_" + uploadedFileName;
				outFile = new File(parentFilePath + "/" + uploadedFileName);
			} else if (outFile.exists() && !overwrited) {
				return uploadedFileName;
			}

			outFileWriter = new FileWriter(outFile);
			outputStreamWriter = new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8");

			if (formatText) {
				StringBuffer formatSb = new StringBuffer();
				formatSb.append(" <P> ");
				for (int i = 0; i < fileContent.length(); i++) {
					if (new String(new char[] { fileContent.charAt(i) }).equals(" ")) {
						formatSb.append(" &nbsp;&nbsp; ");
					} else if (new String(new char[] { fileContent.charAt(i) }).equals("\n")) {
						formatSb.append(" <br> ");
					} else if (new String(new char[] { fileContent.charAt(i) }).equals("\n")
							&& new String(new char[] { fileContent.charAt(i + 1) }).equals("\n")
							&& ((i + 1) != fileContent.length())) {
						formatSb.append(" </P><br><P> ");
						i++;
					} else {
						formatSb.append(new String(new char[] { fileContent.charAt(i) }));
					}
				}
				formatSb.append("</P>");
				fileContent = formatSb.toString();
			}

			outputStreamWriter.write(fileContent);
			outFileWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
			if (outFileWriter != null) {
				outFileWriter.close();
			}
			outputStreamWriter = null;
			outFileWriter = null;
		}

		return uploadedFileName;
	}

	/**
	 * Read file.
	 * 
	 * @param file
	 *            the file
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String readFile(File file) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	/**
	 * Read file.
	 * 
	 * @param file
	 *            the file
	 * @param breakline
	 *            the breakline
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String readFile(File file, boolean breakline) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Upload file and callback.
	 * 
	 * @param file
	 *            the file
	 * @param subPath
	 *            the sub path
	 * @param prefix
	 *            the prefix
	 * @return the string
	 */
	public static String uploadFileAndCallback(MultipartFile file, String subPath, String prefix) {
		if (validateFile(file)) {
			String fileName = file.getOriginalFilename();
			String extName = getFileExtName(fileName);
			if(prefix == null){
				prefix = "";
			}
			String lastFileName = prefix  + System.currentTimeMillis() + randomNumeric(new Random(), 6) + extName;

			String path = subPath + lastFileName;
			File dirPath = new File(RealPathUtil.getBigPicRealPath() + "/" + subPath);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			String fileFullPath = RealPathUtil.getBigPicRealPath() + "/" + path;

			File f = new File(fileFullPath);
			try {
				FileCopyUtils.copy(file.getBytes(), f);
			} catch (Exception e) {
				log.error("upload file:", e);
			}
			return path;
		} else {
			String fileName = null;
			if (file != null) {
				fileName = file.getName();
			}
			throw new InvalidFormatException(ResourceBundleHelper.getString("invalid.file.format", "invalid.file.format") + fileName);
		}
	}

	/**
	 * Validate file.
	 * 
	 * @param file
	 *            the file
	 * @return true, if successful
	 */
	private static boolean validateFile(MultipartFile file) {
		// 3145728 : 3M
		//1048576 = 1024 * 1024
		if ((file.getSize() < 0) || (file.getSize() > PropertiesUtil.getObject(SysParameterEnum.MAX_FILE_SIZE, Long.class) * 1048576)) {
			return false;
		}
		String fileName = file.getOriginalFilename();
		String extName = null;
		try {
			extName = getFileExtName(fileName);
		} catch (Exception e) {
			log.warn("can not get file extName,maybe there is not a file!");
			return false;
		}
		List list = PropertiesUtil.getObject(SysParameterEnum.ALLOWED_UPLOAD_FILE_TPYE, List.class);
		if (list.contains(extName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the file ext name.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the file ext name
	 */
	public static String getFileExtName(String fileName) {
		if (AppUtils.isBlank(fileName)) {
			return "";
		}
		return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
	}

}