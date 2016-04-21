import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.subject.*;
import com.jfshare.subject.util.Commons;
import com.jfshare.subject.util.ZookeeperUtil;
import com.jfshare.utils.JsonMapper;
import junit.framework.TestCase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.ArrayList;
import java.util.List;


public class Client extends TestCase {

    private TTransport transport;
    private SubjectServ.Client client;

    @Override
    public void setUp() throws Exception {

//        transport = new TFramedTransport(new TSocket("localhost", 1982));
//        transport = new TFramedTransport(new TSocket("120.24.153.155", 1982));
        transport = new TFramedTransport(new TSocket("123.56.206.195", 1982));



        TProtocol protocol = new TBinaryProtocol(transport);
        client = new SubjectServ.Client(protocol);
        transport.open();
    }

    @Override
    public void tearDown(){
        transport.close();
    }

    /**
     * 添加后台类目
     * @throws Exception
     */
    public void testAddSubject() throws Exception {
        SubjectInfo subjectInfo = new SubjectInfo();
        subjectInfo.setName("湿巾");
        subjectInfo.setPid(1068);
        subjectInfo.setSorted(2);
        //subjectInfo.setLevel(1);
        //subjectInfo.setIsLeaf(0);
        //subjectInfo.setDemo("测试2");
        //subjectInfo.setCreator(1207);
        //subjectInfo.setUpdater(1207);
        long start = System.currentTimeMillis();
        Result result = this.client.add(subjectInfo);
        System.out.println(">>>>> result : " + JsonMapper.toJson(result));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }


    /**
     * 修改后台类目
     * @throws Exception
     */
    public void testUpdateSubject() throws Exception {
        SubjectInfo subjectInfo = new SubjectInfo();
        subjectInfo.setId(1080);
        subjectInfo.setName("吸尘器");
        //subjectInfo.setPid(1073);
        subjectInfo.setSorted(1);
        subjectInfo.setLevel(3);
        //subjectInfo.setIsLeaf(1);
        subjectInfo.setDemo("");
        //subjectInfo.setCreator(0);
        //subjectInfo.setUpdater(0);
        subjectInfo.setStatus(3);
        long start = System.currentTimeMillis();
        Result result = this.client.update(subjectInfo);
        System.out.println(">>>>> result : " + JsonMapper.toJson(result));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }

    /**
     * 删除后台类目
     * @throws Exception
     */
    public void testDeleteSubject() throws Exception {
        long start = System.currentTimeMillis();
        Result result = this.client.deleteById(2093);
        System.out.println(">>>>> result : " + JsonMapper.toJson(result));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }

    /**
     * 根据ID查询后台类目
     * @throws Exception
     */
    public void testGetbyId() throws Exception {
        for (int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            SubjectInfoResult subjectInfo = this.client.getById(3036);
            System.out.println(">>>>> subjectInfo : " + subjectInfo);
            System.out.println(">>>>>>>>>> subject : " + subjectInfo.getSubjectInfo());
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }


    }

