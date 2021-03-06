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
import java.util.EnumSet;
import java.util.Collections;

import org.apache.thrift.*;
import org.apache.thrift.async.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

// No additional import required for struct/union.

public class SubjectQueryResult implements TBase<SubjectQueryResult, SubjectQueryResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("SubjectQueryResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField SUBJECT_INFOS_FIELD_DESC = new TField("subjectInfos", TType.LIST, (short)2);
  private static final TField PAGE_FIELD_DESC = new TField("page", TType.STRUCT, (short)3);


  public com.jfshare.finagle.thrift.result.Result result;
  public List<SubjectInfo> subjectInfos;
  public Page page;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    SUBJECT_INFOS((short)2, "subjectInfos"),
    PAGE((short)3, "page");
  
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
        case 2: // SUBJECT_INFOS
  	return SUBJECT_INFOS;
        case 3: // PAGE
  	return PAGE;
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
    tmpMap.put(_Fields.SUBJECT_INFOS, new FieldMetaData("subjectInfos", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, SubjectInfo.class))));
    tmpMap.put(_Fields.PAGE, new FieldMetaData("page", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, Page.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(SubjectQueryResult.class, metaDataMap);
  }


  public SubjectQueryResult() {
  }

  public SubjectQueryResult(
    com.jfshare.finagle.thrift.result.Result result,
    List<SubjectInfo> subjectInfos,
    Page page)
  {
    this();
    this.result = result;
    this.subjectInfos = subjectInfos;
    this.page = page;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SubjectQueryResult(SubjectQueryResult other) {
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetSubjectInfos()) {
      List<SubjectInfo> __this__subjectInfos = new ArrayList<SubjectInfo>();
      for (SubjectInfo other_element : other.subjectInfos) {
        __this__subjectInfos.add(new SubjectInfo(other_element));
      }
      this.subjectInfos = __this__subjectInfos;
    }
    if (other.isSetPage()) {
      this.page = new Page(other.page);
    }
  }

  public SubjectQueryResult deepCopy() {
    return new SubjectQueryResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.subjectInfos = null;
    this.page = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public SubjectQueryResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public int getSubjectInfosSize() {
    return (this.subjectInfos == null) ? 0 : this.subjectInfos.size();
  }

  public java.util.Iterator<SubjectInfo> getSubjectInfosIterator() {
    return (this.subjectInfos == null) ? null : this.subjectInfos.iterator();
  }

  public void addToSubjectInfos(SubjectInfo elem) {
    if (this.subjectInfos == null) {
      this.subjectInfos = new ArrayList<SubjectInfo>();
    }
    this.subjectInfos.add(elem);
  }

  public List<SubjectInfo> getSubjectInfos() {
    return this.subjectInfos;
  }

  public SubjectQueryResult setSubjectInfos(List<SubjectInfo> subjectInfos) {
    this.subjectInfos = subjectInfos;
    
    return this;
  }

  public void unsetSubjectInfos() {
    this.subjectInfos = null;
  }

  /** Returns true if field subjectInfos is set (has been asigned a value) and false otherwise */
  public boolean isSetSubjectInfos() {
    return this.subjectInfos != null;
  }

  public void setSubjectInfosIsSet(boolean value) {
    if (!value) {
      this.subjectInfos = null;
    }
  }

  public Page getPage() {
    return this.page;
  }

  public SubjectQueryResult setPage(Page page) {
    this.page = page;
    
    return this;
  }

  public void unsetPage() {
    this.page = null;
  }

  /** Returns true if field page is set (has been asigned a value) and false otherwise */
  public boolean isSetPage() {
    return this.page != null;
  }

  public void setPageIsSet(boolean value) {
    if (!value) {
      this.page = null;
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
    case SUBJECT_INFOS:
      if (value == null) {
        unsetSubjectInfos();
      } else {
        setSubjectInfos((List<SubjectInfo>)value);
      }
      break;
    case PAGE:
      if (value == null) {
        unsetPage();
      } else {
        setPage((Page)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case SUBJECT_INFOS:
      return getSubjectInfos();
    case PAGE:
      return getPage();
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
    case SUBJECT_INFOS:
      return isSetSubjectInfos();
    case PAGE:
      return isSetPage();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SubjectQueryResult)
      return this.equals((SubjectQueryResult)that);
    return false;
  }

  public boolean equals(SubjectQueryResult that) {
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
    boolean this_present_subjectInfos = true && this.isSetSubjectInfos();
    boolean that_present_subjectInfos = true && that.isSetSubjectInfos();
    if (this_present_subjectInfos || that_present_subjectInfos) {
      if (!(this_present_subjectInfos && that_present_subjectInfos))
        return false;
      if (!this.subjectInfos.equals(that.subjectInfos))
        return false;
    }
    boolean this_present_page = true && this.isSetPage();
    boolean that_present_page = true && that.isSetPage();
    if (this_present_page || that_present_page) {
      if (!(this_present_page && that_present_page))
        return false;
      if (!this.page.equals(that.page))
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
    boolean present_subjectInfos = true && (isSetSubjectInfos());
    builder.append(present_subjectInfos);
    if (present_subjectInfos)
      builder.append(subjectInfos);
    boolean present_page = true && (isSetPage());
    builder.append(present_page);
    if (present_page)
      builder.append(page);
    return builder.toHashCode();
  }

  public int compareTo(SubjectQueryResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    SubjectQueryResult typedOther = (SubjectQueryResult)other;

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
    lastComparison = Boolean.valueOf(isSetSubjectInfos()).compareTo(typedOther.isSetSubjectInfos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubjectInfos()) {
      lastComparison = TBaseHelper.compareTo(this.subjectInfos, typedOther.subjectInfos);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPage()).compareTo(typedOther.isSetPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage()) {
      lastComparison = TBaseHelper.compareTo(this.page, typedOther.page);
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
        case 2: // SUBJECT_INFOS
          if (field.type == TType.LIST) {
            {
            TList _list16 = iprot.readListBegin();
            this.subjectInfos = new ArrayList<SubjectInfo>(_list16.size);
            for (int _i17 = 0; _i17 < _list16.size; ++_i17)
            {
              SubjectInfo _elem18;
              _elem18 = new SubjectInfo();
              _elem18.read(iprot);
              this.subjectInfos.add(_elem18);
            }
            iprot.readListEnd();
            }
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // PAGE
          if (field.type == TType.STRUCT) {
            this.page = new Page();
            this.page.read(iprot);
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
    if (this.subjectInfos != null) {
      oprot.writeFieldBegin(SUBJECT_INFOS_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.subjectInfos.size()));
        for (SubjectInfo _iter19 : this.subjectInfos)
        {
          _iter19.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    if (this.page != null) {
      oprot.writeFieldBegin(PAGE_FIELD_DESC);
      this.page.write(oprot);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("SubjectQueryResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("subjectInfos:");
    if (this.subjectInfos == null) {
      sb.append("null");
    } else {
      sb.append(this.subjectInfos);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("page:");
    if (this.page == null) {
      sb.append("null");
    } else {
      sb.append(this.page);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
