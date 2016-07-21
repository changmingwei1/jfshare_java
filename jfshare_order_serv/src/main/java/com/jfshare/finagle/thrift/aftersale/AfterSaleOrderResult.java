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

public class AfterSaleOrderResult implements TBase<AfterSaleOrderResult, AfterSaleOrderResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("AfterSaleOrderResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField AFTER_SALE_ORDERS_FIELD_DESC = new TField("afterSaleOrders", TType.LIST, (short)2);
  private static final TField AFTER_SALE_LIST_FIELD_DESC = new TField("afterSaleList", TType.LIST, (short)3);
  private static final TField PAGINATION_FIELD_DESC = new TField("pagination", TType.STRUCT, (short)4);


  public com.jfshare.finagle.thrift.result.Result result;
  public List<AfterSaleOrder> afterSaleOrders;
  public List<AfterSale> afterSaleList;
  public com.jfshare.finagle.thrift.pagination.Pagination pagination;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    AFTER_SALE_ORDERS((short)2, "afterSaleOrders"),
    AFTER_SALE_LIST((short)3, "afterSaleList"),
    PAGINATION((short)4, "pagination");
  
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
        case 2: // AFTER_SALE_ORDERS
  	return AFTER_SALE_ORDERS;
        case 3: // AFTER_SALE_LIST
  	return AFTER_SALE_LIST;
        case 4: // PAGINATION
  	return PAGINATION;
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
    tmpMap.put(_Fields.AFTER_SALE_ORDERS, new FieldMetaData("afterSaleOrders", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, AfterSaleOrder.class))));
    tmpMap.put(_Fields.AFTER_SALE_LIST, new FieldMetaData("afterSaleList", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, AfterSale.class))));
    tmpMap.put(_Fields.PAGINATION, new FieldMetaData("pagination", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.pagination.Pagination.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(AfterSaleOrderResult.class, metaDataMap);
  }


  public AfterSaleOrderResult() {
  }

  public AfterSaleOrderResult(
    com.jfshare.finagle.thrift.result.Result result,
    List<AfterSaleOrder> afterSaleOrders,
    List<AfterSale> afterSaleList,
    com.jfshare.finagle.thrift.pagination.Pagination pagination)
  {
    this();
    this.result = result;
    this.afterSaleOrders = afterSaleOrders;
    this.afterSaleList = afterSaleList;
    this.pagination = pagination;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AfterSaleOrderResult(AfterSaleOrderResult other) {
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetAfterSaleOrders()) {
      List<AfterSaleOrder> __this__afterSaleOrders = new ArrayList<AfterSaleOrder>();
      for (AfterSaleOrder other_element : other.afterSaleOrders) {
        __this__afterSaleOrders.add(new AfterSaleOrder(other_element));
      }
      this.afterSaleOrders = __this__afterSaleOrders;
    }
    if (other.isSetAfterSaleList()) {
      List<AfterSale> __this__afterSaleList = new ArrayList<AfterSale>();
      for (AfterSale other_element : other.afterSaleList) {
        __this__afterSaleList.add(new AfterSale(other_element));
      }
      this.afterSaleList = __this__afterSaleList;
    }
    if (other.isSetPagination()) {
      this.pagination = new com.jfshare.finagle.thrift.pagination.Pagination(other.pagination);
    }
  }

  public AfterSaleOrderResult deepCopy() {
    return new AfterSaleOrderResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.afterSaleOrders = null;
    this.afterSaleList = null;
    this.pagination = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public AfterSaleOrderResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public int getAfterSaleOrdersSize() {
    return (this.afterSaleOrders == null) ? 0 : this.afterSaleOrders.size();
  }

  public java.util.Iterator<AfterSaleOrder> getAfterSaleOrdersIterator() {
    return (this.afterSaleOrders == null) ? null : this.afterSaleOrders.iterator();
  }

  public void addToAfterSaleOrders(AfterSaleOrder elem) {
    if (this.afterSaleOrders == null) {
      this.afterSaleOrders = new ArrayList<AfterSaleOrder>();
    }
    this.afterSaleOrders.add(elem);
  }

  public List<AfterSaleOrder> getAfterSaleOrders() {
    return this.afterSaleOrders;
  }

  public AfterSaleOrderResult setAfterSaleOrders(List<AfterSaleOrder> afterSaleOrders) {
    this.afterSaleOrders = afterSaleOrders;
    
    return this;
  }

  public void unsetAfterSaleOrders() {
    this.afterSaleOrders = null;
  }

  /** Returns true if field afterSaleOrders is set (has been asigned a value) and false otherwise */
  public boolean isSetAfterSaleOrders() {
    return this.afterSaleOrders != null;
  }

  public void setAfterSaleOrdersIsSet(boolean value) {
    if (!value) {
      this.afterSaleOrders = null;
    }
  }

  public int getAfterSaleListSize() {
    return (this.afterSaleList == null) ? 0 : this.afterSaleList.size();
  }

  public java.util.Iterator<AfterSale> getAfterSaleListIterator() {
    return (this.afterSaleList == null) ? null : this.afterSaleList.iterator();
  }

  public void addToAfterSaleList(AfterSale elem) {
    if (this.afterSaleList == null) {
      this.afterSaleList = new ArrayList<AfterSale>();
    }
    this.afterSaleList.add(elem);
  }

  public List<AfterSale> getAfterSaleList() {
    return this.afterSaleList;
  }

  public AfterSaleOrderResult setAfterSaleList(List<AfterSale> afterSaleList) {
    this.afterSaleList = afterSaleList;
    
    return this;
  }

  public void unsetAfterSaleList() {
    this.afterSaleList = null;
  }

  /** Returns true if field afterSaleList is set (has been asigned a value) and false otherwise */
  public boolean isSetAfterSaleList() {
    return this.afterSaleList != null;
  }

  public void setAfterSaleListIsSet(boolean value) {
    if (!value) {
      this.afterSaleList = null;
    }
  }

  public com.jfshare.finagle.thrift.pagination.Pagination getPagination() {
    return this.pagination;
  }

  public AfterSaleOrderResult setPagination(com.jfshare.finagle.thrift.pagination.Pagination pagination) {
    this.pagination = pagination;
    
    return this;
  }

  public void unsetPagination() {
    this.pagination = null;
  }

  /** Returns true if field pagination is set (has been asigned a value) and false otherwise */
  public boolean isSetPagination() {
    return this.pagination != null;
  }

  public void setPaginationIsSet(boolean value) {
    if (!value) {
      this.pagination = null;
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
    case AFTER_SALE_ORDERS:
      if (value == null) {
        unsetAfterSaleOrders();
      } else {
        setAfterSaleOrders((List<AfterSaleOrder>)value);
      }
      break;
    case AFTER_SALE_LIST:
      if (value == null) {
        unsetAfterSaleList();
      } else {
        setAfterSaleList((List<AfterSale>)value);
      }
      break;
    case PAGINATION:
      if (value == null) {
        unsetPagination();
      } else {
        setPagination((com.jfshare.finagle.thrift.pagination.Pagination)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case AFTER_SALE_ORDERS:
      return getAfterSaleOrders();
    case AFTER_SALE_LIST:
      return getAfterSaleList();
    case PAGINATION:
      return getPagination();
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
    case AFTER_SALE_ORDERS:
      return isSetAfterSaleOrders();
    case AFTER_SALE_LIST:
      return isSetAfterSaleList();
    case PAGINATION:
      return isSetPagination();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AfterSaleOrderResult)
      return this.equals((AfterSaleOrderResult)that);
    return false;
  }

  public boolean equals(AfterSaleOrderResult that) {
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
    boolean this_present_afterSaleOrders = true && this.isSetAfterSaleOrders();
    boolean that_present_afterSaleOrders = true && that.isSetAfterSaleOrders();
    if (this_present_afterSaleOrders || that_present_afterSaleOrders) {
      if (!(this_present_afterSaleOrders && that_present_afterSaleOrders))
        return false;
      if (!this.afterSaleOrders.equals(that.afterSaleOrders))
        return false;
    }
    boolean this_present_afterSaleList = true && this.isSetAfterSaleList();
    boolean that_present_afterSaleList = true && that.isSetAfterSaleList();
    if (this_present_afterSaleList || that_present_afterSaleList) {
      if (!(this_present_afterSaleList && that_present_afterSaleList))
        return false;
      if (!this.afterSaleList.equals(that.afterSaleList))
        return false;
    }
    boolean this_present_pagination = true && this.isSetPagination();
    boolean that_present_pagination = true && that.isSetPagination();
    if (this_present_pagination || that_present_pagination) {
      if (!(this_present_pagination && that_present_pagination))
        return false;
      if (!this.pagination.equals(that.pagination))
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
    boolean present_afterSaleOrders = true && (isSetAfterSaleOrders());
    builder.append(present_afterSaleOrders);
    if (present_afterSaleOrders)
      builder.append(afterSaleOrders);
    boolean present_afterSaleList = true && (isSetAfterSaleList());
    builder.append(present_afterSaleList);
    if (present_afterSaleList)
      builder.append(afterSaleList);
    boolean present_pagination = true && (isSetPagination());
    builder.append(present_pagination);
    if (present_pagination)
      builder.append(pagination);
    return builder.toHashCode();
  }

  public int compareTo(AfterSaleOrderResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    AfterSaleOrderResult typedOther = (AfterSaleOrderResult)other;

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
    lastComparison = Boolean.valueOf(isSetAfterSaleOrders()).compareTo(typedOther.isSetAfterSaleOrders());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAfterSaleOrders()) {
      lastComparison = TBaseHelper.compareTo(this.afterSaleOrders, typedOther.afterSaleOrders);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAfterSaleList()).compareTo(typedOther.isSetAfterSaleList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAfterSaleList()) {
      lastComparison = TBaseHelper.compareTo(this.afterSaleList, typedOther.afterSaleList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPagination()).compareTo(typedOther.isSetPagination());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPagination()) {
      lastComparison = TBaseHelper.compareTo(this.pagination, typedOther.pagination);
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
        case 2: // AFTER_SALE_ORDERS
          if (field.type == TType.LIST) {
            {
            TList _list8 = iprot.readListBegin();
            this.afterSaleOrders = new ArrayList<AfterSaleOrder>(_list8.size);
            for (int _i9 = 0; _i9 < _list8.size; ++_i9)
            {
              AfterSaleOrder _elem10;
              _elem10 = new AfterSaleOrder();
              _elem10.read(iprot);
              this.afterSaleOrders.add(_elem10);
            }
            iprot.readListEnd();
            }
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // AFTER_SALE_LIST
          if (field.type == TType.LIST) {
            {
            TList _list11 = iprot.readListBegin();
            this.afterSaleList = new ArrayList<AfterSale>(_list11.size);
            for (int _i12 = 0; _i12 < _list11.size; ++_i12)
            {
              AfterSale _elem13;
              _elem13 = new AfterSale();
              _elem13.read(iprot);
              this.afterSaleList.add(_elem13);
            }
            iprot.readListEnd();
            }
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // PAGINATION
          if (field.type == TType.STRUCT) {
            this.pagination = new com.jfshare.finagle.thrift.pagination.Pagination();
            this.pagination.read(iprot);
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
    if (this.afterSaleOrders != null) {
      oprot.writeFieldBegin(AFTER_SALE_ORDERS_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.afterSaleOrders.size()));
        for (AfterSaleOrder _iter14 : this.afterSaleOrders)
        {
          _iter14.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    if (this.afterSaleList != null) {
      oprot.writeFieldBegin(AFTER_SALE_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.afterSaleList.size()));
        for (AfterSale _iter15 : this.afterSaleList)
        {
          _iter15.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    if (this.pagination != null) {
      oprot.writeFieldBegin(PAGINATION_FIELD_DESC);
      this.pagination.write(oprot);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("AfterSaleOrderResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("afterSaleOrders:");
    if (this.afterSaleOrders == null) {
      sb.append("null");
    } else {
      sb.append(this.afterSaleOrders);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("afterSaleList:");
    if (this.afterSaleList == null) {
      sb.append("null");
    } else {
      sb.append(this.afterSaleList);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("pagination:");
    if (this.pagination == null) {
      sb.append("null");
    } else {
      sb.append(this.pagination);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
