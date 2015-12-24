/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.constant.ConfigPropertiesEnum;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.constant.SysParameterTypeEnum;
import com.legendshop.model.entity.SystemParameter;
import com.legendshop.util.AppUtils;
import com.legendshop.util.EnvironmentConfig;
import com.legendshop.util.FileConfig;
import com.legendshop.util.SystemUtil;
import com.legendshop.util.TimerUtil;
import com.legendshop.util.converter.ByteConverter;
import com.legendshop.util.des.DES2;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * --------------------------------------------
 * ---------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ------------------
 * -----------------------------------------------------------------.
 */
public class PropertiesUtil extends SystemUtil {

	/** The common object. */
	private static Map<String, Object> commonObject = new HashMap<String, Object>();

	// 小图片
	/**
	 * Gets the small files absolute path.
	 * 
	 * @return the small files absolute path
	 */
	public static String getSmallFilesAbsolutePath() {
		String smallImagePath = EnvironmentConfig.getInstance().getPropertyValue(FileConfig.ConfigFile, ConfigPropertiesEnum.SMALL_PIC_PATH.name());
		if (AppUtils.isBlank(smallImagePath)) {
			smallImagePath = getSystemRealPath() + AttributeKeys.SMALL_IMAGE;
		}
		return smallImagePath;
	}

	// 大图片
	/**
	 * Gets the big files absolute path.
	 * 
	 * @return the big files absolute path
	 */
	public static String getBigFilesAbsolutePath() {
		String bigImagePath = EnvironmentConfig.getInstance().getPropertyValue(FileConfig.ConfigFile, ConfigPropertiesEnum.BIG_PIC_PATH.name());
		if (AppUtils.isBlank(bigImagePath)) {
			bigImagePath = getSystemRealPath() + AttributeKeys.BIG_IMAGE;
		}
		return bigImagePath;
	}

	// 图片备份路径
	/**
	 * Gets the backup files absolute path.
	 * 
	 * @return the backup files absolute path
	 */
	public static String getBackupFilesAbsolutePath() {
		String backupImagePath = EnvironmentConfig.getInstance().getPropertyValue(FileConfig.ConfigFile, ConfigPropertiesEnum.BACKUP_PIC_PATH.name());
		if (AppUtils.isBlank(backupImagePath)) {
			backupImagePath = getSystemRealPath() + AttributeKeys.BACKUP_IMAGE;
		}
		return backupImagePath;
	}

	public static String getHtmlPath() {
		String htmlPath = EnvironmentConfig.getInstance().getPropertyValue(FileConfig.ConfigFile, ConfigPropertiesEnum.HTML_PATH.name());
		if (AppUtils.isBlank(htmlPath)) {
			htmlPath = getSystemRealPath() + AttributeKeys.HTML_COMMON;
		}
		return htmlPath;
	}

	// 读取的缩略图路径
	/**
	 * Gets the user files path servlet.
	 * 
	 * @return the user files path servlet
	 */
	public static String getSmallImagePathPrefix() {
		return EnvironmentConfig.getInstance().getPropertyValue(FileConfig.ConfigFile, AttributeKeys.IMAGES_PATH_PREFIX);
	}

	// 读取的大图片路径
	public static String getPhotoPathPrefix() {
		return EnvironmentConfig.getInstance().getPropertyValue(FileConfig.FCKEditorFile, AttributeKeys.PHOTO_PATH_PREFIX);
	}

	// 读取domain配置
	/**
	 * Gets the domain name.
	 * 
	 * @return the domain name
	 */
	public static String getDomainName() {
		return EnvironmentConfig.getInstance().getPropertyValue(FileConfig.ConfigFile, AttributeKeys.DOMAIN_NAME);
	}

	// 得到下载文件路径
	/**
	 * Gets the download file path.
	 * 
	 * @return the download file path
	 */
	public static String getDownloadFilePath() {
		String downloadPath = EnvironmentConfig.getInstance().getPropertyValue(FileConfig.ConfigFile, ConfigPropertiesEnum.DOWNLOAD_PATH.name());
		if (AppUtils.isBlank(downloadPath)) {
			downloadPath = getSystemRealPath() + AttributeKeys.DOWNLOAD_PATH;
		}
		return downloadPath;
	}

	// 得到LucenePath
	/**
	 * Gets the lucene path.
	 * 
	 * @return the lucene path
	 */
	public static String getLucenePath() {
		String path = EnvironmentConfig.getInstance().getPropertyValue(FileConfig.ConfigFile, ConfigPropertiesEnum.LUCENE_PATH.name());
		if (AppUtils.isBlank(path)) {
			path = getSystemRealPath() + AttributeKeys.LUCENE_PATH;
		}
		return path;
	}

	/**
	 * Gets the properties.
	 * 
	 * @param configFile
	 *            the config file
	 * @param key
	 *            the key
	 * @return the properties
	 */
	public static String getProperties(String configFile, String key) {
		return EnvironmentConfig.getInstance().getPropertyValue(configFile, key);
	}

	public static String getGlobalProperties(String key) {
		return EnvironmentConfig.getInstance().getPropertyValue(FileConfig.GlobalFile, key);
	}

	/**
	 * Checks if is system installed.
	 * 
	 * @return true, if is system installed
	 */
	public static boolean isSystemInstalled() {
		return "INSTALLED".equals(PropertiesUtil.getProperties(FileConfig.ConfigFile, ConfigPropertiesEnum.INSTALLED.name()));
	}

	/**
	 * Checks if is system in debug mode.
	 * 
	 * @return true, if is system in debug mode
	 */
	public static boolean isSystemInDebugMode() {
		return "true".equals(PropertiesUtil.getProperties(FileConfig.GlobalFile, ConfigPropertiesEnum.SQL_DEBUG_MODE.name()));
	}