    /**
     * 获取后台类目子节点
     * @throws Exception
     */
    public void testGetSubTree() throws Exception {
        for (int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
            //559  700
            SubjectTreeResult treeResult = this.client.getSubTree(0);
//            if(treeResult.getSubjectNodes().size() > 19)
            System.out.println(">>>>> treeResult : " + treeResult);
            //System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }

    /**
     * 获取后台类目子节点，类目管理专用
     * @throws Exception
     */
    public void testGetSubTreeForManage() throws  Exception {
        for (int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            //559  700
            SubjectTreeResult treeResult = this.client.getSubTreeForManage(0);
            System.out.println(">>>>> treeResult : " + JsonMapper.toJson(treeResult.getSubjectNodes()));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }


    /**
     * 获取后台类目路径
     * @throws Exception
     */
    public void testGetSuperTee() throws Exception {
        long start = System.currentTimeMillis();
        SubjectTreeResult treeResult = this.client.getSuperTree(1309);
        System.out.println(">>>>> treeResult : " + JsonMapper.toJson(treeResult));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }

    /**
     * 获取后台类目所有叶子节点
     * @throws Exception
     */

    public void testGetLeavesById() throws Exception {
        for (int i = 0; i < 5; i++) {
            long start = System.currentTimeMillis();
            // 全遍历 0   一级 1000  二级  1017
            SubjectTreeResult treeResult = this.client.getLeavesById(1787);
            long end = System.currentTimeMillis() - start;
            System.out.println(">>>>> treeResult : " + JsonMapper.toJson(treeResult));
            System.out.println(treeResult.getSubjectNodes().size());
            System.out.println(">>>>>> 耗时 ：" + end + " ms!");
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }

    }

    /**
     * 获取后台类目整棵树
     * @throws Exception
     */
    public void testGetWholeTree() throws Exception {
        for(int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            StringResult stringResult = this.client.getWholeTree();
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println(">>>>> treeResult : " + JsonMapper.toJson(stringResult));

            System.out.println(">>>>> change to subjectNode : " + JsonMapper.toList(stringResult.getValue(), SubjectNode.class).size());
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }

    /**
     * 获取后台类目整棵树
     * @throws Exception
     */
    public void testGetWholeTreeList() throws Exception {
        for(int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            SubjectTreeResult result = this.client.getWholeTreeList();
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println(">>>>> treeResult : " + JsonMapper.toJson(result.getSubjectNodes()));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }

    /**
     * 发布后台类目
     * @throws Exception
     */
    public void testReleaseSubject() throws Exception {
        long start = System.currentTimeMillis();
        Result result = this.client.releaseSubject(2094);
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        System.out.println(">>>>> treeResult : " + JsonMapper.toJson(result));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }

    /**
     * 添加前台类目
     * @throws Exception
     */
    public void testAddDisSubject() throws Exception {
        DisplaySubjectParam param = new DisplaySubjectParam();
        DisplaySubjectInfo subjectInfo = new DisplaySubjectInfo();
        subjectInfo.setName("女士袜子");
        subjectInfo.setPid(3003);
        subjectInfo.setSorted(2);
        //subjectInfo.setLevel(1);
        //subjectInfo.setIsLeaf(0);
        //subjectInfo.setSubjectIds("700,701,702");
        subjectInfo.setImage("");
        subjectInfo.setDemo("");
        subjectInfo.setCreator(0);
        subjectInfo.setUpdater(0);
        param.setDisplaySubjectInfo(subjectInfo);
        param.setFlag("main");
        long start = System.currentTimeMillis();
        Result result = this.client.add4dis(param);
        System.out.println(">>>>> result : " + JsonMapper.toJson(result));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }

    /**
     * 添加前台类目
     * @throws Exception
     */
    public void testAddDisSubjectAll() throws Exception {
        DisplaySubjectParam param = new DisplaySubjectParam();
        List<String> names = new ArrayList<String>();
        names.add("裤子");
        //names.add("女士内裤");
        //names.add("男士袜子");
        //names.add("女士袜子");
        long start = System.currentTimeMillis();
        for (int i = 1; i <= names.size(); i++) {
            DisplaySubjectInfo subjectInfo = new DisplaySubjectInfo();
            subjectInfo.setName(names.get(i - 1));
            subjectInfo.setPid(0);
            subjectInfo.setSorted(i + 1);
            //subjectInfo.setLevel(1);
            subjectInfo.setIsLeaf(1);
            //subjectInfo.setSubjectIds("700,701,702");
            subjectInfo.setImage("");
            subjectInfo.setDemo("");
            subjectInfo.setCreator(0);
            subjectInfo.setUpdater(0);
            param.setDisplaySubjectInfo(subjectInfo);
            param.setFlag("main");

            Result result = this.client.add4dis(param);
        }

        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }

    /**
     * 修改前台类目
     * @throws Exception
     */
    public void testUpdateDisSubject() throws Exception {
        DisplaySubjectParam param = new DisplaySubjectParam();
        DisplaySubjectInfo subjectInfo = new DisplaySubjectInfo();
        subjectInfo.setId(1079);
        subjectInfo.setName("吸尘器");
        subjectInfo.setPid(1017);
        subjectInfo.setSorted(1);
        subjectInfo.setLevel(3);
        subjectInfo.setIsLeaf(1);
        //subjectInfo.setSubjectIds("700,701,702");
        //subjectInfo.setImage("image.jsp");
        //subjectInfo.setDemo("测试修改了");
        subjectInfo.setCreator(0);
        subjectInfo.setUpdater(0);
        subjectInfo.setDeleted(0);
        param.setDisplaySubjectInfo(subjectInfo);
        param.setFlag("wireless");
        long start = System.currentTimeMillis();
        Result result = this.client.update4dis(param);
        System.out.println(">>>>> result : " + JsonMapper.toJson(result));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }


    /**
     * 删除前台类目
     * @throws Exception
     */
    public void testDeleteDisSubject() throws Exception {
        DisplaySubjectParam param = new DisplaySubjectParam();
        DisplaySubjectInfo subjectInfo = new DisplaySubjectInfo();
        subjectInfo.setId(3255);
        param.setDisplaySubjectInfo(subjectInfo);
        param.setFlag("main");
        long start = System.currentTimeMillis();
        Result result = this.client.delete4dis(param);
        System.out.println(">>>>> result : " + JsonMapper.toJson(result));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }

    /**
     * 根据ID查询前台类目
     * @throws Exception
     */
    public void testGetById4dis() throws Exception {
        for (int i = 0; i < 1; i++) {
            DisplaySubjectParam param = new DisplaySubjectParam();
            DisplaySubjectInfo subjectInfo = new DisplaySubjectInfo();
            subjectInfo.setId(3531);
            param.setDisplaySubjectInfo(subjectInfo);
            param.setFlag("zhuanqu");

            long start = System.currentTimeMillis();
            DisplaySubjectResult result = this.client.getById4dis(param);
            System.out.println(">>>>> result : " + JsonMapper.toJson(result));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }

    }

    /**
     * 获取前台类目子节点
     * @throws Exception
     */
    public void testGetSubTree4dis() throws Exception {
        DisplaySubjectParam param = new DisplaySubjectParam();
        DisplaySubjectInfo subjectInfo = new DisplaySubjectInfo();
        subjectInfo.setId(0);
        param.setDisplaySubjectInfo(subjectInfo);
        param.setFlag("zhuanqu");
        long start = System.currentTimeMillis();
        DisplaySubjectTreeResult result = this.client.getSubTree4dis(param);
        System.out.println(">>>>> result : " + JsonMapper.toJson(result.getDisplaySubjectNodes()));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }

    /**
     * 获取前台类目路径
     * @throws Exception
     */
    public void testGetSuperTree4dis() throws Exception {
        DisplaySubjectParam param = new DisplaySubjectParam();
        DisplaySubjectInfo subjectInfo = new DisplaySubjectInfo();
        subjectInfo.setId(3016);
        param.setDisplaySubjectInfo(subjectInfo);
        param.setFlag("main");
        long start = System.currentTimeMillis();
        DisplaySubjectTreeResult result = this.client.getSuperTree4dis(param);
        System.out.println(">>>>> result : " + JsonMapper.toJson(result));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }

    /**
     * 获取前台类目整棵树
     * @throws Exception
     */
    public void testGetWholeTreeList4dis() throws Exception {
        for(int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            DisplaySubjectTreeResult result = this.client.getWholeTreeList4dis("wireless");
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println(">>>>> treeResult : " + JsonMapper.toJson(result));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println("num : " + result.getDisplaySubjectNodes().size());
        }
    }


    public void testGetDisplayIdsBySubjectId() throws Exception {
        for(int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            StringResult result = this.client.getDisplayIdsBySubjectId(1328);
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println(">>>>> treeResult : " + JsonMapper.toJson(result));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }

    /**
     * 后台类目查询
     * @throws Exception
     */
    public void testQuerySubjects() throws Exception {
        for(int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            SubjectQueryParam param = new SubjectQueryParam();
//            param.setName("男");
            //param.setPId(2093);
            Page page = new Page();
            page.setCurPage(1);
            page.setPageSize(10);
            SubjectQueryResult result = this.client.querySubjects(param, page);
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println(">>>>> treeResult : " + JsonMapper.toJson(result));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }

    /**
     * 前台台类目查询
     * @throws Exception
     */
    public void testQuerySubjects4dis() throws Exception {
        for(int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            DisplaySubjectQueryParam param = new DisplaySubjectQueryParam();
            //param.setName("男");
            param.setType("main");
            param.setPId(3016);
            Page page = new Page();
            page.setCurPage(1);
            page.setPageSize(10);
            DisplaySubjectQueryResult result = this.client.querySubjects4dis(param, page);
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println(">>>>> treeResult : " + JsonMapper.toJson(result));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }

    /**
     * 添加前台类目渠道
     * @throws Exception
     */
    public void testAddChannel() throws Exception{
        for(int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            DisplaySubjectChannelInfo channelInfo = new DisplaySubjectChannelInfo();
            channelInfo.setName("test_channel");
            //channelInfo.setCode("test");
            channelInfo.setCreator(194);
            channelInfo.setUpdater(194);
            Result result = this.client.addChannel(channelInfo);
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println(">>>>> treeResult : " + JsonMapper.toJson(result));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }

    /**
     * 添加前台类目渠道
     * @throws Exception
     */
    public void testUpdateChannel() throws Exception{
        for(int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            DisplaySubjectChannelInfo channelInfo = new DisplaySubjectChannelInfo();
            channelInfo.setId(6);
            channelInfo.setName("测试渠道1");
            channelInfo.setCode("test");
            channelInfo.setCreator(0);
            channelInfo.setUpdater(0);
            Result result = this.client.updateChannel(channelInfo);
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println(">>>>> treeResult : " + JsonMapper.toJson(result));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }

    /**
     * 添加前台类目渠道
     * @throws Exception
     */
    public void testDeleteChannel() throws Exception{
        for(int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            Result result = this.client.deleteChannelById(6);
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println(">>>>> treeResult : " + JsonMapper.toJson(result));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }

    /**
     * 添加前台类目渠道
     * @throws Exception
     */
    public void testQueryChannels() throws Exception{
        for(int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            DisplaySubjectChannelQueryParam param = new DisplaySubjectChannelQueryParam();
            Page page = new Page();
            page.setCurPage(1);
            page.setPageSize(10);
            DisplaySubjectChannelQueryResult result = this.client.queryChannels(param, page);
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println(">>>>> result : " + JsonMapper.toJson(result));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }


    /**
     * 根据前台类目查询对应的后台类目
     * @throws Exception
     */
    public void testQuerySubjectsRelation() throws Exception{
        for(int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            Page page = new Page();
            page.setCurPage(1);
            page.setPageSize(3);
            SubjectQueryResult result = this.client.querySubjectsRelation(3109, page);
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
            System.out.println(">>>>> result : " + JsonMapper.toJson(result.getSubjectInfos()));
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }
    }

    /**
     * 添加前台类目和后台类目关系
     * @throws Exception
     */
    public void testAddRelation() throws Exception{
        long start = System.currentTimeMillis();
        SubjectRefDisplayInfo relation = new SubjectRefDisplayInfo();
        relation.setDisplayId(3104);
        relation.setSubjectId(1000);
        Result result = this.client.addRelation(relation);
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        System.out.println(">>>>> treeResult : " + JsonMapper.toJson(result));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }

    /**
     * 删除前台类目和后台类目关系
     * @throws Exception
     */
    public void testDeleteRelation() throws Exception{
        long start = System.currentTimeMillis();
        SubjectRefDisplayInfo relation = new SubjectRefDisplayInfo();
        relation.setDisplayId(3104);
        relation.setSubjectId(1000);
        Result result = this.client.deleteRelation(relation);
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        System.out.println(">>>>> treeResult : " + JsonMapper.toJson(result));
        System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
    }

    /**
     * 刷新内存数据
     */
    public void testReloadTree() {
        ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.SUBJECT_MESSAGE.getBytes());
//        ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.DISPLAY_SUBJECT_MESSAGE.getBytes());
//        ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.SUBJECT_REF_DISPLAY_MESSAGE.getBytes());
    }


}
