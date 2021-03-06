/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.product;

import org.apache.commons.lang.builder.HashCodeBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.thrift.*;
import org.apache.thrift.async.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

// No additional import required for struct/union.

public class ThirdPartyProduct implements TBase<ThirdPartyProduct, ThirdPartyProduct._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ThirdPartyProduct");

  private static final TField PRODUCT_ID_FIELD_DESC = new TField("productId", TType.STRING, (short)1);
  private static final TField THIRD_PARTY_PRODUCT_ID_FIELD_DESC = new TField("thirdPartyProductId", TType.STRING, (short)2);
  private static final TField THIRD_PARTY_IDENTIFY_FIELD_DESC = new TField("thirdPartyIdentify", TType.I32, (short)3);
  private static final TField NAME_FIELD_DESC = new TField("name", TType.STRING, (short)4);
  private static final TField IMG_KEY_FIELD_DESC = new TField("imgKey", TType.STRING, (short)5);
  private static final TField SELLER_CLASS_NUM_FIELD_DESC = new TField("sellerClassNum", TType.STRING, (short)6);
  private static final TField CUR_PRICE_FIELD_DESC = new TField("curPrice", TType.STRING, (short)7);
  private static final TField PRICE_STATE_FIELD_DESC = new TField("priceState", TType.I32, (short)8);
  private static final TField STOCK_INFO_FIELD_DESC = new TField("stockInfo", TType.STRING, (short)9);
  private static final TField ACTIVE_STATE_FIELD_DESC = new TField("activeState", TType.I32, (short)10);
  private static final TField OFFER_STATE_FIELD_DESC = new TField("offerState", TType.I32, (short)11);
  private static final TField UPDATE_TIME_FIELD_DESC = new TField("updateTime", TType.STRING, (short)12);


  public String productId;
  public String thirdPartyProductId;
  public int thirdPartyIdentify;
  public String name;
  public String imgKey;
  public String sellerClassNum;
  public String curPrice;
  public int priceState;
  public String stockInfo;
  public int activeState;
  public int offerState;
  public String updateTime;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    PRODUCT_ID((short)1, "productId"),
    THIRD_PARTY_PRODUCT_ID((short)2, "thirdPartyProductId"),
    THIRD_PARTY_IDENTIFY((short)3, "thirdPartyIdentify"),
    NAME((short)4, "name"),
    IMG_KEY((short)5, "imgKey"),
    SELLER_CLASS_NUM((short)6, "sellerClassNum"),
    CUR_PRICE((short)7, "curPrice"),
    PRICE_STATE((short)8, "priceState"),
    STOCK_INFO((short)9, "stockInfo"),
    ACTIVE_STATE((short)10, "activeState"),
    OFFER_STATE((short)11, "offerState"),
    UPDATE_TIME((short)12, "updateTime");
  
    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();
  
    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }
  
    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // PRODUCT_ID
  	return PRODUCT_ID;
        case 2: // THIRD_PARTY_PRODUCT_ID
  	return THIRD_PARTY_PRODUCT_ID;
        case 3: // THIRD_PARTY_IDENTIFY
  	return THIRD_PARTY_IDENTIFY;
        case 4: // NAME
  	return NAME;
        case 5: // IMG_KEY
  	return IMG_KEY;
        case 6: // SELLER_CLASS_NUM
  	return SELLER_CLASS_NUM;
        case 7: // CUR_PRICE
  	return CUR_PRICE;
        case 8: // PRICE_STATE
  	return PRICE_STATE;
        case 9: // STOCK_INFO
  	return STOCK_INFO;
        case 10: // ACTIVE_STATE
  	return ACTIVE_STATE;
        case 11: // OFFER_STATE
  	return OFFER_STATE;
        case 12: // UPDATE_TIME
  	return UPDATE_TIME;
        default:
  	return null;
      }
    }
  
    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }
  
    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }
  
    private final short _thriftId;
    private final String _fieldName;
  
    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }
  
    public short getThriftFieldId() {
      return _thriftId;
    }
  
    public String getFieldName() {
      return _fieldName;
    }
  }


  // isset id assignments
  private static final int __THIRDPARTYIDENTIFY_ISSET_ID = 0;
  private static final int __PRICESTATE_ISSET_ID = 1;
  private static final int __ACTIVESTATE_ISSET_ID = 2;
  private static final int __OFFERSTATE_ISSET_ID = 3;
  private BitSet __isset_bit_vector = new BitSet(4);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT_ID, new FieldMetaData("productId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.THIRD_PARTY_PRODUCT_ID, new FieldMetaData("thirdPartyProductId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.THIRD_PARTY_IDENTIFY, new FieldMetaData("thirdPartyIdentify", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.NAME, new FieldMetaData("name", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.IMG_KEY, new FieldMetaData("imgKey", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.SELLER_CLASS_NUM, new FieldMetaData("sellerClassNum", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.CUR_PRICE, new FieldMetaData("curPrice", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.PRICE_STATE, new FieldMetaData("priceState", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.STOCK_INFO, new FieldMetaData("stockInfo", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.ACTIVE_STATE, new FieldMetaData("activeState", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.OFFER_STATE, new FieldMetaData("offerState", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.UPDATE_TIME, new FieldMetaData("updateTime", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ThirdPartyProduct.class, metaDataMap);
  }


  public ThirdPartyProduct() {
  }

  public ThirdPartyProduct(
    String productId,
    String thirdPartyProductId,
    int thirdPartyIdentify,
    String name,
    String imgKey,
    String sellerClassNum,
    String curPrice,
    int priceState,
    String stockInfo,
    int activeState,
    int offerState,
    String updateTime)
  {
    this();
    this.productId = productId;
    this.thirdPartyProductId = thirdPartyProductId;
    this.thirdPartyIdentify = thirdPartyIdentify;
    setThirdPartyIdentifyIsSet(true);
    this.name = name;
    this.imgKey = imgKey;
    this.sellerClassNum = sellerClassNum;
    this.curPrice = curPrice;
    this.priceState = priceState;
    setPriceStateIsSet(true);
    this.stockInfo = stockInfo;
    this.activeState = activeState;
    setActiveStateIsSet(true);
    this.offerState = offerState;
    setOfferStateIsSet(true);
    this.updateTime = updateTime;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ThirdPartyProduct(ThirdPartyProduct other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    if (other.isSetThirdPartyProductId()) {
      this.thirdPartyProductId = other.thirdPartyProductId;
    }
    this.thirdPartyIdentify = other.thirdPartyIdentify;
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetImgKey()) {
      this.imgKey = other.imgKey;
    }
    if (other.isSetSellerClassNum()) {
      this.sellerClassNum = other.sellerClassNum;
    }
    if (other.isSetCurPrice()) {
      this.curPrice = other.curPrice;
    }
    this.priceState = other.priceState;
    if (other.isSetStockInfo()) {
      this.stockInfo = other.stockInfo;
    }
    this.activeState = other.activeState;
    this.offerState = other.offerState;
    if (other.isSetUpdateTime()) {
      this.updateTime = other.updateTime;
    }
  }

  public ThirdPartyProduct deepCopy() {
    return new ThirdPartyProduct(this);
  }

  @Override
  public void clear() {
    this.productId = null;
    this.thirdPartyProductId = null;
    setThirdPartyIdentifyIsSet(false);
    this.thirdPartyIdentify = 0;
    this.name = null;
    this.imgKey = null;
    this.sellerClassNum = null;
    this.curPrice = null;
    setPriceStateIsSet(false);
    this.priceState = 0;
    this.stockInfo = null;
    setActiveStateIsSet(false);
    this.activeState = 0;
    setOfferStateIsSet(false);
    this.offerState = 0;
    this.updateTime = null;
  }

  public String getProductId() {
    return this.productId;
  }

  public ThirdPartyProduct setProductId(String productId) {
    this.productId = productId;
    
    return this;
  }

  public void unsetProductId() {
    this.productId = null;
  }

  /** Returns true if field productId is set (has been asigned a value) and false otherwise */
  public boolean isSetProductId() {
    return this.productId != null;
  }

  public void setProductIdIsSet(boolean value) {
    if (!value) {
      this.productId = null;
    }
  }

  public String getThirdPartyProductId() {
    return this.thirdPartyProductId;
  }

  public ThirdPartyProduct setThirdPartyProductId(String thirdPartyProductId) {
    this.thirdPartyProductId = thirdPartyProductId;
    
    return this;
  }

  public void unsetThirdPartyProductId() {
    this.thirdPartyProductId = null;
  }

  /** Returns true if field thirdPartyProductId is set (has been asigned a value) and false otherwise */
  public boolean isSetThirdPartyProductId() {
    return this.thirdPartyProductId != null;
  }

  public void setThirdPartyProductIdIsSet(boolean value) {
    if (!value) {
      this.thirdPartyProductId = null;
    }
  }

  public int getThirdPartyIdentify() {
    return this.thirdPartyIdentify;
  }

  public ThirdPartyProduct setThirdPartyIdentify(int thirdPartyIdentify) {
    this.thirdPartyIdentify = thirdPartyIdentify;
    setThirdPartyIdentifyIsSet(true);

    return this;
  }

  public void unsetThirdPartyIdentify() {
  __isset_bit_vector.clear(__THIRDPARTYIDENTIFY_ISSET_ID);
  }

  /** Returns true if field thirdPartyIdentify is set (has been asigned a value) and false otherwise */
  public boolean isSetThirdPartyIdentify() {
    return __isset_bit_vector.get(__THIRDPARTYIDENTIFY_ISSET_ID);
  }

  public void setThirdPartyIdentifyIsSet(boolean value) {
    __isset_bit_vector.set(__THIRDPARTYIDENTIFY_ISSET_ID, value);
  }

  public String getName() {
    return this.name;
  }

  public ThirdPartyProduct setName(String name) {
    this.name = name;
    
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been asigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public String getImgKey() {
    return this.imgKey;
  }

  public ThirdPartyProduct setImgKey(String imgKey) {
    this.imgKey = imgKey;
    
    return this;
  }

  public void unsetImgKey() {
    this.imgKey = null;
  }

  /** Returns true if field imgKey is set (has been asigned a value) and false otherwise */
  public boolean isSetImgKey() {
    return this.imgKey != null;
  }

  public void setImgKeyIsSet(boolean value) {
    if (!value) {
      this.imgKey = null;
    }
  }

  public String getSellerClassNum() {
    return this.sellerClassNum;
  }

  public ThirdPartyProduct setSellerClassNum(String sellerClassNum) {
    this.sellerClassNum = sellerClassNum;
    
    return this;
  }

  public void unsetSellerClassNum() {
    this.sellerClassNum = null;
  }

  /** Returns true if field sellerClassNum is set (has been asigned a value) and false otherwise */
  public boolean isSetSellerClassNum() {
    return this.sellerClassNum != null;
  }

  public void setSellerClassNumIsSet(boolean value) {
    if (!value) {
      this.sellerClassNum = null;
    }
  }

  public String getCurPrice() {
    return this.curPrice;
  }

  public ThirdPartyProduct setCurPrice(String curPrice) {
    this.curPrice = curPrice;
    
    return this;
  }

  public void unsetCurPrice() {
    this.curPrice = null;
  }

  /** Returns true if field curPrice is set (has been asigned a value) and false otherwise */
  public boolean isSetCurPrice() {
    return this.curPrice != null;
  }

  public void setCurPriceIsSet(boolean value) {
    if (!value) {
      this.curPrice = null;
    }
  }

  public int getPriceState() {
    return this.priceState;
  }

  public ThirdPartyProduct setPriceState(int priceState) {
    this.priceState = priceState;
    setPriceStateIsSet(true);

    return this;
  }

  public void unsetPriceState() {
  __isset_bit_vector.clear(__PRICESTATE_ISSET_ID);
  }

  /** Returns true if field priceState is set (has been asigned a value) and false otherwise */
  public boolean isSetPriceState() {
    return __isset_bit_vector.get(__PRICESTATE_ISSET_ID);
  }

  public void setPriceStateIsSet(boolean value) {
    __isset_bit_vector.set(__PRICESTATE_ISSET_ID, value);
  }

  public String getStockInfo() {
    return this.stockInfo;
  }

  public ThirdPartyProduct setStockInfo(String stockInfo) {
    this.stockInfo = stockInfo;
    
    return this;
  }

  public void unsetStockInfo() {
    this.stockInfo = null;
  }

  /** Returns true if field stockInfo is set (has been asigned a value) and false otherwise */
  public boolean isSetStockInfo() {
    return this.stockInfo != null;
  }

  public void setStockInfoIsSet(boolean value) {
    if (!value) {
      this.stockInfo = null;
    }
  }

  public int getActiveState() {
    return this.activeState;
  }

  public ThirdPartyProduct setActiveState(int activeState) {
    this.activeState = activeState;
    setActiveStateIsSet(true);

    return this;
  }

  public void unsetActiveState() {
  __isset_bit_vector.clear(__ACTIVESTATE_ISSET_ID);
  }

  /** Returns true if field activeState is set (has been asigned a value) and false otherwise */
  public boolean isSetActiveState() {
    return __isset_bit_vector.get(__ACTIVESTATE_ISSET_ID);
  }

  public void setActiveStateIsSet(boolean value) {
    __isset_bit_vector.set(__ACTIVESTATE_ISSET_ID, value);
  }

  public int getOfferState() {
    return this.offerState;
  }

  public ThirdPartyProduct setOfferState(int offerState) {
    this.offerState = offerState;
    setOfferStateIsSet(true);

    return this;
  }

  public void unsetOfferState() {
  __isset_bit_vector.clear(__OFFERSTATE_ISSET_ID);
  }

  /** Returns true if field offerState is set (has been asigned a value) and false otherwise */
  public boolean isSetOfferState() {
    return __isset_bit_vector.get(__OFFERSTATE_ISSET_ID);
  }

  public void setOfferStateIsSet(boolean value) {
    __isset_bit_vector.set(__OFFERSTATE_ISSET_ID, value);
  }

  public String getUpdateTime() {
    return this.updateTime;
  }

  public ThirdPartyProduct setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
    
    return this;
  }

  public void unsetUpdateTime() {
    this.updateTime = null;
  }

  /** Returns true if field updateTime is set (has been asigned a value) and false otherwise */
  public boolean isSetUpdateTime() {
    return this.updateTime != null;
  }

  public void setUpdateTimeIsSet(boolean value) {
    if (!value) {
      this.updateTime = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT_ID:
      if (value == null) {
        unsetProductId();
      } else {
        setProductId((String)value);
      }
      break;
    case THIRD_PARTY_PRODUCT_ID:
      if (value == null) {
        unsetThirdPartyProductId();
      } else {
        setThirdPartyProductId((String)value);
      }
      break;
    case THIRD_PARTY_IDENTIFY:
      if (value == null) {
        unsetThirdPartyIdentify();
      } else {
        setThirdPartyIdentify((Integer)value);
      }
      break;
    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;
    case IMG_KEY:
      if (value == null) {
        unsetImgKey();
      } else {
        setImgKey((String)value);
      }
      break;
    case SELLER_CLASS_NUM:
      if (value == null) {
        unsetSellerClassNum();
      } else {
        setSellerClassNum((String)value);
      }
      break;
    case CUR_PRICE:
      if (value == null) {
        unsetCurPrice();
      } else {
        setCurPrice((String)value);
      }
      break;
    case PRICE_STATE:
      if (value == null) {
        unsetPriceState();
      } else {
        setPriceState((Integer)value);
      }
      break;
    case STOCK_INFO:
      if (value == null) {
        unsetStockInfo();
      } else {
        setStockInfo((String)value);
      }
      break;
    case ACTIVE_STATE:
      if (value == null) {
        unsetActiveState();
      } else {
        setActiveState((Integer)value);
      }
      break;
    case OFFER_STATE:
      if (value == null) {
        unsetOfferState();
      } else {
        setOfferState((Integer)value);
      }
      break;
    case UPDATE_TIME:
      if (value == null) {
        unsetUpdateTime();
      } else {
        setUpdateTime((String)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_ID:
      return getProductId();
    case THIRD_PARTY_PRODUCT_ID:
      return getThirdPartyProductId();
    case THIRD_PARTY_IDENTIFY:
      return new Integer(getThirdPartyIdentify());
    case NAME:
      return getName();
    case IMG_KEY:
      return getImgKey();
    case SELLER_CLASS_NUM:
      return getSellerClassNum();
    case CUR_PRICE:
      return getCurPrice();
    case PRICE_STATE:
      return new Integer(getPriceState());
    case STOCK_INFO:
      return getStockInfo();
    case ACTIVE_STATE:
      return new Integer(getActiveState());
    case OFFER_STATE:
      return new Integer(getOfferState());
    case UPDATE_TIME:
      return getUpdateTime();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PRODUCT_ID:
      return isSetProductId();
    case THIRD_PARTY_PRODUCT_ID:
      return isSetThirdPartyProductId();
    case THIRD_PARTY_IDENTIFY:
      return isSetThirdPartyIdentify();
    case NAME:
      return isSetName();
    case IMG_KEY:
      return isSetImgKey();
    case SELLER_CLASS_NUM:
      return isSetSellerClassNum();
    case CUR_PRICE:
      return isSetCurPrice();
    case PRICE_STATE:
      return isSetPriceState();
    case STOCK_INFO:
      return isSetStockInfo();
    case ACTIVE_STATE:
      return isSetActiveState();
    case OFFER_STATE:
      return isSetOfferState();
    case UPDATE_TIME:
      return isSetUpdateTime();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ThirdPartyProduct)
      return this.equals((ThirdPartyProduct)that);
    return false;
  }

  public boolean equals(ThirdPartyProduct that) {
    if (that == null)
      return false;
    boolean this_present_productId = true && this.isSetProductId();
    boolean that_present_productId = true && that.isSetProductId();
    if (this_present_productId || that_present_productId) {
      if (!(this_present_productId && that_present_productId))
        return false;
      if (!this.productId.equals(that.productId))
        return false;
    }
    boolean this_present_thirdPartyProductId = true && this.isSetThirdPartyProductId();
    boolean that_present_thirdPartyProductId = true && that.isSetThirdPartyProductId();
    if (this_present_thirdPartyProductId || that_present_thirdPartyProductId) {
      if (!(this_present_thirdPartyProductId && that_present_thirdPartyProductId))
        return false;
      if (!this.thirdPartyProductId.equals(that.thirdPartyProductId))
        return false;
    }
    boolean this_present_thirdPartyIdentify = true;
    boolean that_present_thirdPartyIdentify = true;
    if (this_present_thirdPartyIdentify || that_present_thirdPartyIdentify) {
      if (!(this_present_thirdPartyIdentify && that_present_thirdPartyIdentify))
        return false;
      if (this.thirdPartyIdentify != that.thirdPartyIdentify)
        return false;
    }
    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }
    boolean this_present_imgKey = true && this.isSetImgKey();
    boolean that_present_imgKey = true && that.isSetImgKey();
    if (this_present_imgKey || that_present_imgKey) {
      if (!(this_present_imgKey && that_present_imgKey))
        return false;
      if (!this.imgKey.equals(that.imgKey))
        return false;
    }
    boolean this_present_sellerClassNum = true && this.isSetSellerClassNum();
    boolean that_present_sellerClassNum = true && that.isSetSellerClassNum();
    if (this_present_sellerClassNum || that_present_sellerClassNum) {
      if (!(this_present_sellerClassNum && that_present_sellerClassNum))
        return false;
      if (!this.sellerClassNum.equals(that.sellerClassNum))
        return false;
    }
    boolean this_present_curPrice = true && this.isSetCurPrice();
    boolean that_present_curPrice = true && that.isSetCurPrice();
    if (this_present_curPrice || that_present_curPrice) {
      if (!(this_present_curPrice && that_present_curPrice))
        return false;
      if (!this.curPrice.equals(that.curPrice))
        return false;
    }
    boolean this_present_priceState = true;
    boolean that_present_priceState = true;
    if (this_present_priceState || that_present_priceState) {
      if (!(this_present_priceState && that_present_priceState))
        return false;
      if (this.priceState != that.priceState)
        return false;
    }
    boolean this_present_stockInfo = true && this.isSetStockInfo();
    boolean that_present_stockInfo = true && that.isSetStockInfo();
    if (this_present_stockInfo || that_present_stockInfo) {
      if (!(this_present_stockInfo && that_present_stockInfo))
        return false;
      if (!this.stockInfo.equals(that.stockInfo))
        return false;
    }
    boolean this_present_activeState = true;
    boolean that_present_activeState = true;
    if (this_present_activeState || that_present_activeState) {
      if (!(this_present_activeState && that_present_activeState))
        return false;
      if (this.activeState != that.activeState)
        return false;
    }
    boolean this_present_offerState = true;
    boolean that_present_offerState = true;
    if (this_present_offerState || that_present_offerState) {
      if (!(this_present_offerState && that_present_offerState))
        return false;
      if (this.offerState != that.offerState)
        return false;
    }
    boolean this_present_updateTime = true && this.isSetUpdateTime();
    boolean that_present_updateTime = true && that.isSetUpdateTime();
    if (this_present_updateTime || that_present_updateTime) {
      if (!(this_present_updateTime && that_present_updateTime))
        return false;
      if (!this.updateTime.equals(that.updateTime))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_productId = true && (isSetProductId());
    builder.append(present_productId);
    if (present_productId)
      builder.append(productId);
    boolean present_thirdPartyProductId = true && (isSetThirdPartyProductId());
    builder.append(present_thirdPartyProductId);
    if (present_thirdPartyProductId)
      builder.append(thirdPartyProductId);
    boolean present_thirdPartyIdentify = true;
    builder.append(present_thirdPartyIdentify);
    if (present_thirdPartyIdentify)
      builder.append(thirdPartyIdentify);
    boolean present_name = true && (isSetName());
    builder.append(present_name);
    if (present_name)
      builder.append(name);
    boolean present_imgKey = true && (isSetImgKey());
    builder.append(present_imgKey);
    if (present_imgKey)
      builder.append(imgKey);
    boolean present_sellerClassNum = true && (isSetSellerClassNum());
    builder.append(present_sellerClassNum);
    if (present_sellerClassNum)
      builder.append(sellerClassNum);
    boolean present_curPrice = true && (isSetCurPrice());
    builder.append(present_curPrice);
    if (present_curPrice)
      builder.append(curPrice);
    boolean present_priceState = true;
    builder.append(present_priceState);
    if (present_priceState)
      builder.append(priceState);
    boolean present_stockInfo = true && (isSetStockInfo());
    builder.append(present_stockInfo);
    if (present_stockInfo)
      builder.append(stockInfo);
    boolean present_activeState = true;
    builder.append(present_activeState);
    if (present_activeState)
      builder.append(activeState);
    boolean present_offerState = true;
    builder.append(present_offerState);
    if (present_offerState)
      builder.append(offerState);
    boolean present_updateTime = true && (isSetUpdateTime());
    builder.append(present_updateTime);
    if (present_updateTime)
      builder.append(updateTime);
    return builder.toHashCode();
  }

  public int compareTo(ThirdPartyProduct other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ThirdPartyProduct typedOther = (ThirdPartyProduct)other;

    lastComparison = Boolean.valueOf(isSetProductId()).compareTo(typedOther.isSetProductId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductId()) {
      lastComparison = TBaseHelper.compareTo(this.productId, typedOther.productId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetThirdPartyProductId()).compareTo(typedOther.isSetThirdPartyProductId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetThirdPartyProductId()) {
      lastComparison = TBaseHelper.compareTo(this.thirdPartyProductId, typedOther.thirdPartyProductId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetThirdPartyIdentify()).compareTo(typedOther.isSetThirdPartyIdentify());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetThirdPartyIdentify()) {
      lastComparison = TBaseHelper.compareTo(this.thirdPartyIdentify, typedOther.thirdPartyIdentify);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetName()).compareTo(typedOther.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = TBaseHelper.compareTo(this.name, typedOther.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetImgKey()).compareTo(typedOther.isSetImgKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetImgKey()) {
      lastComparison = TBaseHelper.compareTo(this.imgKey, typedOther.imgKey);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSellerClassNum()).compareTo(typedOther.isSetSellerClassNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSellerClassNum()) {
      lastComparison = TBaseHelper.compareTo(this.sellerClassNum, typedOther.sellerClassNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurPrice()).compareTo(typedOther.isSetCurPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurPrice()) {
      lastComparison = TBaseHelper.compareTo(this.curPrice, typedOther.curPrice);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPriceState()).compareTo(typedOther.isSetPriceState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPriceState()) {
      lastComparison = TBaseHelper.compareTo(this.priceState, typedOther.priceState);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStockInfo()).compareTo(typedOther.isSetStockInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStockInfo()) {
      lastComparison = TBaseHelper.compareTo(this.stockInfo, typedOther.stockInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetActiveState()).compareTo(typedOther.isSetActiveState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActiveState()) {
      lastComparison = TBaseHelper.compareTo(this.activeState, typedOther.activeState);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOfferState()).compareTo(typedOther.isSetOfferState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOfferState()) {
      lastComparison = TBaseHelper.compareTo(this.offerState, typedOther.offerState);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpdateTime()).compareTo(typedOther.isSetUpdateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdateTime()) {
      lastComparison = TBaseHelper.compareTo(this.updateTime, typedOther.updateTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }


  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) {
        break;
      }
      switch (field.id) {
        case 1: // PRODUCT_ID
          if (field.type == TType.STRING) {
            this.productId = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // THIRD_PARTY_PRODUCT_ID
          if (field.type == TType.STRING) {
            this.thirdPartyProductId = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // THIRD_PARTY_IDENTIFY
          if (field.type == TType.I32) {
            this.thirdPartyIdentify = iprot.readI32();
            setThirdPartyIdentifyIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // NAME
          if (field.type == TType.STRING) {
            this.name = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // IMG_KEY
          if (field.type == TType.STRING) {
            this.imgKey = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 6: // SELLER_CLASS_NUM
          if (field.type == TType.STRING) {
            this.sellerClassNum = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 7: // CUR_PRICE
          if (field.type == TType.STRING) {
            this.curPrice = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 8: // PRICE_STATE
          if (field.type == TType.I32) {
            this.priceState = iprot.readI32();
            setPriceStateIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 9: // STOCK_INFO
          if (field.type == TType.STRING) {
            this.stockInfo = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 10: // ACTIVE_STATE
          if (field.type == TType.I32) {
            this.activeState = iprot.readI32();
            setActiveStateIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 11: // OFFER_STATE
          if (field.type == TType.I32) {
            this.offerState = iprot.readI32();
            setOfferStateIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 12: // UPDATE_TIME
          if (field.type == TType.STRING) {
            this.updateTime = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();
    
    oprot.writeStructBegin(STRUCT_DESC);
    if (this.productId != null) {
      oprot.writeFieldBegin(PRODUCT_ID_FIELD_DESC);
      oprot.writeString(this.productId);
      oprot.writeFieldEnd();
    }
    if (this.thirdPartyProductId != null) {
      oprot.writeFieldBegin(THIRD_PARTY_PRODUCT_ID_FIELD_DESC);
      oprot.writeString(this.thirdPartyProductId);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(THIRD_PARTY_IDENTIFY_FIELD_DESC);
    oprot.writeI32(this.thirdPartyIdentify);
    oprot.writeFieldEnd();
    if (this.name != null) {
      oprot.writeFieldBegin(NAME_FIELD_DESC);
      oprot.writeString(this.name);
      oprot.writeFieldEnd();
    }
    if (this.imgKey != null) {
      oprot.writeFieldBegin(IMG_KEY_FIELD_DESC);
      oprot.writeString(this.imgKey);
      oprot.writeFieldEnd();
    }
    if (this.sellerClassNum != null) {
      oprot.writeFieldBegin(SELLER_CLASS_NUM_FIELD_DESC);
      oprot.writeString(this.sellerClassNum);
      oprot.writeFieldEnd();
    }
    if (this.curPrice != null) {
      oprot.writeFieldBegin(CUR_PRICE_FIELD_DESC);
      oprot.writeString(this.curPrice);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(PRICE_STATE_FIELD_DESC);
    oprot.writeI32(this.priceState);
    oprot.writeFieldEnd();
    if (this.stockInfo != null) {
      oprot.writeFieldBegin(STOCK_INFO_FIELD_DESC);
      oprot.writeString(this.stockInfo);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(ACTIVE_STATE_FIELD_DESC);
    oprot.writeI32(this.activeState);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(OFFER_STATE_FIELD_DESC);
    oprot.writeI32(this.offerState);
    oprot.writeFieldEnd();
    if (this.updateTime != null) {
      oprot.writeFieldBegin(UPDATE_TIME_FIELD_DESC);
      oprot.writeString(this.updateTime);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ThirdPartyProduct(");
    boolean first = true;
    sb.append("productId:");
    if (this.productId == null) {
      sb.append("null");
    } else {
      sb.append(this.productId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("thirdPartyProductId:");
    if (this.thirdPartyProductId == null) {
      sb.append("null");
    } else {
      sb.append(this.thirdPartyProductId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("thirdPartyIdentify:");
    sb.append(this.thirdPartyIdentify);
    first = false;
    if (!first) sb.append(", ");
    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("imgKey:");
    if (this.imgKey == null) {
      sb.append("null");
    } else {
      sb.append(this.imgKey);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("sellerClassNum:");
    if (this.sellerClassNum == null) {
      sb.append("null");
    } else {
      sb.append(this.sellerClassNum);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("curPrice:");
    if (this.curPrice == null) {
      sb.append("null");
    } else {
      sb.append(this.curPrice);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("priceState:");
    sb.append(this.priceState);
    first = false;
    if (!first) sb.append(", ");
    sb.append("stockInfo:");
    if (this.stockInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.stockInfo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("activeState:");
    sb.append(this.activeState);
    first = false;
    if (!first) sb.append(", ");
    sb.append("offerState:");
    sb.append(this.offerState);
    first = false;
    if (!first) sb.append(", ");
    sb.append("updateTime:");
    if (this.updateTime == null) {
      sb.append("null");
    } else {
      sb.append(this.updateTime);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
