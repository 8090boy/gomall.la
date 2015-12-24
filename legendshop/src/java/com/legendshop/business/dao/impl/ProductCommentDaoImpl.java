/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.legendshop.business.dao.ProductCommentDao;
import com.legendshop.business.dao.SubDao;
import com.legendshop.core.OperationTypeEnum;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.event.FireEvent;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.event.EventHome;
import com.legendshop.model.entity.ProductComment;
import com.legendshop.model.entity.ProductCommentCategory;
import com.legendshop.spi.constants.Constants;
import com.legendshop.util.AppUtils;
import com.legendshop.util.Arith;

/**
 * 产品评论Dao.
 */
public class ProductCommentDaoImpl extends BaseDaoImpl implements ProductCommentDao {

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;
	
	private SubDao subDao;

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(ProductCommentDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductCommentDao#deleteProductComment(java
	 * .lang.Long, java.lang.String)
	 */
	@Override
	public void deleteProductComment(Long prodId, String userName) {
		exeByHQL("delete from ProductComment where prodId = ? and ownerName = ?", new Object[] { prodId, userName });

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductCommentDao#saveProductComment(com.
	 * legendshop.model.entity.ProductComment)
	 */
	@Override
	public void saveProductComment(ProductComment productComment) {
		save(productComment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductCommentDao#updateProductComment(com
	 * .legendshop.model.entity.ProductComment)
	 */
	@Override
	public void updateProductComment(ProductComment productComment) {
		EventHome.publishEvent(new FireEvent(productComment, OperationTypeEnum.UPDATE));
		update(productComment);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductCommentDao#deleteProductCommentById
	 * (java.lang.Long)
	 */
	@Override
	public void deleteProductCommentById(Long id) {
		ProductComment productComment = get(ProductComment.class, id);
		if (productComment != null) {
			EventHome.publishEvent(new FireEvent(productComment, OperationTypeEnum.DELETE));
			delete(productComment);
		}

	}
	

	// 用户是否可以发表商品评论
	@Override
	public String validateComment(Long prodId, String userName) {
		String level = PropertiesUtil.getObject(SysParameterEnum.COMMENT_LEVEL, String.class);
		if (Constants.LOGONED_COMMENT.equals(level)) {
			if (AppUtils.isBlank(userName)) {
				return "NOLOGON"; // 没有登录
			}
		} else if (Constants.BUYED_COMMENT.equals(level)) {
			if (AppUtils.isBlank(userName)) {
				return "NOLOGON"; // 没有登录
			}
			if (!subDao.isUserOrderProduct(prodId, userName)) {
				return "NOBUYED";
			}
		} else if (Constants.NO_COMMENT.equals(level)) {
			return "NOCOMMENT";
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductCommentDao#initProductCommentCategory
	 * (java.lang.Long)
	 */
	@Override
	public ProductCommentCategory initProductCommentCategory(Long prodId) {
		ProductCommentCategory pcc = new ProductCommentCategory();
		String sql = "select score, count(*) as result from ls_prod_comm where  prod_id = ? group by score";
		log.debug("initProductCommentCategory run sql {}, prodId = {}", sql, prodId);
		List<ScoreCategory> result = jdbcTemplate.query(sql, new Object[] { prodId }, new ProductCommentCategoryRowMapper());
		if (AppUtils.isNotBlank(result)) {
			for (ScoreCategory scoreCategory : result) {
				if (AppUtils.isBlank(scoreCategory.getSocre()) || scoreCategory.getSocre() == 0) {
					// 默认作为中评
					pcc.setMedium(pcc.getMedium() + scoreCategory.getResult());
				} else {
					// score : 0 -5
					if (scoreCategory.getSocre() >= 4) {
						pcc.setHigh(pcc.getHigh() + scoreCategory.getResult());
					} else if (scoreCategory.getSocre() >= 3) {
						pcc.setMedium(pcc.getMedium() + scoreCategory.getResult());
					} else {
						pcc.setLow(pcc.getLow() + scoreCategory.getResult());
					}
				}

			}
		}
		int total = pcc.getHigh() + pcc.getMedium() + pcc.getLow();
		if (total > 0) {
			pcc.setTotal(total);
			pcc.setHighRate(Arith.div(pcc.getHigh(), total));
			pcc.setMediumRate(Arith.div(pcc.getMedium(), total));
			pcc.setLowRate(Arith.div(pcc.getLow(), total));
		}
		pcc.setProdId(prodId);
		return pcc;
	}

	class ProductCommentCategoryRowMapper implements RowMapper<ScoreCategory> {

		@Override
		public ScoreCategory mapRow(ResultSet rs, int arg1) throws SQLException {
			ScoreCategory sc = new ScoreCategory();
			sc.setSocre(rs.getInt("score"));
			sc.setResult(rs.getInt("result"));
			return sc;
		}

	}

	class ScoreCategory implements Serializable {

		private static final long serialVersionUID = 8821497685050946872L;

		private Integer socre;

		private Integer result;

		/**
		 * @return the socre
		 */
		public Integer getSocre() {
			return socre;
		}

		/**
		 * @param socre
		 *            the socre to set
		 */
		public void setSocre(Integer socre) {
			this.socre = socre;
		}

		/**
		 * @return the result
		 */
		public Integer getResult() {
			return result;
		}

		/**
		 * @param result
		 *            the result to set
		 */
		public void setResult(Integer result) {
			this.result = result;
		}
	}

	/**
	 * Sets the jdbc template.
	 * 
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @param subDao the subDao to set
	 */
	public void setSubDao(SubDao subDao) {
		this.subDao = subDao;
	}

}
