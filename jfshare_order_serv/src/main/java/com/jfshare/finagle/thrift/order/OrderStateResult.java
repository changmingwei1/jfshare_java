/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.order;

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

public class OrderStateResult implements TBase<OrderStateResult, OrderStateResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("OrderStateResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField ORDER_COUNT_LIST_FIELD_DESC = new TField("orderCountList", TType.LIST, (short)2);


  public com.jfshare.finagle.thrift.result.Result result;
  public List<OrderCount> orderCountList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    ORDER_COUNT_LIST((short)2, "orderCountList");
  
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
        case 2: // ORDER_COUNT_LIST
  	return ORDER_COUNT_LIST;
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
    tmpMap.put(_Fields.ORDER_COUNT_LIST, new FieldMetaData("orderCountList", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, OrderCount.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(OrderStateResult.class, metaDataMap);
  }


  public OrderStateResult() {
  }

  public OrderStateResult(
    com.jfshare.finagle.thrift.result.Result result,
    List<OrderCount> orderCountList)
  {
    this();
    this.result = result;
    this.orderCountList = orderCountList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OrderStateResult(OrderStateResult other) {
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetOrderCountList()) {
      List<OrderCount> __this__orderCountList = new ArrayList<OrderCount>();
      for (OrderCount other_element : other.orderCountList) {
        __this__orderCountList.add(new OrderCount(other_element));
      }
      this.orderCountList = __this__orderCountList;
    }
  }

  public OrderStateResult deepCopy() {
    return new OrderStateResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.orderCountList = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public OrderStateResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public int getOrderCountListSize() {
    return (this.orderCountList == null) ? 0 : this.orderCountList.size();
  }

  public java.util.Iterator<OrderCount> getOrderCountListIterator() {
    return (this.orderCountList == null) ? null : this.orderCountList.iterator();
  }

  public void addToOrderCountList(OrderCount elem) {
    if (this.orderCountList == null) {
      this.orderCountList = new ArrayList<OrderCount>();
    }
    this.orderCountList.add(elem);
  }

  public List<OrderCount> getOrderCountList() {
    return this.orderCountList;
  }

  public OrderStateResult setOrderCountList(List<OrderCount> orderCountList) {
    this.orderCountList = orderCountList;
    
    return this;
  }

  public void unsetOrderCountList() {
    this.orderCountList = null;
  }

  /** Returns true if field orderCountList is set (has been asigned a value) and false otherwise */
  public boolean isSetOrderCountList() {
    return this.orderCountList != null;
  }

  public void setOrderCountListIsSet(boolean value) {
    if (!value) {
      this.orderCountList = null;
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
    case ORDER_COUNT_LIST:
      if (value == null) {
        unsetOrderCountList();
      } else {
        setOrderCountList((List<OrderCount>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case ORDER_COUNT_LIST:
      return getOrderCountList();
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
    case ORDER_COUNT_LIST:
      return isSetOrderCountList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof OrderStateResult)
      return this.equals((OrderStateResult)that);
    return false;
  }

  public boolean equals(OrderStateResult that) {
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
    boolean this_present_orderCountList = true && this.isSetOrderCountList();
    boolean that_present_orderCountList = true && that.isSetOrderCountList();
    if (this_present_orderCountList || that_present_orderCountList) {
      if (!(this_present_orderCountList && that_present_orderCountList))
        return false;
      if (!this.orderCountList.equals(that.orderCountList))
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
    boolean present_orderCountList = true && (isSetOrderCountList());
    builder.append(present_orderCountList);
    if (present_orderCountList)
      builder.append(orderCountList);
    return builder.toHashCode();
  }

  public int compareTo(OrderStateResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    OrderStateResult typedOther = (OrderStateResult)other;

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
    lastComparison = Boolean.valueOf(isSetOrderCountList()).compareTo(typedOther.isSetOrderCountList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderCountList()) {
      lastComparison = TBaseHelper.compareTo(this.orderCountList, typedOther.orderCountList);
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
        case 2: // ORDER_COUNT_LIST
          if (field.type == TType.LIST) {
            {
            TList _list16 = iprot.readListBegin();
            this.orderCountList = new ArrayList<OrderCount>(_list16.size);
            for (int _i17 = 0; _i17 < _list16.size; ++_i17)
            {
              OrderCount _elem18;
              _elem18 = new OrderCount();
              _elem18.read(iprot);
              this.orderCountList.add(_elem18);
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
    if (this.result != null) {
      oprot.writeFieldBegin(RESULT_FIELD_DESC);
      this.result.write(oprot);
      oprot.writeFieldEnd();
    }
    if (this.orderCountList != null) {
      oprot.writeFieldBegin(ORDER_COUNT_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.orderCountList.size()));
        for (OrderCount _iter19 : this.orderCountList)
        {
          _iter19.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("OrderStateResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("orderCountList:");
    if (this.orderCountList == null) {
      sb.append("null");
    } else {
      sb.append(this.orderCountList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
