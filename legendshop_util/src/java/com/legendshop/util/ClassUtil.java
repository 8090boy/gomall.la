package com.legendshop.util;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import com.legendshop.annotation.RequestMapping;

/**
 * 2007-10-31 上午09:49:41 Author:yangdongyu(yangdy@bingosoft.net) ReadMe:对类操作的帮助类
 * ChangeLog:
 */

public class ClassUtil {
	private static final Log log = LogFactory.getLog(ClassUtil.class);

	/**
	 * Map with primitive wrapper type as key and corresponding primitive type
	 * as value, for example: Integer.class -> int.class.
	 */
	private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap<Class<?>, Class<?>>(8);

	static {
		primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
		primitiveWrapperTypeMap.put(Byte.class, byte.class);
		primitiveWrapperTypeMap.put(Character.class, char.class);
		primitiveWrapperTypeMap.put(Double.class, double.class);
		primitiveWrapperTypeMap.put(Float.class, float.class);
		primitiveWrapperTypeMap.put(Integer.class, int.class);
		primitiveWrapperTypeMap.put(Long.class, long.class);
		primitiveWrapperTypeMap.put(Short.class, short.class);

	}

	/**
	 * Check if the given class represents a primitive (i.e. boolean, byte,
	 * char, short, int, long, float, or double) or a primitive wrapper (i.e.
	 * Boolean, Byte, Character, Short, Integer, Long, Float, or Double).
	 * 
	 * @param clazz
	 *            the class to check
	 * @return whether the given class is a primitive or primitive wrapper class
	 */
	public static boolean isPrimitiveOrWrapper(Class clazz) {
		return clazz.isPrimitive() || primitiveWrapperTypeMap.containsKey(clazz);
	}

	/**
	 * get the wapper class short name of primative Class clazz
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getWapperClassShortName(Class clazz) {
		String clazzName = "";
		if (clazz.isPrimitive()) {

			if (clazz == boolean.class) {
				clazzName = "Boolean";
			} else if (clazz == byte.class) {
				clazzName = "Byte";
			} else if (clazz == char.class) {
				clazzName = "Character";
			} else if (clazz == double.class) {
				clazzName = "Double";
			} else if (clazz == float.class) {
				clazzName = "Float";
			} else if (clazz == int.class) {
				clazzName = "Integer";
			} else if (clazz == long.class) {
				clazzName = "Long";
			} else if (clazz == short.class) {
				clazzName = "Short";
			}
		} else {
			throw new RuntimeException("Primitive class is required ...");
		}

		return clazzName;
	}

	/**
	 * 创建一个特定类型特定长度的数组
	 * 
	 * @param componentType
	 * @param size
	 * @return
	 */
	public static Object createArrayInstance(Class componentType, int size) {
		if (String.class == componentType) {
			return new String[size];
		}
		if (Integer.class == componentType) {
			return new Integer[size];
		}
		if (Long.class == componentType) {
			return new Long[size];
		}
		if (Short.class == componentType) {
			return new Short[size];
		}
		if (Byte.class == componentType) {
			return new Byte[size];
		}
		if (Boolean.class == componentType) {
			return new Boolean[size];
		}
		if (Double.class == componentType) {
			return new Double[size];
		}
		if (Float.class == componentType) {
			return new Float[size];
		}
		return null;
	}

	/**
	 * Return the default ClassLoader to use: typically the thread context
	 * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
	 * class will be used as fallback.
	 * <p>
	 * Call this method if you intend to use the thread context ClassLoader in a
	 * scenario where you absolutely need a non-null ClassLoader reference: for
	 * example, for class path resource loading (but not necessarily for
	 * <code>Class.forName</code>, which accepts a <code>null</code> ClassLoader
	 * reference as well).
	 * 
	 * @return the default ClassLoader (never <code>null</code>)
	 * @see java.lang.Thread#getContextClassLoader()
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			log.debug("Cannot access thread context ClassLoader - falling back to system class loader", ex);
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassUtil.class.getClassLoader();
		}
		return cl;
	}

	/**
	 * 加载指定的类
	 * 
	 * @param name
	 *            要加载的类的名称
	 * @return
	 * @throws ClassNotFoundException
	 * @throws LinkageError
	 */
	public static Class forName(String name) throws ClassNotFoundException, LinkageError {
		Assert.notNull(name, "Name must not be null");
		return getDefaultClassLoader().loadClass(name);
	}