	// 得到CurrencyPattern
	/**
	 * Gets the currency pattern.
	 * 
	 * @return the currency pattern
	 */
	public static String getCurrencyPattern() {
		return EnvironmentConfig.getInstance().getPropertyValue(FileConfig.GlobalFile, ConfigPropertiesEnum.CURRENCY_PATTERN.name());
	}

	/**
	 * ConfigFile end.
	 * 
	 * @param fileName
	 *            the file name
	 * @param map
	 *            the map
	 */

	// 写配置文件
	public static void writeProperties(String fileName, Map<String, String> map) {
		EnvironmentConfig.getInstance().writeProperties(fileName, map);
	}

	/**
	 * Gets the object.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sysParameterEnum
	 *            the parameter enum
	 * @param clazz
	 *            the clazz
	 * @return the object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObject(SysParameterEnum sysParameterEnum, Class<T> clazz) {
		// if (clazz != sysParameterEnum.getClazz()) {
		// throw new RuntimeException("Required Type = " +
		// sysParameterEnum.getClazz() + ", but Actual Type = "
		// + clazz);
		// }
		return (T) commonObject.get(sysParameterEnum.name());
	}

	/**
	 * Gets the boolean object.
	 * 
	 * @param parameterEnum
	 *            the parameter enum
	 * @return the boolean object
	 */
	public static Boolean getBooleanObject(String parameter) {
		return (Boolean) commonObject.get(parameter);
	}

	/**
	 * Sets the object.
	 * 
	 * @param sysParameterEnum
	 *            the parameter enum
	 * @param obj
	 *            the obj
	 */
	public static void setObject(SysParameterEnum sysParameterEnum, Object obj) {
		if (obj.getClass() != sysParameterEnum.getClazz()) {
			throw new RuntimeException("Required Type = " + sysParameterEnum.getClazz() + ", but Actual Type = " + obj.getClass());
		}
		commonObject.put(sysParameterEnum.name(), obj);
	}

	/**
	 * Sets the parameter.
	 * 
	 * @param parameter
	 *            the parameter
	 * @param propertiesUpdater
	 *            the properties updater
	 */
	public static void setParameter(SystemParameter parameter) {
		String type = parameter.getType();
		if (SysParameterTypeEnum.Integer.name().equalsIgnoreCase(type)) {
			try {
				commonObject.put(parameter.getName(), Integer.valueOf(parameter.getValue()));
			} catch (Exception e) {
				commonObject.put(parameter.getName(), Integer.valueOf(parameter.getOptional()));
			}

		} else if (SysParameterTypeEnum.Boolean.name().equalsIgnoreCase(type)) {
			commonObject.put(parameter.getName(), Boolean.valueOf(parameter.getValue()));
		} else if (SysParameterTypeEnum.Long.name().equalsIgnoreCase(type)) {
			try {
				commonObject.put(parameter.getName(), Long.valueOf(parameter.getValue()));
			} catch (Exception e) {
				commonObject.put(parameter.getName(), Integer.valueOf(parameter.getOptional()));
			}

		} else if (SysParameterTypeEnum.List.name().equalsIgnoreCase(type)) {
			// 以英文逗号分隔
			List<String> list = new ArrayList<String>();
			if (AppUtils.isNotBlank(parameter.getValue())) {
				String[] values = parameter.getValue().split(",");
				for (String str : values) {
					list.add(str);
				}
			}
			commonObject.put(parameter.getName(), list);
		} else {// String default and Selection and password
			commonObject.put(parameter.getName(), parameter.getValue());
		}
	}

	// 页面间调用该方法
	/**
	 * Gets the default shop name.
	 * 
	 * @return the default shop name
	 */
	public static String getDefaultShopName() {
		return getObject(SysParameterEnum.DEFAULT_SHOP, String.class);
	}

	/**
	 * 是否是默认商城
	 * 
	 * @param shopName
	 * @return
	 */
	public static boolean isDefaultShopName(String shopName) {
		return getObject(SysParameterEnum.DEFAULT_SHOP, String.class).equals(shopName);
	}

	/**
	 * Checks if is in default shop. 是否位于默认商城
	 * 
	 * @param shopName
	 *            the shop name
	 * @return true, if is in default shop
	 */
	public static boolean isInDefaultShop(String shopName) {
		return getObject(SysParameterEnum.DEFAULT_SHOP, String.class).equals(shopName);
	}

	/**
	 * Gets the legend shop system id.
	 * 
	 * @return the legend shop system id
	 */
	public static String getLegendShopSystemId() {
		return PropertiesUtil.getProperties(FileConfig.ConfigFile, ConfigPropertiesEnum.LEGENDSHOP_SYSTEM_ID.name());
	}

	/**
	 * Change legend shop system id.
	 */
	public static void changeLegendShopSystemId() {
		DES2 des = new DES2();
		Map<String, String> map = new HashMap<String, String>();
		String encryptorString = TimerUtil.getStrDate();
		String legendShopSystemId = ByteConverter.encode(des.byteToString(des.createEncryptor(encryptorString)));
		map.put(ConfigPropertiesEnum.LEGENDSHOP_SYSTEM_ID.name(), legendShopSystemId);
		String file = PropertiesUtil.getSystemRealPath() + FileConfig.ConfigFileRealPath;
		PropertiesUtil.writeProperties(file, map);
	}

	/**
	 * Send mail.
	 * 
	 * @return true, if successful
	 */
	public static boolean sendMail() {
		return PropertiesUtil.getObject(SysParameterEnum.SEND_MAIL, Boolean.class);
	}

}
