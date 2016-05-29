/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.seller;

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

public class SellerResult implements TBase<SellerResult, SellerResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("SellerResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField SELLER_FIELD_DESC = new TField("seller", TType.STRUCT, (short)2);
  private static final TField LOGIN_LOG_FIELD_DESC = new TField("loginLog", TType.STRUCT, (short)3);
  private static final TField VALUE_FIELD_DESC = new TField("value", TType.BOOL, (short)4);


  public com.jfshare.finagle.thrift.result.Result result;
  public Seller seller;
  public LoginLog loginLog;
  public boolean value;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    SELLER((short)2, "seller"),
    LOGIN_LOG((short)3, "loginLog"),
    VALUE((short)4, "value");
  
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
        case 1: // RESULT
  	return RESULT;
        case 2: // SELLER
  	return SELLER;
        case 3: // LOGIN_LOG
  	return LOGIN_LOG;
        case 4: // VALUE
  	return VALUE;
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
  private static final int __VALUE_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESULT, new FieldMetaData("result", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.result.Result.class)));
    tmpMap.put(_Fields.SELLER, new FieldMetaData("seller", TFieldRequirementType.OPTIONAL,
      new StructMetaData(TType.STRUCT, Seller.class)));
    tmpMap.put(_Fields.LOGIN_LOG, new FieldMetaData("loginLog", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, LoginLog.class)));
    tmpMap.put(_Fields.VALUE, new FieldMetaData("value", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(SellerResult.class, metaDataMap);
  }


  public SellerResult() {
  }

  public SellerResult(
    com.jfshare.finagle.thrift.result.Result result,
    LoginLog loginLog)
  {
    this();
    this.result = result;
    this.loginLog = loginLog;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SellerResult(SellerResult other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetSeller()) {
      this.seller = new Seller(other.seller);
    }
    if (other.isSetLoginLog()) {
      this.loginLog = new LoginLog(other.loginLog);
    }
    this.value = other.value;
  }

  public SellerResult deepCopy() {
    return new SellerResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.seller = null;
    this.loginLog = null;
    setValueIsSet(false);
    this.value = false;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public SellerResult setResult(com.jfshare.finagle.thrift.result.Result result) {
    this.result = result;
    
    return this;
  }

  public void unsetResult() {
    this.result = null;
  }

  /** Returns true if field result is set (has been asigned a value) and false otherwise */
  public boolean isSetResult() {
    return this.result != null;
  }

  public void setResultIsSet(boolean value) {
    if (!value) {
      this.result = null;
    }
  }

  public Seller getSeller() {
    return this.seller;
  }

  public SellerResult setSeller(Seller seller) {
    this.seller = seller;
    
    return this;
  }

  public void unsetSeller() {
    this.seller = null;
  }

  /** Returns true if field seller is set (has been asigned a value) and false otherwise */
  public boolean isSetSeller() {
    return this.seller != null;
  }

  public void setSellerIsSet(boolean value) {
    if (!value) {
      this.seller = null;
    }
  }

  public LoginLog getLoginLog() {
    return this.loginLog;
  }

  public SellerResult setLoginLog(LoginLog loginLog) {
    this.loginLog = loginLog;
    
    return this;
  }

  public void unsetLoginLog() {
    this.loginLog = null;
  }

  /** Returns true if field loginLog is set (has been asigned a value) and false otherwise */
  public boolean isSetLoginLog() {
    return this.loginLog != null;
  }

  public void setLoginLogIsSet(boolean value) {
    if (!value) {
      this.loginLog = null;
    }
  }

  public boolean isValue() {
    return this.value;
  }

  public SellerResult setValue(boolean value) {
    this.value = value;
    setValueIsSet(true);

    return this;
  }

  public void unsetValue() {
  __isset_bit_vector.clear(__VALUE_ISSET_ID);
  }

  /** Returns true if field value is set (has been asigned a value) and false otherwise */
  public boolean isSetValue() {
    return __isset_bit_vector.get(__VALUE_ISSET_ID);
  }

  public void setValueIsSet(boolean value) {
    __isset_bit_vector.set(__VALUE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RESULT:
      if (value == null) {
        unsetResult();
      } else {
        setResult((com.jfshare.finagle.thrift.result.Result)value);
      }
      break;
    case SELLER:
      if (value == null) {
        unsetSeller();
      } else {
        setSeller((Seller)value);
      }
      break;
    case LOGIN_LOG:
      if (value == null) {
        unsetLoginLog();
      } else {
        setLoginLog((LoginLog)value);
      }
      break;
    case VALUE:
      if (value == null) {
        unsetValue();
      } else {
        setValue((Boolean)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case SELLER:
      return getSeller();
    case LOGIN_LOG:
      return getLoginLog();
    case VALUE:
      return new Boolean(isValue());
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RESULT:
      return isSetResult();
    case SELLER:
      return isSetSeller();
    case LOGIN_LOG:
      return isSetLoginLog();
    case VALUE:
      return isSetValue();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SellerResult)
      return this.equals((SellerResult)that);
    return false;
  }

  public boolean equals(SellerResult that) {
    if (that == null)
      return false;
    boolean this_present_result = true && this.isSetResult();
    boolean that_present_result = true && that.isSetResult();
    if (this_present_result || that_present_result) {
      if (!(this_present_result && that_present_result))
        return false;
      if (!this.result.equals(that.result))
        return false;
    }
    boolean this_present_seller = true && this.isSetSeller();
    boolean that_present_seller = true && that.isSetSeller();
    if (this_present_seller || that_present_seller) {
      if (!(this_present_seller && that_present_seller))
        return false;
      if (!this.seller.equals(that.seller))
        return false;
    }
    boolean this_present_loginLog = true && this.isSetLoginLog();
    boolean that_present_loginLog = true && that.isSetLoginLog();
    if (this_present_loginLog || that_present_loginLog) {
      if (!(this_present_loginLog && that_present_loginLog))
        return false;
      if (!this.loginLog.equals(that.loginLog))
        return false;
    }
    boolean this_present_value = true && this.isSetValue();
    boolean that_present_value = true && that.isSetValue();
    if (this_present_value || that_present_value) {
      if (!(this_present_value && that_present_value))
        return false;
      if (this.value != that.value)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_result = true && (isSetResult());
    builder.append(present_result);
    if (present_result)
      builder.append(result);
    boolean present_seller = true && (isSetSeller());
    builder.append(present_seller);
    if (present_seller)
      builder.append(seller);
    boolean present_loginLog = true && (isSetLoginLog());
    builder.append(present_loginLog);
    if (present_loginLog)
      builder.append(loginLog);
    boolean present_value = true && (isSetValue());
    builder.append(present_value);
    if (present_value)
      builder.append(value);
    return builder.toHashCode();
  }

  public int compareTo(SellerResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    SellerResult typedOther = (SellerResult)other;

    lastComparison = Boolean.valueOf(isSetResult()).compareTo(typedOther.isSetResult());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResult()) {
      lastComparison = TBaseHelper.compareTo(this.result, typedOther.result);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSeller()).compareTo(typedOther.isSetSeller());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSeller()) {
      lastComparison = TBaseHelper.compareTo(this.seller, typedOther.seller);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLoginLog()).compareTo(typedOther.isSetLoginLog());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoginLog()) {
      lastComparison = TBaseHelper.compareTo(this.loginLog, typedOther.loginLog);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetValue()).compareTo(typedOther.isSetValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValue()) {
      lastComparison = TBaseHelper.compareTo(this.value, typedOther.value);
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
        case 1: // RESULT
          if (field.type == TType.STRUCT) {
            this.result = new com.jfshare.finagle.thrift.result.Result();
            this.result.read(iprot);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // SELLER
          if (field.type == TType.STRUCT) {
            this.seller = new Seller();
            this.seller.read(iprot);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // LOGIN_LOG
          if (field.type == TType.STRUCT) {
            this.loginLog = new LoginLog();
            this.loginLog.read(iprot);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // VALUE
          if (field.type == TType.BOOL) {
            this.value = iprot.readBool();
            setValueIsSet(true);
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
    if (this.result != null) {
      oprot.writeFieldBegin(RESULT_FIELD_DESC);
      this.result.write(oprot);
      oprot.writeFieldEnd();
    }
    if (this.seller != null) {
      if (isSetSeller()) {
        oprot.writeFieldBegin(SELLER_FIELD_DESC);
        this.seller.write(oprot);
        oprot.writeFieldEnd();
      }
    }
    if (this.loginLog != null) {
      oprot.writeFieldBegin(LOGIN_LOG_FIELD_DESC);
      this.loginLog.write(oprot);
      oprot.writeFieldEnd();
    }
    if (isSetValue()) {
      oprot.writeFieldBegin(VALUE_FIELD_DESC);
      oprot.writeBool(this.value);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("SellerResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (isSetSeller()) {
      if (!first) sb.append(", ");
      sb.append("seller:");
      if (this.seller == null) {
        sb.append("null");
      } else {
        sb.append(this.seller);
      }
      first = false;
      }
    if (!first) sb.append(", ");
    sb.append("loginLog:");
    if (this.loginLog == null) {
      sb.append("null");
    } else {
      sb.append(this.loginLog);
    }
    first = false;
    if (isSetValue()) {
      if (!first) sb.append(", ");
      sb.append("value:");
      sb.append(this.value);
      first = false;
      }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}