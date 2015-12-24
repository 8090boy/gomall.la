/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.exception;

/**
 * 定义系统错误变量.
 */
public interface ErrorCodes {

	/** The normal stauts. 系统正常状态*/
	public String NORMAL_STAUTS = "200";

	/** The system error. 系统错误*/
	public String SYSTEM_ERROR = "999";

	/** The business error. 业务错误*/
	public String BUSINESS_ERROR = "998";
	
	/** The system uninstalled. 系统尚未安装*/
	public String SYSTEM_UNINSTALLED = "901";
	
	/** Resource conflict. 资源冲突*/
	public String RESOURCE_CONFLICT = "409";

	/** The internal error. 内部错误 */
	public String INTERNAL_ERROR = "500";

	/** The unauthorized. 未授权*/
	public String UNAUTHORIZED = "401";
	
	/** The insufficient permissions.  权限不足 */
	public String INSUFFICIENT_PERMISSIONS = "402";
	
	/** The entity no found.  找不到记录*/
	public String ENTITY_NO_FOUND = "404";
	
	/** Parameter cannot be empty， 参数不能为空. */
	public String NON_NULLABLE = "405";
	
	/** Request scope does not meet the requirements.  请求范围不符合要求 */
	public String LIMITATION_ERROR = "416";

	/** The auditing.  审核中*/
	public String AUDITING = "502";

	/** The time out. 系统超时 */
	public String TIME_OUT = "503";

	/** The save error. 保存异常*/
	public String SAVE_ERROR = "601";

	/** The update error. 更新异常 */
	public String UPDATE_ERROR = "602";
	
	/** The invalid token. 无效令牌*/
	public String INVALID_TOKEN = "603";

	/** The invalid format. 无效格式*/
	public String INVALID_FORMAT = "604";

	/** The not enough score.  积分不够*/
	public String NOT_ENOUGH_SCORE = "605";

	/** The not enough stocks. 库存不足*/
	public String NOT_ENOUGH_STOCKS = "606";

}
