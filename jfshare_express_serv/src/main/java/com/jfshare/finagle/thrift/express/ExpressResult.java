/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.express;

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

public class ExpressResult implements TBase<ExpressResult, ExpressResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ExpressResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField EXPRESS_INFO_FIELD_DESC = new TField("expressInfo", TType.STRUCT, (short)2);
  private static final TField EXPRESS_TRACE_FIELD_DESC = new TField("expressTrace", TType.STRUCT, (short)3);


  public com.jfshare.finagle.thrift.result.Result result;
  public ExpressInfo expressInfo;
  public ExpressTrace expressTrace;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    EXPRESS_INFO((short)2, "expressInfo"),
    EXPRESS_TRACE((short)3, "expressTrace");
  
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
        case 2: // EXPRESS_INFO
  	return EXPRESS_INFO;
        case 3: // EXPRESS_TRACE
  	return EXPRESS_TRACE;
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

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESULT, new FieldMetaData("result", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.result.Result.class)));
    tmpMap.put(_Fields.EXPRESS_INFO, new FieldMetaData("expressInfo", TFieldRequirementType.OPTIONAL,
      new StructMetaData(TType.STRUCT, ExpressInfo.class)));
    tmpMap.put(_Fields.EXPRESS_TRACE, new FieldMetaData("expressTrace", TFieldRequirementType.OPTIONAL,
      new StructMetaData(TType.STRUCT, ExpressTrace.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ExpressResult.class, metaDataMap);
  }


  public ExpressResult() {
  }

  public ExpressResult(
    com.jfshare.finagle.thrift.result.Result result)
  {
    this();
    this.result = result;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ExpressResult(ExpressResult other) {
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetExpressInfo()) {
      this.expressInfo = new ExpressInfo(other.expressInfo);
    }
    if (other.isSetExpressTrace()) {
      this.expressTrace = new ExpressTrace(other.expressTrace);
    }
  }

  public ExpressResult deepCopy() {
    return new ExpressResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.expressInfo = null;
    this.expressTrace = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public ExpressResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public ExpressInfo getExpressInfo() {
    return this.expressInfo;
  }

  public ExpressResult setExpressInfo(ExpressInfo expressInfo) {
    this.expressInfo = expressInfo;
    
    return this;
  }

  public void unsetExpressInfo() {
    this.expressInfo = null;
  }

  /** Returns true if field expressInfo is set (has been asigned a value) and false otherwise */
  public boolean isSetExpressInfo() {
    return this.expressInfo != null;
  }

  public void setExpressInfoIsSet(boolean value) {
    if (!value) {
      this.expressInfo = null;
    }
  }

  public ExpressTrace getExpressTrace() {
    return this.expressTrace;
  }

  public ExpressResult setExpressTrace(ExpressTrace expressTrace) {
    this.expressTrace = expressTrace;
    
    return this;
  }

  public void unsetExpressTrace() {
    this.expressTrace = null;
  }

  /** Returns true if field expressTrace is set (has been asigned a value) and false otherwise */
  public boolean isSetExpressTrace() {
    return this.expressTrace != null;
  }

  public void setExpressTraceIsSet(boolean value) {
    if (!value) {
      this.expressTrace = null;
    }
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
    case EXPRESS_INFO:
      if (value == null) {
        unsetExpressInfo();
      } else {
        setExpressInfo((ExpressInfo)value);
      }
      break;
    case EXPRESS_TRACE:
      if (value == null) {
        unsetExpressTrace();
      } else {
        setExpressTrace((ExpressTrace)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case EXPRESS_INFO:
      return getExpressInfo();
    case EXPRESS_TRACE:
      return getExpressTrace();
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
    case EXPRESS_INFO:
      return isSetExpressInfo();
    case EXPRESS_TRACE:
      return isSetExpressTrace();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ExpressResult)
      return this.equals((ExpressResult)that);
    return false;
  }

  public boolean equals(ExpressResult that) {
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
    boolean this_present_expressInfo = true && this.isSetExpressInfo();
    boolean that_present_expressInfo = true && that.isSetExpressInfo();
    if (this_present_expressInfo || that_present_expressInfo) {
      if (!(this_present_expressInfo && that_present_expressInfo))
        return false;
      if (!this.expressInfo.equals(that.expressInfo))
        return false;
    }
    boolean this_present_expressTrace = true && this.isSetExpressTrace();
    boolean that_present_expressTrace = true && that.isSetExpressTrace();
    if (this_present_expressTrace || that_present_expressTrace) {
      if (!(this_present_expressTrace && that_present_expressTrace))
        return false;
      if (!this.expressTrace.equals(that.expressTrace))
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
    boolean present_expressInfo = true && (isSetExpressInfo());
    builder.append(present_expressInfo);
    if (present_expressInfo)
      builder.append(expressInfo);
    boolean present_expressTrace = true && (isSetExpressTrace());
    builder.append(present_expressTrace);
    if (present_expressTrace)
      builder.append(expressTrace);
    return builder.toHashCode();
  }

  public int compareTo(ExpressResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ExpressResult typedOther = (ExpressResult)other;

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
    lastComparison = Boolean.valueOf(isSetExpressInfo()).compareTo(typedOther.isSetExpressInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExpressInfo()) {
      lastComparison = TBaseHelper.compareTo(this.expressInfo, typedOther.expressInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetExpressTrace()).compareTo(typedOther.isSetExpressTrace());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExpressTrace()) {
      lastComparison = TBaseHelper.compareTo(this.expressTrace, typedOther.expressTrace);
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
        case 2: // EXPRESS_INFO
          if (field.type == TType.STRUCT) {
            this.expressInfo = new ExpressInfo();
            this.expressInfo.read(iprot);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // EXPRESS_TRACE
          if (field.type == TType.STRUCT) {
            this.expressTrace = new ExpressTrace();
            this.expressTrace.read(iprot);
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
    if (this.expressInfo != null) {
      if (isSetExpressInfo()) {
        oprot.writeFieldBegin(EXPRESS_INFO_FIELD_DESC);
        this.expressInfo.write(oprot);
        oprot.writeFieldEnd();
      }
    }
    if (this.expressTrace != null) {
      if (isSetExpressTrace()) {
        oprot.writeFieldBegin(EXPRESS_TRACE_FIELD_DESC);
        this.expressTrace.write(oprot);
        oprot.writeFieldEnd();
      }
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ExpressResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (isSetExpressInfo()) {
      if (!first) sb.append(", ");
      sb.append("expressInfo:");
      if (this.expressInfo == null) {
        sb.append("null");
      } else {
        sb.append(this.expressInfo);
      }
      first = false;
      }
    if (isSetExpressTrace()) {
      if (!first) sb.append(", ");
      sb.append("expressTrace:");
      if (this.expressTrace == null) {
        sb.append("null");
      } else {
        sb.append(this.expressTrace);
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
