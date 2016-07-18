/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.aftersale;

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

public class AfterSaleQueryParam implements TBase<AfterSaleQueryParam, AfterSaleQueryParam._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("AfterSaleQueryParam");

  private static final TField USER_ID_FIELD_DESC = new TField("userId", TType.I32, (short)1);
  private static final TField SELLER_ID_FIELD_DESC = new TField("sellerId", TType.I32, (short)2);
  private static final TField ORDER_ID_FIELD_DESC = new TField("orderId", TType.STRING, (short)3);
  private static final TField PRODUCT_ID_FIELD_DESC = new TField("productId", TType.STRING, (short)4);
  private static final TField TYPE_FIELD_DESC = new TField("type", TType.I32, (short)5);
  private static final TField STATE_FIELD_DESC = new TField("state", TType.I32, (short)6);
  private static final TField SKU_NUM_FIELD_DESC = new TField("skuNum", TType.STRING, (short)7);
  private static final TField ORDER_ID_LIST_FIELD_DESC = new TField("orderIdList", TType.LIST, (short)8);


  public int userId;
  public int sellerId;
  public String orderId;
  public String productId;
  public int type;
  public int state;
  public String skuNum;
  public List<String> orderIdList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    USER_ID((short)1, "userId"),
    SELLER_ID((short)2, "sellerId"),
    ORDER_ID((short)3, "orderId"),
    PRODUCT_ID((short)4, "productId"),
    TYPE((short)5, "type"),
    STATE((short)6, "state"),
    SKU_NUM((short)7, "skuNum"),
    ORDER_ID_LIST((short)8, "orderIdList");
  
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
        case 1: // USER_ID
  	return USER_ID;
        case 2: // SELLER_ID
  	return SELLER_ID;
        case 3: // ORDER_ID
  	return ORDER_ID;
        case 4: // PRODUCT_ID
  	return PRODUCT_ID;
        case 5: // TYPE
  	return TYPE;
        case 6: // STATE
  	return STATE;
        case 7: // SKU_NUM
  	return SKU_NUM;
        case 8: // ORDER_ID_LIST
  	return ORDER_ID_LIST;
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
  private static final int __USERID_ISSET_ID = 0;
  private static final int __SELLERID_ISSET_ID = 1;
  private static final int __TYPE_ISSET_ID = 2;
  private static final int __STATE_ISSET_ID = 3;
  private BitSet __isset_bit_vector = new BitSet(4);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_ID, new FieldMetaData("userId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.SELLER_ID, new FieldMetaData("sellerId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.ORDER_ID, new FieldMetaData("orderId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.PRODUCT_ID, new FieldMetaData("productId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.TYPE, new FieldMetaData("type", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.STATE, new FieldMetaData("state", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.SKU_NUM, new FieldMetaData("skuNum", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.ORDER_ID_LIST, new FieldMetaData("orderIdList", TFieldRequirementType.OPTIONAL,
      new ListMetaData(TType.LIST,
                new FieldValueMetaData(TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(AfterSaleQueryParam.class, metaDataMap);
  }


  public AfterSaleQueryParam() {
  }

  public AfterSaleQueryParam(
    int userId,
    int sellerId,
    String orderId,
    String productId,
    int type,
    int state,
    String skuNum)
  {
    this();
    this.userId = userId;
    setUserIdIsSet(true);
    this.sellerId = sellerId;
    setSellerIdIsSet(true);
    this.orderId = orderId;
    this.productId = productId;
    this.type = type;
    setTypeIsSet(true);
    this.state = state;
    setStateIsSet(true);
    this.skuNum = skuNum;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AfterSaleQueryParam(AfterSaleQueryParam other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.userId = other.userId;
    this.sellerId = other.sellerId;
    if (other.isSetOrderId()) {
      this.orderId = other.orderId;
    }
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    this.type = other.type;
    this.state = other.state;
    if (other.isSetSkuNum()) {
      this.skuNum = other.skuNum;
    }
    if (other.isSetOrderIdList()) {
      List<String> __this__orderIdList = new ArrayList<String>();
      for (String other_element : other.orderIdList) {
        __this__orderIdList.add(other_element);
      }
      this.orderIdList = __this__orderIdList;
    }
  }

  public AfterSaleQueryParam deepCopy() {
    return new AfterSaleQueryParam(this);
  }

  @Override
  public void clear() {
    setUserIdIsSet(false);
    this.userId = 0;
    setSellerIdIsSet(false);
    this.sellerId = 0;
    this.orderId = null;
    this.productId = null;
    setTypeIsSet(false);
    this.type = 0;
    setStateIsSet(false);
    this.state = 0;
    this.skuNum = null;
    this.orderIdList = null;
  }

  public int getUserId() {
    return this.userId;
  }

  public AfterSaleQueryParam setUserId(int userId) {
    this.userId = userId;
    setUserIdIsSet(true);

    return this;
  }

  public void unsetUserId() {
  __isset_bit_vector.clear(__USERID_ISSET_ID);
  }

  /** Returns true if field userId is set (has been asigned a value) and false otherwise */
  public boolean isSetUserId() {
    return __isset_bit_vector.get(__USERID_ISSET_ID);
  }

  public void setUserIdIsSet(boolean value) {
    __isset_bit_vector.set(__USERID_ISSET_ID, value);
  }

  public int getSellerId() {
    return this.sellerId;
  }

  public AfterSaleQueryParam setSellerId(int sellerId) {
    this.sellerId = sellerId;
    setSellerIdIsSet(true);

    return this;
  }

  public void unsetSellerId() {
  __isset_bit_vector.clear(__SELLERID_ISSET_ID);
  }

  /** Returns true if field sellerId is set (has been asigned a value) and false otherwise */
  public boolean isSetSellerId() {
    return __isset_bit_vector.get(__SELLERID_ISSET_ID);
  }

  public void setSellerIdIsSet(boolean value) {
    __isset_bit_vector.set(__SELLERID_ISSET_ID, value);
  }

  public String getOrderId() {
    return this.orderId;
  }

  public AfterSaleQueryParam setOrderId(String orderId) {
    this.orderId = orderId;
    
    return this;
  }

  public void unsetOrderId() {
    this.orderId = null;
  }

  /** Returns true if field orderId is set (has been asigned a value) and false otherwise */
  public boolean isSetOrderId() {
    return this.orderId != null;
  }

  public void setOrderIdIsSet(boolean value) {
    if (!value) {
      this.orderId = null;
    }
  }

  public String getProductId() {
    return this.productId;
  }

  public AfterSaleQueryParam setProductId(String productId) {
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

  public int getType() {
    return this.type;
  }

  public AfterSaleQueryParam setType(int type) {
    this.type = type;
    setTypeIsSet(true);

    return this;
  }

  public void unsetType() {
  __isset_bit_vector.clear(__TYPE_ISSET_ID);
  }

  /** Returns true if field type is set (has been asigned a value) and false otherwise */
  public boolean isSetType() {
    return __isset_bit_vector.get(__TYPE_ISSET_ID);
  }

  public void setTypeIsSet(boolean value) {
    __isset_bit_vector.set(__TYPE_ISSET_ID, value);
  }

  public int getState() {
    return this.state;
  }

  public AfterSaleQueryParam setState(int state) {
    this.state = state;
    setStateIsSet(true);

    return this;
  }

  public void unsetState() {
  __isset_bit_vector.clear(__STATE_ISSET_ID);
  }

  /** Returns true if field state is set (has been asigned a value) and false otherwise */
  public boolean isSetState() {
    return __isset_bit_vector.get(__STATE_ISSET_ID);
  }

  public void setStateIsSet(boolean value) {
    __isset_bit_vector.set(__STATE_ISSET_ID, value);
  }

  public String getSkuNum() {
    return this.skuNum;
  }

  public AfterSaleQueryParam setSkuNum(String skuNum) {
    this.skuNum = skuNum;
    
    return this;
  }

  public void unsetSkuNum() {
    this.skuNum = null;
  }

  /** Returns true if field skuNum is set (has been asigned a value) and false otherwise */
  public boolean isSetSkuNum() {
    return this.skuNum != null;
  }

  public void setSkuNumIsSet(boolean value) {
    if (!value) {
      this.skuNum = null;
    }
  }

  public int getOrderIdListSize() {
    return (this.orderIdList == null) ? 0 : this.orderIdList.size();
  }

  public java.util.Iterator<String> getOrderIdListIterator() {
    return (this.orderIdList == null) ? null : this.orderIdList.iterator();
  }

  public void addToOrderIdList(String elem) {
    if (this.orderIdList == null) {
      this.orderIdList = new ArrayList<String>();
    }
    this.orderIdList.add(elem);
  }

  public List<String> getOrderIdList() {
    return this.orderIdList;
  }

  public AfterSaleQueryParam setOrderIdList(List<String> orderIdList) {
    this.orderIdList = orderIdList;
    
    return this;
  }

  public void unsetOrderIdList() {
    this.orderIdList = null;
  }

  /** Returns true if field orderIdList is set (has been asigned a value) and false otherwise */
  public boolean isSetOrderIdList() {
    return this.orderIdList != null;
  }

  public void setOrderIdListIsSet(boolean value) {
    if (!value) {
      this.orderIdList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((Integer)value);
      }
      break;
    case SELLER_ID:
      if (value == null) {
        unsetSellerId();
      } else {
        setSellerId((Integer)value);
      }
      break;
    case ORDER_ID:
      if (value == null) {
        unsetOrderId();
      } else {
        setOrderId((String)value);
      }
      break;
    case PRODUCT_ID:
      if (value == null) {
        unsetProductId();
      } else {
        setProductId((String)value);
      }
      break;
    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((Integer)value);
      }
      break;
    case STATE:
      if (value == null) {
        unsetState();
      } else {
        setState((Integer)value);
      }
      break;
    case SKU_NUM:
      if (value == null) {
        unsetSkuNum();
      } else {
        setSkuNum((String)value);
      }
      break;
    case ORDER_ID_LIST:
      if (value == null) {
        unsetOrderIdList();
      } else {
        setOrderIdList((List<String>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return new Integer(getUserId());
    case SELLER_ID:
      return new Integer(getSellerId());
    case ORDER_ID:
      return getOrderId();
    case PRODUCT_ID:
      return getProductId();
    case TYPE:
      return new Integer(getType());
    case STATE:
      return new Integer(getState());
    case SKU_NUM:
      return getSkuNum();
    case ORDER_ID_LIST:
      return getOrderIdList();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case USER_ID:
      return isSetUserId();
    case SELLER_ID:
      return isSetSellerId();
    case ORDER_ID:
      return isSetOrderId();
    case PRODUCT_ID:
      return isSetProductId();
    case TYPE:
      return isSetType();
    case STATE:
      return isSetState();
    case SKU_NUM:
      return isSetSkuNum();
    case ORDER_ID_LIST:
      return isSetOrderIdList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AfterSaleQueryParam)
      return this.equals((AfterSaleQueryParam)that);
    return false;
  }

  public boolean equals(AfterSaleQueryParam that) {
    if (that == null)
      return false;
    boolean this_present_userId = true;
    boolean that_present_userId = true;
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (this.userId != that.userId)
        return false;
    }
    boolean this_present_sellerId = true;
    boolean that_present_sellerId = true;
    if (this_present_sellerId || that_present_sellerId) {
      if (!(this_present_sellerId && that_present_sellerId))
        return false;
      if (this.sellerId != that.sellerId)
        return false;
    }
    boolean this_present_orderId = true && this.isSetOrderId();
    boolean that_present_orderId = true && that.isSetOrderId();
    if (this_present_orderId || that_present_orderId) {
      if (!(this_present_orderId && that_present_orderId))
        return false;
      if (!this.orderId.equals(that.orderId))
        return false;
    }
    boolean this_present_productId = true && this.isSetProductId();
    boolean that_present_productId = true && that.isSetProductId();
    if (this_present_productId || that_present_productId) {
      if (!(this_present_productId && that_present_productId))
        return false;
      if (!this.productId.equals(that.productId))
        return false;
    }
    boolean this_present_type = true;
    boolean that_present_type = true;
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (this.type != that.type)
        return false;
    }
    boolean this_present_state = true;
    boolean that_present_state = true;
    if (this_present_state || that_present_state) {
      if (!(this_present_state && that_present_state))
        return false;
      if (this.state != that.state)
        return false;
    }
    boolean this_present_skuNum = true && this.isSetSkuNum();
    boolean that_present_skuNum = true && that.isSetSkuNum();
    if (this_present_skuNum || that_present_skuNum) {
      if (!(this_present_skuNum && that_present_skuNum))
        return false;
      if (!this.skuNum.equals(that.skuNum))
        return false;
    }
    boolean this_present_orderIdList = true && this.isSetOrderIdList();
    boolean that_present_orderIdList = true && that.isSetOrderIdList();
    if (this_present_orderIdList || that_present_orderIdList) {
      if (!(this_present_orderIdList && that_present_orderIdList))
        return false;
      if (!this.orderIdList.equals(that.orderIdList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_userId = true;
    builder.append(present_userId);
    if (present_userId)
      builder.append(userId);
    boolean present_sellerId = true;
    builder.append(present_sellerId);
    if (present_sellerId)
      builder.append(sellerId);
    boolean present_orderId = true && (isSetOrderId());
    builder.append(present_orderId);
    if (present_orderId)
      builder.append(orderId);
    boolean present_productId = true && (isSetProductId());
    builder.append(present_productId);
    if (present_productId)
      builder.append(productId);
    boolean present_type = true;
    builder.append(present_type);
    if (present_type)
      builder.append(type);
    boolean present_state = true;
    builder.append(present_state);
    if (present_state)
      builder.append(state);
    boolean present_skuNum = true && (isSetSkuNum());
    builder.append(present_skuNum);
    if (present_skuNum)
      builder.append(skuNum);
    boolean present_orderIdList = true && (isSetOrderIdList());
    builder.append(present_orderIdList);
    if (present_orderIdList)
      builder.append(orderIdList);
    return builder.toHashCode();
  }

  public int compareTo(AfterSaleQueryParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    AfterSaleQueryParam typedOther = (AfterSaleQueryParam)other;

    lastComparison = Boolean.valueOf(isSetUserId()).compareTo(typedOther.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = TBaseHelper.compareTo(this.userId, typedOther.userId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSellerId()).compareTo(typedOther.isSetSellerId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSellerId()) {
      lastComparison = TBaseHelper.compareTo(this.sellerId, typedOther.sellerId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrderId()).compareTo(typedOther.isSetOrderId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderId()) {
      lastComparison = TBaseHelper.compareTo(this.orderId, typedOther.orderId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = Boolean.valueOf(isSetType()).compareTo(typedOther.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = TBaseHelper.compareTo(this.type, typedOther.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetState()).compareTo(typedOther.isSetState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetState()) {
      lastComparison = TBaseHelper.compareTo(this.state, typedOther.state);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSkuNum()).compareTo(typedOther.isSetSkuNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSkuNum()) {
      lastComparison = TBaseHelper.compareTo(this.skuNum, typedOther.skuNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrderIdList()).compareTo(typedOther.isSetOrderIdList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderIdList()) {
      lastComparison = TBaseHelper.compareTo(this.orderIdList, typedOther.orderIdList);
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
        case 1: // USER_ID
          if (field.type == TType.I32) {
            this.userId = iprot.readI32();
            setUserIdIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // SELLER_ID
          if (field.type == TType.I32) {
            this.sellerId = iprot.readI32();
            setSellerIdIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // ORDER_ID
          if (field.type == TType.STRING) {
            this.orderId = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // PRODUCT_ID
          if (field.type == TType.STRING) {
            this.productId = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // TYPE
          if (field.type == TType.I32) {
            this.type = iprot.readI32();
            setTypeIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 6: // STATE
          if (field.type == TType.I32) {
            this.state = iprot.readI32();
            setStateIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 7: // SKU_NUM
          if (field.type == TType.STRING) {
            this.skuNum = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 8: // ORDER_ID_LIST
          if (field.type == TType.LIST) {
            {
            TList _list0 = iprot.readListBegin();
            this.orderIdList = new ArrayList<String>(_list0.size);
            for (int _i1 = 0; _i1 < _list0.size; ++_i1)
            {
              String _elem2;
              _elem2 = iprot.readString();
              this.orderIdList.add(_elem2);
            }
            iprot.readListEnd();
            }
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
    oprot.writeFieldBegin(USER_ID_FIELD_DESC);
    oprot.writeI32(this.userId);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(SELLER_ID_FIELD_DESC);
    oprot.writeI32(this.sellerId);
    oprot.writeFieldEnd();
    if (this.orderId != null) {
      oprot.writeFieldBegin(ORDER_ID_FIELD_DESC);
      oprot.writeString(this.orderId);
      oprot.writeFieldEnd();
    }
    if (this.productId != null) {
      oprot.writeFieldBegin(PRODUCT_ID_FIELD_DESC);
      oprot.writeString(this.productId);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(TYPE_FIELD_DESC);
    oprot.writeI32(this.type);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(STATE_FIELD_DESC);
    oprot.writeI32(this.state);
    oprot.writeFieldEnd();
    if (this.skuNum != null) {
      oprot.writeFieldBegin(SKU_NUM_FIELD_DESC);
      oprot.writeString(this.skuNum);
      oprot.writeFieldEnd();
    }
    if (this.orderIdList != null) {
      if (isSetOrderIdList()) {
        oprot.writeFieldBegin(ORDER_ID_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new TList(TType.STRING, this.orderIdList.size()));
          for (String _iter3 : this.orderIdList)
          {
            oprot.writeString(_iter3);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("AfterSaleQueryParam(");
    boolean first = true;
    sb.append("userId:");
    sb.append(this.userId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("sellerId:");
    sb.append(this.sellerId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("orderId:");
    if (this.orderId == null) {
      sb.append("null");
    } else {
      sb.append(this.orderId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("productId:");
    if (this.productId == null) {
      sb.append("null");
    } else {
      sb.append(this.productId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    sb.append(this.type);
    first = false;
    if (!first) sb.append(", ");
    sb.append("state:");
    sb.append(this.state);
    first = false;
    if (!first) sb.append(", ");
    sb.append("skuNum:");
    if (this.skuNum == null) {
      sb.append("null");
    } else {
      sb.append(this.skuNum);
    }
    first = false;
    if (isSetOrderIdList()) {
      if (!first) sb.append(", ");
      sb.append("orderIdList:");
      if (this.orderIdList == null) {
        sb.append("null");
      } else {
        sb.append(this.orderIdList);
      }
      first = false;
      }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
