/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.InputSource;

/**
 * 公共类库配置信息读取类,这个类不可重入，不是线程安全。
 * 
 * 这个类含path的方法都会修改当前的xml路径，getItemValueListWithFullPath方法除外，如果有疑义，
 * 可以先调用currentPath方法获取当前的xml访问路径
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。.
 */
public class Configure {

	/** The root. */
	private Document root = null; // 根节点

	/** The cur node. */
	private Node curNode = null; // Node节点

	/** The cur path. */
	private String curPath = null; // 当前path

	/** The cur nodes. */
	private List<Node> curNodes = null; // 当前Node列表

	// test code......
	/**
	 * Instantiates a new configure.
	 */
	public Configure() {
	}

	/**
	 * 通过文件名初始化Configure.
	 * 
	 * @param fileName
	 *            String
	 * @throws ConfigException
	 *             the config exception
	 */
	public void parse(String fileName) throws ConfigException {
		this.parse_xml(fileName);
	}

	/**
	 * Parses the.
	 * 
	 * @param inputStream
	 *            the input stream
	 * @throws ConfigException
	 *             the config exception
	 */
	public void parse(InputStream inputStream) throws ConfigException {
		this.parse_xml(inputStream);
	}

	/**
	 * Parses the xml str.
	 * 
	 * @param xmlStrig
	 *            the xml strig
	 * @throws ConfigException
	 *             the config exception
	 */
	public void parseXmlStr(String xmlStrig) throws ConfigException {
		this.parse_xml_str(xmlStrig);
	}

	/*
	 * 获取当前访问的路径（当前节点） @return String
	 */
	/**
	 * Current path.
	 * 
	 * @return the string
	 */
	public String currentPath() {
		return this.curPath;
	}

	/**
	 * 根据path设置当前的Node.
	 * 
	 * @param path
	 *            String Path路径
	 * @throws ConfigException
	 *             the config exception
	 */
	public void changePath(String path) throws ConfigException {
		this.curPath = path;
		this.curNode = this.root.selectSingleNode(path);
		if (this.curNode == null)
			throw new ConfigException(path + " not found.");
	};

	/**
	 * 第一类函数，获取当前节点下属性名为attrName的属性值.
	 * 
	 * @param attrName
	 *            String 属性名
	 * @return String 属性值
	 * @throws ConfigException
	 *             the config exception
	 */
	public String getItemProp(String attrName) throws ConfigException {
		String s = this.curNode.valueOf('@' + attrName);
		if (s == null)
			throw new ConfigException(attrName + " not found");
		return s;
	}

	/**
	 * 获取指定path的节点下指定的属性名为attrName的属性值.
	 * 
	 * @param path
	 *            String 指定的Path
	 * @param attrName
	 *            String 指定的属性名
	 * @return String 属性值
	 * @throws ConfigException
	 *             the config exception
	 */
	public String getItemProp(String path, String attrName) throws ConfigException {
		changePath(path);
		return getItemProp(attrName);
	}

	/**
	 * 获取path节点的(直接下级节点中的)第index个子节点的attrName属性值.
	 * 
	 * @param path
	 *            指定的节点路径
	 * @param attrName
	 *            属性名
	 * @param index
	 *            第几个子节点
	 * @return String 属性值
	 * @throws ConfigException
	 *             the config exception
	 * @deprecated 不建议使用,作为替换可使用getItemProp(path,attrName),其中
	 *             path按照XPath语法指定第index个子节点。
	 */
	@Deprecated
	public String getItemProp(String path, String attrName, int index) throws ConfigException {

		this.curNodes = this.root.selectNodes(path);
		try {

			this.curNode = this.curNodes.get(index);
			if (this.curNode == null)
				throw new ConfigException("the index =" + index + " node of " + path + " not found!");
		} catch (Exception e) {
			throw new ConfigException("the index =" + index + " node of " + path + " not found!" + e);
		}

		String s = this.curNode.valueOf(attrName);
		if (s == null)
			throw new ConfigException(attrName + " not found");
		return s;

	}

	/**
	 * 获取当前节点下属性名为attrName的属性值列表.
	 * 
	 * @param attrName
	 *            属性名
	 * @return List 属性值的列表
	 * @throws ConfigException
	 *             the config exception
	 */
	public List<String> getItemPropList(String attrName) throws ConfigException {

		LinkedList<String> valueList = new LinkedList<String>();
		// 遍历curNodes
		this.curNodes = this.root.selectNodes(this.curPath);
		Iterator<Node> nodeIt = curNodes.iterator();
		while (nodeIt.hasNext()) {
			Node node = nodeIt.next();
			// 逐个Node进行查找
			String s = node.valueOf('@' + attrName);
			if (s != null && !s.equals("")) {
				valueList.add(s);
			}
		}
		return valueList;
	}

