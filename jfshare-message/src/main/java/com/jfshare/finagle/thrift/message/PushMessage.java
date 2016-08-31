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

public class PushMessage implements TBase<PushMessage, PushMessage._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("PushMessage");

  private static final TField TITLE_FIELD_DESC = new TField("title", TType.STRING, (short)1);
  private static final TField CONTENT_FIELD_DESC = new TField("content", TType.STRING, (short)2);
  private static final TField OBJ_TYPE_FIELD_DESC = new TField("objType", TType.I32, (short)3);
  private static final TField ALERT_FIELD_DESC = new TField("alert", TType.STRING, (short)4);


  public String title;
  public String content;
  public int objType;
  public String alert;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    TITLE((short)1, "title"),
    CONTENT((short)2, "content"),
    OBJ_TYPE((short)3, "objType"),
    ALERT((short)4, "alert");
  
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
        case 1: // TITLE
  	return TITLE;
        case 2: // CONTENT
  	return CONTENT;
        case 3: // OBJ_TYPE
  	return OBJ_TYPE;
        case 4: // ALERT
  	return ALERT;
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
  private static final int __OBJTYPE_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TITLE, new FieldMetaData("title", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.CONTENT, new FieldMetaData("content", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.OBJ_TYPE, new FieldMetaData("objType", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.ALERT, new FieldMetaData("alert", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(PushMessage.class, metaDataMap);
  }


  public PushMessage() {
  }

  public PushMessage(
    String title,
    String content,
    int objType,
    String alert)
  {
    this();
    this.title = title;
    this.content = content;
    this.objType = objType;
    setObjTypeIsSet(true);
    this.alert = alert;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PushMessage(PushMessage other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetTitle()) {
      this.title = other.title;
    }
    if (other.isSetContent()) {
      this.content = other.content;
    }
    this.objType = other.objType;
    if (other.isSetAlert()) {
      this.alert = other.alert;
    }
  }

  public PushMessage deepCopy() {
    return new PushMessage(this);
  }

  @Override
  public void clear() {
    this.title = null;
    this.content = null;
    setObjTypeIsSet(false);
    this.objType = 0;
    this.alert = null;
  }

  public String getTitle() {
    return this.title;
  }

  public PushMessage setTitle(String title) {
    this.title = title;
    
    return this;
  }

  public void unsetTitle() {
    this.title = null;
  }

  /** Returns true if field title is set (has been asigned a value) and false otherwise */
  public boolean isSetTitle() {
    return this.title != null;
  }

  public void setTitleIsSet(boolean value) {
    if (!value) {
      this.title = null;
    }
  }

  public String getContent() {
    return this.content;
  }

  public PushMessage setContent(String content) {
    this.content = content;
    
    return this;
  }

  public void unsetContent() {
    this.content = null;
  }

  /** Returns true if field content is set (has been asigned a value) and false otherwise */
  public boolean isSetContent() {
    return this.content != null;
  }

  public void setContentIsSet(boolean value) {
    if (!value) {
      this.content = null;
    }
  }

  public int getObjType() {
    return this.objType;
  }

  public PushMessage setObjType(int objType) {
    this.objType = objType;
    setObjTypeIsSet(true);

    return this;
  }

  public void unsetObjType() {
  __isset_bit_vector.clear(__OBJTYPE_ISSET_ID);
  }

  /** Returns true if field objType is set (has been asigned a value) and false otherwise */
  public boolean isSetObjType() {
    return __isset_bit_vector.get(__OBJTYPE_ISSET_ID);
  }

  public void setObjTypeIsSet(boolean value) {
    __isset_bit_vector.set(__OBJTYPE_ISSET_ID, value);
  }

  public String getAlert() {
    return this.alert;
  }

  public PushMessage setAlert(String alert) {
    this.alert = alert;
    
    return this;
  }

  public void unsetAlert() {
    this.alert = null;
  }

  /** Returns true if field alert is set (has been asigned a value) and false otherwise */
  public boolean isSetAlert() {
    return this.alert != null;
  }

  public void setAlertIsSet(boolean value) {
    if (!value) {
      this.alert = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TITLE:
      if (value == null) {
        unsetTitle();
      } else {
        setTitle((String)value);
      }
      break;
    case CONTENT:
      if (value == null) {
        unsetContent();
      } else {
        setContent((String)value);
      }
      break;
    case OBJ_TYPE:
      if (value == null) {
        unsetObjType();
      } else {
        setObjType((Integer)value);
      }
      break;
    case ALERT:
      if (value == null) {
        unsetAlert();
      } else {
        setAlert((String)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TITLE:
      return getTitle();
    case CONTENT:
      return getContent();
    case OBJ_TYPE:
      return new Integer(getObjType());
    case ALERT:
      return getAlert();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TITLE:
      return isSetTitle();
    case CONTENT:
      return isSetContent();
    case OBJ_TYPE:
      return isSetObjType();
    case ALERT:
      return isSetAlert();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PushMessage)
      return this.equals((PushMessage)that);
    return false;
  }

  public boolean equals(PushMessage that) {
    if (that == null)
      return false;
    boolean this_present_title = true && this.isSetTitle();
    boolean that_present_title = true && that.isSetTitle();
    if (this_present_title || that_present_title) {
      if (!(this_present_title && that_present_title))
        return false;
      if (!this.title.equals(that.title))
        return false;
    }
    boolean this_present_content = true && this.isSetContent();
    boolean that_present_content = true && that.isSetContent();
    if (this_present_content || that_present_content) {
      if (!(this_present_content && that_present_content))
        return false;
      if (!this.content.equals(that.content))
        return false;
    }
    boolean this_present_objType = true;
    boolean that_present_objType = true;
    if (this_present_objType || that_present_objType) {
      if (!(this_present_objType && that_present_objType))
        return false;
      if (this.objType != that.objType)
        return false;
    }
    boolean this_present_alert = true && this.isSetAlert();
    boolean that_present_alert = true && that.isSetAlert();
    if (this_present_alert || that_present_alert) {
      if (!(this_present_alert && that_present_alert))
        return false;
      if (!this.alert.equals(that.alert))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_title = true && (isSetTitle());
    builder.append(present_title);
    if (present_title)
      builder.append(title);
    boolean present_content = true && (isSetContent());
    builder.append(present_content);
    if (present_content)
      builder.append(content);
    boolean present_objType = true;
    builder.append(present_objType);
    if (present_objType)
      builder.append(objType);
    boolean present_alert = true && (isSetAlert());
    builder.append(present_alert);
    if (present_alert)
      builder.append(alert);
    return builder.toHashCode();
  }

  public int compareTo(PushMessage other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    PushMessage typedOther = (PushMessage)other;

    lastComparison = Boolean.valueOf(isSetTitle()).compareTo(typedOther.isSetTitle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTitle()) {
      lastComparison = TBaseHelper.compareTo(this.title, typedOther.title);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetContent()).compareTo(typedOther.isSetContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContent()) {
      lastComparison = TBaseHelper.compareTo(this.content, typedOther.content);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetObjType()).compareTo(typedOther.isSetObjType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetObjType()) {
      lastComparison = TBaseHelper.compareTo(this.objType, typedOther.objType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAlert()).compareTo(typedOther.isSetAlert());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAlert()) {
      lastComparison = TBaseHelper.compareTo(this.alert, typedOther.alert);
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
        case 1: // TITLE
          if (field.type == TType.STRING) {
            this.title = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // CONTENT
          if (field.type == TType.STRING) {
            this.content = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // OBJ_TYPE
          if (field.type == TType.I32) {
            this.objType = iprot.readI32();
            setObjTypeIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // ALERT
          if (field.type == TType.STRING) {
            this.alert = iprot.readString();
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
    if (this.title != null) {
      oprot.writeFieldBegin(TITLE_FIELD_DESC);
      oprot.writeString(this.title);
      oprot.writeFieldEnd();
    }
    if (this.content != null) {
      oprot.writeFieldBegin(CONTENT_FIELD_DESC);
      oprot.writeString(this.content);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(OBJ_TYPE_FIELD_DESC);
    oprot.writeI32(this.objType);
    oprot.writeFieldEnd();
    if (this.alert != null) {
      oprot.writeFieldBegin(ALERT_FIELD_DESC);
      oprot.writeString(this.alert);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("PushMessage(");
    boolean first = true;
    sb.append("title:");
    if (this.title == null) {
      sb.append("null");
    } else {
      sb.append(this.title);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("content:");
    if (this.content == null) {
      sb.append("null");
    } else {
      sb.append(this.content);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("objType:");
    sb.append(this.objType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("alert:");
    if (this.alert == null) {
      sb.append("null");
    } else {
      sb.append(this.alert);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
