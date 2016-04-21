package com.jfshare.product.dao.es;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.jfshare.product.model.vo.ESProductInfo;

/**
 * 
 * @author L
 */

public class ESProductInfoDao {
	
	private Client client;

	private String indexName = "no index name";
    
	private String type = "no type";
	
	@SuppressWarnings("resource")
	public ESProductInfoDao(String ip, int host, String clusterName, String indexName, String type){
		
		this.indexName = indexName;
		this.type = type;
		
    	Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", clusterName)
				.build();

		client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(ip, host));
	}
	
	
	
	/**
	 * 增加
	 */
	public void insert(ESProductInfo eSProductInfo) {

		try {

			XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
			
			jsonBuild.startObject()
					.field("productId", eSProductInfo.getProductId())
					.field("name", eSProductInfo.getName());
			
			String jsonData = jsonBuild.string();

			client.prepareIndex(this.indexName, this.type, eSProductInfo.getProductId()).setSource(jsonData)
					.get();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 删除
	 */
	public void delete(String productId){
		client.prepareDelete(this.indexName, this.type, productId)
        .setOperationThreaded(false)
        .get();
	}
}