	/**
	 * 创建指定类的对象
	 * 
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object newInstance(String className) {
		try {
			return forName(className).newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (LinkageError e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取指定方法名的名称
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static List<Method> getMethods(Class<?> clazz, String methodName) throws NoSuchMethodException {
		Method[] methods = clazz.getMethods();
		List<Method> methodsWithTheSameName = new ArrayList<Method>();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
			if (requestMapping != null && requestMapping.value() != null) {
				for (int j = 0; j < requestMapping.value().length; j++) {
					if (methodName.equals(requestMapping.value()[j])) {
						System.out.println("match method mapping = " + requestMapping.value()[j]);
						methodsWithTheSameName.add(method);
					}
				}
			}

		}
		if (methodsWithTheSameName.size() == 0) {
			throw new NoSuchMethodException(methodName);
		}
		return methodsWithTheSameName;
	}

	public static Method getMethodByName(Class<?> clazz, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
		StringBuilder paramKey = new StringBuilder(methodName);
		if (parameterTypes != null && parameterTypes.length > 0) {
			for (Class<?> class2 : parameterTypes) {
				paramKey.append(class2.toString());
			}
		}
		// System.out.println("paramKey = " + paramKey);
		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			StringBuilder methodKey = new StringBuilder(method.getName());
			Class<?>[] pt = method.getParameterTypes();
			if (pt != null && pt.length > 0) {
				for (Class<?> class1 : pt) {
					methodKey.append(class1.toString());
				}
			}
			// System.out.println("methodKey = " + methodKey);
			if (methodKey.toString().equals(paramKey.toString())) {
				System.out.println("match method mapping = " + method);
				return method;
			}
		}
		return null;
	}

	// ---------------------------------------------------------------------------------------------------------

	public static final String CLASS_SUFFIX = ".class";

	/** URL prefix for loading from the file system: "file:" */
	public static final String FILE_URL_PREFIX = "file:";

	/** URL protocol for a file in the file system: "file" */
	public static final String URL_PROTOCOL_FILE = "file";

	/** URL protocol for an entry from a jar file: "jar" */
	public static final String URL_PROTOCOL_JAR = "jar";

	/** URL protocol for an entry from a zip file: "zip" */
	public static final String URL_PROTOCOL_ZIP = "zip";

	/** URL protocol for an entry from a WebSphere jar file: "wsjar" */
	public static final String URL_PROTOCOL_WSJAR = "wsjar";

	/** Separator between JAR URL and file path within the JAR */
	public static final String JAR_URL_SEPARATOR = "!/";

	static class Queued {
		final URL _packageURL;

		final String _packagePath;

		public Queued(final URL packageURL, final String packagePath) {
			_packageURL = packageURL;
			_packagePath = packagePath;
		}
	}

	/**
	 * 查找指定包下面的所有类
	 * 
	 * @param filter
	 *            类过滤器
	 * @param packageName
	 *            包名称
	 * @return 包下面所有的类
	 */
	public static synchronized Collection<Class<?>> getClassesOfPackage(ClassFilter filter, String... packageNames) {
		Collection<Class<?>> result = new ArrayList<Class<?>>();
		if (filter == null) {
			filter = new ClassFilter() {
				public boolean accept(Class<?> clazz) {
					return true;
				}
			};
		}
		for (String pkgName : packageNames) {
			String trimName = pkgName.trim();
			if (!"".equals(trimName)) {
				String packagePath = trimName.replace('.', '/') + "/";
				log.debug("try to find classes in pakcage " + packagePath);
				try {
					result.addAll(findClassesWithinPath(getDefaultClassLoader(), packagePath, filter));
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}

		}
		return result;

	}

	/**
	 * 查找指定包路径下面的类的集合
	 * 
	 * @param classLoader
	 * @param packagePath
	 * @param filter
	 * @return
	 * @throws IOException
	 */
	private static Collection<Class<?>> findClassesWithinPath(ClassLoader classLoader, String packagePath, ClassFilter filter)
			throws IOException {
		Collection<Class<?>> result = new ArrayList<Class<?>>();

		Enumeration<URL> urls = classLoader.getResources(packagePath);

		Set<String> paths = new HashSet<String>();
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			if (!paths.contains(url.getPath())) {
				paths.add(url.getPath());

				scanURL(packagePath, result, url, classLoader, filter);
			} else {
				// log.info("url aleady scanned,ignore it");
			}
		}

		paths.clear();

		return result;
	}

	private static void scanURL(String packagePath, Collection<Class<?>> componentClassNames, URL url, ClassLoader classLoader,
			ClassFilter filter) throws IOException {
		if (isJarURL(url)) {
			log.debug("url is [jar or weblogic zip or websphere wsjar],scan the classes in jar file");

			URLConnection connection = url.openConnection();

			JarFile jarFile = null;
			if (connection instanceof JarURLConnection) {
				jarFile = ((JarURLConnection) connection).getJarFile();
			} else {
				// No JarURLConnection -> need to resort to URL file parsing.
				// We'll assume URLs of the format "jar:path!/entry", with the
				// protocol
				// being arbitrary as long as following the entry format.
				// We'll also handle paths with and without leading "file:"
				// prefix.
				String urlFile = url.getFile();
				int separatorIndex = urlFile.indexOf(JAR_URL_SEPARATOR);
				String jarFileUrl = urlFile.substring(0, separatorIndex);
				if (jarFileUrl.startsWith(FILE_URL_PREFIX)) {
					jarFileUrl = jarFileUrl.substring(FILE_URL_PREFIX.length());
				}

				log.debug("jar file url is " + jarFileUrl + ",create jar file");

				jarFile = new JarFile(jarFileUrl);
			}

			scanJarConnection(packagePath, componentClassNames, jarFile, filter);
		} else {
			// Otherwise, we're forced to assume that it is a file: URL for
			// files in
			// the user's workSpace.

			Stack<Queued> queue = new Stack<Queued>();

			queue.push(new Queued(url, packagePath));

			while (!queue.isEmpty()) {
				Queued queued = queue.pop();

				scan(queued._packagePath, queued._packageURL, componentClassNames, queue, classLoader, filter);
			}
		}
	}

