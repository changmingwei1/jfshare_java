package com.jfshare.ridge.json.template;

/**
 * Class description goes here
 */
public class MongodbReplicaSet implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -375431157826441559L;
	/**
     * 'primary/primaryPreferred/secondary/secondaryPreferred/nearest'
     */
    private String read;
    /**
     * 'primary/primaryPreferred/secondary/secondaryPreferred/nearest'
     */
    private String write;

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }
}
