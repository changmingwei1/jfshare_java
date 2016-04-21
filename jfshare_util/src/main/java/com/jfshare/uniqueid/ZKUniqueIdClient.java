package com.jfshare.uniqueid;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryOneTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZKUniqueIdClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZKUniqueIdClient.class);
	private long gap; // 1次从zookeeper取得ID数
	private String idPath;
	private String zkHost;
	private ConnectionStateListener zkStateListener;
	
	private boolean stop = false;
	private CuratorFramework client = null;
	private DistributedAtomicLong daId; // 分布式全局ID

	/**
	 * @param zkHost
	 * @param idPath 
	 * @param gap	1次从zookeeper取得ID数
	 * @param zkStateListener zookeeper连接状态监听器
	 */
	public ZKUniqueIdClient(String zkHost,  String idPath,  int gap, ConnectionStateListener zkStateListener) {
		this.zkHost = zkHost;
		this.idPath = idPath;
		this.gap = gap;
		this.zkStateListener = zkStateListener;
		init();
	}
	
	/**
	 * @param zkHost
	 * @param idPath 
	 * @param gap	1次从zookeeper取得ID数
	 */
	public ZKUniqueIdClient(String zkHost,  String idPath,  int gap) {
		this.zkHost = zkHost;
		this.idPath = idPath;
		this.gap = gap;
		init();
	}

	private void init() {
		client = CuratorFrameworkFactory.newClient(zkHost, 10000, 10000, new RetryOneTime(1));
		if(zkStateListener != null){
			client.getConnectionStateListenable().addListener(zkStateListener);			
		}
		client.start();
		daId = new DistributedAtomicLong(client, idPath, new RetryOneTime(1));
	}

	/**
	 * 获取分布式全局ID
	 * 
	 * @return
	 * @throws Exception
	 */
	public long getDistributedId() throws Exception {
		AtomicValue<Long> avId = daId.add(gap);
		while (!stop && !avId.succeeded()) {
			LOGGER.debug("取[{}]-----[{}]的id值失败,重新获取", zkHost, idPath);
			avId = daId.add(gap);
		}
		return avId.postValue().longValue();
	}

	/**
	 * 重置分布式ID值
	 * @param value
	 * @throws Exception
	 */
	public void resetDistributedId(Long value) throws Exception{
		daId.forceSet(value);
	}
	
	public void shutdown() {
		stop = true;
		if (client != null)
			client.close();
	}

	public long getDelta() {
		return this.gap;
	}

}
