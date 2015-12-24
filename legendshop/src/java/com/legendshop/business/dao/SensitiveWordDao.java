/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao;
 
import com.legendshop.core.dao.BaseDao;
import java.util.List;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.SensitiveWord;

/**
 * The Class SensitiveWordDao.
 */

public interface SensitiveWordDao extends BaseDao {
     
    public abstract List<SensitiveWord> getSensitiveWord(String shopName);

	public abstract SensitiveWord getSensitiveWord(Long id);
	
    public abstract void deleteSensitiveWord(SensitiveWord sensitiveWord);
	
	public abstract Long saveSensitiveWord(SensitiveWord sensitiveWord);
	
	public abstract void updateSensitiveWord(SensitiveWord sensitiveWord);
	
	public abstract PageSupport getSensitiveWord(CriteriaQuery cq);
	/**
	 * 通过一二三级的id查找敏感词表里的敏感词words，string word
	 * @param sortId
	 * @param nsortId
	 * @param subNsortId
	 * @return
	 */
	public abstract List<String> getWords(Long sortId, Long nsortId, Long subNsortId);
 }
