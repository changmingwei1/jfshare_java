package com.jfshare.score.model;

import org.joda.time.DateTime;

public class TbScoreTrade {
    private Integer id;

    private Integer userId;

    private DateTime tradeTime;

    private Integer inOrOut;

    private Integer type;

    private Integer amount;

    private Integer trader;
    

	private String cashMobile;
	private String tradeId;

    public String getCashMobile() {
		return cashMobile;
	}

	public void setCashMobile(String cashMobile) {
		this.cashMobile = cashMobile;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public DateTime getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(DateTime tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Integer getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(Integer inOrOut) {
        this.inOrOut = inOrOut;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTrader() {
        return trader;
    }

    public void setTrader(Integer trader) {
        this.trader = trader;
    }

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

    
}