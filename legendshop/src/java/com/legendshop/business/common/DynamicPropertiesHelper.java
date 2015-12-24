/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.common;

import java.util.List;

import com.legendshop.model.dynamic.Item;
import com.legendshop.model.dynamic.Model;
import com.legendshop.model.dynamic.ModelType;
import com.legendshop.util.AppUtils;

/**
 * The Class DynamicPropertiesHelper.
 */
public class DynamicPropertiesHelper {


	/**
	 * Gerenate html.
	 * 
	 * @param modelList
	 *            the model list
	 * @return the string
	 */
	public String gerenateHTML(List<Model> modelList) {
		StringBuffer sb = new StringBuffer();
		if (AppUtils.isNotBlank(modelList)) {
			for (Model model : modelList) {
				if (ModelType.Select.name().equals(model.getType())) {
					generateSelect(model, sb);
				} else if (ModelType.Text.name().equals(model.getType())) {
					generateText(model, sb);
				} else if (ModelType.CheckBox.name().equals(model.getType())) {
					generateCheckBox(model, sb);
				} else if (ModelType.Radio.name().equals(model.getType())) {
					generateRadio(model, sb);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Generate select.
	 * 
	 * @param model
	 *            the model
	 * @param sb
	 *            the sb
	 * @return the string buffer
	 */
	public StringBuffer generateSelect(Model model, StringBuffer sb) {
		if (ModelType.Select.name().equals(model.getType())) {
			sb.append(model.getId()).append("&nbsp;<select id='").append(model.getId()).append("' class='attrselect'")
					.append(" name='").append(model.getId()).append("'>");
			sb.append("<option value=''>请选择</option>");
			for (Item item : model.getItems()) {
				sb.append("<option value='").append(item.getKey()).append("'>").append(item.getKey()).append("</option>");
			}
			sb.append("</select><br>");
		}
		return sb;
	}

	/**
	 * Generate text.
	 * 
	 * @param model
	 *            the model
	 * @param sb
	 *            the sb
	 * @return the string buffer
	 */
	public StringBuffer generateText(Model model, StringBuffer sb) {
		if (ModelType.Text.name().equals(model.getType())) {
			if (AppUtils.isNotBlank(model.getItems())) {
				sb.append("<tr><th>").append(model.getId()).append("</th><td>").append(model.getItems()[0].getValue())
						.append("</td></tr>");
			} else {
				sb.append("<div>").append(model.getId()).append("<input id='").append(model.getId()).append("' class='attrtext'")
						.append(" name='").append(model.getId()).append(" value='").append("value").append("'/></div>");
			}

		}
		return sb;
	}

	/**
	 * Generate radio.
	 * 
	 * @param model
	 *            the model
	 * @param sb
	 *            the sb
	 * @return the string buffer
	 */
	public StringBuffer generateRadio(Model model, StringBuffer sb) {
		if (ModelType.Radio.name().equals(model.getType())) {
			sb.append(model.getId()).append("&nbsp;");
			for (Item item : model.getItems()) {
				sb.append(item.getKey()).append("<input type='radio' id='").append(model.getId()).append("' class='attrradio'")
						.append(" name='").append(model.getId()).append("' value='").append(item.getKey()).append("'/>&nbsp;");
			}
			sb.append("<br>");
		}
		return sb;
	}

	/**
	 * Generate check box.
	 * 
	 * @param model
	 *            the model
	 * @param sb
	 *            the sb
	 * @return the string buffer
	 */
	public StringBuffer generateCheckBox(Model model, StringBuffer sb) {
		if (ModelType.CheckBox.name().equals(model.getType())) {
			sb.append(model.getId()).append("&nbsp;");
			for (Item item : model.getItems()) {
				sb.append(item.getKey()).append("<input type='checkbox' id='").append(item.getKey()).append("' class='attrchx'")
						.append("' name='").append(model.getId()).append("'/>&nbsp;");
			}
			sb.append("<br>");
		}
		return sb;
	}
}
