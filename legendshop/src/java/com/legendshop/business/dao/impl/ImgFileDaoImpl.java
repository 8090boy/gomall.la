/**
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */

package com.legendshop.business.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.legendshop.business.dao.ImgFileDao;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.entity.ImgFile;
import com.legendshop.model.entity.Indexjpg;
import com.legendshop.util.AppUtils;

/**
 * 获取图片Dao.
 */
@SuppressWarnings("unchecked")
public class ImgFileDaoImpl extends BaseDaoImpl implements ImgFileDao {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(ImgFileDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ImgFileDao#getIndexJpeg(java.lang.String
	 * )
	 */
	@Override
	@Cacheable(value = "IndexjpgList", key = "#userName")
	public List<Indexjpg> getIndexJpeg(final String userName) {
		log.debug("getIndexJpeg, userName = {}", userName);
		String defaultShopName = PropertiesUtil.getObject(SysParameterEnum.DEFAULT_SHOP, String.class);
		if (AppUtils.isBlank(userName)) {
			return findByHQLLimit("from Indexjpg where status = 1 and  userName = ? order by seq asc", 0, 7, defaultShopName);
		}else{
			return findByHQLLimit("from Indexjpg where status = 1 and userName = ? OR userName = ? order by seq asc", 0, 7, userName,defaultShopName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ImgFileDao#getProductPics(java.lang.
	 * String, java.lang.Long)
	 */
	@Override
	@Cacheable(value = "ImgFileList", key = "#userName + #prodId")
	public List<ImgFile> getProductPics(final String userName, final Long prodId) {
		return findByHQL("from ImgFile where productType = 1 and status = 1 and  userName = ? and productId = ?", userName, prodId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ImgFileDao#deleteImgFileById(java.lang.Class,
	 * java.lang.Long)
	 */
	@Override
	@CacheEvict(value = "ImgFile", key = "#fileId")
	public void deleteImgFileById(Long fileId) {
		deleteById(ImgFile.class, fileId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ImgFileDao#updateImgFile(com.legendshop.model
	 * .entity.ImgFile)
	 */
	@Override
	@CacheEvict(value = "ImgFile", key = "#imgFile.fileId")
	public void updateImgFile(ImgFile imgFile) {
		update(imgFile);
	}

	@Override
	@CacheEvict(value = "ImgFile", key = "#imgFile.fileId")
	public void deleteImgFile(ImgFile imgFile) {
		delete(imgFile);
	}

	@Override
	public List<ImgFile> getAllProductPics( Long prodId) {
		return findByHQL("from ImgFile where productType = 1 and  productId = ?",  prodId);
	}

}
