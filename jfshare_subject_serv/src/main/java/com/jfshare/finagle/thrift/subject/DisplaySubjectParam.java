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

public class DisplaySubjectParam implements TBase<DisplaySubjectParam, DisplaySubjectParam._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("DisplaySubjectParam");

  private static final TField DISPLAY_SUBJECT_INFO_FIELD_DESC = new TField("displaySubjectInfo", TType.STRUCT, (short)1);
  private static final TField FLAG_FIELD_DESC = new TField("flag", TType.STRING, (short)2);


  public DisplaySubjectInfo displaySubjectInfo;
  public String flag;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    DISPLAY_SUBJECT_INFO((short)1, "displaySubjectInfo"),
    FLAG((short)2, "flag");
  
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
        case 1: // DISPLAY_SUBJECT_INFO
  	return DISPLAY_SUBJECT_INFO;
        case 2: // FLAG
  	return FLAG;
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
    tmpMap.put(_Fields.DISPLAY_SUBJECT_INFO, new FieldMetaData("displaySubjectInfo", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, DisplaySubjectInfo.class)));
    tmpMap.put(_Fields.FLAG, new FieldMetaData("flag", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(DisplaySubjectParam.class, metaDataMap);
  }


  public DisplaySubjectParam() {
  }

  public DisplaySubjectParam(
    DisplaySubjectInfo displaySubjectInfo,
    String flag)
  {
    this();
    this.displaySubjectInfo = displaySubjectInfo;
    this.flag = flag;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DisplaySubjectParam(DisplaySubjectParam other) {
    if (other.isSetDisplaySubjectInfo()) {
      this.displaySubjectInfo = new DisplaySubjectInfo(other.displaySubjectInfo);
    }
    if (other.isSetFlag()) {
      this.flag = other.flag;
    }
  }

  public DisplaySubjectParam deepCopy() {
    return new DisplaySubjectParam(this);
  }

  @Override
  public void clear() {
    this.displaySubjectInfo = null;
    this.flag = null;
  }

  public DisplaySubjectInfo getDisplaySubjectInfo() {
    return this.displaySubjectInfo;
  }

  public DisplaySubjectParam setDisplaySubjectInfo(DisplaySubjectInfo displaySubjectInfo) {
    this.displaySubjectInfo = displaySubjectInfo;
    
    return this;
  }

  public void unsetDisplaySubjectInfo() {
    this.displaySubjectInfo = null;
  }

  /** Returns true if field displaySubjectInfo is set (has been asigned a value) and false otherwise */
  public boolean isSetDisplaySubjectInfo() {
    return this.displaySubjectInfo != null;
  }

  public void setDisplaySubjectInfoIsSet(boolean value) {
    if (!value) {
      this.displaySubjectInfo = null;
    }
  }

  public String getFlag() {
    return this.flag;
  }

  public DisplaySubjectParam setFlag(String flag) {
    this.flag = flag;
    
    return this;
  }

  public void unsetFlag() {
    this.flag = null;
  }

  /** Returns true if field flag is set (has been asigned a value) and false otherwise */
  public boolean isSetFlag() {
    return this.flag != null;
  }

  public void setFlagIsSet(boolean value) {
    if (!value) {
      this.flag = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DISPLAY_SUBJECT_INFO:
      if (value == null) {
        unsetDisplaySubjectInfo();
      } else {
        setDisplaySubjectInfo((DisplaySubjectInfo)value);
      }
      break;
    case FLAG:
      if (value == null) {
        unsetFlag();
      } else {
        setFlag((String)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DISPLAY_SUBJECT_INFO:
      return getDisplaySubjectInfo();
    case FLAG:
      return getFlag();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DISPLAY_SUBJECT_INFO:
      return isSetDisplaySubjectInfo();
    case FLAG:
      return isSetFlag();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DisplaySubjectParam)
      return this.equals((DisplaySubjectParam)that);
    return false;
  }

  public boolean equals(DisplaySubjectParam that) {
    if (that == null)
      return false;
    boolean this_present_displaySubjectInfo = true && this.isSetDisplaySubjectInfo();
    boolean that_present_displaySubjectInfo = true && that.isSetDisplaySubjectInfo();
    if (this_present_displaySubjectInfo || that_present_displaySubjectInfo) {
      if (!(this_present_displaySubjectInfo && that_present_displaySubjectInfo))
        return false;
      if (!this.displaySubjectInfo.equals(that.displaySubjectInfo))
        return false;
    }
    boolean this_present_flag = true && this.isSetFlag();
    boolean that_present_flag = true && that.isSetFlag();
    if (this_present_flag || that_present_flag) {
      if (!(this_present_flag && that_present_flag))
        return false;
      if (!this.flag.equals(that.flag))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_displaySubjectInfo = true && (isSetDisplaySubjectInfo());
    builder.append(present_displaySubjectInfo);
    if (present_displaySubjectInfo)
      builder.append(displaySubjectInfo);
    boolean present_flag = true && (isSetFlag());
    builder.append(present_flag);
    if (present_flag)
      builder.append(flag);
    return builder.toHashCode();
  }

  public int compareTo(DisplaySubjectParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    DisplaySubjectParam typedOther = (DisplaySubjectParam)other;

    lastComparison = Boolean.valueOf(isSetDisplaySubjectInfo()).compareTo(typedOther.isSetDisplaySubjectInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDisplaySubjectInfo()) {
      lastComparison = TBaseHelper.compareTo(this.displaySubjectInfo, typedOther.displaySubjectInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFlag()).compareTo(typedOther.isSetFlag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFlag()) {
      lastComparison = TBaseHelper.compareTo(this.flag, typedOther.flag);
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
        case 1: // DISPLAY_SUBJECT_INFO
          if (field.type == TType.STRUCT) {
            this.displaySubjectInfo = new DisplaySubjectInfo();
            this.displaySubjectInfo.read(iprot);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // FLAG
          if (field.type == TType.STRING) {
            this.flag = iprot.readString();
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
    if (this.displaySubjectInfo != null) {
      oprot.writeFieldBegin(DISPLAY_SUBJECT_INFO_FIELD_DESC);
      this.displaySubjectInfo.write(oprot);
      oprot.writeFieldEnd();
    }
    if (this.flag != null) {
      oprot.writeFieldBegin(FLAG_FIELD_DESC);
      oprot.writeString(this.flag);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("DisplaySubjectParam(");
    boolean first = true;
    sb.append("displaySubjectInfo:");
    if (this.displaySubjectInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.displaySubjectInfo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("flag:");
    if (this.flag == null) {
      sb.append("null");
    } else {
      sb.append(this.flag);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
