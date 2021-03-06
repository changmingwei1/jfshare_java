/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.baseTemplate;

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

public class StorehouseResult implements TBase<StorehouseResult, StorehouseResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("StorehouseResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField STOREHOUSE_LIST_FIELD_DESC = new TField("storehouseList", TType.LIST, (short)2);


  public com.jfshare.finagle.thrift.result.Result result;
  public List<Storehouse> storehouseList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    STOREHOUSE_LIST((short)2, "storehouseList");
  
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
        case 2: // STOREHOUSE_LIST
  	return STOREHOUSE_LIST;
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
    tmpMap.put(_Fields.STOREHOUSE_LIST, new FieldMetaData("storehouseList", TFieldRequirementType.OPTIONAL,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, Storehouse.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(StorehouseResult.class, metaDataMap);
  }


  public StorehouseResult() {
  }

  public StorehouseResult(
    com.jfshare.finagle.thrift.result.Result result)
  {
    this();
    this.result = result;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StorehouseResult(StorehouseResult other) {
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetStorehouseList()) {
      List<Storehouse> __this__storehouseList = new ArrayList<Storehouse>();
      for (Storehouse other_element : other.storehouseList) {
        __this__storehouseList.add(new Storehouse(other_element));
      }
      this.storehouseList = __this__storehouseList;
    }
  }

  public StorehouseResult deepCopy() {
    return new StorehouseResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.storehouseList = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public StorehouseResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public int getStorehouseListSize() {
    return (this.storehouseList == null) ? 0 : this.storehouseList.size();
  }

  public java.util.Iterator<Storehouse> getStorehouseListIterator() {
    return (this.storehouseList == null) ? null : this.storehouseList.iterator();
  }

  public void addToStorehouseList(Storehouse elem) {
    if (this.storehouseList == null) {
      this.storehouseList = new ArrayList<Storehouse>();
    }
    this.storehouseList.add(elem);
  }

  public List<Storehouse> getStorehouseList() {
    return this.storehouseList;
  }

  public StorehouseResult setStorehouseList(List<Storehouse> storehouseList) {
    this.storehouseList = storehouseList;
    
    return this;
  }

  public void unsetStorehouseList() {
    this.storehouseList = null;
  }

  /** Returns true if field storehouseList is set (has been asigned a value) and false otherwise */
  public boolean isSetStorehouseList() {
    return this.storehouseList != null;
  }

  public void setStorehouseListIsSet(boolean value) {
    if (!value) {
      this.storehouseList = null;
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
    case STOREHOUSE_LIST:
      if (value == null) {
        unsetStorehouseList();
      } else {
        setStorehouseList((List<Storehouse>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case STOREHOUSE_LIST:
      return getStorehouseList();
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
    case STOREHOUSE_LIST:
      return isSetStorehouseList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StorehouseResult)
      return this.equals((StorehouseResult)that);
    return false;
  }

  public boolean equals(StorehouseResult that) {
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
    boolean this_present_storehouseList = true && this.isSetStorehouseList();
    boolean that_present_storehouseList = true && that.isSetStorehouseList();
    if (this_present_storehouseList || that_present_storehouseList) {
      if (!(this_present_storehouseList && that_present_storehouseList))
        return false;
      if (!this.storehouseList.equals(that.storehouseList))
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
    boolean present_storehouseList = true && (isSetStorehouseList());
    builder.append(present_storehouseList);
    if (present_storehouseList)
      builder.append(storehouseList);
    return builder.toHashCode();
  }

  public int compareTo(StorehouseResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    StorehouseResult typedOther = (StorehouseResult)other;

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
    lastComparison = Boolean.valueOf(isSetStorehouseList()).compareTo(typedOther.isSetStorehouseList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStorehouseList()) {
      lastComparison = TBaseHelper.compareTo(this.storehouseList, typedOther.storehouseList);
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
        case 2: // STOREHOUSE_LIST
          if (field.type == TType.LIST) {
            {
            TList _list0 = iprot.readListBegin();
            this.storehouseList = new ArrayList<Storehouse>(_list0.size);
            for (int _i1 = 0; _i1 < _list0.size; ++_i1)
            {
              Storehouse _elem2;
              _elem2 = new Storehouse();
              _elem2.read(iprot);
              this.storehouseList.add(_elem2);
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
    if (this.storehouseList != null) {
      if (isSetStorehouseList()) {
        oprot.writeFieldBegin(STOREHOUSE_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new TList(TType.STRUCT, this.storehouseList.size()));
          for (Storehouse _iter3 : this.storehouseList)
          {
            _iter3.write(oprot);
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
    StringBuilder sb = new StringBuilder("StorehouseResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (isSetStorehouseList()) {
      if (!first) sb.append(", ");
      sb.append("storehouseList:");
      if (this.storehouseList == null) {
        sb.append("null");
      } else {
        sb.append(this.storehouseList);
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