	/**
	 * 在指定的path路径下,获取属性名为attrName的属性值列表.
	 * 
	 * @param path
	 *            指定的节点路径
	 * @param attrName
	 *            属性名
	 * @return List 属性值的列表
	 * @throws ConfigException
	 *             the config exception
	 */
	public List<String> getItemPropList(String path, String attrName) throws ConfigException {
		this.curPath = path;
		this.curNodes = this.root.selectNodes(path);
		return getItemPropList(attrName);
	}

	/**
	 * 获取path节点的(直接下级节点中的)第index个子节点的attrName属性值的列表.
	 * 
	 * @param path
	 *            指定的节点路径
	 * @param nodepath
	 *            the nodepath
	 * @param attrName
	 *            属性名
	 * @param index
	 *            第几个子节点
	 * @return List 属性值列表
	 * @throws ConfigException
	 *             the config exception
	 * @deprecated 不建议使用,作为替换可使用getItemPropList(path,attrName),其中
	 *             path按照XPath语法指定第index个子节点。
	 */
	@Deprecated
	public List getItemPropList(String path, String nodepath, String attrName, int index) throws ConfigException {

		try {
			this.curNodes = this.root.selectNodes(path);
			this.curNode = this.curNodes.get(index);
			if (this.curNode == null)
				throw new ConfigException("the index =" + index + " node of " + path + " not found!");
		} catch (Exception e) {
			throw new ConfigException("the index =" + index + " node of " + path + " not found!" + e);
		}

		this.curNodes = this.curNode.selectNodes(nodepath);

		List<String> valueList = new ArrayList<String>();
		// 遍历curNodes
		Iterator<Node> nodeIt = curNodes.iterator();
		while (nodeIt.hasNext()) {
			Node node = nodeIt.next();
			// 逐个Node进行查找
			String s = node.valueOf('@' + attrName);
			if (s != null && !s.equals("")) {
				valueList.add(s);
			}
		}
		return valueList;

	}

	/**
	 * 第二类函数，获取节点的文本 获取path节点的nodeName子节点的文本.
	 * 
	 * @param path
	 *            String 指定节点的路径
	 * @param nodeName
	 *            String 节点名
	 * @param defaultValue
	 *            String 缺省值
	 * @return String 子节点文本
	 */
	public String getItemValueEx(String path, String nodeName, String defaultValue) {
		String s = null;
		try {
			this.getItemValue(path, nodeName);
		} catch (Exception e) {
			s = defaultValue;
		}
		return s;
	};

	/**
	 * 获取path节点的nodeName节点文本，如果获取操作失败， 则获取defaultPath的的nodeName的节点文本，
	 * 若仍失败则返回defaultValue.
	 * 
	 * @param path
	 *            String 指定节点的路径
	 * @param defaultPath
	 *            String 缺省路径
	 * @param nodeName
	 *            String 节点名
	 * @param defaultValue
	 *            String 缺省值
	 * @return String 节点文本
	 */
	public String getItemValueEx(String path, String defaultPath, String nodeName, String defaultValue) {
		String s = null;
		try {
			s = getItemValue(path, defaultPath, nodeName);
		} catch (Exception e) {
			s = defaultValue;
		}
		return s;
	};

	/**
	 * 获取path节点的nodeName节点文本，如果获取操作失败，则获取defaultPath的的nodeName的节点文本.
	 * 
	 * @param path
	 *            String指定节点的路径
	 * @param defaultPath
	 *            String缺省路径
	 * @param nodeName
	 *            String节点名
	 * @return String节点文本
	 * @throws ConfigException
	 *             the config exception
	 */
	public String getItemValue(String path, String defaultPath, String nodeName) throws ConfigException {
		String s = null;
		try {
			s = getItemValue(path, nodeName);
		} catch (Exception e) {
			s = getItemValue(defaultPath, nodeName);
		}
		return s;
	};

	/**
	 * 获取path节点的nodeName节点文本.
	 * 
	 * @param path
	 *            String指定节点的路径
	 * @param nodeName
	 *            String节点名
	 * @return String节点文本
	 * @throws ConfigException
	 *             the config exception
	 */
	public String getItemValue(String path, String nodeName) throws ConfigException {
		this.changePath(path);
		return this.getItemValue(nodeName);
	};

