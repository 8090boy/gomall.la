package com.legendshop.model.entity ;
import java.util.Date;

/**
 * 
 * @author Legendshop
 * @version 1.0.0
 *
 */

// 属性值
public class ProductPropertyValue  extends UploadFile implements BaseEntity{

	private static final long serialVersionUID = 7248859099398637969L;

	// 属性值ID
	private Long valueId ; 
		
	// 属性ID
	private Long propId ; 
		
	// 属性值名称
	private String name ; 
		
	// 状态
	private Short status ; 
		
	// 图片路径
	private String pic ; 
		
	// 排序
	private Long sequence ; 
		
	// 修改时间
	private Date modifyDate ; 
		
	// 记录时间
	private Date recDate ; 
		
	
	public ProductPropertyValue() {
    }
		
	public Long  getValueId(){
		return valueId ;
	} 
		
	public void setValueId(Long valueId){
		this.valueId = valueId ;
	}
		
		
	public Long  getPropId(){
		return propId ;
	} 
		
	public void setPropId(Long propId){
		this.propId = propId ;
	}
		
		
	public String  getName(){
		return name ;
	} 
		
	public void setName(String name){
		this.name = name ;
	}
		
		
	public Short  getStatus(){
		return status ;
	} 
		
	public void setStatus(Short status){
		this.status = status ;
	}
		
		
	public String  getPic(){
		return pic ;
	} 
		
	public void setPic(String pic){
		this.pic = pic ;
	}
		
		
	public Long  getSequence(){
		return sequence ;
	} 
		
	public void setSequence(Long sequence){
		this.sequence = sequence ;
	}
		
		
	public Date  getModifyDate(){
		return modifyDate ;
	} 
		
	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate ;
	}
		
		
	public Date  getRecDate(){
		return recDate ;
	} 
		
	public void setRecDate(Date recDate){
		this.recDate = recDate ;
	}
		
	
  public Long getId() {
		return 	valueId;
	}

@Override
public String getUserName() {
	return null;
}

} 
