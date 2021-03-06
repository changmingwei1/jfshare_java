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

public class DeliverStorehouseResult implements TBase<DeliverStorehouseResult, DeliverStorehouseResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("DeliverStorehouseResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField PRODUCT_STOREHOUSE_LIST_FIELD_DESC = new TField("productStorehouseList", TType.LIST, (short)2);


  public com.jfshare.finagle.thrift.result.Result result;
  public List<ProductStorehouse> productStorehouseList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    PRODUCT_STOREHOUSE_LIST((short)2, "productStorehouseList");
  
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
        case 2: // PRODUCT_STOREHOUSE_LIST
  	return PRODUCT_STOREHOUSE_LIST;
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
    tmpMap.put(_Fields.PRODUCT_STOREHOUSE_LIST, new FieldMetaData("productStorehouseList", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, ProductStorehouse.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(DeliverStorehouseResult.class, metaDataMap);
  }


  public DeliverStorehouseResult() {
  }

  public DeliverStorehouseResult(
    com.jfshare.finagle.thrift.result.Result result,
    List<ProductStorehouse> productStorehouseList)
  {
    this();
    this.result = result;
    this.productStorehouseList = productStorehouseList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DeliverStorehouseResult(DeliverStorehouseResult other) {
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetProductStorehouseList()) {
      List<ProductStorehouse> __this__productStorehouseList = new ArrayList<ProductStorehouse>();
      for (ProductStorehouse other_element : other.productStorehouseList) {
        __this__productStorehouseList.add(new ProductStorehouse(other_element));
      }
      this.productStorehouseList = __this__productStorehouseList;
    }
  }

  public DeliverStorehouseResult deepCopy() {
    return new DeliverStorehouseResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.productStorehouseList = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public DeliverStorehouseResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public int getProductStorehouseListSize() {
    return (this.productStorehouseList == null) ? 0 : this.productStorehouseList.size();
  }

  public java.util.Iterator<ProductStorehouse> getProductStorehouseListIterator() {
    return (this.productStorehouseList == null) ? null : this.productStorehouseList.iterator();
  }

  public void addToProductStorehouseList(ProductStorehouse elem) {
    if (this.productStorehouseList == null) {
      this.productStorehouseList = new ArrayList<ProductStorehouse>();
    }
    this.productStorehouseList.add(elem);
  }

  public List<ProductStorehouse> getProductStorehouseList() {
    return this.productStorehouseList;
  }

  public DeliverStorehouseResult setProductStorehouseList(List<ProductStorehouse> productStorehouseList) {
    this.productStorehouseList = productStorehouseList;
    
    return this;
  }

  public void unsetProductStorehouseList() {
    this.productStorehouseList = null;
  }

  /** Returns true if field productStorehouseList is set (has been asigned a value) and false otherwise */
  public boolean isSetProductStorehouseList() {
    return this.productStorehouseList != null;
  }

  public void setProductStorehouseListIsSet(boolean value) {
    if (!value) {
      this.productStorehouseList = null;
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
    case PRODUCT_STOREHOUSE_LIST:
      if (value == null) {
        unsetProductStorehouseList();
      } else {
        setProductStorehouseList((List<ProductStorehouse>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case PRODUCT_STOREHOUSE_LIST:
      return getProductStorehouseList();
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
    case PRODUCT_STOREHOUSE_LIST:
      return isSetProductStorehouseList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DeliverStorehouseResult)
      return this.equals((DeliverStorehouseResult)that);
    return false;
  }

  public boolean equals(DeliverStorehouseResult that) {
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
    boolean this_present_productStorehouseList = true && this.isSetProductStorehouseList();
    boolean that_present_productStorehouseList = true && that.isSetProductStorehouseList();
    if (this_present_productStorehouseList || that_present_productStorehouseList) {
      if (!(this_present_productStorehouseList && that_present_productStorehouseList))
        return false;
      if (!this.productStorehouseList.equals(that.productStorehouseList))
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
    boolean present_productStorehouseList = true && (isSetProductStorehouseList());
    builder.append(present_productStorehouseList);
    if (present_productStorehouseList)
      builder.append(productStorehouseList);
    return builder.toHashCode();
  }

  public int compareTo(DeliverStorehouseResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    DeliverStorehouseResult typedOther = (DeliverStorehouseResult)other;

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
    lastComparison = Boolean.valueOf(isSetProductStorehouseList()).compareTo(typedOther.isSetProductStorehouseList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductStorehouseList()) {
      lastComparison = TBaseHelper.compareTo(this.productStorehouseList, typedOther.productStorehouseList);
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
        case 2: // PRODUCT_STOREHOUSE_LIST
          if (field.type == TType.LIST) {
            {
            TList _list8 = iprot.readListBegin();
            this.productStorehouseList = new ArrayList<ProductStorehouse>(_list8.size);
            for (int _i9 = 0; _i9 < _list8.size; ++_i9)
            {
              ProductStorehouse _elem10;
              _elem10 = new ProductStorehouse();
              _elem10.read(iprot);
              this.productStorehouseList.add(_elem10);
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
    if (this.productStorehouseList != null) {
      oprot.writeFieldBegin(PRODUCT_STOREHOUSE_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.productStorehouseList.size()));
        for (ProductStorehouse _iter11 : this.productStorehouseList)
        {
          _iter11.write(oprot);
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
    StringBuilder sb = new StringBuilder("DeliverStorehouseResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("productStorehouseList:");
    if (this.productStorehouseList == null) {
      sb.append("null");
    } else {
      sb.append(this.productStorehouseList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
