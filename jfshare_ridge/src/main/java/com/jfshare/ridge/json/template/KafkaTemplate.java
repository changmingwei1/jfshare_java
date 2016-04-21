package com.jfshare.ridge.json.template;

/**
 * Class description goes here
 */
public class KafkaTemplate {
    private String broker;
    private String zookeeper;
    private String desc;

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(String zookeeper) {
        this.zookeeper = zookeeper;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
