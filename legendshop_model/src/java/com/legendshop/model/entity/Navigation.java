package com.legendshop.model.entity;


import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author Legendshop
 * @version 1.0.0
 * 
 */

// 网站导航
public class Navigation implements BaseEntity {

	private static final long serialVersionUID = -4558504540386164776L;

	// 主键
	private Long naviId;

	// 名称
	private String name;

	// 顺序
	private Integer seq;

	// 状态

	private Integer status;
 
	
	// 子节点
	/** The sub sort. */
	Set<NavigationItem> subItems = new TreeSet<NavigationItem>(new NavigationItemComparator());
		
	
	public Navigation() {
	}

	public Long getNaviId() {
		return naviId;
	}

	public void setNaviId(Long naviId) {
		this.naviId = naviId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
  
	public Set<NavigationItem> getSubItems() {
		return subItems;
	}


	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
		return naviId;
	}


	public void setSubItems(Set<NavigationItem> subItems) {
		this.subItems = subItems;
	}
	
	/**
	 * 增加子导航元素
	 * @param item
	 */
	public void addSubItems(NavigationItem item) {
		if(this.getNaviId().equals(item.getNaviId())){
			subItems.add(item);
		}
		
	}

}
 