	private static void scan(String packagePath, URL packageURL, Collection<Class<?>> classList, Stack<Queued> queue,
			ClassLoader classLoader, ClassFilter filter) throws IOException {
		InputStream is = null;

		try {
			is = new BufferedInputStream(packageURL.openStream());
		} catch (FileNotFoundException ex) {
			// This can happen for certain application servers (JBoss 4.0.5 for
			// example), that
			// export part of the exploded WAR for deployment, but leave part
			// (WEB-INF/classes)

			log.info("FielNotFoundException for url " + packagePath, ex);

			return;
		}

		Reader reader = new InputStreamReader(is);
		LineNumberReader lineReader = new LineNumberReader(reader);

		String packageName = null;

		try {
			while (true) {
				String line = lineReader.readLine();

				if (line == null)
					break;

				if (line.contains("$"))
					continue;

				if (line.endsWith(CLASS_SUFFIX)) {
					if (packageName == null)
						packageName = packagePath.replace('/', '.');

					// packagePath ends with '/', packageName ends with '.'

					String className = line.substring(0, line.length() - CLASS_SUFFIX.length());
					String fullClassName = packageName + className;

					try {
						Class<?> clz = Class.forName(fullClassName);
						if (filter.accept(clz)) {
							log.debug("found class " + fullClassName);
							classList.add(clz);
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					continue;
				}

				// Either a file or a hidden directory (such as .svn)

				if (line.contains("."))
					continue;

				// The name of a subdirectory.

				URL newURL = new URL(packageURL.toExternalForm() + line + "/");
				String newPackagePath = packagePath + line + "/";

				queue.push(new Queued(newURL, newPackagePath));
			}

			lineReader.close();
			lineReader = null;
		} finally {
			if (lineReader != null) {
				try {
					lineReader.close();
				} catch (IOException ex) {
					// Ignore.
				}
			}
		}
	}

	/**
	 * 扫描jar包中指定包路径下面的类
	 * 
	 * @param packageName
	 * @param componentClassNames
	 * @param jarFile
	 * @param filter
	 * @throws IOException
	 */
	private static void scanJarConnection(String packageName, Collection<Class<?>> componentClassNames, JarFile jarFile,
			ClassFilter filter) throws IOException {
		String packagePath = nameToPath(packageName);
		try {
			Enumeration<JarEntry> en = jarFile.entries();
			while (en.hasMoreElements()) {
				JarEntry je = en.nextElement();
				String name = je.getName();
				if (name.startsWith(packagePath) && name.endsWith(CLASS_SUFFIX) && !name.contains("$")) {
					String className = name.substring(0, name.length() - CLASS_SUFFIX.length());
					try {
						Class<?> clz = Class.forName(pathToName(className));
						if (filter.accept(clz)) {
							componentClassNames.add(clz);
						}
					} catch (ClassNotFoundException e) {
						log.error("无法获取类:" + className, e.getCause()); // 该错误应该不会出现
					}
				}
			}
		} finally {
			if (jarFile != null)
				try {
					jarFile.close();
				} catch (IOException e) {
					// ignore
				}
		}
	}

	/**
	 * 将类名的字符串形式转换为路径表现形式
	 * 
	 * @param className
	 *            类名
	 * @return 路径的字符串
	 */
	private static String nameToPath(String className) {
		return className.replace('.', '/');
	}

	/**
	 * 将路径转换为类的字符串表现形式
	 * 
	 * @param path
	 * @return 类名的字符串形式
	 */
	private static String pathToName(String path) {
		return path.replace('/', '.').replace('\\', '.');
	}

	private static boolean isJarURL(URL url) {
		String protocol = url.getProtocol();
		return (URL_PROTOCOL_JAR.equals(protocol) || URL_PROTOCOL_ZIP.equals(protocol) || URL_PROTOCOL_WSJAR.equals(protocol));
	}

	/**
	 * Jul 24, 2008 5:08:11 PM Author:yangdongyu(yangdy@bingosoft.net)
	 * ReadMe:类过滤器 ChangeLog:
	 */
	public static interface ClassFilter {
		boolean accept(Class<?> clazz);
	}

}
