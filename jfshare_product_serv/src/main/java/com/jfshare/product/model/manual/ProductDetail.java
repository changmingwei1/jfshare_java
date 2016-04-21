package com.jfshare.product.model.manual;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection="product_detail")
public class ProductDetail implements Serializable{
	
	private static final long serialVersionUID = -2831959483154727548L;
	
	public static final String DETAIL_CONTENT =  "detailContent";
	public static final String UPDATE_STATUS =  "updateStatus";
	public static final String SELLER_ID = "sellerId";
	public static final String CREATE_TIME = "createTime";
	public static final String UPDATE_TIME = "updateTime";
	public static final String UPDATE_COUNT = "updateCount";
	public static final String ID = "_id";
	
	/**
	 * 代理主键
	 */
	@Id
	private String id;
	
	/**
	 * 详情信息
	 */
	private String detailContent;
	
	/**
	 * 更新状态
	 */
	@Indexed
	private int updateStatus;
	
	/**
	 * 卖家id
	 */
	@Indexed
	private int sellerId;
	
	/**
	 * 更新次数
	 */
	private int updateCount;
	
	/**
	 * 商品id
	 */
	@Indexed
	private String productId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	public ProductDetail jsonToBean(String json){
		if(json==null||"".equals(json)){
			return null;
		}
		JSONObject idJson = JSON.parseObject(json);
		if(idJson!=null){
			String oid = JSON.parseObject(idJson.getString(ID)).getString("$oid");
			setId(oid);
		}
		JSONObject detailContentJson = JSON.parseObject(json);
		if(detailContentJson!=null){
			setDetailContent(detailContentJson.getString(DETAIL_CONTENT));
		}
		JSONObject updateStatusJson = JSON.parseObject(json);
		if(updateStatusJson!=null){
			setUpdateStatus(updateStatusJson.getInteger(UPDATE_STATUS));
		}
		JSONObject sellerIdJson = JSON.parseObject(json);
		if(sellerIdJson!=null){
			setSellerId(sellerIdJson.getInteger(SELLER_ID));
		}
		JSONObject updateCountJson = JSON.parseObject(json);
		if(updateCountJson!=null){
			setUpdateCount(updateCountJson.getInteger(UPDATE_COUNT));
		}
		return this;
	}
	
	@Override
	public String toString() {
		return "BaseProductDetailBean [id=" + id + ", detailContent="
				+ detailContent + ", updateStatus=" + updateStatus
				+ ", sellerId=" + sellerId + ", updateCount=" + updateCount
				+ "]";
	}


	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getDetailContent() {
		return detailContent;
	}



	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}



	public int getUpdateStatus() {
		return updateStatus;
	}



	public void setUpdateStatus(int updateStatus) {
		this.updateStatus = updateStatus;
	}



	public int getSellerId() {
		return sellerId;
	}



	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}



	public int getUpdateCount() {
		return updateCount;
	}



	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}



	public String getProductId() {
		return productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public Date getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
