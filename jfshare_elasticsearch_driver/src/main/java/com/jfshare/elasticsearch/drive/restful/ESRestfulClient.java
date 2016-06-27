package com.jfshare.elasticsearch.drive.restful;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.*;
import io.searchbox.indices.CloseIndex;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.OpenIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class ESRestfulClient {

    protected static final Logger logger = LoggerFactory.getLogger(ESRestfulClient.class);

    private JestClient client = null;

    /**
     * @param connectionUrl 参数格式为:"http://127.0.0.1:9200"
     */
    public ESRestfulClient(String connectionUrl){
        this(connectionUrl, 30000, 30000);
    }

    /**
     * @param connectionUrl 参数格式为:"http://127.0.0.1:9200"
     * @param connTimeout 连接超时时间,单位为ms
     * @param readTimeout read超时时间,单位为ms
     */
    public ESRestfulClient(String connectionUrl, int connTimeout, int readTimeout){
        logger.info("开始构建JestClient,connectionUrl:" + connectionUrl + ",connTimeout:" + connTimeout + ",readTimeout:" + readTimeout);

        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder(connectionUrl).connTimeout(connTimeout).readTimeout(readTimeout).multiThreaded(true).build());
        client = factory.getObject();

        logger.info("构建JestClient完毕");
    }

    /**
     * 创建index
     * @param settings
     * @return
     */
    public JestResult createIndex(String indexName, String settings){
        JestResult jestResult = null;

        try {
            CreateIndex.Builder builder = new CreateIndex.Builder(indexName).settings(settings);
            CreateIndex createIndex = builder.build();
            jestResult = client.execute(createIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jestResult;
    }

    /**
     * 删除index
     * @param index
     * @return
     */
    public JestResult deleteIndex(String index){
        JestResult jestResult = null;

        try {
            DeleteIndex.Builder builder = new DeleteIndex.Builder(index);
            DeleteIndex deleteIndex = builder.build();
            jestResult = client.execute(deleteIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jestResult;
    }

    /**
     * 关闭index
     * @param index
     * @return
     */
    public JestResult closeIndex(String index){
        JestResult jestResult = null;

        try {
            CloseIndex.Builder builder = new CloseIndex.Builder(index);
            CloseIndex closeIndex = builder.build();
            jestResult = client.execute(closeIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jestResult;
    }

    /**
     * 打开index
     * @param index
     * @return
     */
    public JestResult openIndex(String index){
        JestResult jestResult = null;

        try {
            OpenIndex.Builder builder = new OpenIndex.Builder(index);
            OpenIndex openIndex = builder.build();
            jestResult = client.execute(openIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jestResult;
    }

    /**
     * 创建type
     * @param index
     * @param type
     * @param source
     * @return
     */
    public JestResult putMapping(String index, String type, String source){
        JestResult jestResult = null;

        try {
            PutMapping.Builder builder = new PutMapping.Builder(index, type, source);
            PutMapping putMapping = builder.build();
            jestResult = client.execute(putMapping);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jestResult;
    }

    /**
     * 插入或更新文档数据
     * @param index
     * @param type
     * @param source
     * @return
     */
    public JestResult add(String index, String type, String id, String source){
        JestResult jestResult = null;

        try {
            Index.Builder builder = new Index.Builder(source).index(index).type(type);
            if(id != null && id.trim().length() > 0){
                builder.id(id);
            }
            Index indexMethod = builder.build();
            jestResult = client.execute(indexMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jestResult;
    }

    /**
     * 根据id获取文档
     * @param index
     * @param type
     * @param id
     * @return
     */
    public JestResult get(String index, String type, String id){
        JestResult jestResult = null;

        try {
            Get.Builder builder = new Get.Builder(index, id);
            if(type != null && type.trim().length() > 0){
                builder.type(type);
            }
            Get get = builder.build();
            jestResult = client.execute(get);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jestResult;
    }

    /**
     * 删除指定id的文档
     * @param id
     * @return
     */
    public JestResult delete(String index, String type, String id){
        JestResult jestResult = null;

        try {
            Delete.Builder builder = new Delete.Builder(id).index(index).type(type);
            Delete delete = builder.build();
            jestResult = client.execute(delete);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jestResult;
    }

    /**
     * 验证查询语句语法是否正确
     * @param queryStr
     * @return
     */
    public JestResult validate(String queryStr){
        JestResult jestResult = null;

        try {
            Validate.Builder builder = new Validate.Builder(queryStr);
            Validate validate = builder.build();
            jestResult = client.execute(validate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jestResult;
    }

    /**
     * 查询数量
     * @param index
     * @param queryStr
     * @return
     */
    public JestResult count(String index, String queryStr){
        Set<String> indices = new HashSet<String>();
        indices.add(index);
        return count(indices, "", queryStr);
    }

    /**
     * 查询数量
     * @param index
     * @param type
     * @param queryStr
     * @return
     */
    public JestResult count(String index, String type, String queryStr){
        Set<String> indices = new HashSet<String>();
        indices.add(index);
        return count(indices, type, queryStr);
    }

    /**
     * 查询数量
     * @param indices
     * @param queryStr
     * @return
     */
    public JestResult count(Collection<String> indices, String queryStr){
        return count(indices, "", queryStr);
    }

    /**
     * 查询数量
     * @param indices
     * @param type
     * @param queryStr
     * @return
     */
    public JestResult count(Collection<String> indices, String type, String queryStr){
        JestResult jestResult = null;

        try {
            Count.Builder builder = new Count.Builder().addIndex(indices).query(queryStr);
            if(type != null && type.trim().length() > 0){
                builder.addType(type);
            }
            Count count = builder.build();
            jestResult = client.execute(count);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jestResult;
    }

    /**
     * 查询文档
     * @param index
     * @param queryStr
     * @return
     */
    public JestResult search(String index, String queryStr){
        return search(index, "", queryStr);
    }

    /**
     * 查询文档
     * @param index
     * @param type
     * @param queryStr
     * @return
     */
    public JestResult search(String index, String type, String queryStr){
        Set<String> indices = new HashSet<String>();
        indices.add(index);

        return search(indices, type, queryStr);
    }

    /**
     * 查询文档
     * @param indices
     * @param queryStr
     * @return
     */
    public JestResult search(Collection<String> indices, String queryStr){
        return search(indices, "", queryStr);
    }

    /**
     * 查询文档
     * @param indices
     * @param type
     * @param queryStr
     * @return
     */
    public JestResult search(Collection<String> indices, String type, String queryStr){
        JestResult jestResult = null;

        try {
            Search.Builder builder = new Search.Builder(queryStr).addIndex(indices);
            if(type != null && type.trim().length() > 0){
                builder.addType(type);
            }
            Search search = builder.build();
            jestResult = client.execute(search);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jestResult;
    }

    /**
     * 获取jestClient
     * @return
     */
    public JestClient getClient() {
        return client;
    }

    /**
     * 关闭连接
     */
    public void close(){
        if(client != null){
            client.shutdownClient();
        }
    }

}
