/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;
 
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.legendshop.business.dao.ProductPropertyValueDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.ProductPropertyValue;
import com.legendshop.util.AppUtils;
/**
 * The Class ProductPropertyValueDaoImpl.
 */

public class ProductPropertyValueDaoImpl extends BaseDaoImpl implements ProductPropertyValueDao {
    private static Logger log = LoggerFactory.getLogger(ProductPropertyValueDaoImpl.class);
     
    public List<ProductPropertyValue> getProductPropertyValue(String userName){
   		return findByHQL("from ProductPropertyValue where userName = ?", userName);
    }

	public ProductPropertyValue getProductPropertyValue(Long id){
		return get(ProductPropertyValue.class, id);
	}
	
    public void deleteProductPropertyValue(ProductPropertyValue productPropertyValue){
    	delete(productPropertyValue);
    }
	
	public void saveProductPropertyValue(ProductPropertyValue productPropertyValue){
		MultipartFile formFile = productPropertyValue.getFile();// 取得上传的文件
		boolean uploadFile = fileUploaded(formFile);
		String orginPic = null; // 记住当前路径用来删除
		if(AppUtils.isNotBlank(productPropertyValue.getName()) && AppUtils.isNotBlank(productPropertyValue.getFile())){
			if(AppUtils.isNotBlank(productPropertyValue.getId())){//update
				ProductPropertyValue origin = this.get(ProductPropertyValue.class, productPropertyValue.getId());
				if(origin != null){
					origin.setModifyDate(new Date());
					origin.setName(productPropertyValue.getName());
					origin.setPic(productPropertyValue.getPic());
					origin.setSequence(productPropertyValue.getSequence());
					update(origin);
				}
			}else{//save
				Date date = new Date();
				productPropertyValue.setModifyDate(date);
				productPropertyValue.setRecDate(date);
			    save(productPropertyValue);
			}
		}
	}
	
	
	public void updateProductPropertyValue(ProductPropertyValue productPropertyValue){
		 update(productPropertyValue);
	}
	
	public PageSupport getProductPropertyValue(CriteriaQuery cq){
		return find(cq);
	}
	
	/**
	 * File uploaded. 是否上传了文件
	 * 
	 * @param formFile
	 *            the form file
	 * @return true, if successful
	 */
	private boolean fileUploaded(MultipartFile formFile) {
		return formFile != null && formFile.getSize() > 0;
	}
	
 }
