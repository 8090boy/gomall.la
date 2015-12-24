package com.legendshop.business;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
 
import com.legendshop.core.AttributeKeys;
import com.legendshop.core.tag.TableCache;
import com.legendshop.event.processor.BaseProcessor;
import com.legendshop.spi.service.SystemParameterService;

public class SysInitProcessor extends BaseProcessor<ServletContextEvent> {

	/** The system parameter service. */
	private SystemParameterService systemParameterService;

	/** The code tables cache. */
	private TableCache codeTablesCache;

	
	@Override
	public void process(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		// Load System Parameter
		systemParameterService.initSystemParameter();
		codeTablesCache.initCodeTablesCache();
		servletContext.setAttribute("LEGENDSHOP_DOMAIN_NAME", AttributeKeys.LEGENDSHOP_DOMAIN_NAME);

	}

	 
	public void setSystemParameterService(SystemParameterService systemParameterService) {
		this.systemParameterService = systemParameterService;
	}

	 
	public void setCodeTablesCache(TableCache codeTablesCache) {
		this.codeTablesCache = codeTablesCache;
	}

	public TableCache getCodeTablesCache() {
		return codeTablesCache;
	}

	
}
