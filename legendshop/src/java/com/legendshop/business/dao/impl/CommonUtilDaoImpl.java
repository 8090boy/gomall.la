/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.util.List;

import com.legendshop.business.dao.CommonUtilDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.model.entity.UserRole;
import com.legendshop.model.entity.UserRoleId;

/**
 * The Class CommonUtilDaoImpl.
 */
public class CommonUtilDaoImpl extends BaseDaoImpl implements CommonUtilDao {

	/** The admin right sql. */
	private static String adminRightSQL = "select id from ls_role where name = 'ROLE_ADMIN'";

	/** The user right sql. */
	private static String userRightSQL = "select id from ls_role where name = 'ROLE_USER'";

	/**
	 * 删除权限
	 */
	@Override
	public void removeRole(List<String> roles, String userId) {
		if(roles != null){
			for (String roleId : roles) {
				UserRole ur = new UserRole();
				UserRoleId id = new UserRoleId();
				id.setRoleId(roleId);
				id.setUserId(userId);
				ur.setId(id);
				delete(ur);
			}
		}
	}
	/**
	 * 删除用户所有的权限
	 * @param userId
	 */
	public void removeAllRole(String userId) {
		super.exeByHQL("delete from UserRole where id.userId = ?", userId);
	}

	/**
	 * Save role.
	 * 
	 * @param roles
	 *            the roles
	 * @param userId
	 *            the user id
	 */
	private void saveRole(List<String> roles, String userId) {
		for (String roleId : roles) {
			UserRole userRole = new UserRole();
			UserRoleId id = new UserRoleId();
			id.setRoleId(roleId);
			id.setUserId(userId);
			userRole.setId(id);
			if (get(UserRole.class, id) == null) {
				save(userRole);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.CommonUtilDao#saveAdminRight(java.lang.String
	 * )
	 */
	@Override
	public void saveAdminRight(String userId) {
		List<String> roles = findBySQL(adminRightSQL);
		saveRole(roles, userId);

	}

	/**
	 * 保存用户权限
	 * 
	 */
	@Override
	public void saveUserRight(String userId) {
		List<String> roles = findBySQL(userRightSQL);
		saveRole(roles, userId);
	}

	/**
	 * 删除管理员权限
	 */
	@Override
	public void removeAdminRight(String userId) {
		//	List<String> roles = findBySQL(adminRightSQL);
		//	removeRole(roles, userId);
		//先去掉所有的权限再保存用户权限
		removeAllRole(userId);
		saveUserRight(userId);
	}

	/**
	 * 删除用户权限
	 * )
	 */
	@Override
	public void removeUserRight(String userId) {
		List<String> roles = findBySQL(userRightSQL);
		removeRole(roles, userId);
	}

}
