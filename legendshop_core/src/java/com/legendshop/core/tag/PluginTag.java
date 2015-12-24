/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import javax.servlet.jsp.tagext.TagSupport;

import com.legendshop.plugins.PluginManager;
import com.legendshop.util.handler.PluginRepository;

/**
 * The Class PluginTag. 检查Plugin是否在运行
 */
public class PluginTag extends TagSupport {

	private static final long serialVersionUID = -8943927608529578818L;
	/** The key. */
	private String pluginId;

	private PluginManager pluginManager;

	public PluginTag() {
		pluginManager =PluginRepository.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() {
		if (pluginManager.isPluginRunning(pluginId)) {
			return TagSupport.EVAL_BODY_INCLUDE;
		} else {
			return TagSupport.SKIP_BODY;
		}
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

}
