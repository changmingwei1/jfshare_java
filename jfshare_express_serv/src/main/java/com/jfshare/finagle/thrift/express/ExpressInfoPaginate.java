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

public class ExpressInfoPaginate implements TBase<ExpressInfoPaginate, ExpressInfoPaginate._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ExpressInfoPaginate");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField TOTAL_FIELD_DESC = new TField("total", TType.I32, (short)2);
  private static final TField PAGE_COUNT_FIELD_DESC = new TField("pageCount", TType.I32, (short)3);
  private static final TField EXPRESS_INFO_LIST_FIELD_DESC = new TField("expressInfoList", TType.LIST, (short)4);


  public com.jfshare.finagle.thrift.result.Result result;
  public int total;
  public int pageCount;
  public List<ExpressInfo> expressInfoList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    TOTAL((short)2, "total"),
    PAGE_COUNT((short)3, "pageCount"),
    EXPRESS_INFO_LIST((short)4, "expressInfoList");
  
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
        case 2: // TOTAL
  	return TOTAL;
        case 3: // PAGE_COUNT
  	return PAGE_COUNT;
        case 4: // EXPRESS_INFO_LIST
  	return EXPRESS_INFO_LIST;
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
  private static final int __TOTAL_ISSET_ID = 0;
  private static final int __PAGECOUNT_ISSET_ID = 1;
  private BitSet __isset_bit_vector = new BitSet(2);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESULT, new FieldMetaData("result", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.result.Result.class)));
    tmpMap.put(_Fields.TOTAL, new FieldMetaData("total", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.PAGE_COUNT, new FieldMetaData("pageCount", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.EXPRESS_INFO_LIST, new FieldMetaData("expressInfoList", TFieldRequirementType.OPTIONAL,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, ExpressInfo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ExpressInfoPaginate.class, metaDataMap);
  }


  public ExpressInfoPaginate() {
  }

  public ExpressInfoPaginate(
    com.jfshare.finagle.thrift.result.Result result)
  {
    this();
    this.result = result;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ExpressInfoPaginate(ExpressInfoPaginate other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    this.total = other.total;
    this.pageCount = other.pageCount;
    if (other.isSetExpressInfoList()) {
      List<ExpressInfo> __this__expressInfoList = new ArrayList<ExpressInfo>();
      for (ExpressInfo other_element : other.expressInfoList) {
        __this__expressInfoList.add(new ExpressInfo(other_element));
      }
      this.expressInfoList = __this__expressInfoList;
    }
  }

  public ExpressInfoPaginate deepCopy() {
    return new ExpressInfoPaginate(this);
  }

  @Override
  public void clear() {
    this.result = null;
    setTotalIsSet(false);
    this.total = 0;
    setPageCountIsSet(false);
    this.pageCount = 0;
    this.expressInfoList = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public ExpressInfoPaginate setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public int getTotal() {
    return this.total;
  }

  public ExpressInfoPaginate setTotal(int total) {
    this.total = total;
    setTotalIsSet(true);

    return this;
  }

  public void unsetTotal() {
  __isset_bit_vector.clear(__TOTAL_ISSET_ID);
  }

  /** Returns true if field total is set (has been asigned a value) and false otherwise */
  public boolean isSetTotal() {
    return __isset_bit_vector.get(__TOTAL_ISSET_ID);
  }

  public void setTotalIsSet(boolean value) {
    __isset_bit_vector.set(__TOTAL_ISSET_ID, value);
  }

  public int getPageCount() {
    return this.pageCount;
  }

  public ExpressInfoPaginate setPageCount(int pageCount) {
    this.pageCount = pageCount;
    setPageCountIsSet(true);

    return this;
  }

  public void unsetPageCount() {
  __isset_bit_vector.clear(__PAGECOUNT_ISSET_ID);
  }

  /** Returns true if field pageCount is set (has been asigned a value) and false otherwise */
  public boolean isSetPageCount() {
    return __isset_bit_vector.get(__PAGECOUNT_ISSET_ID);
  }

  public void setPageCountIsSet(boolean value) {
    __isset_bit_vector.set(__PAGECOUNT_ISSET_ID, value);
  }

  public int getExpressInfoListSize() {
    return (this.expressInfoList == null) ? 0 : this.expressInfoList.size();
  }

  public java.util.Iterator<ExpressInfo> getExpressInfoListIterator() {
    return (this.expressInfoList == null) ? null : this.expressInfoList.iterator();
  }

  public void addToExpressInfoList(ExpressInfo elem) {
    if (this.expressInfoList == null) {
      this.expressInfoList = new ArrayList<ExpressInfo>();
    }
    this.expressInfoList.add(elem);
  }

  public List<ExpressInfo> getExpressInfoList() {
    return this.expressInfoList;
  }

  public ExpressInfoPaginate setExpressInfoList(List<ExpressInfo> expressInfoList) {
    this.expressInfoList = expressInfoList;
    
    return this;
  }

  public void unsetExpressInfoList() {
    this.expressInfoList = null;
  }

  /** Returns true if field expressInfoList is set (has been asigned a value) and false otherwise */
  public boolean isSetExpressInfoList() {
    return this.expressInfoList != null;
  }

  public void setExpressInfoListIsSet(boolean value) {
    if (!value) {
      this.expressInfoList = null;
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
    case TOTAL:
      if (value == null) {
        unsetTotal();
      } else {
        setTotal((Integer)value);
      }
      break;
    case PAGE_COUNT:
      if (value == null) {
        unsetPageCount();
      } else {
        setPageCount((Integer)value);
      }
      break;
    case EXPRESS_INFO_LIST:
      if (value == null) {
        unsetExpressInfoList();
      } else {
        setExpressInfoList((List<ExpressInfo>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case TOTAL:
      return new Integer(getTotal());
    case PAGE_COUNT:
      return new Integer(getPageCount());
    case EXPRESS_INFO_LIST:
      return getExpressInfoList();
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
    case TOTAL:
      return isSetTotal();
    case PAGE_COUNT:
      return isSetPageCount();
    case EXPRESS_INFO_LIST:
      return isSetExpressInfoList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ExpressInfoPaginate)
      return this.equals((ExpressInfoPaginate)that);
    return false;
  }

  public boolean equals(ExpressInfoPaginate that) {
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
    boolean this_present_total = true && this.isSetTotal();
    boolean that_present_total = true && that.isSetTotal();
    if (this_present_total || that_present_total) {
      if (!(this_present_total && that_present_total))
        return false;
      if (this.total != that.total)
        return false;
    }
    boolean this_present_pageCount = true && this.isSetPageCount();
    boolean that_present_pageCount = true && that.isSetPageCount();
    if (this_present_pageCount || that_present_pageCount) {
      if (!(this_present_pageCount && that_present_pageCount))
        return false;
      if (this.pageCount != that.pageCount)
        return false;
    }
    boolean this_present_expressInfoList = true && this.isSetExpressInfoList();
    boolean that_present_expressInfoList = true && that.isSetExpressInfoList();
    if (this_present_expressInfoList || that_present_expressInfoList) {
      if (!(this_present_expressInfoList && that_present_expressInfoList))
        return false;
      if (!this.expressInfoList.equals(that.expressInfoList))
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
    boolean present_total = true && (isSetTotal());
    builder.append(present_total);
    if (present_total)
      builder.append(total);
    boolean present_pageCount = true && (isSetPageCount());
    builder.append(present_pageCount);
    if (present_pageCount)
      builder.append(pageCount);
    boolean present_expressInfoList = true && (isSetExpressInfoList());
    builder.append(present_expressInfoList);
    if (present_expressInfoList)
      builder.append(expressInfoList);
    return builder.toHashCode();
  }

  public int compareTo(ExpressInfoPaginate other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ExpressInfoPaginate typedOther = (ExpressInfoPaginate)other;

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
    lastComparison = Boolean.valueOf(isSetTotal()).compareTo(typedOther.isSetTotal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotal()) {
      lastComparison = TBaseHelper.compareTo(this.total, typedOther.total);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPageCount()).compareTo(typedOther.isSetPageCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPageCount()) {
      lastComparison = TBaseHelper.compareTo(this.pageCount, typedOther.pageCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetExpressInfoList()).compareTo(typedOther.isSetExpressInfoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExpressInfoList()) {
      lastComparison = TBaseHelper.compareTo(this.expressInfoList, typedOther.expressInfoList);
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
        case 2: // TOTAL
          if (field.type == TType.I32) {
            this.total = iprot.readI32();
            setTotalIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // PAGE_COUNT
          if (field.type == TType.I32) {
            this.pageCount = iprot.readI32();
            setPageCountIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // EXPRESS_INFO_LIST
          if (field.type == TType.LIST) {
            {
            TList _list8 = iprot.readListBegin();
            this.expressInfoList = new ArrayList<ExpressInfo>(_list8.size);
            for (int _i9 = 0; _i9 < _list8.size; ++_i9)
            {
              ExpressInfo _elem10;
              _elem10 = new ExpressInfo();
              _elem10.read(iprot);
              this.expressInfoList.add(_elem10);
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
    if (isSetTotal()) {
      oprot.writeFieldBegin(TOTAL_FIELD_DESC);
      oprot.writeI32(this.total);
      oprot.writeFieldEnd();
    }
    if (isSetPageCount()) {
      oprot.writeFieldBegin(PAGE_COUNT_FIELD_DESC);
      oprot.writeI32(this.pageCount);
      oprot.writeFieldEnd();
    }
    if (this.expressInfoList != null) {
      if (isSetExpressInfoList()) {
        oprot.writeFieldBegin(EXPRESS_INFO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new TList(TType.STRUCT, this.expressInfoList.size()));
          for (ExpressInfo _iter11 : this.expressInfoList)
          {
            _iter11.write(oprot);
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
    StringBuilder sb = new StringBuilder("ExpressInfoPaginate(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (isSetTotal()) {
      if (!first) sb.append(", ");
      sb.append("total:");
      sb.append(this.total);
      first = false;
      }
    if (isSetPageCount()) {
      if (!first) sb.append(", ");
      sb.append("pageCount:");
      sb.append(this.pageCount);
      first = false;
      }
    if (isSetExpressInfoList()) {
      if (!first) sb.append(", ");
      sb.append("expressInfoList:");
      if (this.expressInfoList == null) {
        sb.append("null");
      } else {
        sb.append(this.expressInfoList);
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
