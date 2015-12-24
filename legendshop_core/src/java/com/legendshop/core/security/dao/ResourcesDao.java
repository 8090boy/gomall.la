package com.legendshop.core.security.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import com.legendshop.core.security.model.Resource;

public class ResourcesDao {
	private JdbcTemplate jdbcTemplate;
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ResourcesDao.class);

	public ResourcesDao() {
		System.out.println("加载ResourcesDao..." + jdbcTemplate);
	}

	public List<Resource> findAll() {
		try {
			List<Resource> resourceList = new ArrayList<Resource>();
			List<Map<String, Object>> resources = jdbcTemplate.queryForList("select * from ls_resource");
			for (Map<String, Object> map : resources) {
				Resource r = new Resource();
				r.setId(Integer.valueOf(map.get("id").toString()));
				r.setName(map.get("name").toString());
				r.setResType(map.get("res_type").toString());
				r.setResString(map.get("res_string").toString());
				r.setDescn(map.get("descn").toString());
				resourceList.add(r);
			}

			return resourceList;
		} catch (RuntimeException re) {
			log.error("find all resource failed " + re);
			throw re;
		}
	}

	// 加载资源与对应的权限
	public Collection<ConfigAttribute> loadRoleByResource(String url) {
		try {
			String sql = "select ro.name as role,re.res_string as url " + "from ls_role ro join ls_resc_role rr on ro.id=rr.role_id "
					+ "join ls_resource re on re.id=rr.resc_id " + "where re.res_string='" + url + "'";
			List<Map<String, Object>> authList = jdbcTemplate.queryForList(sql);
			Collection<ConfigAttribute> auths = new ArrayList<ConfigAttribute>();

			for (Map<String, Object> map : authList) {
				ConfigAttribute auth = new SecurityConfig(map.get("role").toString());
				auths.add(auth);
			}

			return auths;
		} catch (RuntimeException re) {
			log.error("find roles by url failed " + re);
			throw re;
		}
	}
}
