/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import java.util.List;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.SensitiveWord;

/**
 * The Class SensitiveWordService.
 */
public interface SensitiveWordService  {

    public List<SensitiveWord> getSensitiveWord(String userName);

    public SensitiveWord getSensitiveWord(Long id);
    
    public void deleteSensitiveWord(SensitiveWord sensitiveWord);
    
    public Long saveSensitiveWord(SensitiveWord sensitiveWord);

    public void updateSensitiveWord(SensitiveWord sensitiveWord);

    public PageSupport getSensitiveWord(CriteriaQuery cq);
    
    
    /**
     * 过滤方法
     * @param src 要过滤的语句
     * @param sensitiveWordList 敏感词words
     */
    public String newcontainSensitiveWords(String src,Long sortId, Long nsortId, Long subNsortId);
}
