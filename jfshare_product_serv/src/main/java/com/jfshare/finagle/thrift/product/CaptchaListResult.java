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

public class CaptchaListResult implements TBase<CaptchaListResult, CaptchaListResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("CaptchaListResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField YED_NUM_FIELD_DESC = new TField("yedNum", TType.I32, (short)2);
  private static final TField MON_NUM_FIELD_DESC = new TField("monNum", TType.I32, (short)3);
  private static final TField PAGINATION_FIELD_DESC = new TField("pagination", TType.STRUCT, (short)4);
  private static final TField ITEM_LIST_FIELD_DESC = new TField("itemList", TType.LIST, (short)5);


  public com.jfshare.finagle.thrift.result.Result result;
  public int yedNum;
  public int monNum;
  public com.jfshare.finagle.thrift.pagination.Pagination pagination;
  public List<AldCaptchaItem> itemList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    YED_NUM((short)2, "yedNum"),
    MON_NUM((short)3, "monNum"),
    PAGINATION((short)4, "pagination"),
    ITEM_LIST((short)5, "itemList");
  
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
        case 2: // YED_NUM
  	return YED_NUM;
        case 3: // MON_NUM
  	return MON_NUM;
        case 4: // PAGINATION
  	return PAGINATION;
        case 5: // ITEM_LIST
  	return ITEM_LIST;
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
  private static final int __YEDNUM_ISSET_ID = 0;
  private static final int __MONNUM_ISSET_ID = 1;
  private BitSet __isset_bit_vector = new BitSet(2);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESULT, new FieldMetaData("result", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.result.Result.class)));
    tmpMap.put(_Fields.YED_NUM, new FieldMetaData("yedNum", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.MON_NUM, new FieldMetaData("monNum", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.PAGINATION, new FieldMetaData("pagination", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.pagination.Pagination.class)));
    tmpMap.put(_Fields.ITEM_LIST, new FieldMetaData("itemList", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, AldCaptchaItem.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(CaptchaListResult.class, metaDataMap);
  }


  public CaptchaListResult() {
  }

  public CaptchaListResult(
    com.jfshare.finagle.thrift.result.Result result,
    int yedNum,
    int monNum,
    com.jfshare.finagle.thrift.pagination.Pagination pagination,
    List<AldCaptchaItem> itemList)
  {
    this();
    this.result = result;
    this.yedNum = yedNum;
    setYedNumIsSet(true);
    this.monNum = monNum;
    setMonNumIsSet(true);
    this.pagination = pagination;
    this.itemList = itemList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CaptchaListResult(CaptchaListResult other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    this.yedNum = other.yedNum;
    this.monNum = other.monNum;
    if (other.isSetPagination()) {
      this.pagination = new com.jfshare.finagle.thrift.pagination.Pagination(other.pagination);
    }
    if (other.isSetItemList()) {
      List<AldCaptchaItem> __this__itemList = new ArrayList<AldCaptchaItem>();
      for (AldCaptchaItem other_element : other.itemList) {
        __this__itemList.add(new AldCaptchaItem(other_element));
      }
      this.itemList = __this__itemList;
    }
  }

  public CaptchaListResult deepCopy() {
    return new CaptchaListResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    setYedNumIsSet(false);
    this.yedNum = 0;
    setMonNumIsSet(false);
    this.monNum = 0;
    this.pagination = null;
    this.itemList = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public CaptchaListResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public int getYedNum() {
    return this.yedNum;
  }

  public CaptchaListResult setYedNum(int yedNum) {
    this.yedNum = yedNum;
    setYedNumIsSet(true);

    return this;
  }

  public void unsetYedNum() {
  __isset_bit_vector.clear(__YEDNUM_ISSET_ID);
  }

  /** Returns true if field yedNum is set (has been asigned a value) and false otherwise */
  public boolean isSetYedNum() {
    return __isset_bit_vector.get(__YEDNUM_ISSET_ID);
  }

  public void setYedNumIsSet(boolean value) {
    __isset_bit_vector.set(__YEDNUM_ISSET_ID, value);
  }

  public int getMonNum() {
    return this.monNum;
  }

  public CaptchaListResult setMonNum(int monNum) {
    this.monNum = monNum;
    setMonNumIsSet(true);

    return this;
  }

  public void unsetMonNum() {
  __isset_bit_vector.clear(__MONNUM_ISSET_ID);
  }

  /** Returns true if field monNum is set (has been asigned a value) and false otherwise */
  public boolean isSetMonNum() {
    return __isset_bit_vector.get(__MONNUM_ISSET_ID);
  }

  public void setMonNumIsSet(boolean value) {
    __isset_bit_vector.set(__MONNUM_ISSET_ID, value);
  }

  public com.jfshare.finagle.thrift.pagination.Pagination getPagination() {
    return this.pagination;
  }

  public CaptchaListResult setPagination(com.jfshare.finagle.thrift.pagination.Pagination pagination) {
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

  public int getItemListSize() {
    return (this.itemList == null) ? 0 : this.itemList.size();
  }

  public java.util.Iterator<AldCaptchaItem> getItemListIterator() {
    return (this.itemList == null) ? null : this.itemList.iterator();
  }

  public void addToItemList(AldCaptchaItem elem) {
    if (this.itemList == null) {
      this.itemList = new ArrayList<AldCaptchaItem>();
    }
    this.itemList.add(elem);
  }

  public List<AldCaptchaItem> getItemList() {
    return this.itemList;
  }

  public CaptchaListResult setItemList(List<AldCaptchaItem> itemList) {
    this.itemList = itemList;
    
    return this;
  }

  public void unsetItemList() {
    this.itemList = null;
  }

  /** Returns true if field itemList is set (has been asigned a value) and false otherwise */
  public boolean isSetItemList() {
    return this.itemList != null;
  }

  public void setItemListIsSet(boolean value) {
    if (!value) {
      this.itemList = null;
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
    case YED_NUM:
      if (value == null) {
        unsetYedNum();
      } else {
        setYedNum((Integer)value);
      }
      break;
    case MON_NUM:
      if (value == null) {
        unsetMonNum();
      } else {
        setMonNum((Integer)value);
      }
      break;
    case PAGINATION:
      if (value == null) {
        unsetPagination();
      } else {
        setPagination((com.jfshare.finagle.thrift.pagination.Pagination)value);
      }
      break;
    case ITEM_LIST:
      if (value == null) {
        unsetItemList();
      } else {
        setItemList((List<AldCaptchaItem>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case YED_NUM:
      return new Integer(getYedNum());
    case MON_NUM:
      return new Integer(getMonNum());
    case PAGINATION:
      return getPagination();
    case ITEM_LIST:
      return getItemList();
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
    case YED_NUM:
      return isSetYedNum();
    case MON_NUM:
      return isSetMonNum();
    case PAGINATION:
      return isSetPagination();
    case ITEM_LIST:
      return isSetItemList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CaptchaListResult)
      return this.equals((CaptchaListResult)that);
    return false;
  }

  public boolean equals(CaptchaListResult that) {
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
    boolean this_present_yedNum = true;
    boolean that_present_yedNum = true;
    if (this_present_yedNum || that_present_yedNum) {
      if (!(this_present_yedNum && that_present_yedNum))
        return false;
      if (this.yedNum != that.yedNum)
        return false;
    }
    boolean this_present_monNum = true;
    boolean that_present_monNum = true;
    if (this_present_monNum || that_present_monNum) {
      if (!(this_present_monNum && that_present_monNum))
        return false;
      if (this.monNum != that.monNum)
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
    boolean this_present_itemList = true && this.isSetItemList();
    boolean that_present_itemList = true && that.isSetItemList();
    if (this_present_itemList || that_present_itemList) {
      if (!(this_present_itemList && that_present_itemList))
        return false;
      if (!this.itemList.equals(that.itemList))
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
    boolean present_yedNum = true;
    builder.append(present_yedNum);
    if (present_yedNum)
      builder.append(yedNum);
    boolean present_monNum = true;
    builder.append(present_monNum);
    if (present_monNum)
      builder.append(monNum);
    boolean present_pagination = true && (isSetPagination());
    builder.append(present_pagination);
    if (present_pagination)
      builder.append(pagination);
    boolean present_itemList = true && (isSetItemList());
    builder.append(present_itemList);
    if (present_itemList)
      builder.append(itemList);
    return builder.toHashCode();
  }

  public int compareTo(CaptchaListResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    CaptchaListResult typedOther = (CaptchaListResult)other;

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
    lastComparison = Boolean.valueOf(isSetYedNum()).compareTo(typedOther.isSetYedNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetYedNum()) {
      lastComparison = TBaseHelper.compareTo(this.yedNum, typedOther.yedNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMonNum()).compareTo(typedOther.isSetMonNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMonNum()) {
      lastComparison = TBaseHelper.compareTo(this.monNum, typedOther.monNum);
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
    lastComparison = Boolean.valueOf(isSetItemList()).compareTo(typedOther.isSetItemList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetItemList()) {
      lastComparison = TBaseHelper.compareTo(this.itemList, typedOther.itemList);
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
        case 2: // YED_NUM
          if (field.type == TType.I32) {
            this.yedNum = iprot.readI32();
            setYedNumIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // MON_NUM
          if (field.type == TType.I32) {
            this.monNum = iprot.readI32();
            setMonNumIsSet(true);
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
        case 5: // ITEM_LIST
          if (field.type == TType.LIST) {
            {
            TList _list40 = iprot.readListBegin();
            this.itemList = new ArrayList<AldCaptchaItem>(_list40.size);
            for (int _i41 = 0; _i41 < _list40.size; ++_i41)
            {
              AldCaptchaItem _elem42;
              _elem42 = new AldCaptchaItem();
              _elem42.read(iprot);
              this.itemList.add(_elem42);
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
    oprot.writeFieldBegin(YED_NUM_FIELD_DESC);
    oprot.writeI32(this.yedNum);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(MON_NUM_FIELD_DESC);
    oprot.writeI32(this.monNum);
    oprot.writeFieldEnd();
    if (this.pagination != null) {
      oprot.writeFieldBegin(PAGINATION_FIELD_DESC);
      this.pagination.write(oprot);
      oprot.writeFieldEnd();
    }
    if (this.itemList != null) {
      oprot.writeFieldBegin(ITEM_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.itemList.size()));
        for (AldCaptchaItem _iter43 : this.itemList)
        {
          _iter43.write(oprot);
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
    StringBuilder sb = new StringBuilder("CaptchaListResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("yedNum:");
    sb.append(this.yedNum);
    first = false;
    if (!first) sb.append(", ");
    sb.append("monNum:");
    sb.append(this.monNum);
    first = false;
    if (!first) sb.append(", ");
    sb.append("pagination:");
    if (this.pagination == null) {
      sb.append("null");
    } else {
      sb.append(this.pagination);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("itemList:");
    if (this.itemList == null) {
      sb.append("null");
    } else {
      sb.append(this.itemList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
