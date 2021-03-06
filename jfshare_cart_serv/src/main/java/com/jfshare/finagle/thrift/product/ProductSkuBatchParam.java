/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.product;

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

public class ProductSkuBatchParam implements TBase<ProductSkuBatchParam, ProductSkuBatchParam._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ProductSkuBatchParam");

  private static final TField PRODUCT_SKU_PARAMS_FIELD_DESC = new TField("productSkuParams", TType.LIST, (short)1);


  public List<ProductSkuParam> productSkuParams;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    PRODUCT_SKU_PARAMS((short)1, "productSkuParams");
  
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
        case 1: // PRODUCT_SKU_PARAMS
  	return PRODUCT_SKU_PARAMS;
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
    tmpMap.put(_Fields.PRODUCT_SKU_PARAMS, new FieldMetaData("productSkuParams", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, ProductSkuParam.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ProductSkuBatchParam.class, metaDataMap);
  }


  public ProductSkuBatchParam() {
  }

  public ProductSkuBatchParam(
    List<ProductSkuParam> productSkuParams)
  {
    this();
    this.productSkuParams = productSkuParams;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductSkuBatchParam(ProductSkuBatchParam other) {
    if (other.isSetProductSkuParams()) {
      List<ProductSkuParam> __this__productSkuParams = new ArrayList<ProductSkuParam>();
      for (ProductSkuParam other_element : other.productSkuParams) {
        __this__productSkuParams.add(new ProductSkuParam(other_element));
      }
      this.productSkuParams = __this__productSkuParams;
    }
  }

  public ProductSkuBatchParam deepCopy() {
    return new ProductSkuBatchParam(this);
  }

  @Override
  public void clear() {
    this.productSkuParams = null;
  }

  public int getProductSkuParamsSize() {
    return (this.productSkuParams == null) ? 0 : this.productSkuParams.size();
  }

  public java.util.Iterator<ProductSkuParam> getProductSkuParamsIterator() {
    return (this.productSkuParams == null) ? null : this.productSkuParams.iterator();
  }

  public void addToProductSkuParams(ProductSkuParam elem) {
    if (this.productSkuParams == null) {
      this.productSkuParams = new ArrayList<ProductSkuParam>();
    }
    this.productSkuParams.add(elem);
  }

  public List<ProductSkuParam> getProductSkuParams() {
    return this.productSkuParams;
  }

  public ProductSkuBatchParam setProductSkuParams(List<ProductSkuParam> productSkuParams) {
    this.productSkuParams = productSkuParams;
    
    return this;
  }

  public void unsetProductSkuParams() {
    this.productSkuParams = null;
  }

  /** Returns true if field productSkuParams is set (has been asigned a value) and false otherwise */
  public boolean isSetProductSkuParams() {
    return this.productSkuParams != null;
  }

  public void setProductSkuParamsIsSet(boolean value) {
    if (!value) {
      this.productSkuParams = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT_SKU_PARAMS:
      if (value == null) {
        unsetProductSkuParams();
      } else {
        setProductSkuParams((List<ProductSkuParam>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_SKU_PARAMS:
      return getProductSkuParams();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PRODUCT_SKU_PARAMS:
      return isSetProductSkuParams();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductSkuBatchParam)
      return this.equals((ProductSkuBatchParam)that);
    return false;
  }

  public boolean equals(ProductSkuBatchParam that) {
    if (that == null)
      return false;
    boolean this_present_productSkuParams = true && this.isSetProductSkuParams();
    boolean that_present_productSkuParams = true && that.isSetProductSkuParams();
    if (this_present_productSkuParams || that_present_productSkuParams) {
      if (!(this_present_productSkuParams && that_present_productSkuParams))
        return false;
      if (!this.productSkuParams.equals(that.productSkuParams))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_productSkuParams = true && (isSetProductSkuParams());
    builder.append(present_productSkuParams);
    if (present_productSkuParams)
      builder.append(productSkuParams);
    return builder.toHashCode();
  }

  public int compareTo(ProductSkuBatchParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ProductSkuBatchParam typedOther = (ProductSkuBatchParam)other;

    lastComparison = Boolean.valueOf(isSetProductSkuParams()).compareTo(typedOther.isSetProductSkuParams());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductSkuParams()) {
      lastComparison = TBaseHelper.compareTo(this.productSkuParams, typedOther.productSkuParams);
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
        case 1: // PRODUCT_SKU_PARAMS
          if (field.type == TType.LIST) {
            {
            TList _list20 = iprot.readListBegin();
            this.productSkuParams = new ArrayList<ProductSkuParam>(_list20.size);
            for (int _i21 = 0; _i21 < _list20.size; ++_i21)
            {
              ProductSkuParam _elem22;
              _elem22 = new ProductSkuParam();
              _elem22.read(iprot);
              this.productSkuParams.add(_elem22);
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
    if (this.productSkuParams != null) {
      oprot.writeFieldBegin(PRODUCT_SKU_PARAMS_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.productSkuParams.size()));
        for (ProductSkuParam _iter23 : this.productSkuParams)
        {
          _iter23.write(oprot);
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
    StringBuilder sb = new StringBuilder("ProductSkuBatchParam(");
    boolean first = true;
    sb.append("productSkuParams:");
    if (this.productSkuParams == null) {
      sb.append("null");
    } else {
      sb.append(this.productSkuParams);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
