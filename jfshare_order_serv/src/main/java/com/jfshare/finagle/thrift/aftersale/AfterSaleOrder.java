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

public class AfterSaleOrder implements TBase<AfterSaleOrder, AfterSaleOrder._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("AfterSaleOrder");

  private static final TField USER_ID_FIELD_DESC = new TField("userId", TType.I32, (short)1);
  private static final TField ORDER_ID_FIELD_DESC = new TField("orderId", TType.STRING, (short)2);
  private static final TField SELLER_ID_FIELD_DESC = new TField("sellerId", TType.I32, (short)3);


  public int userId;
  public String orderId;
  public int sellerId;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    USER_ID((short)1, "userId"),
    ORDER_ID((short)2, "orderId"),
    SELLER_ID((short)3, "sellerId");
  
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
        case 2: // ORDER_ID
  	return ORDER_ID;
        case 3: // SELLER_ID
  	return SELLER_ID;
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
  private BitSet __isset_bit_vector = new BitSet(2);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_ID, new FieldMetaData("userId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.ORDER_ID, new FieldMetaData("orderId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.SELLER_ID, new FieldMetaData("sellerId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(AfterSaleOrder.class, metaDataMap);
  }


  public AfterSaleOrder() {
  }

  public AfterSaleOrder(
    int userId,
    String orderId,
    int sellerId)
  {
    this();
    this.userId = userId;
    setUserIdIsSet(true);
    this.orderId = orderId;
    this.sellerId = sellerId;
    setSellerIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AfterSaleOrder(AfterSaleOrder other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.userId = other.userId;
    if (other.isSetOrderId()) {
      this.orderId = other.orderId;
    }
    this.sellerId = other.sellerId;
  }

  public AfterSaleOrder deepCopy() {
    return new AfterSaleOrder(this);
  }

  @Override
  public void clear() {
    setUserIdIsSet(false);
    this.userId = 0;
    this.orderId = null;
    setSellerIdIsSet(false);
    this.sellerId = 0;
  }

  public int getUserId() {
    return this.userId;
  }

  public AfterSaleOrder setUserId(int userId) {
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

  public String getOrderId() {
    return this.orderId;
  }

  public AfterSaleOrder setOrderId(String orderId) {
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

  public int getSellerId() {
    return this.sellerId;
  }

  public AfterSaleOrder setSellerId(int sellerId) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((Integer)value);
      }
      break;
    case ORDER_ID:
      if (value == null) {
        unsetOrderId();
      } else {
        setOrderId((String)value);
      }
      break;
    case SELLER_ID:
      if (value == null) {
        unsetSellerId();
      } else {
        setSellerId((Integer)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return new Integer(getUserId());
    case ORDER_ID:
      return getOrderId();
    case SELLER_ID:
      return new Integer(getSellerId());
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
    case ORDER_ID:
      return isSetOrderId();
    case SELLER_ID:
      return isSetSellerId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AfterSaleOrder)
      return this.equals((AfterSaleOrder)that);
    return false;
  }

  public boolean equals(AfterSaleOrder that) {
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
    boolean this_present_orderId = true && this.isSetOrderId();
    boolean that_present_orderId = true && that.isSetOrderId();
    if (this_present_orderId || that_present_orderId) {
      if (!(this_present_orderId && that_present_orderId))
        return false;
      if (!this.orderId.equals(that.orderId))
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

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_userId = true;
    builder.append(present_userId);
    if (present_userId)
      builder.append(userId);
    boolean present_orderId = true && (isSetOrderId());
    builder.append(present_orderId);
    if (present_orderId)
      builder.append(orderId);
    boolean present_sellerId = true;
    builder.append(present_sellerId);
    if (present_sellerId)
      builder.append(sellerId);
    return builder.toHashCode();
  }

  public int compareTo(AfterSaleOrder other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    AfterSaleOrder typedOther = (AfterSaleOrder)other;

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
        case 2: // ORDER_ID
          if (field.type == TType.STRING) {
            this.orderId = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // SELLER_ID
          if (field.type == TType.I32) {
            this.sellerId = iprot.readI32();
            setSellerIdIsSet(true);
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
    if (this.orderId != null) {
      oprot.writeFieldBegin(ORDER_ID_FIELD_DESC);
      oprot.writeString(this.orderId);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(SELLER_ID_FIELD_DESC);
    oprot.writeI32(this.sellerId);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("AfterSaleOrder(");
    boolean first = true;
    sb.append("userId:");
    sb.append(this.userId);
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
    sb.append("sellerId:");
    sb.append(this.sellerId);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