	/**
	 * 获取path节点的(直接下级节点中的)第index个子节点的nodeName节点文本.
	 * 
	 * @param path
	 *            the path
	 * @param nodeName
	 *            String节点名
	 * @param index
	 *            int 第几个节点
	 * @return 节点文本
	 * @throws ConfigException
	 *             the config exception
	 * @deprecated 不建议使用,作为替换可使用getItemValue(path,nodeName),其中
	 *             path按照XPath语法指定第index个子节点。
	 */
	@Deprecated
	public String getItemValue(String path, String nodeName, int index) throws ConfigException {

		this.curNodes = this.root.selectNodes(path);
		try {

			this.curNode = this.curNodes.get(index);
			if (this.curNode == null)
				throw new ConfigException("the index =" + index + " node of " + path + " not found!");
		} catch (Exception e) {
			throw new ConfigException("the index =" + index + " node of " + path + " not found!" + e);
		}
		Node node = this.curNode.selectSingleNode("./" + nodeName);
		if (node == null)
			throw new ConfigException(nodeName + " not found");
		return node.getText();

	}

	/**
	 * 获取当前节点的nodeName节点文本.
	 * 
	 * @param nodeName
	 *            String 节点名
	 * @return String 节点文本
	 * @throws ConfigException
	 *             the config exception
	 */
	public String getItemValue(String nodeName) throws ConfigException {
		if (this.curPath != null && !this.curPath.equals("")) {
			this.curNode = root.selectSingleNode(this.curPath);
		}
		Node node = this.curNode.selectSingleNode("./" + nodeName);
		if (node == null)
			throw new ConfigException(nodeName + " not found");
		return node.getText();
	};

	/**
	 * 获取当前节点的文本.
	 * 
	 * @return String 节点文本
	 * @throws ConfigException
	 *             the config exception
	 */
	public String getItemValue() throws ConfigException {
		String s = this.curNode.getText();
		if (s == null) {
			throw new ConfigException("data error ,curPath has something wrong");
		}
		;
		return s;
	};

	/**
	 * 第三类函数：同第二类函数，返回值为List 获取当前节点的所有childName子节点的文本列表.
	 * 
	 * @param childName
	 *            String 子节点名
	 * @return List 子节点文本列表
	 * @throws ConfigException
	 *             the config exception
	 */
	public List<String> getItemValueList(String childName) throws ConfigException {

		LinkedList<String> valueList = new LinkedList<String>();
		// 遍历curNodes
		Iterator<Node> nodeIt = curNodes.iterator();
		while (nodeIt.hasNext()) {
			Node node = nodeIt.next();
			// 逐个Node进行查找
			List<Node> list = node.selectNodes("./" + childName);
			// 遍历查找到的node list
			Iterator<Node> it = list.iterator();
			while (it.hasNext()) {
				Node n = it.next();
				valueList.add(n.getText());
			}
		}
		return valueList;

	}

	/**
	 * 获取xml文件中所有path节点的文本.
	 * 
	 * @param path
	 *            String 指定路径
	 * @return List 文本列表
	 * @throws ConfigException
	 *             the config exception
	 */
	public List<String> getItemValueListWithFullPath(String path) throws ConfigException {
		List<Node> list = this.curNode.selectNodes(path);
		LinkedList<String> valueList = new LinkedList<String>();
		Iterator<Node> it = list.iterator();
		while (it.hasNext()) {
			Node node = it.next();
			valueList.add(node.getText());
		}
		return valueList;
	}

	/**
	 * 获取path节点的所有nodeName子节点的文本.
	 * 
	 * @param path
	 *            String 指定节点的路径
	 * @param nodeName
	 *            String 子节点名
	 * @return List 子节点文本的列表
	 * @throws ConfigException
	 *             the config exception
	 */
	public List getItemValueList(String path, String nodeName) throws ConfigException {
		this.curNodes = this.root.selectNodes(path);
		return getItemValueList(nodeName);
	}

	/**
	 * 获取path节点的(直接下级节点中的)第index个子节点的nodeName节点文本.
	 * 
	 * @param path
	 *            指定节点的路径
	 * @param nodeName
	 *            子节点
	 * @param index
	 *            第几个子节点
	 * @return 子节点文本的列表
	 * @throws ConfigException
	 *             the config exception
	 * @deprecated 不建议使用,作为替换可使用getItemValueList(path,nodeName),其中
	 *             path按照XPath语法指定第index个子节点。
	 */
	@Deprecated
	public List getItemValueList(String path, String nodeName, int index) throws ConfigException {

		this.curNodes = this.root.selectNodes(path);
		try {

			this.curNode = this.curNodes.get(index);
			if (this.curNode == null)
				throw new ConfigException("the index =" + index + " node of " + path + " not found!");
		} catch (Exception e) {
			throw new ConfigException("the index =" + index + " node of " + path + " not found!" + e);
		}

		List<String> valueList = new ArrayList<String>();
		// 逐个Node进行查找
		List<Node> list = this.curNode.selectNodes("./" + nodeName);
		// 遍历查找到的node list
		Iterator<Node> it = list.iterator();
		while (it.hasNext()) {
			Node n = it.next();
			valueList.add(n.getText());
		}
		return valueList;
	}

