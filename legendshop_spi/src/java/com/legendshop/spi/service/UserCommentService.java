/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.UserComment;

/**
 * The Interface UserCommentService.
 */
public interface UserCommentService {

	/**
	 * Query user comment.
	 * 
	 * @param cq
	 *            the cq
	 * @return the page support
	 */
	public abstract PageSupport getUserCommentList(CriteriaQuery cq);

	/**
	 * Query user comment.
	 * 
	 * @param id
	 *            the id
	 * @return the user comment
	 */
	public abstract UserComment getUserComment(Long id);

	/**
	 * Delete.
	 * 
	 * @param userComment
	 *            the user comment
	 */
	public abstract void delete(UserComment userComment);

	/**
	 * Update user comment to readed.
	 * 
	 * @param comment
	 *            the comment
	 */
	public abstract void updateUserCommentToReaded(UserComment comment);

	/**
	 * Save or update user comment.
	 * 
	 * @param comment
	 *            the comment
	 */
	public abstract void saveOrUpdateUserComment(UserComment comment);

	/**
	 * 更新用户评论
	 *
	 * @param id the id
	 * @param answer the answer
	 * @param loginName the login name
	 * @return true, if successful
	 */
	public abstract boolean updateUserComment(Long id, String answer, String loginName);

}