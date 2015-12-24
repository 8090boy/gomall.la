package com.legendshop.model.entity ;
import java.util.Date;
/**
 * 
 * @author Legendshop
 * @version 1.0.0
 *
 */

// 单品SKU表
public class Sku implements BaseEntity{

	private static final long serialVersionUID = -2102752405219316621L;

	// 单品ID
	private Long skuId ; 
		
	// 商品ID
	private Long prodId ; 
		
	// sku的销售属性组合字符串（颜色，大小，等等，可通过类目API获取某类目下的销售属性）,格式是p1:v1;p2:v2
	private String properties ; 
		
	// 价格
	private Float price ; 
		
	// 商品在付款减库存的状态下，该sku上未付款的订单数量
	private Long stocks ; 
		
	// 实际库存
	private Long actualStocks ; 
		
	// SKU名称
	private String name ; 
		
	// sku状态。 1l:正常 ；0:删除
	private Short status ; 
		
	// sku级别发货时间
	private Date skuDeliveryTime ; 
		
	// 商家设置的外部id
	private String outerId ; 
		
	// 修改时间
	private Date modifyDate ; 
		
	// 记录时间
	private Date recDate ; 
		
	
	public Sku() {
    }
		
	public Long  getSkuId(){
		return skuId ;
	} 
		
	public void setSkuId(Long skuId){
		this.skuId = skuId ;
	}
		
		
	public Long  getProdId(){
		return prodId ;
	} 
		
	public void setProdId(Long prodId){
		this.prodId = prodId ;
	}
		
		
	public String  getProperties(){
		return properties ;
	} 
		
	public void setProperties(String properties){
		this.properties = properties ;
	}
		
		
	public Float  getPrice(){
		return price ;
	} 
		
	public void setPrice(Float price){
		this.price = price ;
	}
		
		
	public Long  getStocks(){
		return stocks ;
	} 
		
	public void setStocks(Long stocks){
		this.stocks = stocks ;
	}
		
		
	public Long  getActualStocks(){
		return actualStocks ;
	} 
		
	public void setActualStocks(Long actualStocks){
		this.actualStocks = actualStocks ;
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
		
		
	public Date  getSkuDeliveryTime(){
		return skuDeliveryTime ;
	} 
		
	public void setSkuDeliveryTime(Date skuDeliveryTime){
		this.skuDeliveryTime = skuDeliveryTime ;
	}
		
		
	public String  getOuterId(){
		return outerId ;
	} 
		
	public void setOuterId(String outerId){
		this.outerId = outerId ;
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
		return 	skuId;
	}

} 