	/**
	 * 第四类函数，获取子节点的个数 获取path节点的子节点个数.
	 * 
	 * @param path
	 *            String 节点的路径
	 * @return int 子节点个数
	 * @throws ConfigException
	 *             the config exception
	 */
	public int getItemCount(String path) throws ConfigException {
		this.changePath(path);
		return this.getItemCount();
	}

	/**
	 * 获取当前节点的子节点个数.
	 * 
	 * @return int 子节点个数
	 */
	public int getItemCount() {
		try {
			List list = this.curNode.selectNodes("./*");
			if (list != null) {
				return list.size();
			} else {
				return 0;
			}
		} catch (Exception e) {
			System.out.println(" Cofigure getItemCount fail : " + e.toString());
			return 0;
		}
	}

	/**
	 * 第五类函数，获取所有子节点，HashMap的key为子节点的name，value类型String，为子节点的文本 获取path路径的所有子节点.
	 * 
	 * @param path
	 *            String 指定节点的路径
	 * @return HashMap 存放所有子节点name-value对应的Map
	 * @throws ConfigException
	 *             the config exception
	 */
	public HashMap getItem(String path) throws ConfigException {
		changePath(path);
		return getItem();
	}

	/**
	 * 获取当前路径的所有子节点.
	 * 
	 * @return HashMap 存放所有子节点name-value对应的Map
	 * @throws ConfigException
	 *             the config exception
	 */
	public HashMap<String, String> getItem() throws ConfigException {
		List<Node> list = this.curNode.selectNodes("./*");
		HashMap<String, String> map = new HashMap<String, String>();
		Iterator<Node> it = list.iterator();
		while (it.hasNext()) {
			Node node = it.next();
			map.put(node.getName(), node.getText());
		}
		return map;
	};

	/**
	 * 第六类函数获取子节点的名字列表 获取path路径的子节点的名字列表.
	 * 
	 * @param path
	 *            String 指定节点的path
	 * @return List 节点名称列表
	 * @throws ConfigException
	 *             the config exception
	 */
	public List getItemNameList(String path) throws ConfigException {
		changePath(path);
		return getItemNameList();
	}

	/**
	 * 获取当前路径的子节点的名字列表.
	 * 
	 * @return List 节点名称列表
	 * @throws ConfigException
	 *             the config exception
	 */
	public List getItemNameList() throws ConfigException {
		List<Node> list = this.curNode.selectNodes("./*");
		List<String> nameList = new LinkedList<String>();
		Iterator<Node> it = list.iterator();
		while (it.hasNext()) {
			Node node = it.next();
			nameList.add(node.getName());
		}
		return nameList;
	}

	/**
	 * 解析xml配置文件.
	 * 
	 * @param fileName
	 *            String 文件名
	 * @throws ConfigException
	 *             the config exception
	 */
	private void parse_xml(String fileName) throws ConfigException {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(getFile(fileName));
		} catch (Exception e) {
			throw new ConfigException("解析XML文件出错", e);
		}
		this.root = document;
		this.curNode = this.root.getRootElement();
	};

	/**
	 * Parse_xml.
	 * 
	 * @param inputStream
	 *            the input stream
	 * @throws ConfigException
	 *             the config exception
	 */
	private void parse_xml(InputStream inputStream) throws ConfigException {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new InputSource(inputStream));
		} catch (Exception e) {
			throw new ConfigException("解析XML文件出错", e);
		}
		this.root = document;
		this.curNode = this.root.getRootElement();
	};

	/**
	 * Parses the.
	 * 
	 * @param file
	 *            the file
	 * @throws ConfigException
	 *             the config exception
	 */
	public void parse(File file) throws ConfigException {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
		} catch (Exception e) {
			throw new ConfigException("解析XML文件出错", e);
		}
		this.root = document;
		this.curNode = this.root.getRootElement();
	};

	/**
	 * 解析XML字符串.
	 * 
	 * @param xmlStr
	 *            String 字符串
	 * @throws ConfigException
	 *             the config exception
	 */
	private void parse_xml_str(String xmlStr) throws ConfigException {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new StringReader(xmlStr));
		} catch (Exception e) {
			throw new ConfigException("解析XML字符串出错", e);
		}
		this.root = document;
		this.curNode = this.root.getRootElement();
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

}
