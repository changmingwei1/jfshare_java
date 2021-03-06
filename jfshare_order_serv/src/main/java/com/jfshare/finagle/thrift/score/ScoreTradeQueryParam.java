/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.score;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.thrift.*;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.protocol.*;

import java.util.*;

// No additional import required for struct/union.

public class ScoreTradeQueryParam implements TBase<ScoreTradeQueryParam, ScoreTradeQueryParam._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ScoreTradeQueryParam");

  private static final TField USER_ID_FIELD_DESC = new TField("userId", TType.I32, (short)1);
  private static final TField START_TIME_FIELD_DESC = new TField("startTime", TType.STRING, (short)2);
  private static final TField END_TIME_FIELD_DESC = new TField("endTime", TType.STRING, (short)3);
  private static final TField IN_OR_OUT_FIELD_DESC = new TField("inOrOut", TType.I32, (short)4);
  private static final TField TYPE_FIELD_DESC = new TField("type", TType.I32, (short)5);


  public int userId;
  public String startTime;
  public String endTime;
  public int inOrOut;
  public int type;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    USER_ID((short)1, "userId"),
    START_TIME((short)2, "startTime"),
    END_TIME((short)3, "endTime"),
    IN_OR_OUT((short)4, "inOrOut"),
    TYPE((short)5, "type");
  
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
        case 1: // USER_ID
  	return USER_ID;
        case 2: // START_TIME
  	return START_TIME;
        case 3: // END_TIME
  	return END_TIME;
        case 4: // IN_OR_OUT
  	return IN_OR_OUT;
        case 5: // TYPE
  	return TYPE;
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
  private static final int __USERID_ISSET_ID = 0;
  private static final int __INOROUT_ISSET_ID = 1;
  private static final int __TYPE_ISSET_ID = 2;
  private BitSet __isset_bit_vector = new BitSet(3);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_ID, new FieldMetaData("userId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.START_TIME, new FieldMetaData("startTime", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.END_TIME, new FieldMetaData("endTime", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.IN_OR_OUT, new FieldMetaData("inOrOut", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.TYPE, new FieldMetaData("type", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ScoreTradeQueryParam.class, metaDataMap);
  }


  public ScoreTradeQueryParam() {
  }

  public ScoreTradeQueryParam(
    int userId,
    String startTime,
    String endTime,
    int inOrOut,
    int type)
  {
    this();
    this.userId = userId;
    setUserIdIsSet(true);
    this.startTime = startTime;
    this.endTime = endTime;
    this.inOrOut = inOrOut;
    setInOrOutIsSet(true);
    this.type = type;
    setTypeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ScoreTradeQueryParam(ScoreTradeQueryParam other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.userId = other.userId;
    if (other.isSetStartTime()) {
      this.startTime = other.startTime;
    }
    if (other.isSetEndTime()) {
      this.endTime = other.endTime;
    }
    this.inOrOut = other.inOrOut;
    this.type = other.type;
  }

  public ScoreTradeQueryParam deepCopy() {
    return new ScoreTradeQueryParam(this);
  }

  @Override
  public void clear() {
    setUserIdIsSet(false);
    this.userId = 0;
    this.startTime = null;
    this.endTime = null;
    setInOrOutIsSet(false);
    this.inOrOut = 0;
    setTypeIsSet(false);
    this.type = 0;
  }

  public int getUserId() {
    return this.userId;
  }

  public ScoreTradeQueryParam setUserId(int userId) {
    this.userId = userId;
    setUserIdIsSet(true);

    return this;
  }

  public void unsetUserId() {
  __isset_bit_vector.clear(__USERID_ISSET_ID);
  }

  /** Returns true if field userId is set (has been asigned a value) and false otherwise */
  public boolean isSetUserId() {
    return __isset_bit_vector.get(__USERID_ISSET_ID);
  }

  public void setUserIdIsSet(boolean value) {
    __isset_bit_vector.set(__USERID_ISSET_ID, value);
  }

  public String getStartTime() {
    return this.startTime;
  }

  public ScoreTradeQueryParam setStartTime(String startTime) {
    this.startTime = startTime;
    
    return this;
  }

  public void unsetStartTime() {
    this.startTime = null;
  }

  /** Returns true if field startTime is set (has been asigned a value) and false otherwise */
  public boolean isSetStartTime() {
    return this.startTime != null;
  }

  public void setStartTimeIsSet(boolean value) {
    if (!value) {
      this.startTime = null;
    }
  }

  public String getEndTime() {
    return this.endTime;
  }

  public ScoreTradeQueryParam setEndTime(String endTime) {
    this.endTime = endTime;
    
    return this;
  }

  public void unsetEndTime() {
    this.endTime = null;
  }

  /** Returns true if field endTime is set (has been asigned a value) and false otherwise */
  public boolean isSetEndTime() {
    return this.endTime != null;
  }

  public void setEndTimeIsSet(boolean value) {
    if (!value) {
      this.endTime = null;
    }
  }

  public int getInOrOut() {
    return this.inOrOut;
  }

  public ScoreTradeQueryParam setInOrOut(int inOrOut) {
    this.inOrOut = inOrOut;
    setInOrOutIsSet(true);

    return this;
  }

  public void unsetInOrOut() {
  __isset_bit_vector.clear(__INOROUT_ISSET_ID);
  }

  /** Returns true if field inOrOut is set (has been asigned a value) and false otherwise */
  public boolean isSetInOrOut() {
    return __isset_bit_vector.get(__INOROUT_ISSET_ID);
  }

  public void setInOrOutIsSet(boolean value) {
    __isset_bit_vector.set(__INOROUT_ISSET_ID, value);
  }

  public int getType() {
    return this.type;
  }

  public ScoreTradeQueryParam setType(int type) {
    this.type = type;
    setTypeIsSet(true);

    return this;
  }

  public void unsetType() {
  __isset_bit_vector.clear(__TYPE_ISSET_ID);
  }

  /** Returns true if field type is set (has been asigned a value) and false otherwise */
  public boolean isSetType() {
    return __isset_bit_vector.get(__TYPE_ISSET_ID);
  }

  public void setTypeIsSet(boolean value) {
    __isset_bit_vector.set(__TYPE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((Integer)value);
      }
      break;
    case START_TIME:
      if (value == null) {
        unsetStartTime();
      } else {
        setStartTime((String)value);
      }
      break;
    case END_TIME:
      if (value == null) {
        unsetEndTime();
      } else {
        setEndTime((String)value);
      }
      break;
    case IN_OR_OUT:
      if (value == null) {
        unsetInOrOut();
      } else {
        setInOrOut((Integer)value);
      }
      break;
    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((Integer)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return new Integer(getUserId());
    case START_TIME:
      return getStartTime();
    case END_TIME:
      return getEndTime();
    case IN_OR_OUT:
      return new Integer(getInOrOut());
    case TYPE:
      return new Integer(getType());
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case USER_ID:
      return isSetUserId();
    case START_TIME:
      return isSetStartTime();
    case END_TIME:
      return isSetEndTime();
    case IN_OR_OUT:
      return isSetInOrOut();
    case TYPE:
      return isSetType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ScoreTradeQueryParam)
      return this.equals((ScoreTradeQueryParam)that);
    return false;
  }

  public boolean equals(ScoreTradeQueryParam that) {
    if (that == null)
      return false;
    boolean this_present_userId = true;
    boolean that_present_userId = true;
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (this.userId != that.userId)
        return false;
    }
    boolean this_present_startTime = true && this.isSetStartTime();
    boolean that_present_startTime = true && that.isSetStartTime();
    if (this_present_startTime || that_present_startTime) {
      if (!(this_present_startTime && that_present_startTime))
        return false;
      if (!this.startTime.equals(that.startTime))
        return false;
    }
    boolean this_present_endTime = true && this.isSetEndTime();
    boolean that_present_endTime = true && that.isSetEndTime();
    if (this_present_endTime || that_present_endTime) {
      if (!(this_present_endTime && that_present_endTime))
        return false;
      if (!this.endTime.equals(that.endTime))
        return false;
    }
    boolean this_present_inOrOut = true;
    boolean that_present_inOrOut = true;
    if (this_present_inOrOut || that_present_inOrOut) {
      if (!(this_present_inOrOut && that_present_inOrOut))
        return false;
      if (this.inOrOut != that.inOrOut)
        return false;
    }
    boolean this_present_type = true;
    boolean that_present_type = true;
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (this.type != that.type)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_userId = true;
    builder.append(present_userId);
    if (present_userId)
      builder.append(userId);
    boolean present_startTime = true && (isSetStartTime());
    builder.append(present_startTime);
    if (present_startTime)
      builder.append(startTime);
    boolean present_endTime = true && (isSetEndTime());
    builder.append(present_endTime);
    if (present_endTime)
      builder.append(endTime);
    boolean present_inOrOut = true;
    builder.append(present_inOrOut);
    if (present_inOrOut)
      builder.append(inOrOut);
    boolean present_type = true;
    builder.append(present_type);
    if (present_type)
      builder.append(type);
    return builder.toHashCode();
  }

  public int compareTo(ScoreTradeQueryParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ScoreTradeQueryParam typedOther = (ScoreTradeQueryParam)other;

    lastComparison = Boolean.valueOf(isSetUserId()).compareTo(typedOther.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = TBaseHelper.compareTo(this.userId, typedOther.userId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStartTime()).compareTo(typedOther.isSetStartTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartTime()) {
      lastComparison = TBaseHelper.compareTo(this.startTime, typedOther.startTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndTime()).compareTo(typedOther.isSetEndTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndTime()) {
      lastComparison = TBaseHelper.compareTo(this.endTime, typedOther.endTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetInOrOut()).compareTo(typedOther.isSetInOrOut());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInOrOut()) {
      lastComparison = TBaseHelper.compareTo(this.inOrOut, typedOther.inOrOut);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(typedOther.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = TBaseHelper.compareTo(this.type, typedOther.type);
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
        case 1: // USER_ID
          if (field.type == TType.I32) {
            this.userId = iprot.readI32();
            setUserIdIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // START_TIME
          if (field.type == TType.STRING) {
            this.startTime = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // END_TIME
          if (field.type == TType.STRING) {
            this.endTime = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // IN_OR_OUT
          if (field.type == TType.I32) {
            this.inOrOut = iprot.readI32();
            setInOrOutIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // TYPE
          if (field.type == TType.I32) {
            this.type = iprot.readI32();
            setTypeIsSet(true);
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
    oprot.writeFieldBegin(USER_ID_FIELD_DESC);
    oprot.writeI32(this.userId);
    oprot.writeFieldEnd();
    if (this.startTime != null) {
      oprot.writeFieldBegin(START_TIME_FIELD_DESC);
      oprot.writeString(this.startTime);
      oprot.writeFieldEnd();
    }
    if (this.endTime != null) {
      oprot.writeFieldBegin(END_TIME_FIELD_DESC);
      oprot.writeString(this.endTime);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(IN_OR_OUT_FIELD_DESC);
    oprot.writeI32(this.inOrOut);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(TYPE_FIELD_DESC);
    oprot.writeI32(this.type);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ScoreTradeQueryParam(");
    boolean first = true;
    sb.append("userId:");
    sb.append(this.userId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("startTime:");
    if (this.startTime == null) {
      sb.append("null");
    } else {
      sb.append(this.startTime);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("endTime:");
    if (this.endTime == null) {
      sb.append("null");
    } else {
      sb.append(this.endTime);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("inOrOut:");
    sb.append(this.inOrOut);
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    sb.append(this.type);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
