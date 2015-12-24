/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity ;
import java.util.Date;


// 商城公告表
/**
 * The Class Pub.
 */
public class Pub implements BaseEntity{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4935608546923180664L;

	// ID
	/** The id. */
	private Long id ; 
		
	// 标题
	/** The title. */
	private String title ; 
		
	// 内容
	/** The msg. */
	private String msg ; 
		
	// 录入时间
	/** The rec date. */
	private Date recDate ; 
		
	// 开始有效时间
	/** The start date. */
	private Date startDate ; 
		
	// 结束时间
	/** The end date. */
	private Date endDate ; 
		
	// 用户名称
	/** The user id. */
	private String userId ; 
		
	// 用户名称,备用
	/** The user name. */
	private String userName ; 
		
	// 状态，1:上线，0：下线
	/** The status. */
	private Integer status ; 
	
	//是否处于有效期
	private boolean isValid = true;
	
	/**
	 * Instantiates a new pub.
	 */
	public Pub() {
    }
		
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id){
		this.id = id ;
	}
		
		
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String  getTitle(){
		return title ;
	} 
		
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title){
		this.title = title ;
	}
		
		
	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public String  getMsg(){
		return msg ;
	} 
		
	/**
	 * Sets the msg.
	 *
	 * @param msg the new msg
	 */
	public void setMsg(String msg){
		this.msg = msg ;
	}
		
		
	/**
	 * Gets the rec date.
	 *
	 * @return the rec date
	 */
	public Date  getRecDate(){
		return recDate ;
	} 
		
	/**
	 * Sets the rec date.
	 *
	 * @param recDate the new rec date
	 */
	public void setRecDate(Date recDate){
		this.recDate = recDate ;
	}
		
		
	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public Date  getStartDate(){
		return startDate ;
	} 
		
	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate){
		this.startDate = startDate ;
	}
		
		
	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public Date  getEndDate(){
		return endDate ;
	} 
		
	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	public void setEndDate(Date endDate){
		this.endDate = endDate ;
	}
		
		
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String  getUserId(){
		return userId ;
	} 
		
	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId){
		this.userId = userId ;
	}
		
		
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String  getUserName(){
		return userName ;
	} 
		
	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName){
		this.userName = userName ;
	}
		
		
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Integer  getStatus(){
		return status ;
	} 
		
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Integer status){
		this.status = status ;
	}
		
	
  /* (non-Javadoc)
   * @see com.legendshop.model.entity.BaseEntity#getId()
   */
  public Long getId() {
		return 	id;
	}

public boolean isValid() {
	return isValid;
}

public void setValid(boolean isValid) {
	this.isValid = isValid;
}

} 
