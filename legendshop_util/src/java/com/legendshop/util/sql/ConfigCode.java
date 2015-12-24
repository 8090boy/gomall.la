/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.sql;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.legendshop.util.AppUtils;
import com.legendshop.util.xml.Configure;

/**
 * 主配置文件解析类
 * 
 * 1、每一行只有一对大括号{}
 * 
 * 2、在{之后紧跟！表示这个条件即使没有传值过来就采用默认值"",如果带有||则将||之前的作为默认值
 * 
 * 3、如果{之后不跟!,表示如果不传值的话这个条件忽略
 * 
 * 4、用$$括起来的参数（key）将会用parameterMap中的value代替
 * 
 * 5、采用objectName.MethodName作为key放在parameterMap中
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class ConfigCode {

	/** The logger. */
	private static Logger logger = Logger.getLogger(ConfigCode.class);

	/** The Constant DAL_VERSION. */
	private final static String DAL_VERSION = "2012-04-26";

	/** The config code. */
	private static ConfigCode configCode = null;
	// 是否是调试模式，如果是调试模式则只要文件有改动则重新加载 true:是，false：否
	/** The debug. */
	private boolean debug = false;

	/** The parameters. */
	private Map<String, String> parameters = null;

	/** file, modifiedtime. */
	private Map<String, CodeItem> fileNames = null;

	/** The mainfile. */
	private File mainfile = null;

	/** The last modified time. */
	private long lastModifiedTime;

	/** The init status. */
	private boolean initStatus;
	
	private static final Object statusMonitor = new Object();

	/** The dynamic code. */
	private final DynamicCode dynamicCode;

	/** The conf name. */
	private String confName = "classpath*:DAL.cfg.xml";

	/**
	 * Instantiates a new config code.
	 */
	private ConfigCode() {
		parameters = new HashMap<String, String>();
		fileNames = new HashMap<String, CodeItem>();
		dynamicCode = new DynamicCode();
		initStatus = false;
	}

	/**
	 * 获取单态实例.
	 * 
	 * @return 实例对象
	 */
	public static ConfigCode getInstance() {
		if (configCode == null) {
			configCode = new ConfigCode();
			if (!configCode.initStatus) {
				configCode.init(configCode.confName);
			}
		}
		return configCode;
	}

	/**
	 * Gets the single instance of ConfigCode.
	 * 
	 * @param fileName
	 *            the file name
	 * @return single instance of ConfigCode
	 */
	public static ConfigCode getInstance(String fileName) {
		if (configCode == null) {
			configCode = new ConfigCode();
			if (!configCode.initStatus) {
				configCode.setConfName(fileName);
				configCode.init(fileName);
			}
		}
		return configCode;
	}

	/**
	 * 刷新实例.
	 * 
	 * @return 实例对象
	 */

	public  static ConfigCode refresh() {
		synchronized (statusMonitor) {
			if (!configCode.initStatus) {
				logger.warn("ConfigCode还没有初始化，不能刷新，先要调用getInstance方法");
			} else {
				logger.info("注意：开始刷新ConfigCode！");
			}
			String confName = configCode.getConfName();
			configCode = null;
			configCode = new ConfigCode();
			if (!configCode.initStatus) {
				configCode.setConfName(confName);
				configCode.init(confName);
			}
			return configCode;
		}
		
	}

	/**
	 * 获取相应的配置信息.
	 * 
	 * @param signature
	 *            对象名
	 * @return String
	 */
	public String getCode(String signature) {
		isDebugMode();
		String sql = parameters.get(signature);
		if (sql == null) {
			logger.warn(" getCode return null, signature = " + signature);
			return null;
		}

		return sql;
	}

	/**
	 * 获取相应的配置信息.
	 * 
	 * @param signature
	 *            对象名
	 * @param parameterMap
	 *            the parameter map
	 * @return String对象
	 */
	public String getCode(String signature, Map<String, Object> parameterMap) {
		isDebugMode();
		return dynamicCode.convert(getCode(signature), parameterMap);
	}

	/**
	 * 
	 */
	private void isDebugMode() {
		if (debug) {// 测试模式
			if (configCode.isModified()) {
				logger.debug(confName + " had modify,load again!");
				ConfigCode.configCode = new ConfigCode();
				ConfigCode.configCode.init(confName);
			} else {
				for (String fileName : fileNames.keySet()) {
					CodeItem item = fileNames.get(fileName);
					if (item.isModified()) {
						refreshCodeItem(fileName, item);
					}
				}
			}
		}
	}

	/**
	 * 利用配置文件来进行初始化工作.
	 * 
	 * @param fileName
	 *            配置文件名字
	 * @return 初始化是否成功
	 */
	private boolean init(String fileName) {
		synchronized (statusMonitor) {
			if (initStatus) {
				logger.warn("ConfigCode had inited, should not init again");
				return true;
			}
			logger.info("The current version of DAL is : " + DAL_VERSION + ".");
			Configure configure = new Configure();
			try {
				mainfile = getFile(fileName);
				// String mainpath = mainfile.getAbsoluteFile().getParent();// 主目录
				lastModifiedTime = mainfile.lastModified();
				configure.parse(fileName);
				// 读取映射的配置文件
				String path = "/DataAccessLayer/MappingFiles";
				int resCount = configure.getItemCount(path);

				for (int i = 1; i <= resCount; i++) {
					path = "/DataAccessLayer/MappingFiles/Mapping[" + i + "]";
					String filename = configure.getItemProp(path, "resource");
					if (filename.startsWith("classpath")) {
						Resource[] resorces = getResources(filename);
						for (int j = 0; j < resorces.length; j++) {
							Resource resource = resorces[j];
							CodeItem item = new CodeItem();
							try {
								String name = resource.getFile().toString();// 全路径
								initCodeItem(name, item);
							} catch (Exception e) {
								initCodeItem(resource.getInputStream(), item);
							}

						}
					} else {
						CodeItem item = new CodeItem();
						initCodeItem(filename, item);
					}

				}

			} catch (Exception e) {
				logger.error("初始化DAL配置文件出错", e);
				initStatus = false;
				return initStatus;
			}
			initStatus = true;
			
			//after initialize
			afterInitialize();
			return initStatus;
		}
		
	}
	
	private void afterInitialize(){
		//replace parameters
		filterPlaceholder();
	}
	
	private void filterPlaceholder() {
		try {
			for (String codeKey : parameters.keySet()) {
				Map<String,Boolean> recursionChecking = new HashMap<String,Boolean>();
				dynamicCode.fillPlaceHolder(codeKey, parameters, recursionChecking);
			}
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initCodeItem(InputStream inputStream, CodeItem item) {
		logger.debug("initCodeItem by inputStream");
		Map<String, String> itemCodes = item.init(inputStream);
		// check unique key constraints
		if (AppUtils.isNotBlank(itemCodes)) {
			for (String itemKey : itemCodes.keySet()) {
				checkObjectAndPut(itemKey, itemCodes.get(itemKey));
			}
		}
	}

	private void refreshCodeItem(String filename, CodeItem item) {
		logger.debug("sql mapping fileName = " + filename);
		Map<String, String> itemCodes = item.init(filename);
		// check unique key constraints
		if (AppUtils.isNotBlank(itemCodes)) {
			for (String itemKey : itemCodes.keySet()) {
				parameters.put(itemKey, itemCodes.get(itemKey));
			}
		}

		fileNames.put(filename, item);

	}

	/**
	 * @param filename
	 * @param item
	 */
	private void initCodeItem(String filename, CodeItem item) {
		logger.debug("sql mapping fileName = " + filename);
		Map<String, String> itemCodes = item.init(filename);
		// check unique key constraints
		if (AppUtils.isNotBlank(itemCodes)) {
			for (String itemKey : itemCodes.keySet()) {
				checkObjectAndPut(itemKey, itemCodes.get(itemKey));
			}
		}

		fileNames.put(filename, item);
	}

	/**
	 * 主要用于调试，输出对象内部状态.
	 * 
	 * @return 字符串对象
	 */
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		Iterator itor = fileNames.entrySet().iterator();
		while (itor.hasNext()) {
			Map.Entry entry = (Map.Entry) itor.next();
			strBuf.append("<Mapping resource=").append((String) entry.getKey()).append(">\n");
			CodeItem item = (CodeItem) entry.getValue();
			strBuf.append(item.toString());
			strBuf.append("</Mapping>").append('\n');
		}
		return strBuf.toString();
	}

	private void checkObjectAndPut(String key, String value) {
		if (parameters.containsKey(key)) {
			logger.warn(" unique constraint violated ,key = " + key);
		} else {
			parameters.put(key, value);
		}
	}

	/**
	 * 检查属性文件是否被修改过.
	 * 
	 * @return true, if is modified
	 */
	private boolean isModified() {
		if (this.mainfile == null) {
			return false;
		}
		return this.mainfile.lastModified() > this.lastModifiedTime;
	}

	/**
	 * Gets the parameters.
	 * 
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/*
	 * 设置是否调试模式，true为调试模式，配置更改后自动更改 @param debug
	 */
	/**
	 * Sets the debug.
	 * 
	 * @param debug
	 *            the new debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * Checks if is inits the status.
	 * 
	 * @return true, if is inits the status
	 */
	public boolean isInitStatus() {
		return initStatus;
	}

	/**
	 * Checks if is debug.
	 * 
	 * @return true, if is debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * Gets the conf name.
	 * 
	 * @return the conf name
	 */
	public String getConfName() {
		return confName;
	}

	/**
	 * Sets the conf name.
	 * 
	 * @param confName
	 *            the new conf name
	 */
	public void setConfName(String confName) {
		this.confName = confName;
	}

	/**
	 * 使用Spring提供的组件获取文件信息.
	 * 
	 * @param path
	 *            the path
	 * @return the resources
	 * @throws Exception
	 *             the exception
	 */
	public Resource[] getResources(String path) throws Exception {
		ResourcePatternResolver rer = new PathMatchingResourcePatternResolver();
		if (path.startsWith("classpath")) {
			return rer.getResources(path);
		}
		return rer.getResources("classpath*:" + path);
	}

	/**
	 * Gets the file.
	 * 
	 * @param path
	 *            the path
	 * @return the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public File getFile(String path) throws IOException {
		File file = null;
		if (path.startsWith("classpath")) {
			int pos = path.indexOf(":");
			String filePath = path.substring(pos + 1);
			ClassPathResource cpr = new ClassPathResource(filePath);
			file = cpr.getFile();
		} else {
			file = new File(path);
		}
		return file;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		try {
			ConfigCode configCode = ConfigCode.getInstance();
			configCode.setDebug(true);
			String os = ObjectSignature.toSignature("TestObject1", "find");
			String result = configCode.getCode(os);
			HashMap map = new HashMap();
			map.put("id", "1");
			map.put("name", "name");
			map.put("condition1", "and address = gm");
			System.out.println(configCode.getCode("TestObject1.update", map));
			System.out.println(configCode.getCode("TestObject1.update"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
