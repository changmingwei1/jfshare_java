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

public class ProductSurveyResult implements TBase<ProductSurveyResult, ProductSurveyResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ProductSurveyResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField PAGINATION_FIELD_DESC = new TField("pagination", TType.STRUCT, (short)2);
  private static final TField PRODUCT_SURVEY_LIST_FIELD_DESC = new TField("productSurveyList", TType.LIST, (short)3);


  public com.jfshare.finagle.thrift.result.Result result;
  public com.jfshare.finagle.thrift.pagination.Pagination pagination;
  public List<ProductSurvey> productSurveyList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    PAGINATION((short)2, "pagination"),
    PRODUCT_SURVEY_LIST((short)3, "productSurveyList");
  
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
        case 2: // PAGINATION
  	return PAGINATION;
        case 3: // PRODUCT_SURVEY_LIST
  	return PRODUCT_SURVEY_LIST;
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
    tmpMap.put(_Fields.PAGINATION, new FieldMetaData("pagination", TFieldRequirementType.OPTIONAL,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.pagination.Pagination.class)));
    tmpMap.put(_Fields.PRODUCT_SURVEY_LIST, new FieldMetaData("productSurveyList", TFieldRequirementType.OPTIONAL,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, ProductSurvey.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ProductSurveyResult.class, metaDataMap);
  }


  public ProductSurveyResult() {
  }

  public ProductSurveyResult(
    com.jfshare.finagle.thrift.result.Result result)
  {
    this();
    this.result = result;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductSurveyResult(ProductSurveyResult other) {
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetPagination()) {
      this.pagination = new com.jfshare.finagle.thrift.pagination.Pagination(other.pagination);
    }
    if (other.isSetProductSurveyList()) {
      List<ProductSurvey> __this__productSurveyList = new ArrayList<ProductSurvey>();
      for (ProductSurvey other_element : other.productSurveyList) {
        __this__productSurveyList.add(new ProductSurvey(other_element));
      }
      this.productSurveyList = __this__productSurveyList;
    }
  }

  public ProductSurveyResult deepCopy() {
    return new ProductSurveyResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.pagination = null;
    this.productSurveyList = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public ProductSurveyResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public com.jfshare.finagle.thrift.pagination.Pagination getPagination() {
    return this.pagination;
  }

  public ProductSurveyResult setPagination(com.jfshare.finagle.thrift.pagination.Pagination pagination) {
    this.pagination = pagination;
    
    return this;
  }

  public void unsetPagination() {
    this.pagination = null;
  }

  /** Returns true if field pagination is set (has been asigned a value) and false otherwise */
  public boolean isSetPagination() {
    return this.pagination != null;
  }

  public void setPaginationIsSet(boolean value) {
    if (!value) {
      this.pagination = null;
    }
  }

  public int getProductSurveyListSize() {
    return (this.productSurveyList == null) ? 0 : this.productSurveyList.size();
  }

  public java.util.Iterator<ProductSurvey> getProductSurveyListIterator() {
    return (this.productSurveyList == null) ? null : this.productSurveyList.iterator();
  }

  public void addToProductSurveyList(ProductSurvey elem) {
    if (this.productSurveyList == null) {
      this.productSurveyList = new ArrayList<ProductSurvey>();
    }
    this.productSurveyList.add(elem);
  }

  public List<ProductSurvey> getProductSurveyList() {
    return this.productSurveyList;
  }

  public ProductSurveyResult setProductSurveyList(List<ProductSurvey> productSurveyList) {
    this.productSurveyList = productSurveyList;
    
    return this;
  }

  public void unsetProductSurveyList() {
    this.productSurveyList = null;
  }

  /** Returns true if field productSurveyList is set (has been asigned a value) and false otherwise */
  public boolean isSetProductSurveyList() {
    return this.productSurveyList != null;
  }

  public void setProductSurveyListIsSet(boolean value) {
    if (!value) {
      this.productSurveyList = null;
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
    case PAGINATION:
      if (value == null) {
        unsetPagination();
      } else {
        setPagination((com.jfshare.finagle.thrift.pagination.Pagination)value);
      }
      break;
    case PRODUCT_SURVEY_LIST:
      if (value == null) {
        unsetProductSurveyList();
      } else {
        setProductSurveyList((List<ProductSurvey>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case PAGINATION:
      return getPagination();
    case PRODUCT_SURVEY_LIST:
      return getProductSurveyList();
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
    case PAGINATION:
      return isSetPagination();
    case PRODUCT_SURVEY_LIST:
      return isSetProductSurveyList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductSurveyResult)
      return this.equals((ProductSurveyResult)that);
    return false;
  }

  public boolean equals(ProductSurveyResult that) {
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
    boolean this_present_pagination = true && this.isSetPagination();
    boolean that_present_pagination = true && that.isSetPagination();
    if (this_present_pagination || that_present_pagination) {
      if (!(this_present_pagination && that_present_pagination))
        return false;
      if (!this.pagination.equals(that.pagination))
        return false;
    }
    boolean this_present_productSurveyList = true && this.isSetProductSurveyList();
    boolean that_present_productSurveyList = true && that.isSetProductSurveyList();
    if (this_present_productSurveyList || that_present_productSurveyList) {
      if (!(this_present_productSurveyList && that_present_productSurveyList))
        return false;
      if (!this.productSurveyList.equals(that.productSurveyList))
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
    boolean present_pagination = true && (isSetPagination());
    builder.append(present_pagination);
    if (present_pagination)
      builder.append(pagination);
    boolean present_productSurveyList = true && (isSetProductSurveyList());
    builder.append(present_productSurveyList);
    if (present_productSurveyList)
      builder.append(productSurveyList);
    return builder.toHashCode();
  }

  public int compareTo(ProductSurveyResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ProductSurveyResult typedOther = (ProductSurveyResult)other;

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
    lastComparison = Boolean.valueOf(isSetPagination()).compareTo(typedOther.isSetPagination());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPagination()) {
      lastComparison = TBaseHelper.compareTo(this.pagination, typedOther.pagination);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductSurveyList()).compareTo(typedOther.isSetProductSurveyList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductSurveyList()) {
      lastComparison = TBaseHelper.compareTo(this.productSurveyList, typedOther.productSurveyList);
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
        case 2: // PAGINATION
          if (field.type == TType.STRUCT) {
            this.pagination = new com.jfshare.finagle.thrift.pagination.Pagination();
            this.pagination.read(iprot);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // PRODUCT_SURVEY_LIST
          if (field.type == TType.LIST) {
            {
            TList _list24 = iprot.readListBegin();
            this.productSurveyList = new ArrayList<ProductSurvey>(_list24.size);
            for (int _i25 = 0; _i25 < _list24.size; ++_i25)
            {
              ProductSurvey _elem26;
              _elem26 = new ProductSurvey();
              _elem26.read(iprot);
              this.productSurveyList.add(_elem26);
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
    if (this.pagination != null) {
      if (isSetPagination()) {
        oprot.writeFieldBegin(PAGINATION_FIELD_DESC);
        this.pagination.write(oprot);
        oprot.writeFieldEnd();
      }
    }
    if (this.productSurveyList != null) {
      if (isSetProductSurveyList()) {
        oprot.writeFieldBegin(PRODUCT_SURVEY_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new TList(TType.STRUCT, this.productSurveyList.size()));
          for (ProductSurvey _iter27 : this.productSurveyList)
          {
            _iter27.write(oprot);
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
    StringBuilder sb = new StringBuilder("ProductSurveyResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (isSetPagination()) {
      if (!first) sb.append(", ");
      sb.append("pagination:");
      if (this.pagination == null) {
        sb.append("null");
      } else {
        sb.append(this.pagination);
      }
      first = false;
      }
    if (isSetProductSurveyList()) {
      if (!first) sb.append(", ");
      sb.append("productSurveyList:");
      if (this.productSurveyList == null) {
        sb.append("null");
      } else {
        sb.append(this.productSurveyList);
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
