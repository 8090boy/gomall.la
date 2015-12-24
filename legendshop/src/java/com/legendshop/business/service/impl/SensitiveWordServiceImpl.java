/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.spi.service.SensitiveWordService;
import com.legendshop.util.AppUtils;
import com.legendshop.business.dao.SensitiveWordDao;
import com.legendshop.model.entity.SensitiveWord;

/**
 * The Class SensitiveWordServiceImpl.
 */
public class SensitiveWordServiceImpl  implements SensitiveWordService{
    private SensitiveWordDao sensitiveWordDao;

    public void setSensitiveWordDao(SensitiveWordDao sensitiveWordDao) {
        this.sensitiveWordDao = sensitiveWordDao;
    }

    public List<SensitiveWord> getSensitiveWord(String userName) {
        return sensitiveWordDao.getSensitiveWord(userName);
    }

    public SensitiveWord getSensitiveWord(Long id) {
        return sensitiveWordDao.getSensitiveWord(id);
    }

    public void deleteSensitiveWord(SensitiveWord sensitiveWord) {
        sensitiveWordDao.deleteSensitiveWord(sensitiveWord);
    }

    public Long saveSensitiveWord(SensitiveWord sensitiveWord) {
        if (!AppUtils.isBlank(sensitiveWord.getSensId())) {
            updateSensitiveWord(sensitiveWord);
            return sensitiveWord.getSensId();
        }
        return (Long) sensitiveWordDao.save(sensitiveWord);
    }

    public void updateSensitiveWord(SensitiveWord sensitiveWord) {
        sensitiveWordDao.updateSensitiveWord(sensitiveWord);
    }

    public PageSupport getSensitiveWord(CriteriaQuery cq) {
        return sensitiveWordDao.find(cq);
    }
    
    /**
     * 
     * @param src 要过滤的语句
     */
	public String newcontainSensitiveWords(String src,Long sortId, Long nsortId, Long subNsortId) {
		List<String> sensitiveWordList=sensitiveWordDao.getWords(sortId, nsortId, subNsortId);
		 Map<Character,List<String>> wordMap=new HashMap<Character,List<String>>();
	        for (String s:sensitiveWordList){
	            char c=s.charAt(0);
	            List<String> strs=wordMap.get(c);
	            if (strs==null){
	                strs=new ArrayList<String>();
	                wordMap.put(c,strs);
	            }
	            strs.add(s);
	        }
	        String temp=null;
	        StringBuilder strb=new StringBuilder();
	        StringBuilder findwords=new StringBuilder();
	        for (int i=0;i<src.length();i++){
	            char c=src.charAt(i);
	            String find=null;
	            if (wordMap.containsKey(c)){
	                List<String> words=wordMap.get(c);
	                for (String s:words){
	                    temp=src.substring(i,(s.length()<=(src.length()-i))?i+s.length():i);
	                    if (s.equals(temp)){
	                        find=s;
	                        break;
	                    }
	                }
	            }
	            if (find!=null){           	
//	                strb.append("***");
	            	findwords.append(find);
	                i+=(find.length()-1);  
	            } else {
	                strb.append(c);
	            }
	        }  
	        
		return findwords.toString();
	}
}
