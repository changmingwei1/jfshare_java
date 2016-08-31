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

public class ThirdPartyProductLog implements TBase<ThirdPartyProductLog, ThirdPartyProductLog._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ThirdPartyProductLog");

  private static final TField PRODUCT_STATE_FIELD_DESC = new TField("productState", TType.I32, (short)1);
  private static final TField CUR_PRICE_FIELD_DESC = new TField("curPrice", TType.STRING, (short)2);
  private static final TField UPDATE_TIME_FIELD_DESC = new TField("updateTime", TType.STRING, (short)3);


  public int productState;
  public String curPrice;
  public String updateTime;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    PRODUCT_STATE((short)1, "productState"),
    CUR_PRICE((short)2, "curPrice"),
    UPDATE_TIME((short)3, "updateTime");
  
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
        case 1: // PRODUCT_STATE
  	return PRODUCT_STATE;
        case 2: // CUR_PRICE
  	return CUR_PRICE;
        case 3: // UPDATE_TIME
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
  private static final int __PRODUCTSTATE_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT_STATE, new FieldMetaData("productState", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.CUR_PRICE, new FieldMetaData("curPrice", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.UPDATE_TIME, new FieldMetaData("updateTime", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ThirdPartyProductLog.class, metaDataMap);
  }


  public ThirdPartyProductLog() {
  }

  public ThirdPartyProductLog(
    int productState,
    String curPrice,
    String updateTime)
  {
    this();
    this.productState = productState;
    setProductStateIsSet(true);
    this.curPrice = curPrice;
    this.updateTime = updateTime;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ThirdPartyProductLog(ThirdPartyProductLog other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.productState = other.productState;
    if (other.isSetCurPrice()) {
      this.curPrice = other.curPrice;
    }
    if (other.isSetUpdateTime()) {
      this.updateTime = other.updateTime;
    }
  }

  public ThirdPartyProductLog deepCopy() {
    return new ThirdPartyProductLog(this);
  }

  @Override
  public void clear() {
    setProductStateIsSet(false);
    this.productState = 0;
    this.curPrice = null;
    this.updateTime = null;
  }

  public int getProductState() {
    return this.productState;
  }

  public ThirdPartyProductLog setProductState(int productState) {
    this.productState = productState;
    setProductStateIsSet(true);

    return this;
  }

  public void unsetProductState() {
  __isset_bit_vector.clear(__PRODUCTSTATE_ISSET_ID);
  }

  /** Returns true if field productState is set (has been asigned a value) and false otherwise */
  public boolean isSetProductState() {
    return __isset_bit_vector.get(__PRODUCTSTATE_ISSET_ID);
  }

  public void setProductStateIsSet(boolean value) {
    __isset_bit_vector.set(__PRODUCTSTATE_ISSET_ID, value);
  }

  public String getCurPrice() {
    return this.curPrice;
  }

  public ThirdPartyProductLog setCurPrice(String curPrice) {
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

  public String getUpdateTime() {
    return this.updateTime;
  }

  public ThirdPartyProductLog setUpdateTime(String updateTime) {
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
    case PRODUCT_STATE:
      if (value == null) {
        unsetProductState();
      } else {
        setProductState((Integer)value);
      }
      break;
    case CUR_PRICE:
      if (value == null) {
        unsetCurPrice();
      } else {
        setCurPrice((String)value);
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
    case PRODUCT_STATE:
      return new Integer(getProductState());
    case CUR_PRICE:
      return getCurPrice();
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
    case PRODUCT_STATE:
      return isSetProductState();
    case CUR_PRICE:
      return isSetCurPrice();
    case UPDATE_TIME:
      return isSetUpdateTime();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ThirdPartyProductLog)
      return this.equals((ThirdPartyProductLog)that);
    return false;
  }

  public boolean equals(ThirdPartyProductLog that) {
    if (that == null)
      return false;
    boolean this_present_productState = true;
    boolean that_present_productState = true;
    if (this_present_productState || that_present_productState) {
      if (!(this_present_productState && that_present_productState))
        return false;
      if (this.productState != that.productState)
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
    boolean present_productState = true;
    builder.append(present_productState);
    if (present_productState)
      builder.append(productState);
    boolean present_curPrice = true && (isSetCurPrice());
    builder.append(present_curPrice);
    if (present_curPrice)
      builder.append(curPrice);
    boolean present_updateTime = true && (isSetUpdateTime());
    builder.append(present_updateTime);
    if (present_updateTime)
      builder.append(updateTime);
    return builder.toHashCode();
  }

  public int compareTo(ThirdPartyProductLog other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ThirdPartyProductLog typedOther = (ThirdPartyProductLog)other;

    lastComparison = Boolean.valueOf(isSetProductState()).compareTo(typedOther.isSetProductState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductState()) {
      lastComparison = TBaseHelper.compareTo(this.productState, typedOther.productState);
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
        case 1: // PRODUCT_STATE
          if (field.type == TType.I32) {
            this.productState = iprot.readI32();
            setProductStateIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // CUR_PRICE
          if (field.type == TType.STRING) {
            this.curPrice = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // UPDATE_TIME
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
    oprot.writeFieldBegin(PRODUCT_STATE_FIELD_DESC);
    oprot.writeI32(this.productState);
    oprot.writeFieldEnd();
    if (this.curPrice != null) {
      oprot.writeFieldBegin(CUR_PRICE_FIELD_DESC);
      oprot.writeString(this.curPrice);
      oprot.writeFieldEnd();
    }
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
    StringBuilder sb = new StringBuilder("ThirdPartyProductLog(");
    boolean first = true;
    sb.append("productState:");
    sb.append(this.productState);
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