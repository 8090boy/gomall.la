/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.model.entity.SensitiveWord;
import com.legendshop.business.dao.SensitiveWordDao;
/**
 * The Class SensitiveWordDaoImpl.
 */

public class SensitiveWordDaoImpl extends BaseDaoImpl implements SensitiveWordDao {
    private static Logger log = LoggerFactory.getLogger(SensitiveWordDaoImpl.class);
     
    public List<SensitiveWord> getSensitiveWord(String userName){
   		return findByHQL("from SensitiveWord where userName = ?", userName);
    }

	public SensitiveWord getSensitiveWord(Long id){
		return get(SensitiveWord.class, id);
	}
	
    public void deleteSensitiveWord(SensitiveWord sensitiveWord){
    	delete(sensitiveWord);
    }
	
	public Long saveSensitiveWord(SensitiveWord sensitiveWord){
		return (Long)save(sensitiveWord);
	}
	
	public void updateSensitiveWord(SensitiveWord sensitiveWord){
		 update(sensitiveWord);
	}
	
	public PageSupport getSensitiveWord(CriteriaQuery cq){
		return find(cq);
	}

	@Override
	public List<String> getWords(Long sortId, Long nsortId,Long subNsortId) {
		List<String> list=findByHQL("select words from SensitiveWord where sortId = ? and nsortId = ? and subNsortId = ?",sortId,nsortId,subNsortId);
		return list;
	}

	
 }
