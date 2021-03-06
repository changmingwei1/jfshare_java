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

public class DeliverStorehouseParam implements TBase<DeliverStorehouseParam, DeliverStorehouseParam._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("DeliverStorehouseParam");

  private static final TField PRODUCT_REF_PROVINCE_LIST_FIELD_DESC = new TField("productRefProvinceList", TType.LIST, (short)1);


  public List<ProductRefProvince> productRefProvinceList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    PRODUCT_REF_PROVINCE_LIST((short)1, "productRefProvinceList");
  
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
        case 1: // PRODUCT_REF_PROVINCE_LIST
  	return PRODUCT_REF_PROVINCE_LIST;
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
    tmpMap.put(_Fields.PRODUCT_REF_PROVINCE_LIST, new FieldMetaData("productRefProvinceList", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, ProductRefProvince.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(DeliverStorehouseParam.class, metaDataMap);
  }


  public DeliverStorehouseParam() {
  }

  public DeliverStorehouseParam(
    List<ProductRefProvince> productRefProvinceList)
  {
    this();
    this.productRefProvinceList = productRefProvinceList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DeliverStorehouseParam(DeliverStorehouseParam other) {
    if (other.isSetProductRefProvinceList()) {
      List<ProductRefProvince> __this__productRefProvinceList = new ArrayList<ProductRefProvince>();
      for (ProductRefProvince other_element : other.productRefProvinceList) {
        __this__productRefProvinceList.add(new ProductRefProvince(other_element));
      }
      this.productRefProvinceList = __this__productRefProvinceList;
    }
  }

  public DeliverStorehouseParam deepCopy() {
    return new DeliverStorehouseParam(this);
  }

  @Override
  public void clear() {
    this.productRefProvinceList = null;
  }

  public int getProductRefProvinceListSize() {
    return (this.productRefProvinceList == null) ? 0 : this.productRefProvinceList.size();
  }

  public java.util.Iterator<ProductRefProvince> getProductRefProvinceListIterator() {
    return (this.productRefProvinceList == null) ? null : this.productRefProvinceList.iterator();
  }

  public void addToProductRefProvinceList(ProductRefProvince elem) {
    if (this.productRefProvinceList == null) {
      this.productRefProvinceList = new ArrayList<ProductRefProvince>();
    }
    this.productRefProvinceList.add(elem);
  }

  public List<ProductRefProvince> getProductRefProvinceList() {
    return this.productRefProvinceList;
  }

  public DeliverStorehouseParam setProductRefProvinceList(List<ProductRefProvince> productRefProvinceList) {
    this.productRefProvinceList = productRefProvinceList;
    
    return this;
  }

  public void unsetProductRefProvinceList() {
    this.productRefProvinceList = null;
  }

  /** Returns true if field productRefProvinceList is set (has been asigned a value) and false otherwise */
  public boolean isSetProductRefProvinceList() {
    return this.productRefProvinceList != null;
  }

  public void setProductRefProvinceListIsSet(boolean value) {
    if (!value) {
      this.productRefProvinceList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT_REF_PROVINCE_LIST:
      if (value == null) {
        unsetProductRefProvinceList();
      } else {
        setProductRefProvinceList((List<ProductRefProvince>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_REF_PROVINCE_LIST:
      return getProductRefProvinceList();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PRODUCT_REF_PROVINCE_LIST:
      return isSetProductRefProvinceList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DeliverStorehouseParam)
      return this.equals((DeliverStorehouseParam)that);
    return false;
  }

  public boolean equals(DeliverStorehouseParam that) {
    if (that == null)
      return false;
    boolean this_present_productRefProvinceList = true && this.isSetProductRefProvinceList();
    boolean that_present_productRefProvinceList = true && that.isSetProductRefProvinceList();
    if (this_present_productRefProvinceList || that_present_productRefProvinceList) {
      if (!(this_present_productRefProvinceList && that_present_productRefProvinceList))
        return false;
      if (!this.productRefProvinceList.equals(that.productRefProvinceList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_productRefProvinceList = true && (isSetProductRefProvinceList());
    builder.append(present_productRefProvinceList);
    if (present_productRefProvinceList)
      builder.append(productRefProvinceList);
    return builder.toHashCode();
  }

  public int compareTo(DeliverStorehouseParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    DeliverStorehouseParam typedOther = (DeliverStorehouseParam)other;

    lastComparison = Boolean.valueOf(isSetProductRefProvinceList()).compareTo(typedOther.isSetProductRefProvinceList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductRefProvinceList()) {
      lastComparison = TBaseHelper.compareTo(this.productRefProvinceList, typedOther.productRefProvinceList);
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
        case 1: // PRODUCT_REF_PROVINCE_LIST
          if (field.type == TType.LIST) {
            {
            TList _list4 = iprot.readListBegin();
            this.productRefProvinceList = new ArrayList<ProductRefProvince>(_list4.size);
            for (int _i5 = 0; _i5 < _list4.size; ++_i5)
            {
              ProductRefProvince _elem6;
              _elem6 = new ProductRefProvince();
              _elem6.read(iprot);
              this.productRefProvinceList.add(_elem6);
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
    if (this.productRefProvinceList != null) {
      oprot.writeFieldBegin(PRODUCT_REF_PROVINCE_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.productRefProvinceList.size()));
        for (ProductRefProvince _iter7 : this.productRefProvinceList)
        {
          _iter7.write(oprot);
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
    StringBuilder sb = new StringBuilder("DeliverStorehouseParam(");
    boolean first = true;
    sb.append("productRefProvinceList:");
    if (this.productRefProvinceList == null) {
      sb.append("null");
    } else {
      sb.append(this.productRefProvinceList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
