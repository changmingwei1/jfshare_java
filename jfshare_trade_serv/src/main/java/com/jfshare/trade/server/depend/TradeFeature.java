package com.jfshare.trade.server.depend;


import com.twitter.util.FutureEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

//import com.twitter.logging.Logger;


public class TradeFeature<T> implements FutureEventListener<T>{
	private Logger logger = LoggerFactory.getLogger(TradeFeature.class);
	private CountDownLatch latch = null;

	private Object result = null;
	
	private Object[] param = null;
	
    public TradeFeature(Object... param) {
    	this.param = param;
    }
    
    
    public TradeFeature(CountDownLatch latch, Object... param) {
        this.param = param;
    	this.latch = latch;
    }


	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	

	@Override
	public void onFailure(Throwable arg0) {
		logger.info(arg0.getMessage());
		if(latch != null)
			latch.countDown();
	}


	@Override
	public void onSuccess(T arg0) {
		
		result = arg0;
		if(latch != null)
			latch.countDown();
	}





	
	
	
	
    public static void main(String[] args) {
        try {

        	
        } catch (Exception x) {
            x.printStackTrace();
        }
    }



}
