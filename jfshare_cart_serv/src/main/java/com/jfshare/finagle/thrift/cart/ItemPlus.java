/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.cart;

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

public class ItemPlus implements TBase<ItemPlus, ItemPlus._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ItemPlus");

  private static final TField PRODUCT_FIELD_DESC = new TField("product", TType.STRUCT, (short)1);
  private static final TField COUNT_FIELD_DESC = new TField("count", TType.I32, (short)2);
  private static final TField LOCK_COUNT_FIELD_DESC = new TField("lockCount", TType.I32, (short)3);
  private static final TField CART_PRICE_FIELD_DESC = new TField("cartPrice", TType.STRING, (short)4);
  private static final TField TOTAL_COUNT_FIELD_DESC = new TField("totalCount", TType.I32, (short)5);
  private static final TField SKU_COUNT_FIELD_DESC = new TField("skuCount", TType.I32, (short)6);
  private static final TField WI_FIELD_DESC = new TField("wi", TType.STRING, (short)7);
  private static final TField CURR_BUY_FIELD_DESC = new TField("currBuy", TType.I32, (short)8);
  private static final TField SELLER_SEND_TIME_FIELD_DESC = new TField("sellerSendTime", TType.I32, (short)9);


  public com.jfshare.finagle.thrift.product.Product product;
  public int count;
  public int lockCount;
  public String cartPrice;
  public int totalCount;
  public int skuCount;
  public String wi;
  public int currBuy;
  public int sellerSendTime;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    PRODUCT((short)1, "product"),
    COUNT((short)2, "count"),
    LOCK_COUNT((short)3, "lockCount"),
    CART_PRICE((short)4, "cartPrice"),
    TOTAL_COUNT((short)5, "totalCount"),
    SKU_COUNT((short)6, "skuCount"),
    WI((short)7, "wi"),
    CURR_BUY((short)8, "currBuy"),
    SELLER_SEND_TIME((short)9, "sellerSendTime");
  
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
        case 1: // PRODUCT
  	return PRODUCT;
        case 2: // COUNT
  	return COUNT;
        case 3: // LOCK_COUNT
  	return LOCK_COUNT;
        case 4: // CART_PRICE
  	return CART_PRICE;
        case 5: // TOTAL_COUNT
  	return TOTAL_COUNT;
        case 6: // SKU_COUNT
  	return SKU_COUNT;
        case 7: // WI
  	return WI;
        case 8: // CURR_BUY
  	return CURR_BUY;
        case 9: // SELLER_SEND_TIME
  	return SELLER_SEND_TIME;
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
  private static final int __COUNT_ISSET_ID = 0;
  private static final int __LOCKCOUNT_ISSET_ID = 1;
  private static final int __TOTALCOUNT_ISSET_ID = 2;
  private static final int __SKUCOUNT_ISSET_ID = 3;
  private static final int __CURRBUY_ISSET_ID = 4;
  private static final int __SELLERSENDTIME_ISSET_ID = 5;
  private BitSet __isset_bit_vector = new BitSet(6);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT, new FieldMetaData("product", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.product.Product.class)));
    tmpMap.put(_Fields.COUNT, new FieldMetaData("count", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.LOCK_COUNT, new FieldMetaData("lockCount", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.CART_PRICE, new FieldMetaData("cartPrice", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.TOTAL_COUNT, new FieldMetaData("totalCount", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.SKU_COUNT, new FieldMetaData("skuCount", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.WI, new FieldMetaData("wi", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.CURR_BUY, new FieldMetaData("currBuy", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.SELLER_SEND_TIME, new FieldMetaData("sellerSendTime", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ItemPlus.class, metaDataMap);
  }


  public ItemPlus() {
  }

  public ItemPlus(
    com.jfshare.finagle.thrift.product.Product product,
    int count,
    int lockCount,
    String cartPrice,
    int totalCount,
    int skuCount,
    String wi,
    int currBuy,
    int sellerSendTime)
  {
    this();
    this.product = product;
    this.count = count;
    setCountIsSet(true);
    this.lockCount = lockCount;
    setLockCountIsSet(true);
    this.cartPrice = cartPrice;
    this.totalCount = totalCount;
    setTotalCountIsSet(true);
    this.skuCount = skuCount;
    setSkuCountIsSet(true);
    this.wi = wi;
    this.currBuy = currBuy;
    setCurrBuyIsSet(true);
    this.sellerSendTime = sellerSendTime;
    setSellerSendTimeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ItemPlus(ItemPlus other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetProduct()) {
      this.product = new com.jfshare.finagle.thrift.product.Product(other.product);
    }
    this.count = other.count;
    this.lockCount = other.lockCount;
    if (other.isSetCartPrice()) {
      this.cartPrice = other.cartPrice;
    }
    this.totalCount = other.totalCount;
    this.skuCount = other.skuCount;
    if (other.isSetWi()) {
      this.wi = other.wi;
    }
    this.currBuy = other.currBuy;
    this.sellerSendTime = other.sellerSendTime;
  }

  public ItemPlus deepCopy() {
    return new ItemPlus(this);
  }

  @Override
  public void clear() {
    this.product = null;
    setCountIsSet(false);
    this.count = 0;
    setLockCountIsSet(false);
    this.lockCount = 0;
    this.cartPrice = null;
    setTotalCountIsSet(false);
    this.totalCount = 0;
    setSkuCountIsSet(false);
    this.skuCount = 0;
    this.wi = null;
    setCurrBuyIsSet(false);
    this.currBuy = 0;
    setSellerSendTimeIsSet(false);
    this.sellerSendTime = 0;
  }

  public com.jfshare.finagle.thrift.product.Product getProduct() {
    return this.product;
  }

  public ItemPlus setProduct(com.jfshare.finagle.thrift.product.Product product) {
    this.product = product;
    
    return this;
  }

  public void unsetProduct() {
    this.product = null;
  }

  /** Returns true if field product is set (has been asigned a value) and false otherwise */
  public boolean isSetProduct() {
    return this.product != null;
  }

  public void setProductIsSet(boolean value) {
    if (!value) {
      this.product = null;
    }
  }

  public int getCount() {
    return this.count;
  }

  public ItemPlus setCount(int count) {
    this.count = count;
    setCountIsSet(true);

    return this;
  }

  public void unsetCount() {
  __isset_bit_vector.clear(__COUNT_ISSET_ID);
  }

  /** Returns true if field count is set (has been asigned a value) and false otherwise */
  public boolean isSetCount() {
    return __isset_bit_vector.get(__COUNT_ISSET_ID);
  }

  public void setCountIsSet(boolean value) {
    __isset_bit_vector.set(__COUNT_ISSET_ID, value);
  }

  public int getLockCount() {
    return this.lockCount;
  }

  public ItemPlus setLockCount(int lockCount) {
    this.lockCount = lockCount;
    setLockCountIsSet(true);

    return this;
  }

  public void unsetLockCount() {
  __isset_bit_vector.clear(__LOCKCOUNT_ISSET_ID);
  }

  /** Returns true if field lockCount is set (has been asigned a value) and false otherwise */
  public boolean isSetLockCount() {
    return __isset_bit_vector.get(__LOCKCOUNT_ISSET_ID);
  }

  public void setLockCountIsSet(boolean value) {
    __isset_bit_vector.set(__LOCKCOUNT_ISSET_ID, value);
  }

  public String getCartPrice() {
    return this.cartPrice;
  }

  public ItemPlus setCartPrice(String cartPrice) {
    this.cartPrice = cartPrice;
    
    return this;
  }

  public void unsetCartPrice() {
    this.cartPrice = null;
  }

  /** Returns true if field cartPrice is set (has been asigned a value) and false otherwise */
  public boolean isSetCartPrice() {
    return this.cartPrice != null;
  }

  public void setCartPriceIsSet(boolean value) {
    if (!value) {
      this.cartPrice = null;
    }
  }

  public int getTotalCount() {
    return this.totalCount;
  }

  public ItemPlus setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    setTotalCountIsSet(true);

    return this;
  }

  public void unsetTotalCount() {
  __isset_bit_vector.clear(__TOTALCOUNT_ISSET_ID);
  }

  /** Returns true if field totalCount is set (has been asigned a value) and false otherwise */
  public boolean isSetTotalCount() {
    return __isset_bit_vector.get(__TOTALCOUNT_ISSET_ID);
  }

  public void setTotalCountIsSet(boolean value) {
    __isset_bit_vector.set(__TOTALCOUNT_ISSET_ID, value);
  }

  public int getSkuCount() {
    return this.skuCount;
  }

  public ItemPlus setSkuCount(int skuCount) {
    this.skuCount = skuCount;
    setSkuCountIsSet(true);

    return this;
  }

  public void unsetSkuCount() {
  __isset_bit_vector.clear(__SKUCOUNT_ISSET_ID);
  }

  /** Returns true if field skuCount is set (has been asigned a value) and false otherwise */
  public boolean isSetSkuCount() {
    return __isset_bit_vector.get(__SKUCOUNT_ISSET_ID);
  }

  public void setSkuCountIsSet(boolean value) {
    __isset_bit_vector.set(__SKUCOUNT_ISSET_ID, value);
  }

  public String getWi() {
    return this.wi;
  }

  public ItemPlus setWi(String wi) {
    this.wi = wi;
    
    return this;
  }

  public void unsetWi() {
    this.wi = null;
  }

  /** Returns true if field wi is set (has been asigned a value) and false otherwise */
  public boolean isSetWi() {
    return this.wi != null;
  }

  public void setWiIsSet(boolean value) {
    if (!value) {
      this.wi = null;
    }
  }

  public int getCurrBuy() {
    return this.currBuy;
  }

  public ItemPlus setCurrBuy(int currBuy) {
    this.currBuy = currBuy;
    setCurrBuyIsSet(true);

    return this;
  }

  public void unsetCurrBuy() {
  __isset_bit_vector.clear(__CURRBUY_ISSET_ID);
  }

  /** Returns true if field currBuy is set (has been asigned a value) and false otherwise */
  public boolean isSetCurrBuy() {
    return __isset_bit_vector.get(__CURRBUY_ISSET_ID);
  }

  public void setCurrBuyIsSet(boolean value) {
    __isset_bit_vector.set(__CURRBUY_ISSET_ID, value);
  }

  public int getSellerSendTime() {
    return this.sellerSendTime;
  }

  public ItemPlus setSellerSendTime(int sellerSendTime) {
    this.sellerSendTime = sellerSendTime;
    setSellerSendTimeIsSet(true);

    return this;
  }

  public void unsetSellerSendTime() {
  __isset_bit_vector.clear(__SELLERSENDTIME_ISSET_ID);
  }

  /** Returns true if field sellerSendTime is set (has been asigned a value) and false otherwise */
  public boolean isSetSellerSendTime() {
    return __isset_bit_vector.get(__SELLERSENDTIME_ISSET_ID);
  }

  public void setSellerSendTimeIsSet(boolean value) {
    __isset_bit_vector.set(__SELLERSENDTIME_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT:
      if (value == null) {
        unsetProduct();
      } else {
        setProduct((com.jfshare.finagle.thrift.product.Product)value);
      }
      break;
    case COUNT:
      if (value == null) {
        unsetCount();
      } else {
        setCount((Integer)value);
      }
      break;
    case LOCK_COUNT:
      if (value == null) {
        unsetLockCount();
      } else {
        setLockCount((Integer)value);
      }
      break;
    case CART_PRICE:
      if (value == null) {
        unsetCartPrice();
      } else {
        setCartPrice((String)value);
      }
      break;
    case TOTAL_COUNT:
      if (value == null) {
        unsetTotalCount();
      } else {
        setTotalCount((Integer)value);
      }
      break;
    case SKU_COUNT:
      if (value == null) {
        unsetSkuCount();
      } else {
        setSkuCount((Integer)value);
      }
      break;
    case WI:
      if (value == null) {
        unsetWi();
      } else {
        setWi((String)value);
      }
      break;
    case CURR_BUY:
      if (value == null) {
        unsetCurrBuy();
      } else {
        setCurrBuy((Integer)value);
      }
      break;
    case SELLER_SEND_TIME:
      if (value == null) {
        unsetSellerSendTime();
      } else {
        setSellerSendTime((Integer)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT:
      return getProduct();
    case COUNT:
      return new Integer(getCount());
    case LOCK_COUNT:
      return new Integer(getLockCount());
    case CART_PRICE:
      return getCartPrice();
    case TOTAL_COUNT:
      return new Integer(getTotalCount());
    case SKU_COUNT:
      return new Integer(getSkuCount());
    case WI:
      return getWi();
    case CURR_BUY:
      return new Integer(getCurrBuy());
    case SELLER_SEND_TIME:
      return new Integer(getSellerSendTime());
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PRODUCT:
      return isSetProduct();
    case COUNT:
      return isSetCount();
    case LOCK_COUNT:
      return isSetLockCount();
    case CART_PRICE:
      return isSetCartPrice();
    case TOTAL_COUNT:
      return isSetTotalCount();
    case SKU_COUNT:
      return isSetSkuCount();
    case WI:
      return isSetWi();
    case CURR_BUY:
      return isSetCurrBuy();
    case SELLER_SEND_TIME:
      return isSetSellerSendTime();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ItemPlus)
      return this.equals((ItemPlus)that);
    return false;
  }

  public boolean equals(ItemPlus that) {
    if (that == null)
      return false;
    boolean this_present_product = true && this.isSetProduct();
    boolean that_present_product = true && that.isSetProduct();
    if (this_present_product || that_present_product) {
      if (!(this_present_product && that_present_product))
        return false;
      if (!this.product.equals(that.product))
        return false;
    }
    boolean this_present_count = true;
    boolean that_present_count = true;
    if (this_present_count || that_present_count) {
      if (!(this_present_count && that_present_count))
        return false;
      if (this.count != that.count)
        return false;
    }
    boolean this_present_lockCount = true;
    boolean that_present_lockCount = true;
    if (this_present_lockCount || that_present_lockCount) {
      if (!(this_present_lockCount && that_present_lockCount))
        return false;
      if (this.lockCount != that.lockCount)
        return false;
    }
    boolean this_present_cartPrice = true && this.isSetCartPrice();
    boolean that_present_cartPrice = true && that.isSetCartPrice();
    if (this_present_cartPrice || that_present_cartPrice) {
      if (!(this_present_cartPrice && that_present_cartPrice))
        return false;
      if (!this.cartPrice.equals(that.cartPrice))
        return false;
    }
    boolean this_present_totalCount = true;
    boolean that_present_totalCount = true;
    if (this_present_totalCount || that_present_totalCount) {
      if (!(this_present_totalCount && that_present_totalCount))
        return false;
      if (this.totalCount != that.totalCount)
        return false;
    }
    boolean this_present_skuCount = true;
    boolean that_present_skuCount = true;
    if (this_present_skuCount || that_present_skuCount) {
      if (!(this_present_skuCount && that_present_skuCount))
        return false;
      if (this.skuCount != that.skuCount)
        return false;
    }
    boolean this_present_wi = true && this.isSetWi();
    boolean that_present_wi = true && that.isSetWi();
    if (this_present_wi || that_present_wi) {
      if (!(this_present_wi && that_present_wi))
        return false;
      if (!this.wi.equals(that.wi))
        return false;
    }
    boolean this_present_currBuy = true;
    boolean that_present_currBuy = true;
    if (this_present_currBuy || that_present_currBuy) {
      if (!(this_present_currBuy && that_present_currBuy))
        return false;
      if (this.currBuy != that.currBuy)
        return false;
    }
    boolean this_present_sellerSendTime = true;
    boolean that_present_sellerSendTime = true;
    if (this_present_sellerSendTime || that_present_sellerSendTime) {
      if (!(this_present_sellerSendTime && that_present_sellerSendTime))
        return false;
      if (this.sellerSendTime != that.sellerSendTime)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_product = true && (isSetProduct());
    builder.append(present_product);
    if (present_product)
      builder.append(product);
    boolean present_count = true;
    builder.append(present_count);
    if (present_count)
      builder.append(count);
    boolean present_lockCount = true;
    builder.append(present_lockCount);
    if (present_lockCount)
      builder.append(lockCount);
    boolean present_cartPrice = true && (isSetCartPrice());
    builder.append(present_cartPrice);
    if (present_cartPrice)
      builder.append(cartPrice);
    boolean present_totalCount = true;
    builder.append(present_totalCount);
    if (present_totalCount)
      builder.append(totalCount);
    boolean present_skuCount = true;
    builder.append(present_skuCount);
    if (present_skuCount)
      builder.append(skuCount);
    boolean present_wi = true && (isSetWi());
    builder.append(present_wi);
    if (present_wi)
      builder.append(wi);
    boolean present_currBuy = true;
    builder.append(present_currBuy);
    if (present_currBuy)
      builder.append(currBuy);
    boolean present_sellerSendTime = true;
    builder.append(present_sellerSendTime);
    if (present_sellerSendTime)
      builder.append(sellerSendTime);
    return builder.toHashCode();
  }

  public int compareTo(ItemPlus other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ItemPlus typedOther = (ItemPlus)other;

    lastComparison = Boolean.valueOf(isSetProduct()).compareTo(typedOther.isSetProduct());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProduct()) {
      lastComparison = TBaseHelper.compareTo(this.product, typedOther.product);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCount()).compareTo(typedOther.isSetCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCount()) {
      lastComparison = TBaseHelper.compareTo(this.count, typedOther.count);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLockCount()).compareTo(typedOther.isSetLockCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLockCount()) {
      lastComparison = TBaseHelper.compareTo(this.lockCount, typedOther.lockCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCartPrice()).compareTo(typedOther.isSetCartPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCartPrice()) {
      lastComparison = TBaseHelper.compareTo(this.cartPrice, typedOther.cartPrice);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalCount()).compareTo(typedOther.isSetTotalCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalCount()) {
      lastComparison = TBaseHelper.compareTo(this.totalCount, typedOther.totalCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSkuCount()).compareTo(typedOther.isSetSkuCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSkuCount()) {
      lastComparison = TBaseHelper.compareTo(this.skuCount, typedOther.skuCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetWi()).compareTo(typedOther.isSetWi());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWi()) {
      lastComparison = TBaseHelper.compareTo(this.wi, typedOther.wi);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurrBuy()).compareTo(typedOther.isSetCurrBuy());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrBuy()) {
      lastComparison = TBaseHelper.compareTo(this.currBuy, typedOther.currBuy);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSellerSendTime()).compareTo(typedOther.isSetSellerSendTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSellerSendTime()) {
      lastComparison = TBaseHelper.compareTo(this.sellerSendTime, typedOther.sellerSendTime);
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
        case 1: // PRODUCT
          if (field.type == TType.STRUCT) {
            this.product = new com.jfshare.finagle.thrift.product.Product();
            this.product.read(iprot);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // COUNT
          if (field.type == TType.I32) {
            this.count = iprot.readI32();
            setCountIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // LOCK_COUNT
          if (field.type == TType.I32) {
            this.lockCount = iprot.readI32();
            setLockCountIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // CART_PRICE
          if (field.type == TType.STRING) {
            this.cartPrice = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // TOTAL_COUNT
          if (field.type == TType.I32) {
            this.totalCount = iprot.readI32();
            setTotalCountIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 6: // SKU_COUNT
          if (field.type == TType.I32) {
            this.skuCount = iprot.readI32();
            setSkuCountIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 7: // WI
          if (field.type == TType.STRING) {
            this.wi = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 8: // CURR_BUY
          if (field.type == TType.I32) {
            this.currBuy = iprot.readI32();
            setCurrBuyIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 9: // SELLER_SEND_TIME
          if (field.type == TType.I32) {
            this.sellerSendTime = iprot.readI32();
            setSellerSendTimeIsSet(true);
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
    if (this.product != null) {
      oprot.writeFieldBegin(PRODUCT_FIELD_DESC);
      this.product.write(oprot);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(COUNT_FIELD_DESC);
    oprot.writeI32(this.count);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(LOCK_COUNT_FIELD_DESC);
    oprot.writeI32(this.lockCount);
    oprot.writeFieldEnd();
    if (this.cartPrice != null) {
      oprot.writeFieldBegin(CART_PRICE_FIELD_DESC);
      oprot.writeString(this.cartPrice);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(TOTAL_COUNT_FIELD_DESC);
    oprot.writeI32(this.totalCount);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(SKU_COUNT_FIELD_DESC);
    oprot.writeI32(this.skuCount);
    oprot.writeFieldEnd();
    if (this.wi != null) {
      oprot.writeFieldBegin(WI_FIELD_DESC);
      oprot.writeString(this.wi);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(CURR_BUY_FIELD_DESC);
    oprot.writeI32(this.currBuy);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(SELLER_SEND_TIME_FIELD_DESC);
    oprot.writeI32(this.sellerSendTime);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ItemPlus(");
    boolean first = true;
    sb.append("product:");
    if (this.product == null) {
      sb.append("null");
    } else {
      sb.append(this.product);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("count:");
    sb.append(this.count);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lockCount:");
    sb.append(this.lockCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("cartPrice:");
    if (this.cartPrice == null) {
      sb.append("null");
    } else {
      sb.append(this.cartPrice);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("totalCount:");
    sb.append(this.totalCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("skuCount:");
    sb.append(this.skuCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("wi:");
    if (this.wi == null) {
      sb.append("null");
    } else {
      sb.append(this.wi);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("currBuy:");
    sb.append(this.currBuy);
    first = false;
    if (!first) sb.append(", ");
    sb.append("sellerSendTime:");
    sb.append(this.sellerSendTime);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
