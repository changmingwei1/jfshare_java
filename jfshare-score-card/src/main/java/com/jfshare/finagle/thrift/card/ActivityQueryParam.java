/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.card;

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

public class ActivityQueryParam implements TBase<ActivityQueryParam, ActivityQueryParam._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ActivityQueryParam");

  private static final TField NAME_FIELD_DESC = new TField("name", TType.STRING, (short)1);
  private static final TField MIN_PIECE_VALUE_FIELD_DESC = new TField("minPieceValue", TType.STRING, (short)2);
  private static final TField MAX_PIECE_VALUE_FIELD_DESC = new TField("maxPieceValue", TType.STRING, (short)3);
  private static final TField MIN_START_TIME_FIELD_DESC = new TField("minStartTime", TType.STRING, (short)4);
  private static final TField MAX_START_TIME_FIELD_DESC = new TField("maxStartTime", TType.STRING, (short)5);
  private static final TField MIN_END_TIME_FIELD_DESC = new TField("minEndTime", TType.STRING, (short)6);
  private static final TField MAX_END_TIME_FIELD_DESC = new TField("maxEndTime", TType.STRING, (short)7);
  private static final TField CUR_STATUS_FIELD_DESC = new TField("curStatus", TType.STRING, (short)8);


  public String name;
  public String minPieceValue;
  public String maxPieceValue;
  public String minStartTime;
  public String maxStartTime;
  public String minEndTime;
  public String maxEndTime;
  public String curStatus;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    NAME((short)1, "name"),
    MIN_PIECE_VALUE((short)2, "minPieceValue"),
    MAX_PIECE_VALUE((short)3, "maxPieceValue"),
    MIN_START_TIME((short)4, "minStartTime"),
    MAX_START_TIME((short)5, "maxStartTime"),
    MIN_END_TIME((short)6, "minEndTime"),
    MAX_END_TIME((short)7, "maxEndTime"),
    CUR_STATUS((short)8, "curStatus");
  
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
        case 1: // NAME
  	return NAME;
        case 2: // MIN_PIECE_VALUE
  	return MIN_PIECE_VALUE;
        case 3: // MAX_PIECE_VALUE
  	return MAX_PIECE_VALUE;
        case 4: // MIN_START_TIME
  	return MIN_START_TIME;
        case 5: // MAX_START_TIME
  	return MAX_START_TIME;
        case 6: // MIN_END_TIME
  	return MIN_END_TIME;
        case 7: // MAX_END_TIME
  	return MAX_END_TIME;
        case 8: // CUR_STATUS
  	return CUR_STATUS;
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
    tmpMap.put(_Fields.NAME, new FieldMetaData("name", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.MIN_PIECE_VALUE, new FieldMetaData("minPieceValue", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.MAX_PIECE_VALUE, new FieldMetaData("maxPieceValue", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.MIN_START_TIME, new FieldMetaData("minStartTime", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.MAX_START_TIME, new FieldMetaData("maxStartTime", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.MIN_END_TIME, new FieldMetaData("minEndTime", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.MAX_END_TIME, new FieldMetaData("maxEndTime", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.CUR_STATUS, new FieldMetaData("curStatus", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ActivityQueryParam.class, metaDataMap);
  }


  public ActivityQueryParam() {
  }


  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ActivityQueryParam(ActivityQueryParam other) {
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetMinPieceValue()) {
      this.minPieceValue = other.minPieceValue;
    }
    if (other.isSetMaxPieceValue()) {
      this.maxPieceValue = other.maxPieceValue;
    }
    if (other.isSetMinStartTime()) {
      this.minStartTime = other.minStartTime;
    }
    if (other.isSetMaxStartTime()) {
      this.maxStartTime = other.maxStartTime;
    }
    if (other.isSetMinEndTime()) {
      this.minEndTime = other.minEndTime;
    }
    if (other.isSetMaxEndTime()) {
      this.maxEndTime = other.maxEndTime;
    }
    if (other.isSetCurStatus()) {
      this.curStatus = other.curStatus;
    }
  }

  public ActivityQueryParam deepCopy() {
    return new ActivityQueryParam(this);
  }

  @Override
  public void clear() {
    this.name = null;
    this.minPieceValue = null;
    this.maxPieceValue = null;
    this.minStartTime = null;
    this.maxStartTime = null;
    this.minEndTime = null;
    this.maxEndTime = null;
    this.curStatus = null;
  }

  public String getName() {
    return this.name;
  }

  public ActivityQueryParam setName(String name) {
    this.name = name;
    
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been asigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public String getMinPieceValue() {
    return this.minPieceValue;
  }

  public ActivityQueryParam setMinPieceValue(String minPieceValue) {
    this.minPieceValue = minPieceValue;
    
    return this;
  }

  public void unsetMinPieceValue() {
    this.minPieceValue = null;
  }

  /** Returns true if field minPieceValue is set (has been asigned a value) and false otherwise */
  public boolean isSetMinPieceValue() {
    return this.minPieceValue != null;
  }

  public void setMinPieceValueIsSet(boolean value) {
    if (!value) {
      this.minPieceValue = null;
    }
  }

  public String getMaxPieceValue() {
    return this.maxPieceValue;
  }

  public ActivityQueryParam setMaxPieceValue(String maxPieceValue) {
    this.maxPieceValue = maxPieceValue;
    
    return this;
  }

  public void unsetMaxPieceValue() {
    this.maxPieceValue = null;
  }

  /** Returns true if field maxPieceValue is set (has been asigned a value) and false otherwise */
  public boolean isSetMaxPieceValue() {
    return this.maxPieceValue != null;
  }

  public void setMaxPieceValueIsSet(boolean value) {
    if (!value) {
      this.maxPieceValue = null;
    }
  }

  public String getMinStartTime() {
    return this.minStartTime;
  }

  public ActivityQueryParam setMinStartTime(String minStartTime) {
    this.minStartTime = minStartTime;
    
    return this;
  }

  public void unsetMinStartTime() {
    this.minStartTime = null;
  }

  /** Returns true if field minStartTime is set (has been asigned a value) and false otherwise */
  public boolean isSetMinStartTime() {
    return this.minStartTime != null;
  }

  public void setMinStartTimeIsSet(boolean value) {
    if (!value) {
      this.minStartTime = null;
    }
  }

  public String getMaxStartTime() {
    return this.maxStartTime;
  }

  public ActivityQueryParam setMaxStartTime(String maxStartTime) {
    this.maxStartTime = maxStartTime;
    
    return this;
  }

  public void unsetMaxStartTime() {
    this.maxStartTime = null;
  }

  /** Returns true if field maxStartTime is set (has been asigned a value) and false otherwise */
  public boolean isSetMaxStartTime() {
    return this.maxStartTime != null;
  }

  public void setMaxStartTimeIsSet(boolean value) {
    if (!value) {
      this.maxStartTime = null;
    }
  }

  public String getMinEndTime() {
    return this.minEndTime;
  }

  public ActivityQueryParam setMinEndTime(String minEndTime) {
    this.minEndTime = minEndTime;
    
    return this;
  }

  public void unsetMinEndTime() {
    this.minEndTime = null;
  }

  /** Returns true if field minEndTime is set (has been asigned a value) and false otherwise */
  public boolean isSetMinEndTime() {
    return this.minEndTime != null;
  }

  public void setMinEndTimeIsSet(boolean value) {
    if (!value) {
      this.minEndTime = null;
    }
  }

  public String getMaxEndTime() {
    return this.maxEndTime;
  }

  public ActivityQueryParam setMaxEndTime(String maxEndTime) {
    this.maxEndTime = maxEndTime;
    
    return this;
  }

  public void unsetMaxEndTime() {
    this.maxEndTime = null;
  }

  /** Returns true if field maxEndTime is set (has been asigned a value) and false otherwise */
  public boolean isSetMaxEndTime() {
    return this.maxEndTime != null;
  }

  public void setMaxEndTimeIsSet(boolean value) {
    if (!value) {
      this.maxEndTime = null;
    }
  }

  public String getCurStatus() {
    return this.curStatus;
  }

  public ActivityQueryParam setCurStatus(String curStatus) {
    this.curStatus = curStatus;
    
    return this;
  }

  public void unsetCurStatus() {
    this.curStatus = null;
  }

  /** Returns true if field curStatus is set (has been asigned a value) and false otherwise */
  public boolean isSetCurStatus() {
    return this.curStatus != null;
  }

  public void setCurStatusIsSet(boolean value) {
    if (!value) {
      this.curStatus = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;
    case MIN_PIECE_VALUE:
      if (value == null) {
        unsetMinPieceValue();
      } else {
        setMinPieceValue((String)value);
      }
      break;
    case MAX_PIECE_VALUE:
      if (value == null) {
        unsetMaxPieceValue();
      } else {
        setMaxPieceValue((String)value);
      }
      break;
    case MIN_START_TIME:
      if (value == null) {
        unsetMinStartTime();
      } else {
        setMinStartTime((String)value);
      }
      break;
    case MAX_START_TIME:
      if (value == null) {
        unsetMaxStartTime();
      } else {
        setMaxStartTime((String)value);
      }
      break;
    case MIN_END_TIME:
      if (value == null) {
        unsetMinEndTime();
      } else {
        setMinEndTime((String)value);
      }
      break;
    case MAX_END_TIME:
      if (value == null) {
        unsetMaxEndTime();
      } else {
        setMaxEndTime((String)value);
      }
      break;
    case CUR_STATUS:
      if (value == null) {
        unsetCurStatus();
      } else {
        setCurStatus((String)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME:
      return getName();
    case MIN_PIECE_VALUE:
      return getMinPieceValue();
    case MAX_PIECE_VALUE:
      return getMaxPieceValue();
    case MIN_START_TIME:
      return getMinStartTime();
    case MAX_START_TIME:
      return getMaxStartTime();
    case MIN_END_TIME:
      return getMinEndTime();
    case MAX_END_TIME:
      return getMaxEndTime();
    case CUR_STATUS:
      return getCurStatus();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NAME:
      return isSetName();
    case MIN_PIECE_VALUE:
      return isSetMinPieceValue();
    case MAX_PIECE_VALUE:
      return isSetMaxPieceValue();
    case MIN_START_TIME:
      return isSetMinStartTime();
    case MAX_START_TIME:
      return isSetMaxStartTime();
    case MIN_END_TIME:
      return isSetMinEndTime();
    case MAX_END_TIME:
      return isSetMaxEndTime();
    case CUR_STATUS:
      return isSetCurStatus();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ActivityQueryParam)
      return this.equals((ActivityQueryParam)that);
    return false;
  }

  public boolean equals(ActivityQueryParam that) {
    if (that == null)
      return false;
    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }
    boolean this_present_minPieceValue = true && this.isSetMinPieceValue();
    boolean that_present_minPieceValue = true && that.isSetMinPieceValue();
    if (this_present_minPieceValue || that_present_minPieceValue) {
      if (!(this_present_minPieceValue && that_present_minPieceValue))
        return false;
      if (!this.minPieceValue.equals(that.minPieceValue))
        return false;
    }
    boolean this_present_maxPieceValue = true && this.isSetMaxPieceValue();
    boolean that_present_maxPieceValue = true && that.isSetMaxPieceValue();
    if (this_present_maxPieceValue || that_present_maxPieceValue) {
      if (!(this_present_maxPieceValue && that_present_maxPieceValue))
        return false;
      if (!this.maxPieceValue.equals(that.maxPieceValue))
        return false;
    }
    boolean this_present_minStartTime = true && this.isSetMinStartTime();
    boolean that_present_minStartTime = true && that.isSetMinStartTime();
    if (this_present_minStartTime || that_present_minStartTime) {
      if (!(this_present_minStartTime && that_present_minStartTime))
        return false;
      if (!this.minStartTime.equals(that.minStartTime))
        return false;
    }
    boolean this_present_maxStartTime = true && this.isSetMaxStartTime();
    boolean that_present_maxStartTime = true && that.isSetMaxStartTime();
    if (this_present_maxStartTime || that_present_maxStartTime) {
      if (!(this_present_maxStartTime && that_present_maxStartTime))
        return false;
      if (!this.maxStartTime.equals(that.maxStartTime))
        return false;
    }
    boolean this_present_minEndTime = true && this.isSetMinEndTime();
    boolean that_present_minEndTime = true && that.isSetMinEndTime();
    if (this_present_minEndTime || that_present_minEndTime) {
      if (!(this_present_minEndTime && that_present_minEndTime))
        return false;
      if (!this.minEndTime.equals(that.minEndTime))
        return false;
    }
    boolean this_present_maxEndTime = true && this.isSetMaxEndTime();
    boolean that_present_maxEndTime = true && that.isSetMaxEndTime();
    if (this_present_maxEndTime || that_present_maxEndTime) {
      if (!(this_present_maxEndTime && that_present_maxEndTime))
        return false;
      if (!this.maxEndTime.equals(that.maxEndTime))
        return false;
    }
    boolean this_present_curStatus = true && this.isSetCurStatus();
    boolean that_present_curStatus = true && that.isSetCurStatus();
    if (this_present_curStatus || that_present_curStatus) {
      if (!(this_present_curStatus && that_present_curStatus))
        return false;
      if (!this.curStatus.equals(that.curStatus))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_name = true && (isSetName());
    builder.append(present_name);
    if (present_name)
      builder.append(name);
    boolean present_minPieceValue = true && (isSetMinPieceValue());
    builder.append(present_minPieceValue);
    if (present_minPieceValue)
      builder.append(minPieceValue);
    boolean present_maxPieceValue = true && (isSetMaxPieceValue());
    builder.append(present_maxPieceValue);
    if (present_maxPieceValue)
      builder.append(maxPieceValue);
    boolean present_minStartTime = true && (isSetMinStartTime());
    builder.append(present_minStartTime);
    if (present_minStartTime)
      builder.append(minStartTime);
    boolean present_maxStartTime = true && (isSetMaxStartTime());
    builder.append(present_maxStartTime);
    if (present_maxStartTime)
      builder.append(maxStartTime);
    boolean present_minEndTime = true && (isSetMinEndTime());
    builder.append(present_minEndTime);
    if (present_minEndTime)
      builder.append(minEndTime);
    boolean present_maxEndTime = true && (isSetMaxEndTime());
    builder.append(present_maxEndTime);
    if (present_maxEndTime)
      builder.append(maxEndTime);
    boolean present_curStatus = true && (isSetCurStatus());
    builder.append(present_curStatus);
    if (present_curStatus)
      builder.append(curStatus);
    return builder.toHashCode();
  }

  public int compareTo(ActivityQueryParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ActivityQueryParam typedOther = (ActivityQueryParam)other;

    lastComparison = Boolean.valueOf(isSetName()).compareTo(typedOther.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = TBaseHelper.compareTo(this.name, typedOther.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMinPieceValue()).compareTo(typedOther.isSetMinPieceValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMinPieceValue()) {
      lastComparison = TBaseHelper.compareTo(this.minPieceValue, typedOther.minPieceValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMaxPieceValue()).compareTo(typedOther.isSetMaxPieceValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMaxPieceValue()) {
      lastComparison = TBaseHelper.compareTo(this.maxPieceValue, typedOther.maxPieceValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMinStartTime()).compareTo(typedOther.isSetMinStartTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMinStartTime()) {
      lastComparison = TBaseHelper.compareTo(this.minStartTime, typedOther.minStartTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMaxStartTime()).compareTo(typedOther.isSetMaxStartTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMaxStartTime()) {
      lastComparison = TBaseHelper.compareTo(this.maxStartTime, typedOther.maxStartTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMinEndTime()).compareTo(typedOther.isSetMinEndTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMinEndTime()) {
      lastComparison = TBaseHelper.compareTo(this.minEndTime, typedOther.minEndTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMaxEndTime()).compareTo(typedOther.isSetMaxEndTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMaxEndTime()) {
      lastComparison = TBaseHelper.compareTo(this.maxEndTime, typedOther.maxEndTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurStatus()).compareTo(typedOther.isSetCurStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurStatus()) {
      lastComparison = TBaseHelper.compareTo(this.curStatus, typedOther.curStatus);
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
        case 1: // NAME
          if (field.type == TType.STRING) {
            this.name = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // MIN_PIECE_VALUE
          if (field.type == TType.STRING) {
            this.minPieceValue = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // MAX_PIECE_VALUE
          if (field.type == TType.STRING) {
            this.maxPieceValue = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // MIN_START_TIME
          if (field.type == TType.STRING) {
            this.minStartTime = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // MAX_START_TIME
          if (field.type == TType.STRING) {
            this.maxStartTime = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 6: // MIN_END_TIME
          if (field.type == TType.STRING) {
            this.minEndTime = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 7: // MAX_END_TIME
          if (field.type == TType.STRING) {
            this.maxEndTime = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 8: // CUR_STATUS
          if (field.type == TType.STRING) {
            this.curStatus = iprot.readString();
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
    if (this.name != null) {
      if (isSetName()) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(this.name);
        oprot.writeFieldEnd();
      }
    }
    if (this.minPieceValue != null) {
      if (isSetMinPieceValue()) {
        oprot.writeFieldBegin(MIN_PIECE_VALUE_FIELD_DESC);
        oprot.writeString(this.minPieceValue);
        oprot.writeFieldEnd();
      }
    }
    if (this.maxPieceValue != null) {
      if (isSetMaxPieceValue()) {
        oprot.writeFieldBegin(MAX_PIECE_VALUE_FIELD_DESC);
        oprot.writeString(this.maxPieceValue);
        oprot.writeFieldEnd();
      }
    }
    if (this.minStartTime != null) {
      if (isSetMinStartTime()) {
        oprot.writeFieldBegin(MIN_START_TIME_FIELD_DESC);
        oprot.writeString(this.minStartTime);
        oprot.writeFieldEnd();
      }
    }
    if (this.maxStartTime != null) {
      if (isSetMaxStartTime()) {
        oprot.writeFieldBegin(MAX_START_TIME_FIELD_DESC);
        oprot.writeString(this.maxStartTime);
        oprot.writeFieldEnd();
      }
    }
    if (this.minEndTime != null) {
      if (isSetMinEndTime()) {
        oprot.writeFieldBegin(MIN_END_TIME_FIELD_DESC);
        oprot.writeString(this.minEndTime);
        oprot.writeFieldEnd();
      }
    }
    if (this.maxEndTime != null) {
      if (isSetMaxEndTime()) {
        oprot.writeFieldBegin(MAX_END_TIME_FIELD_DESC);
        oprot.writeString(this.maxEndTime);
        oprot.writeFieldEnd();
      }
    }
    if (this.curStatus != null) {
      if (isSetCurStatus()) {
        oprot.writeFieldBegin(CUR_STATUS_FIELD_DESC);
        oprot.writeString(this.curStatus);
        oprot.writeFieldEnd();
      }
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ActivityQueryParam(");
    boolean first = true;
    if (isSetName()) {
      sb.append("name:");
      if (this.name == null) {
        sb.append("null");
      } else {
        sb.append(this.name);
      }
      first = false;
      }
    if (isSetMinPieceValue()) {
      if (!first) sb.append(", ");
      sb.append("minPieceValue:");
      if (this.minPieceValue == null) {
        sb.append("null");
      } else {
        sb.append(this.minPieceValue);
      }
      first = false;
      }
    if (isSetMaxPieceValue()) {
      if (!first) sb.append(", ");
      sb.append("maxPieceValue:");
      if (this.maxPieceValue == null) {
        sb.append("null");
      } else {
        sb.append(this.maxPieceValue);
      }
      first = false;
      }
    if (isSetMinStartTime()) {
      if (!first) sb.append(", ");
      sb.append("minStartTime:");
      if (this.minStartTime == null) {
        sb.append("null");
      } else {
        sb.append(this.minStartTime);
      }
      first = false;
      }
    if (isSetMaxStartTime()) {
      if (!first) sb.append(", ");
      sb.append("maxStartTime:");
      if (this.maxStartTime == null) {
        sb.append("null");
      } else {
        sb.append(this.maxStartTime);
      }
      first = false;
      }
    if (isSetMinEndTime()) {
      if (!first) sb.append(", ");
      sb.append("minEndTime:");
      if (this.minEndTime == null) {
        sb.append("null");
      } else {
        sb.append(this.minEndTime);
      }
      first = false;
      }
    if (isSetMaxEndTime()) {
      if (!first) sb.append(", ");
      sb.append("maxEndTime:");
      if (this.maxEndTime == null) {
        sb.append("null");
      } else {
        sb.append(this.maxEndTime);
      }
      first = false;
      }
    if (isSetCurStatus()) {
      if (!first) sb.append(", ");
      sb.append("curStatus:");
      if (this.curStatus == null) {
        sb.append("null");
      } else {
        sb.append(this.curStatus);
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
