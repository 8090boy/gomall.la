/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */

public class Nsort implements BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2161524693470603026L;

	/** The nsort id. */
	private Long nsortId;

	/** The nsort name. */
	private String nsortName;

	/** The sort id. */
	private Long sortId;
	
	/** The sort name. */
	private String sortName;
	// 父节点
	/** The parent nsort id. */
	private Long parentNsortId;

	/** The parent. */
	private Nsort parent;

	/** The seq. */
	private Integer seq;
	
	/** The status. */
	protected Integer status;
	
	/** The sort deputy. */
	private Integer sortDeputy;
	// 子节点
	/** The sub sort. */
	Set<Nsort> subSort = new TreeSet<Nsort>(new NsortComparator());

	// Constructors

	/**
	 * default constructor.
	 */
	public Nsort() {
	}

	/**
	 * minimal constructor.
	 * 
	 * @param nsortId
	 *            the nsort id
	 */
	public Nsort(Long nsortId) {
		this.nsortId = nsortId;
	}

	/**
	 * full constructor.
	 *
	 * @param nsortId the nsort id
	 * @param nsortName the nsort name
	 * @param sortId the sort id
	 * @param sortDeputy the sort deputy
	 */
	public Nsort(Long nsortId, String nsortName, Long sortId,Integer sortDeputy) {
		this.nsortId = nsortId;
		this.nsortName = nsortName;
		this.sortId = sortId;
		this.sortDeputy=sortDeputy;
	}

	// Property accessors

	/**
	 * Gets the nsort id.
	 * 
	 * @return the nsort id
	 */
	public Long getNsortId() {
		return this.nsortId;
	}

	/**
	 * Sets the nsort id.
	 * 
	 * @param nsortId
	 *            the new nsort id
	 */
	public void setNsortId(Long nsortId) {
		this.nsortId = nsortId;
	}

	/**
	 * Gets the nsort name.
	 * 
	 * @return the nsort name
	 */
	public String getNsortName() {
		return this.nsortName;
	}

	/**
	 * Sets the nsort name.
	 * 
	 * @param nsortName
	 *            the new nsort name
	 */
	public void setNsortName(String nsortName) {
		this.nsortName = nsortName;
	}

	/**
	 * Gets the sort id.
	 * 
	 * @return the sort id
	 */
	public Long getSortId() {
		return this.sortId;
	}

	/**
	 * Sets the sort id.
	 * 
	 * @param sortId
	 *            the new sort id
	 */
	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	/**
	 * Gets the parent nsort id.
	 * 
	 * @return the parent nsort id
	 */
	public Long getParentNsortId() {
		return parentNsortId;
	}

	/**
	 * Sets the parent nsort id.
	 * 
	 * @param parentNsortId
	 *            the new parent nsort id
	 */
	public void setParentNsortId(Long parentNsortId) {
		this.parentNsortId = parentNsortId;
	}

	/**
	 * Gets the sub sort.
	 * 
	 * @return the sub sort
	 */
	public Set<Nsort> getSubSort() {
		return subSort;
	}

	/**
	 * Sets the sub sort.
	 * 
	 * @param subSort
	 *            the new sub sort
	 */
	public void setSubSort(Set<Nsort> subSort) {
		this.subSort = subSort;
	}

	/**
	 * Adds the sub sort.
	 * 
	 * @param nsort
	 *            the nsort
	 */
	public void addSubSort(Nsort nsort) {
		if (this.getNsortId().equals(nsort.getParentNsortId())) {
			subSort.add(nsort);
		}
	}

	/**
	 * Gets the seq.
	 * 
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * Sets the seq.
	 * 
	 * @param seq
	 *            the new seq
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * Gets the parent.
	 * 
	 * @return the parent
	 */
	public Nsort getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 * 
	 * @param parent
	 *            the new parent
	 */
	public void setParent(Nsort parent) {
		this.parent = parent;
	}

	/**
	 * Gets the sort deputy.
	 *
	 * @return the sort deputy
	 */
	public Integer getSortDeputy() {
		return sortDeputy;
	}

	/**
	 * Sets the sort deputy.
	 *
	 * @param sortDeputy the new sort deputy
	 */
	public void setSortDeputy(Integer sortDeputy) {
		this.sortDeputy = sortDeputy;
	}

	/* (non-Javadoc)
	 * @see com.legendshop.model.entity.BaseEntity#getId()
	 */
	public Serializable getId() {
		return nsortId;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the sort name.
	 *
	 * @return the sort name
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * Sets the sort name.
	 *
	 * @param sortName the new sort name
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

}