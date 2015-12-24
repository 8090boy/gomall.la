package com.legendshop.model.entity ;
/**
 * 
 * @author Legendshop
 * @version 1.0.0
 *
 */

// 敏感字过滤表
public class SensitiveWord implements BaseEntity{

	private static final long serialVersionUID = -8021084501779040179L;

	// ID
	private Long sensId ; 
		
	// 一级分类
	private Long sortId ; 
		
	// 二级商品分类
	private Long nsortId ; 
		
	// 三级商品分类
	private Long subNsortId ; 
		
	// 关键字
	private String words ; 
		
	// 是否全局敏感字
	private Integer isGlobal ; 
		
	
	public SensitiveWord() {
    }
		
	public Long  getSensId(){
		return sensId ;
	} 
		
	public void setSensId(Long sensId){
		this.sensId = sensId ;
	}
		
	
		
		
	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public Long getNsortId() {
		return nsortId;
	}

	public void setNsortId(Long nsortId) {
		this.nsortId = nsortId;
	}

	public Long getSubNsortId() {
		return subNsortId;
	}

	public void setSubNsortId(Long subNsortId) {
		this.subNsortId = subNsortId;
	}

	public String  getWords(){
		return words ;
	} 
		
	public void setWords(String words){
		this.words = words ;
	}
		
		
	public Integer  getIsGlobal(){
		return isGlobal ;
	} 
		
	public void setIsGlobal(Integer isGlobal){
		this.isGlobal = isGlobal ;
	}
		
	
  public Long getId() {
		return 	sensId;
	}

} 
