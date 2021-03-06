/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.message;

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

public class GetUpgradeParam implements TBase<GetUpgradeParam, GetUpgradeParam._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("GetUpgradeParam");

  private static final TField APP_TYPE_FIELD_DESC = new TField("appType", TType.I32, (short)1);
  private static final TField VERSION_FIELD_DESC = new TField("version", TType.I32, (short)2);


  public int appType;
  public int version;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    APP_TYPE((short)1, "appType"),
    VERSION((short)2, "version");
  
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
        case 1: // APP_TYPE
  	return APP_TYPE;
        case 2: // VERSION
  	return VERSION;
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
  private static final int __APPTYPE_ISSET_ID = 0;
  private static final int __VERSION_ISSET_ID = 1;
  private BitSet __isset_bit_vector = new BitSet(2);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.APP_TYPE, new FieldMetaData("appType", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.VERSION, new FieldMetaData("version", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(GetUpgradeParam.class, metaDataMap);
  }


  public GetUpgradeParam() {
  }

  public GetUpgradeParam(
    int appType,
    int version)
  {
    this();
    this.appType = appType;
    setAppTypeIsSet(true);
    this.version = version;
    setVersionIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetUpgradeParam(GetUpgradeParam other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.appType = other.appType;
    this.version = other.version;
  }

  public GetUpgradeParam deepCopy() {
    return new GetUpgradeParam(this);
  }

  @Override
  public void clear() {
    setAppTypeIsSet(false);
    this.appType = 0;
    setVersionIsSet(false);
    this.version = 0;
  }

  public int getAppType() {
    return this.appType;
  }

  public GetUpgradeParam setAppType(int appType) {
    this.appType = appType;
    setAppTypeIsSet(true);

    return this;
  }

  public void unsetAppType() {
  __isset_bit_vector.clear(__APPTYPE_ISSET_ID);
  }

  /** Returns true if field appType is set (has been asigned a value) and false otherwise */
  public boolean isSetAppType() {
    return __isset_bit_vector.get(__APPTYPE_ISSET_ID);
  }

  public void setAppTypeIsSet(boolean value) {
    __isset_bit_vector.set(__APPTYPE_ISSET_ID, value);
  }

  public int getVersion() {
    return this.version;
  }

  public GetUpgradeParam setVersion(int version) {
    this.version = version;
    setVersionIsSet(true);

    return this;
  }

  public void unsetVersion() {
  __isset_bit_vector.clear(__VERSION_ISSET_ID);
  }

  /** Returns true if field version is set (has been asigned a value) and false otherwise */
  public boolean isSetVersion() {
    return __isset_bit_vector.get(__VERSION_ISSET_ID);
  }

  public void setVersionIsSet(boolean value) {
    __isset_bit_vector.set(__VERSION_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case APP_TYPE:
      if (value == null) {
        unsetAppType();
      } else {
        setAppType((Integer)value);
      }
      break;
    case VERSION:
      if (value == null) {
        unsetVersion();
      } else {
        setVersion((Integer)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case APP_TYPE:
      return new Integer(getAppType());
    case VERSION:
      return new Integer(getVersion());
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case APP_TYPE:
      return isSetAppType();
    case VERSION:
      return isSetVersion();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetUpgradeParam)
      return this.equals((GetUpgradeParam)that);
    return false;
  }

  public boolean equals(GetUpgradeParam that) {
    if (that == null)
      return false;
    boolean this_present_appType = true;
    boolean that_present_appType = true;
    if (this_present_appType || that_present_appType) {
      if (!(this_present_appType && that_present_appType))
        return false;
      if (this.appType != that.appType)
        return false;
    }
    boolean this_present_version = true;
    boolean that_present_version = true;
    if (this_present_version || that_present_version) {
      if (!(this_present_version && that_present_version))
        return false;
      if (this.version != that.version)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_appType = true;
    builder.append(present_appType);
    if (present_appType)
      builder.append(appType);
    boolean present_version = true;
    builder.append(present_version);
    if (present_version)
      builder.append(version);
    return builder.toHashCode();
  }

  public int compareTo(GetUpgradeParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    GetUpgradeParam typedOther = (GetUpgradeParam)other;

    lastComparison = Boolean.valueOf(isSetAppType()).compareTo(typedOther.isSetAppType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAppType()) {
      lastComparison = TBaseHelper.compareTo(this.appType, typedOther.appType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVersion()).compareTo(typedOther.isSetVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersion()) {
      lastComparison = TBaseHelper.compareTo(this.version, typedOther.version);
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
        case 1: // APP_TYPE
          if (field.type == TType.I32) {
            this.appType = iprot.readI32();
            setAppTypeIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // VERSION
          if (field.type == TType.I32) {
            this.version = iprot.readI32();
            setVersionIsSet(true);
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
    oprot.writeFieldBegin(APP_TYPE_FIELD_DESC);
    oprot.writeI32(this.appType);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(VERSION_FIELD_DESC);
    oprot.writeI32(this.version);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("GetUpgradeParam(");
    boolean first = true;
    sb.append("appType:");
    sb.append(this.appType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("version:");
    sb.append(this.version);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
