/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.LocaleResolver;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.Selectable;
import com.legendshop.core.dao.BaseDao;
import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.util.AppUtils;
import com.legendshop.util.ContextServiceLocator;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class OptionGroupTag extends TagSupport {

	/** The log. */
	private static Log log = LogFactory.getLog(OptionGroupTag.class);

	/** The Constant RADIO. */
	private final static String RADIO = "radio";

	/** The Constant LABEL. */
	private final static String LABEL = "label";

	/** The Constant CHECKBOX. */
	private final static String CHECKBOX = "checkbox";

	/** The Constant JSON. */
	private final static String JSON = "json";

	/** The Constant SELECT. */
	private final static String SELECT = "select";

	/** The index. */
	private int index = 0;

	/** The item num. */
	private int itemNum = 0;

	/** The name. */
	private String name;

	/** The type. */
	private String type;

	/** The selected value. */
	private String selectedValue;

	/** The required. */
	private boolean required;

	/** The cols. */
	private int cols = 0;

	/** The cache. */
	private boolean cache;

	/** The bean name. */
	private String beanName;

	/** The bean id. */
	private String beanId;

	/** The bean disp. */
	private String beanDisp;

	/** The hql. */
	private String hql;

	/** The sql. */
	private String sql;

	/** The param. */
	private Object param;

	/** The params. */
	private Object[] params;

	/** The default disp. */
	private String defaultDisp = "-- 请选择 --";

	/** The disabled. */
	private boolean disabled = false;

	/** The checkbox values. */
	private String[] checkboxValues;

	/** The exclude. */
	private String exclude;

	/** The excludes. */
	private String[] excludes;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -518291441012821104L;

	private final BaseDao baseDao = (BaseDao) ContextServiceLocator.getInstance().getBean("tagLibDao");

	private final LocaleResolver localeResolver = (LocaleResolver) ContextServiceLocator.getInstance().getBean(
			AttributeKeys.LOCALE_RESOLVER);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() {
		if (!SELECT.equalsIgnoreCase(type) && !LABEL.equalsIgnoreCase(type) && !RADIO.equalsIgnoreCase(type)
				&& !CHECKBOX.equalsIgnoreCase(type) && !JSON.equalsIgnoreCase(type)) {
			log.error("type \"" + type + "\" is unknown!");
			return TagSupport.SKIP_BODY;
		}
		index = 0;
		itemNum = 0;
		StringBuilder sb = new StringBuilder();
		if (!required) {
			if (SELECT.equalsIgnoreCase(type)) {
				sb.append("<option value=\"\">").append(defaultDisp).append("</option> ");
			} else if (JSON.equalsIgnoreCase(type)) {
				sb.append("{'value':'', 'text':'").append(defaultDisp).append("'},");
			}
		}
		if (RADIO.equalsIgnoreCase(type) || CHECKBOX.equalsIgnoreCase(type)) {
			if (name == null || name.trim().length() == 0) {
				log.error("name is required for radio group!");
				return TagSupport.SKIP_BODY;
			}
			if (CHECKBOX.equalsIgnoreCase(type)) {
				if (StringUtils.isNotEmpty(selectedValue)) {
					checkboxValues = selectedValue.split(",");
				} else {
					checkboxValues = null;
				}
			}
		}

		if (StringUtils.isNotEmpty(exclude)) {
			excludes = exclude.split(",");
		} else {
			excludes = null;
		}

		if (cache) {
			if (AppUtils.isBlank(beanId) && AppUtils.isBlank(beanName)) {
				log.error("beanName is required in cache mode!");
				return TagSupport.SKIP_BODY;
			}
			loadDataFromCache(sb);
		} else {
			List<Object[]> list = null;
			if (sql != null) {
				list = baseDao.findBySQL(sql);
			} else {
				if (hql == null || this.hql.trim().length() == 0) {
					if (beanName == null || beanName.trim().length() == 0 || beanId == null || beanId.trim().length() == 0
							|| beanDisp == null || beanDisp.trim().length() == 0) {
						log.error("beanId and beanDisp is requied if data is not cached!");
						return TagSupport.SKIP_BODY;
					}
					hql = new StringBuilder("select t.").append(beanId).append(", t.").append(beanDisp).append(" from ")
							.append(beanName).append(" t order by t.").append(beanId).toString();
				}
				try {
					if (params != null) {
						list = baseDao.findByHQL(hql, this.params);
					} else if (param != null) {
						list = baseDao.findByHQL(hql, this.param);
					} else {
						list = baseDao.findByHQL(hql);
					}
				} catch (Exception e) {
					log.error("get data throgh hql " + hql, e);
				}

			}

			if (list != null) {
				if ((RADIO.equalsIgnoreCase(type) || CHECKBOX.equalsIgnoreCase(type)) && cols == 0) {
					setItemsPerLine(list.size());
				}
				for (Object[] objArray : list) {
					boolean isExclude = false;
					if (!AppUtils.isBlank(excludes)) {
						for (String ex : excludes) {
							if (ex.equals(String.valueOf(objArray[0]))) {
								isExclude = true;
								break;
							}
						}
					}
					if (!isExclude) {
						this.addOption(sb, objArray[0], objArray[1]);
					}

				}
			}
		}

		try {
			if (sb.length() != 0) {
				pageContext.getOut().println(sb.substring(0, sb.length() - 1));
			}
			if (LABEL.equalsIgnoreCase(type) && sb.length() == 0 && defaultDisp != null) {
				pageContext.getOut().println(defaultDisp);
			}
		} catch (IOException e) {
			log.error("IOException in SelectTag: ", e);
		}
		return TagSupport.SKIP_BODY;
	}
	
	/**
	 * 从Spring或者CodeTable中取值
	 * @param sb
	 */
	private void loadDataFromCache(StringBuilder sb){
		Map<String, String> codeTable = null;
		if(AppUtils.isNotBlank(beanId)){
			//如果定义了beanId, 从Spring定义中取值
			Map<String, Selectable> selectable = (Map<String, Selectable>) ContextServiceLocator.getInstance().getBean(beanId);
			if(AppUtils.isNotBlank(selectable)){
				codeTable = new HashMap<String, String>(selectable.size());
				for (String key : selectable.keySet()) {
					codeTable.put(key, selectable.get(key).getName());
				}
			}
		}else if(AppUtils.isNotBlank(beanName)){
			//如果定义了beanName, 从Spring定义中取值
			TableCache codeTablesCache = (TableCache) ContextServiceLocator.getInstance().getBean("codeTablesCache");
			codeTable = codeTablesCache.getCodeTable(beanName);
		}

		if (codeTable != null) {
			if ((RADIO.equalsIgnoreCase(type) || CHECKBOX.equalsIgnoreCase(type)) && cols == 0) {
				setItemsPerLine(codeTable.size());
			}
			for (Entry<String, String> entry : codeTable.entrySet()) {
				boolean isExclude = false;
				if (!AppUtils.isBlank(excludes)) {
					for (String ex : excludes) {
						if (ex.equals(String.valueOf(entry.getKey()))) {
							isExclude = true;
							break;
						}
					}
				}
				if (!isExclude) {
					this.addOption(sb, entry.getKey(), entry.getValue());
				}
			}
		}
	}

	/**
	 * Adds the option.
	 * 
	 * @param sb
	 *            the sb
	 * @param value
	 *            the value
	 * @param disp
	 *            the disp
	 */
	private void addOption(StringBuilder sb, Object value, Object disp) {
		String dispValue = (String) disp;
		if (dispValue != null && dispValue.startsWith("message:")) {
			String temp = dispValue.substring("message:".length());
			Locale locale = localeResolver.resolveLocale((HttpServletRequest) pageContext.getRequest());
			if (locale != null) {
				disp = ResourceBundleHelper.getString(locale, String.valueOf(temp), String.valueOf(temp));
			} else {
				disp = ResourceBundleHelper.getString(String.valueOf(temp), String.valueOf(temp));
			}
		}
		if (SELECT.equalsIgnoreCase(type)) {
			sb.append("<option value=\"").append(value).append("\"");
			if (String.valueOf(value).equals(selectedValue)) {
				sb.append(" selected ");
			}
			sb.append(">");
			sb.append(disp);
			sb.append("</option> ");
		} else if (RADIO.equalsIgnoreCase(type) || CHECKBOX.equalsIgnoreCase(type)) {
			itemNum++;
			if (itemNum > cols) {
				itemNum = 0;
				sb.append("<br>");
			}
			String radioId = name + (index++);
			sb.append("<span style=\"cursor:hand\" onclick=\"$('").append(radioId)
					.append("').click();\">&nbsp;<input type=\"" + type + "\" value=\"").append(value).append("\" name=\"")
					.append(name).append("\" id=\"").append(radioId).append("\"");

			if (RADIO.equalsIgnoreCase(type)) {
				if (String.valueOf(value).equals(selectedValue)) {
					sb.append(" checked ");
				}
			} else {
				if (!AppUtils.isBlank(checkboxValues)) {
					for (String checkboxValue : checkboxValues) {
						if (String.valueOf(value).equals(checkboxValue)) {
							sb.append(" checked ");
							break;
						}
					}
				}
			}

			if (disabled) {
				sb.append(" disabled ");
			}
			sb.append("/>").append(disp).append("&nbsp;</span> ");
		} else if (JSON.equalsIgnoreCase(type)) {
			sb.append("{'value':'").append(value).append("', 'text':'").append(disp).append("'},");
		} else if (LABEL.equalsIgnoreCase(type)) {
			if (String.valueOf(value).equals(selectedValue)) {
				sb.append(disp).append(" ");
			}
		}
	}

	/**
	 * Sets the items per line.
	 * 
	 * @param totalNum
	 *            the new items per line
	 */
	private void setItemsPerLine(int totalNum) {
		this.cols = totalNum;
	}

	/**
	 * Gets the sql.
	 * 
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * Sets the sql.
	 * 
	 * @param sql
	 *            the new sql
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the bean name.
	 * 
	 * @param beanName
	 *            the new bean name
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	/**
	 * Sets the selected value.
	 * 
	 * @param selectedValue
	 *            the new selected value
	 */
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	/**
	 * Sets the required.
	 * 
	 * @param required
	 *            the new required
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Sets the cols.
	 * 
	 * @param cols
	 *            the new cols
	 */
	public void setCols(int cols) {
		this.cols = cols;
	}

	/**
	 * Sets the bean disp.
	 * 
	 * @param beanDisp
	 *            the new bean disp
	 */
	public void setBeanDisp(String beanDisp) {
		this.beanDisp = beanDisp;
	}

	/**
	 * Sets the bean id.
	 * 
	 * @param beanId
	 *            the new bean id
	 */
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	/**
	 * Sets the cache.
	 * 
	 * @param cache
	 *            the new cache
	 */
	public void setCache(boolean cache) {
		this.cache = cache;
	}

	/**
	 * Sets the hql.
	 * 
	 * @param hql
	 *            the new hql
	 */
	public void setHql(String hql) {
		this.hql = hql;
	}

	/**
	 * Sets the param.
	 * 
	 * @param param
	 *            the new param
	 */
	public void setParam(Object param) {
		this.param = param;
	}

	/**
	 * Sets the params.
	 * 
	 * @param params
	 *            the new params
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}

	/**
	 * Sets the default disp.
	 * 
	 * @param defaultDisp
	 *            the new default disp
	 */
	public void setDefaultDisp(String defaultDisp) {
		this.defaultDisp = defaultDisp;
	}

	/**
	 * Sets the disabled.
	 * 
	 * @param disabled
	 *            the new disabled
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * Sets the exclude.
	 * 
	 * @param exclude
	 *            the new exclude
	 */
	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

}