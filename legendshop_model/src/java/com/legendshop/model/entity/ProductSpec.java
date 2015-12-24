package com.legendshop.model.entity ;
import java.util.Date;
/**
 * 
 * @author Legendshop
 * @version 1.0.0
 *
 */

// 产品规范
public class ProductSpec implements BaseEntity{

	private static final long serialVersionUID = -6327498161416369071L;

	// 产品规格ID
	private Long prodSpecId ; 
		
	// 商品ID
	private Long productId ; 
		
	// 属性ID
	private Long propId ; 
		
	// 属性值ID
	private Long valueId ; 
		
	// 是否Sku
	private Short isSku ; 
		
	// Stock Keeping Unit（库存量单位）ID
	private Long skuId ; 
		
	// 修改时间
	private Date modifyDate ; 
		
	// 记录时间
	private Date recDate ; 
	
	private String pic;
		
	
	public ProductSpec() {
    }
		
	public Long  getProdSpecId(){
		return prodSpecId ;
	} 
		
	public void setProdSpecId(Long prodSpecId){
		this.prodSpecId = prodSpecId ;
	}
		
		
	public Long  getProductId(){
		return productId ;
	} 
		
	public void setProductId(Long productId){
		this.productId = productId ;
	}
		
		
	public Long  getPropId(){
		return propId ;
	} 
		
	public void setPropId(Long propId){
		this.propId = propId ;
	}
		
		
	public Long  getValueId(){
		return valueId ;
	} 
		
	public void setValueId(Long valueId){
		this.valueId = valueId ;
	}
		
		
	public Short  getIsSku(){
		return isSku ;
	} 
		
	public void setIsSku(Short isSku){
		this.isSku = isSku ;
	}
		
		
	public Long  getSkuId(){
		return skuId ;
	} 
		
	public void setSkuId(Long skuId){
		this.skuId = skuId ;
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
		return 	prodSpecId;
	}

/**
 * @return the pic
 */
public String getPic() {
	return pic;
}

/**
 * @param pic the pic to set
 */
public void setPic(String pic) {
	this.pic = pic;
}

} 
