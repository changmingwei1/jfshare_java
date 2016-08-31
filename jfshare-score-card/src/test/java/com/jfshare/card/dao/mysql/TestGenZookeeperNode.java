package com.jfshare.card.dao.mysql;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

/**
 * 生成zk几点的测试类
 * chiwenheng
 *
 *  自己用来添加zookeeper的插件
 *
 * 2016-08-15-35
 */
public class TestGenZookeeperNode {

    @Test
    public void testGenJavaSeverNode() throws Exception{

        ZooKeeper zk = new ZooKeeper("101.201.39.63:2181",
                300000, new DemoWatcher());//连接zk server
        //*******************************************************************************************

        String node = "/.ridge/jfx_public_client/score_cards_serv_ips";
        Stat stat = zk.exists(node, false);//检测/app1是否存在
        if (stat == null) {
            System.out.println("stat  is   null !!");
            //创建节点
            String createResult = zk.create(node, "101.201.39.38".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(createResult);
        }else{
            System.out.println("当前存在");
        }
        System.out.println("----score_cards_serv_ips----result----------");
        //获取节点的值
        byte[] b = zk.getData(node, false, stat);
        System.out.println(new String(b));


        //*******************************************************************************************

        node = "/.ridge/jfx_public_client/score_cards_serv_port";
        stat = zk.exists(node, false);//检测/app1是否存在
        if (stat == null) {
            System.out.println("stat  is   null !!");
            //创建节点
            String createResult = zk.create(node, "2005".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(createResult);
        }else{
            System.out.println("当前存在");
        }
        System.out.println("----score_cards_serv_port----result----------");
        //获取节点的值
        b = zk.getData(node, false, stat);
        System.out.println(new String(b));

        zk.close();


    }
    @Test
    public void testGenZookeeper() throws Exception{

        ZooKeeper zk = new ZooKeeper("101.201.39.63:2181",
                300000, new DemoWatcher());//连接zk server
        //*******************************************************************************************

        String node = "/.ridge/jfx_score_card_serv/def_fastdfs_port";
        Stat stat = null;//检测/app1是否存在
//        if (stat == null) {
//            System.out.println("stat  is   null !!");
//            //创建节点
//            String createResult = zk.create(node, "22122".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            System.out.println(createResult);
//        }else{
//            System.out.println("当前存在");
//        }
//        System.out.println("----def_fastdfs_port----result----------");
//        //获取节点的值
        byte[] b = null;
//        System.out.println(new String(b));

        //*******************************************************************************************
//        node = "/.ridge/jfx_score_card_serv/def_export_name";
//        stat = zk.exists(node, false);//检测/app1是否存在
//        if (stat == null) {
//            System.out.println("stat  is   null !!");
//            //创建节点
//            String createResult = zk.create(node, "activity4cards.xls".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            System.out.println(createResult);
//        }else{
//            System.out.println("当前存在");
//        }
//        System.out.println("----def_export_name----result----------");
//        //获取节点的值
//        b = zk.getData(node, false, stat);
//        System.out.println(new String(b));

        //*******************************************************************************************

//        node = "/.ridge/jfx_score_card_serv/def_fastdfs_ip";
//        stat = zk.exists(node, false);//检测/app1是否存在
//        if (stat == null) {
//            System.out.println("stat  is   null !!");
//            //创建节点
//            String createResult = zk.create(node, "10.10.202.109".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            System.out.println(createResult);
//        }else{
//            System.out.println("当前存在");
//        }
//        System.out.println("----def_fastdfs_ip----result----------");
//        //获取节点的值
//        b = zk.getData(node, false, stat);
//        System.out.println(new String(b));

        //*******************************************************************************************
/*
        node = "/.ridge/jfx_score_card_serv/export_excel_path";
        stat = zk.exists(node, false);//检测/app1是否存在
        if (stat == null) {
            System.out.println("stat  is   null !!");
            //创建节点
//            String createResult = zk.create(node, "/Users/chiwenheng/Desktop/excel/".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            String createResult = zk.create(node, "/data/run/java_package/jfshare-score-card/".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(createResult);
        }else{
//            zk.setData(node, "/data/run/java_package/jfshare-score-card/".getBytes(),1);// 重新修改节点内容
            System.out.println("当前存在");
        }
        System.out.println("----export_excel_path----result----------");
        //获取节点的值
        b = zk.getData(node, false, stat);
        System.out.println(new String(b));*/

        //*******************************************************************************************

        node = "/.ridge/jfx_score_card_serv/export_psd";
        stat = zk.exists(node, false);//检测/app1是否存在
        if (stat == null) {
            System.out.println("stat  is   null !!");
            //创建节点
            String createResult = zk.create(node, "123456".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(createResult);
        }else{
            System.out.println("当前存在");
        }
        System.out.println("----export_psd----result----------");
        //获取节点的值
        b = zk.getData(node, false, stat);
        System.out.println(new String(b));



        zk.close();


    }

    static class DemoWatcher implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            System.out.println("----------->");
            System.out.println("path:" + event.getPath());
            System.out.println("type:" + event.getType());
            System.out.println("stat:" + event.getState());
            System.out.println("<-----------");
        }
    }


}
