/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.subject;

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

public class Page implements TBase<Page, Page._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("Page");

  private static final TField TOTAL_FIELD_DESC = new TField("total", TType.I32, (short)1);
  private static final TField PAGE_COUNT_FIELD_DESC = new TField("pageCount", TType.I32, (short)2);
  private static final TField PAGE_SIZE_FIELD_DESC = new TField("pageSize", TType.I32, (short)3);
  private static final TField CUR_PAGE_FIELD_DESC = new TField("curPage", TType.I32, (short)4);


  public int total;
  public int pageCount;
  public int pageSize;
  public int curPage;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    TOTAL((short)1, "total"),
    PAGE_COUNT((short)2, "pageCount"),
    PAGE_SIZE((short)3, "pageSize"),
    CUR_PAGE((short)4, "curPage");
  
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
        case 1: // TOTAL
  	return TOTAL;
        case 2: // PAGE_COUNT
  	return PAGE_COUNT;
        case 3: // PAGE_SIZE
  	return PAGE_SIZE;
        case 4: // CUR_PAGE
  	return CUR_PAGE;
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
  private static final int __PAGESIZE_ISSET_ID = 2;
  private static final int __CURPAGE_ISSET_ID = 3;
  private BitSet __isset_bit_vector = new BitSet(4);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL, new FieldMetaData("total", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.PAGE_COUNT, new FieldMetaData("pageCount", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.PAGE_SIZE, new FieldMetaData("pageSize", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.CUR_PAGE, new FieldMetaData("curPage", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(Page.class, metaDataMap);
  }


  public Page() {
  }

  public Page(
    int total,
    int pageCount,
    int pageSize,
    int curPage)
  {
    this();
    this.total = total;
    setTotalIsSet(true);
    this.pageCount = pageCount;
    setPageCountIsSet(true);
    this.pageSize = pageSize;
    setPageSizeIsSet(true);
    this.curPage = curPage;
    setCurPageIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Page(Page other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.total = other.total;
    this.pageCount = other.pageCount;
    this.pageSize = other.pageSize;
    this.curPage = other.curPage;
  }

  public Page deepCopy() {
    return new Page(this);
  }

  @Override
  public void clear() {
    setTotalIsSet(false);
    this.total = 0;
    setPageCountIsSet(false);
    this.pageCount = 0;
    setPageSizeIsSet(false);
    this.pageSize = 0;
    setCurPageIsSet(false);
    this.curPage = 0;
  }

  public int getTotal() {
    return this.total;
  }

  public Page setTotal(int total) {
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

  public Page setPageCount(int pageCount) {
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

  public int getPageSize() {
    return this.pageSize;
  }

  public Page setPageSize(int pageSize) {
    this.pageSize = pageSize;
    setPageSizeIsSet(true);

    return this;
  }

  public void unsetPageSize() {
  __isset_bit_vector.clear(__PAGESIZE_ISSET_ID);
  }

  /** Returns true if field pageSize is set (has been asigned a value) and false otherwise */
  public boolean isSetPageSize() {
    return __isset_bit_vector.get(__PAGESIZE_ISSET_ID);
  }

  public void setPageSizeIsSet(boolean value) {
    __isset_bit_vector.set(__PAGESIZE_ISSET_ID, value);
  }

  public int getCurPage() {
    return this.curPage;
  }

  public Page setCurPage(int curPage) {
    this.curPage = curPage;
    setCurPageIsSet(true);

    return this;
  }

  public void unsetCurPage() {
  __isset_bit_vector.clear(__CURPAGE_ISSET_ID);
  }

  /** Returns true if field curPage is set (has been asigned a value) and false otherwise */
  public boolean isSetCurPage() {
    return __isset_bit_vector.get(__CURPAGE_ISSET_ID);
  }

  public void setCurPageIsSet(boolean value) {
    __isset_bit_vector.set(__CURPAGE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
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
    case PAGE_SIZE:
      if (value == null) {
        unsetPageSize();
      } else {
        setPageSize((Integer)value);
      }
      break;
    case CUR_PAGE:
      if (value == null) {
        unsetCurPage();
      } else {
        setCurPage((Integer)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL:
      return new Integer(getTotal());
    case PAGE_COUNT:
      return new Integer(getPageCount());
    case PAGE_SIZE:
      return new Integer(getPageSize());
    case CUR_PAGE:
      return new Integer(getCurPage());
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TOTAL:
      return isSetTotal();
    case PAGE_COUNT:
      return isSetPageCount();
    case PAGE_SIZE:
      return isSetPageSize();
    case CUR_PAGE:
      return isSetCurPage();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Page)
      return this.equals((Page)that);
    return false;
  }

  public boolean equals(Page that) {
    if (that == null)
      return false;
    boolean this_present_total = true;
    boolean that_present_total = true;
    if (this_present_total || that_present_total) {
      if (!(this_present_total && that_present_total))
        return false;
      if (this.total != that.total)
        return false;
    }
    boolean this_present_pageCount = true;
    boolean that_present_pageCount = true;
    if (this_present_pageCount || that_present_pageCount) {
      if (!(this_present_pageCount && that_present_pageCount))
        return false;
      if (this.pageCount != that.pageCount)
        return false;
    }
    boolean this_present_pageSize = true;
    boolean that_present_pageSize = true;
    if (this_present_pageSize || that_present_pageSize) {
      if (!(this_present_pageSize && that_present_pageSize))
        return false;
      if (this.pageSize != that.pageSize)
        return false;
    }
    boolean this_present_curPage = true;
    boolean that_present_curPage = true;
    if (this_present_curPage || that_present_curPage) {
      if (!(this_present_curPage && that_present_curPage))
        return false;
      if (this.curPage != that.curPage)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_total = true;
    builder.append(present_total);
    if (present_total)
      builder.append(total);
    boolean present_pageCount = true;
    builder.append(present_pageCount);
    if (present_pageCount)
      builder.append(pageCount);
    boolean present_pageSize = true;
    builder.append(present_pageSize);
    if (present_pageSize)
      builder.append(pageSize);
    boolean present_curPage = true;
    builder.append(present_curPage);
    if (present_curPage)
      builder.append(curPage);
    return builder.toHashCode();
  }

  public int compareTo(Page other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Page typedOther = (Page)other;

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
    lastComparison = Boolean.valueOf(isSetPageSize()).compareTo(typedOther.isSetPageSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPageSize()) {
      lastComparison = TBaseHelper.compareTo(this.pageSize, typedOther.pageSize);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurPage()).compareTo(typedOther.isSetCurPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurPage()) {
      lastComparison = TBaseHelper.compareTo(this.curPage, typedOther.curPage);
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
        case 1: // TOTAL
          if (field.type == TType.I32) {
            this.total = iprot.readI32();
            setTotalIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // PAGE_COUNT
          if (field.type == TType.I32) {
            this.pageCount = iprot.readI32();
            setPageCountIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // PAGE_SIZE
          if (field.type == TType.I32) {
            this.pageSize = iprot.readI32();
            setPageSizeIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // CUR_PAGE
          if (field.type == TType.I32) {
            this.curPage = iprot.readI32();
            setCurPageIsSet(true);
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
    oprot.writeFieldBegin(TOTAL_FIELD_DESC);
    oprot.writeI32(this.total);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(PAGE_COUNT_FIELD_DESC);
    oprot.writeI32(this.pageCount);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(PAGE_SIZE_FIELD_DESC);
    oprot.writeI32(this.pageSize);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(CUR_PAGE_FIELD_DESC);
    oprot.writeI32(this.curPage);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Page(");
    boolean first = true;
    sb.append("total:");
    sb.append(this.total);
    first = false;
    if (!first) sb.append(", ");
    sb.append("pageCount:");
    sb.append(this.pageCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("pageSize:");
    sb.append(this.pageSize);
    first = false;
    if (!first) sb.append(", ");
    sb.append("curPage:");
    sb.append(this.curPage);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
