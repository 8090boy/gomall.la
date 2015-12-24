package com.legendshop.model.entity ;
import java.util.Date;
import java.util.List;
/**
 * 
 * @author Legendshop
 * @version 1.0.0
 *
 */

// 属性名
public class ProductProperty implements BaseEntity{

	private static final long serialVersionUID = 7157140918610933422L;

	// 属性ID
	private Long propId ; 
		
	// 属性名称
	private String propName ; 
		
	// 如果分类ID为空，意味着全局可以用
	private Long sortId ; 
		
	// 别名
	private String memo ; 
		
	// 是否必须
	private Short isRequired ; 
		
	// 是否多选
	private Short isMulti ; 
		
	// 排序
	private Long sequence ; 
		
	// 状态
	private Short status ; 
		
	// 属性类型，1：有图片，0：文字
	private Short type ; 
		
	// 修改时间
	private Date modifyDate ; 
		
	// 记录时间
	private Date recDate ; 
		
	// 是否关键属性。可选值:1(是),0(否)
	private Short isKeyProp ; 
		
	// 是否参数属性
	private Short isParamProp ; 
		
	// 是否销售属性。可选值:1(是),0(否)
	private Short isSaleProp ; 
		
	// 是否可以搜索
	private Short isForSearch ; 
		
	// 是否输入属性
	private Short isInputProp ; 
	
	//商品属性
	private List<ProductPropertyValue> productPropertyValueList;
		
	
	public ProductProperty() {
    }
		
	public Long  getPropId(){
		return propId ;
	} 
		
	public void setPropId(Long propId){
		this.propId = propId ;
	}
		
		
	public String  getPropName(){
		return propName ;
	} 
		
	public void setPropName(String propName){
		this.propName = propName ;
	}
		
		
	public Long  getSortId(){
		return sortId ;
	} 
		
	public void setSortId(Long sortId){
		this.sortId = sortId ;
	}
		
		
	public String  getMemo(){
		return memo ;
	} 
		
	public void setMemo(String memo){
		this.memo = memo ;
	}
		
		
	public Short  getIsRequired(){
		return isRequired ;
	} 
		
	public void setIsRequired(Short isRequired){
		this.isRequired = isRequired ;
	}
		
		
	public Short  getIsMulti(){
		return isMulti ;
	} 
		
	public void setIsMulti(Short isMulti){
		this.isMulti = isMulti ;
	}
		
		
	public Long  getSequence(){
		return sequence ;
	} 
		
	public void setSequence(Long sequence){
		this.sequence = sequence ;
	}
		
		
	public Short  getStatus(){
		return status ;
	} 
		
	public void setStatus(Short status){
		this.status = status ;
	}
		
		
	public Short  getType(){
		return type ;
	} 
		
	public void setType(Short type){
		this.type = type ;
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
		
		
	public Short  getIsKeyProp(){
		return isKeyProp ;
	} 
		
	public void setIsKeyProp(Short isKeyProp){
		this.isKeyProp = isKeyProp ;
	}
		
		
	public Short  getIsParamProp(){
		return isParamProp ;
	} 
		
	public void setIsParamProp(Short isParamProp){
		this.isParamProp = isParamProp ;
	}
		
		
	public Short  getIsSaleProp(){
		return isSaleProp ;
	} 
		
	public void setIsSaleProp(Short isSaleProp){
		this.isSaleProp = isSaleProp ;
	}
		
		
	public Short  getIsForSearch(){
		return isForSearch ;
	} 
		
	public void setIsForSearch(Short isForSearch){
		this.isForSearch = isForSearch ;
	}
		
		
	public Short  getIsInputProp(){
		return isInputProp ;
	} 
		
	public void setIsInputProp(Short isInputProp){
		this.isInputProp = isInputProp ;
	}
		
	
  public Long getId() {
		return 	propId;
	}

/**
 * @return the productPropertyValueList
 */
public List<ProductPropertyValue> getProductPropertyValueList() {
	return productPropertyValueList;
}

/**
 * @param productPropertyValueList the productPropertyValueList to set
 */
public void setProductPropertyValueList(List<ProductPropertyValue> productPropertyValueList) {
	this.productPropertyValueList = productPropertyValueList;
}

} 
