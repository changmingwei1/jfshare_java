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

public class SellerVipResult implements TBase<SellerVipResult, SellerVipResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("SellerVipResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField VIP_TOTAL_FIELD_DESC = new TField("vipTotal", TType.I32, (short)2);
  private static final TField VIP_LIST_FIELD_DESC = new TField("vipList", TType.LIST, (short)3);
  private static final TField PAGINATION_FIELD_DESC = new TField("pagination", TType.STRUCT, (short)4);


  public com.jfshare.finagle.thrift.result.Result result;
  public int vipTotal;
  public List<SellerVip> vipList;
  public com.jfshare.finagle.thrift.pagination.Pagination pagination;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    VIP_TOTAL((short)2, "vipTotal"),
    VIP_LIST((short)3, "vipList"),
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
        case 2: // VIP_TOTAL
  	return VIP_TOTAL;
        case 3: // VIP_LIST
  	return VIP_LIST;
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
  private static final int __VIPTOTAL_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESULT, new FieldMetaData("result", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.result.Result.class)));
    tmpMap.put(_Fields.VIP_TOTAL, new FieldMetaData("vipTotal", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.VIP_LIST, new FieldMetaData("vipList", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, SellerVip.class))));
    tmpMap.put(_Fields.PAGINATION, new FieldMetaData("pagination", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.pagination.Pagination.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(SellerVipResult.class, metaDataMap);
  }


  public SellerVipResult() {
  }

  public SellerVipResult(
    com.jfshare.finagle.thrift.result.Result result,
    int vipTotal,
    List<SellerVip> vipList,
    com.jfshare.finagle.thrift.pagination.Pagination pagination)
  {
    this();
    this.result = result;
    this.vipTotal = vipTotal;
    setVipTotalIsSet(true);
    this.vipList = vipList;
    this.pagination = pagination;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SellerVipResult(SellerVipResult other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    this.vipTotal = other.vipTotal;
    if (other.isSetVipList()) {
      List<SellerVip> __this__vipList = new ArrayList<SellerVip>();
      for (SellerVip other_element : other.vipList) {
        __this__vipList.add(new SellerVip(other_element));
      }
      this.vipList = __this__vipList;
    }
    if (other.isSetPagination()) {
      this.pagination = new com.jfshare.finagle.thrift.pagination.Pagination(other.pagination);
    }
  }

  public SellerVipResult deepCopy() {
    return new SellerVipResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    setVipTotalIsSet(false);
    this.vipTotal = 0;
    this.vipList = null;
    this.pagination = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public SellerVipResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public int getVipTotal() {
    return this.vipTotal;
  }

  public SellerVipResult setVipTotal(int vipTotal) {
    this.vipTotal = vipTotal;
    setVipTotalIsSet(true);

    return this;
  }

  public void unsetVipTotal() {
  __isset_bit_vector.clear(__VIPTOTAL_ISSET_ID);
  }

  /** Returns true if field vipTotal is set (has been asigned a value) and false otherwise */
  public boolean isSetVipTotal() {
    return __isset_bit_vector.get(__VIPTOTAL_ISSET_ID);
  }

  public void setVipTotalIsSet(boolean value) {
    __isset_bit_vector.set(__VIPTOTAL_ISSET_ID, value);
  }

  public int getVipListSize() {
    return (this.vipList == null) ? 0 : this.vipList.size();
  }

  public java.util.Iterator<SellerVip> getVipListIterator() {
    return (this.vipList == null) ? null : this.vipList.iterator();
  }

  public void addToVipList(SellerVip elem) {
    if (this.vipList == null) {
      this.vipList = new ArrayList<SellerVip>();
    }
    this.vipList.add(elem);
  }

  public List<SellerVip> getVipList() {
    return this.vipList;
  }

  public SellerVipResult setVipList(List<SellerVip> vipList) {
    this.vipList = vipList;
    
    return this;
  }

  public void unsetVipList() {
    this.vipList = null;
  }

  /** Returns true if field vipList is set (has been asigned a value) and false otherwise */
  public boolean isSetVipList() {
    return this.vipList != null;
  }

  public void setVipListIsSet(boolean value) {
    if (!value) {
      this.vipList = null;
    }
  }

  public com.jfshare.finagle.thrift.pagination.Pagination getPagination() {
    return this.pagination;
  }

  public SellerVipResult setPagination(com.jfshare.finagle.thrift.pagination.Pagination pagination) {
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
    case VIP_TOTAL:
      if (value == null) {
        unsetVipTotal();
      } else {
        setVipTotal((Integer)value);
      }
      break;
    case VIP_LIST:
      if (value == null) {
        unsetVipList();
      } else {
        setVipList((List<SellerVip>)value);
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
    case VIP_TOTAL:
      return new Integer(getVipTotal());
    case VIP_LIST:
      return getVipList();
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
    case VIP_TOTAL:
      return isSetVipTotal();
    case VIP_LIST:
      return isSetVipList();
    case PAGINATION:
      return isSetPagination();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SellerVipResult)
      return this.equals((SellerVipResult)that);
    return false;
  }

  public boolean equals(SellerVipResult that) {
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
    boolean this_present_vipTotal = true;
    boolean that_present_vipTotal = true;
    if (this_present_vipTotal || that_present_vipTotal) {
      if (!(this_present_vipTotal && that_present_vipTotal))
        return false;
      if (this.vipTotal != that.vipTotal)
        return false;
    }
    boolean this_present_vipList = true && this.isSetVipList();
    boolean that_present_vipList = true && that.isSetVipList();
    if (this_present_vipList || that_present_vipList) {
      if (!(this_present_vipList && that_present_vipList))
        return false;
      if (!this.vipList.equals(that.vipList))
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
    boolean present_vipTotal = true;
    builder.append(present_vipTotal);
    if (present_vipTotal)
      builder.append(vipTotal);
    boolean present_vipList = true && (isSetVipList());
    builder.append(present_vipList);
    if (present_vipList)
      builder.append(vipList);
    boolean present_pagination = true && (isSetPagination());
    builder.append(present_pagination);
    if (present_pagination)
      builder.append(pagination);
    return builder.toHashCode();
  }

  public int compareTo(SellerVipResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    SellerVipResult typedOther = (SellerVipResult)other;

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
    lastComparison = Boolean.valueOf(isSetVipTotal()).compareTo(typedOther.isSetVipTotal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVipTotal()) {
      lastComparison = TBaseHelper.compareTo(this.vipTotal, typedOther.vipTotal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVipList()).compareTo(typedOther.isSetVipList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVipList()) {
      lastComparison = TBaseHelper.compareTo(this.vipList, typedOther.vipList);
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
        case 2: // VIP_TOTAL
          if (field.type == TType.I32) {
            this.vipTotal = iprot.readI32();
            setVipTotalIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // VIP_LIST
          if (field.type == TType.LIST) {
            {
            TList _list9 = iprot.readListBegin();
            this.vipList = new ArrayList<SellerVip>(_list9.size);
            for (int _i10 = 0; _i10 < _list9.size; ++_i10)
            {
              SellerVip _elem11;
              _elem11 = new SellerVip();
              _elem11.read(iprot);
              this.vipList.add(_elem11);
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
    oprot.writeFieldBegin(VIP_TOTAL_FIELD_DESC);
    oprot.writeI32(this.vipTotal);
    oprot.writeFieldEnd();
    if (this.vipList != null) {
      oprot.writeFieldBegin(VIP_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.vipList.size()));
        for (SellerVip _iter12 : this.vipList)
        {
          _iter12.write(oprot);
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
    StringBuilder sb = new StringBuilder("SellerVipResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("vipTotal:");
    sb.append(this.vipTotal);
    first = false;
    if (!first) sb.append(", ");
    sb.append("vipList:");
    if (this.vipList == null) {
      sb.append("null");
    } else {
      sb.append(this.vipList);
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
