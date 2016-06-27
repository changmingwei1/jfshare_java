package com.jfshare.elasticsearch.drive;

import com.google.common.collect.Lists;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MetaData;

import java.util.List;

public class ESSupport {

    TransportClient transportClient;

    public ESSupport(ESClient esClient) {
        this.transportClient = esClient.getTransportClient();
    }

    /**
     * 判断索引是否存在
     * @param index
     * @return
     */
    public boolean hasIndex(String index) {
        boolean req = false;
        req = transportClient.admin().cluster()
                .prepareState().execute()
                .actionGet().getState()
                .getMetaData().hasIndex(index);
        return req;
    }


    /**
     * 给出需要判断的索引列表, 然后给你返回存在的索引列表
     * @param indexlist
     * @return
     */
    public List<String> hasIndexList(List<String> indexlist) {
        List<String> newIndexList = Lists.newArrayList();
        MetaData metaData = transportClient.admin().cluster()
                .prepareState().execute()
                .actionGet().getState()
                .getMetaData();
        for (String index : indexlist) {
            if (metaData.hasIndex(index)) {
                newIndexList.add(index);
            }
        }
        return newIndexList;
    }


}
