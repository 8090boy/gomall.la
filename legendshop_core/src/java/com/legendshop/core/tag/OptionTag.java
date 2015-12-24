/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.LocaleResolver;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.UserManager;
import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.entity.NamedEntity;
import com.legendshop.util.AppUtils;
import com.legendshop.util.ContextServiceLocator;

/**
 * The Class OptionTag.
 */
public abstract class OptionTag extends LegendShopTag {

	/** The log. */
	private static Log log = LogFactory.getLog(OptionTag.class);

	/** The id. */
	protected String id;

	/** The default disp. */
	protected String defaultDisp = "-- 请选择 --";

	/** The exclude. */
	protected String exclude;

	/** The locale resolver. */
	protected LocaleResolver localeResolver;

	/** The required. */
	protected boolean required;

	/** The selected value. */
	protected String selectedValue;

	/** The shop name. */
	protected String shopName;

	/** The sort type. */
	protected String type;

	/** The on change. */
	protected String onChange;

	/**
	 * Instantiates a new option tag.
	 */
	public OptionTag() {
		if (localeResolver == null) {
			localeResolver = (LocaleResolver) ContextServiceLocator.getInstance().getBean(AttributeKeys.LOCALE_RESOLVER);
		}
	}

	/**
	 * Do tag.
	 * 
	 * @throws JspException
	 *             the jsp exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		if (AppUtils.isBlank(shopName)) {
			//shopName = ThreadLocalContext.getCurrentShopName(this.request(), this.response());
			shopName = UserManager.getUserName();
		}
		StringBuilder sb = new StringBuilder();
		// start
		if (AppUtils.isNotBlank(onChange)) {
			sb.append("<select id=\"").append(id).append("\" name=\"").append(id).append("\" ");
			sb.append("onChange=\"").append(onChange).append("\">");
		} else {
			sb.append("<select id=\"").append(id).append("\" name=\"").append(id).append("\">");
		}
		if (!required) {
			sb.append("<option value=\"\">").append(defaultDisp).append("</option> ");
		}

		String[] excludes = null;
		if (AppUtils.isNotBlank(exclude)) {
			excludes = exclude.split(",");
		}
		List<?> list = retrieveData(type, shopName);
		if (AppUtils.isNotBlank(list)) {
			for (Object obj : list) {
				NamedEntity entity = (NamedEntity) obj;
				String sortId = String.valueOf(entity.getId());
				boolean isExclude = false;
				if (AppUtils.isNotBlank(excludes)) {
					for (String ex : excludes) {
						if (ex.equals(sortId)) {
							isExclude = true;
							break;
						}
					}
				}
				if (!isExclude) {
					this.addOption(sb, sortId, entity.getName());
				}

			}
		}

		// end
		sb.append("</select>");
		try {
			this.pageContext().getOut().println(sb);
		} catch (IOException e) {
			log.error("IOException in SelectTag: ", e);
		}

	}

	/**
	 * Retrieve data.
	 * 
	 * @param type
	 *            the type
	 * @param userName
	 *            the user name
	 * @return the list
	 */
	public abstract List<?> retrieveData(String type, String userName);

	/**
	 * Adds the option.
	 * 
	 * @param sb
	 *            the sb
	 * @param value
	 *            the value
	 * @param dispValue
	 *            the disp value
	 */
	private void addOption(StringBuilder sb, String value, String dispValue) {
		if (dispValue != null && dispValue.startsWith("message:")) {
			String temp = dispValue.substring("message:".length());
			Locale locale = localeResolver.resolveLocale(this.request());
			if (locale != null) {
				dispValue = ResourceBundleHelper.getString(locale, String.valueOf(temp), temp);
			} else {
				dispValue = ResourceBundleHelper.getString(String.valueOf(temp), temp);
			}
		}
		sb.append("<option value=\"").append(value).append("\"");
		if (value.equals(selectedValue)) {
			sb.append(" selected ");
		}
		sb.append(">");
		sb.append(dispValue);
		sb.append("</option>");
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the default disp.
	 * 
	 * @return the default disp
	 */
	public String getDefaultDisp() {
		return defaultDisp;
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
	 * Gets the exclude.
	 * 
	 * @return the exclude
	 */
	public String getExclude() {
		return exclude;
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

	/**
	 * Gets the locale resolver.
	 * 
	 * @return the locale resolver
	 */
	public LocaleResolver getLocaleResolver() {
		return localeResolver;
	}

	/**
	 * Sets the locale resolver.
	 * 
	 * @param localeResolver
	 *            the new locale resolver
	 */
	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	/**
	 * Checks if is required.
	 * 
	 * @return true, if is required
	 */
	public boolean isRequired() {
		return required;
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
	 * Gets the selected value.
	 * 
	 * @return the selected value
	 */
	public String getSelectedValue() {
		return selectedValue;
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
	 * Gets the shop name.
	 * 
	 * @return the shop name
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * Sets the shop name.
	 * 
	 * @param shopName
	 *            the new shop name
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
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
	 * Gets the on change.
	 * 
	 * @return the on change
	 */
	public String getOnChange() {
		return onChange;
	}

	/**
	 * Sets the on change.
	 * 
	 * @param onChange
	 *            the new on change
	 */
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

}
