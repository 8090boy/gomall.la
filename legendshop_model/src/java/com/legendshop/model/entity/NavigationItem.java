package com.legendshop.model.entity ;
/**
 * 
 * @author Legendshop
 * @version 1.0.0
 *
 */

// 网站导航
public class NavigationItem implements BaseEntity{

	private static final long serialVersionUID = 6385487397211680962L;

	// 主键
	private Long itemId ; 
		
	// 导航ID
	private Long naviId ; 
		
	// 名称
	private String name ; 
		
	// 连接
	private String link ; 
		
	// 顺序
	private Long seq ; 
		
	// 状态
	private Integer status ; 
		
	
	public NavigationItem() {
    }
		
	public Long  getItemId(){
		return itemId ;
	} 
		
	public void setItemId(Long itemId){
		this.itemId = itemId ;
	}
		
		
	public Long  getNaviId(){
		return naviId ;
	} 
		
	public void setNaviId(Long naviId){
		this.naviId = naviId ;
	}
		
		
	public String  getName(){
		return name ;
	} 
		
	public void setName(String name){
		this.name = name ;
	}
		
		
	public String  getLink(){
		return link ;
	} 
		
	public void setLink(String link){
		this.link = link ;
	}
		
		
	public Long  getSeq(){
		return seq ;
	} 
		
	public void setSeq(Long seq){
		this.seq = seq ;
	}
			
	
  public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

public Long getId() {
		return 	itemId;
	}

} 
