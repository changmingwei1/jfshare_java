package com.jfshare.uniqueid;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IDGenerater {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IDGenerater.class);

	private ZKUniqueIdClient zkUniqueIdClient;
	private AtomicLong startId = new AtomicLong();
	private AtomicLong endId = new AtomicLong();

	public IDGenerater(ZKUniqueIdClient zkUniqueIdClient) throws Exception {
		this.zkUniqueIdClient = zkUniqueIdClient;
		initId();
	}

	public long getId() throws Exception {
		long id = startId.incrementAndGet();
		if (id > endId.get()) {
			synchronized (this) {
				if (startId.get() > endId.get()) {
					initId();
				}
				return getId();
			}
		}
		return id;
	}

	private void initId() throws Exception {
		LOGGER.info("去zookeeper取分布式全局ID");
		long dId = zkUniqueIdClient.getDistributedId();
		while (dId <= endId.get()) {
			LOGGER.info("从zookeeper取的分布式全局ID{}小于等于本机ID{}，重新获取.", dId,
					endId.get());
			dId = zkUniqueIdClient.getDistributedId();
		}
		endId.set(dId);
		startId.set(endId.get() - zkUniqueIdClient.getDelta());
		LOGGER.info("从zookeeper取分布式全局ID成功，ID from[{}]to[{}]", startId.get(),
				endId.get());
	}

}
