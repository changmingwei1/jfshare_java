/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.product;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.thrift.*;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.ListMetaData;
import org.apache.thrift.meta_data.StructMetaData;
import org.apache.thrift.protocol.*;

import java.util.*;

// No additional import required for struct/union.

public class ProductCardStatisticsResult implements TBase<ProductCardStatisticsResult, ProductCardStatisticsResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ProductCardStatisticsResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField CARDTATISTICS_LIST_FIELD_DESC = new TField("cardtatisticsList", TType.LIST, (short)2);
  private static final TField PAGINATION_FIELD_DESC = new TField("pagination", TType.STRUCT, (short)3);


  public com.jfshare.finagle.thrift.result.Result result;
  public List<ProductCardStatistics> cardtatisticsList;
  public com.jfshare.finagle.thrift.pagination.Pagination pagination;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    CARDTATISTICS_LIST((short)2, "cardtatisticsList"),
    PAGINATION((short)3, "pagination");
  
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
        case 2: // CARDTATISTICS_LIST
  	return CARDTATISTICS_LIST;
        case 3: // PAGINATION
  	return PAGINATION;
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
    tmpMap.put(_Fields.CARDTATISTICS_LIST, new FieldMetaData("cardtatisticsList", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, ProductCardStatistics.class))));
    tmpMap.put(_Fields.PAGINATION, new FieldMetaData("pagination", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.pagination.Pagination.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ProductCardStatisticsResult.class, metaDataMap);
  }


  public ProductCardStatisticsResult() {
  }

  public ProductCardStatisticsResult(
    com.jfshare.finagle.thrift.result.Result result,
    List<ProductCardStatistics> cardtatisticsList,
    com.jfshare.finagle.thrift.pagination.Pagination pagination)
  {
    this();
    this.result = result;
    this.cardtatisticsList = cardtatisticsList;
    this.pagination = pagination;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductCardStatisticsResult(ProductCardStatisticsResult other) {
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetCardtatisticsList()) {
      List<ProductCardStatistics> __this__cardtatisticsList = new ArrayList<ProductCardStatistics>();
      for (ProductCardStatistics other_element : other.cardtatisticsList) {
        __this__cardtatisticsList.add(new ProductCardStatistics(other_element));
      }
      this.cardtatisticsList = __this__cardtatisticsList;
    }
    if (other.isSetPagination()) {
      this.pagination = new com.jfshare.finagle.thrift.pagination.Pagination(other.pagination);
    }
  }

  public ProductCardStatisticsResult deepCopy() {
    return new ProductCardStatisticsResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.cardtatisticsList = null;
    this.pagination = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public ProductCardStatisticsResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public int getCardtatisticsListSize() {
    return (this.cardtatisticsList == null) ? 0 : this.cardtatisticsList.size();
  }

  public java.util.Iterator<ProductCardStatistics> getCardtatisticsListIterator() {
    return (this.cardtatisticsList == null) ? null : this.cardtatisticsList.iterator();
  }

  public void addToCardtatisticsList(ProductCardStatistics elem) {
    if (this.cardtatisticsList == null) {
      this.cardtatisticsList = new ArrayList<ProductCardStatistics>();
    }
    this.cardtatisticsList.add(elem);
  }

  public List<ProductCardStatistics> getCardtatisticsList() {
    return this.cardtatisticsList;
  }

  public ProductCardStatisticsResult setCardtatisticsList(List<ProductCardStatistics> cardtatisticsList) {
    this.cardtatisticsList = cardtatisticsList;
    
    return this;
  }

  public void unsetCardtatisticsList() {
    this.cardtatisticsList = null;
  }

  /** Returns true if field cardtatisticsList is set (has been asigned a value) and false otherwise */
  public boolean isSetCardtatisticsList() {
    return this.cardtatisticsList != null;
  }

  public void setCardtatisticsListIsSet(boolean value) {
    if (!value) {
      this.cardtatisticsList = null;
    }
  }

  public com.jfshare.finagle.thrift.pagination.Pagination getPagination() {
    return this.pagination;
  }

  public ProductCardStatisticsResult setPagination(com.jfshare.finagle.thrift.pagination.Pagination pagination) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RESULT:
      if (value == null) {
        unsetResult();
      } else {
        setResult((com.jfshare.finagle.thrift.result.Result)value);
      }
      break;
    case CARDTATISTICS_LIST:
      if (value == null) {
        unsetCardtatisticsList();
      } else {
        setCardtatisticsList((List<ProductCardStatistics>)value);
      }
      break;
    case PAGINATION:
      if (value == null) {
        unsetPagination();
      } else {
        setPagination((com.jfshare.finagle.thrift.pagination.Pagination)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case CARDTATISTICS_LIST:
      return getCardtatisticsList();
    case PAGINATION:
      return getPagination();
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
    case CARDTATISTICS_LIST:
      return isSetCardtatisticsList();
    case PAGINATION:
      return isSetPagination();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductCardStatisticsResult)
      return this.equals((ProductCardStatisticsResult)that);
    return false;
  }

  public boolean equals(ProductCardStatisticsResult that) {
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
    boolean this_present_cardtatisticsList = true && this.isSetCardtatisticsList();
    boolean that_present_cardtatisticsList = true && that.isSetCardtatisticsList();
    if (this_present_cardtatisticsList || that_present_cardtatisticsList) {
      if (!(this_present_cardtatisticsList && that_present_cardtatisticsList))
        return false;
      if (!this.cardtatisticsList.equals(that.cardtatisticsList))
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

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_result = true && (isSetResult());
    builder.append(present_result);
    if (present_result)
      builder.append(result);
    boolean present_cardtatisticsList = true && (isSetCardtatisticsList());
    builder.append(present_cardtatisticsList);
    if (present_cardtatisticsList)
      builder.append(cardtatisticsList);
    boolean present_pagination = true && (isSetPagination());
    builder.append(present_pagination);
    if (present_pagination)
      builder.append(pagination);
    return builder.toHashCode();
  }

  public int compareTo(ProductCardStatisticsResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ProductCardStatisticsResult typedOther = (ProductCardStatisticsResult)other;

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
    lastComparison = Boolean.valueOf(isSetCardtatisticsList()).compareTo(typedOther.isSetCardtatisticsList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCardtatisticsList()) {
      lastComparison = TBaseHelper.compareTo(this.cardtatisticsList, typedOther.cardtatisticsList);
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
        case 2: // CARDTATISTICS_LIST
          if (field.type == TType.LIST) {
            {
            TList _list36 = iprot.readListBegin();
            this.cardtatisticsList = new ArrayList<ProductCardStatistics>(_list36.size);
            for (int _i37 = 0; _i37 < _list36.size; ++_i37)
            {
              ProductCardStatistics _elem38;
              _elem38 = new ProductCardStatistics();
              _elem38.read(iprot);
              this.cardtatisticsList.add(_elem38);
            }
            iprot.readListEnd();
            }
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // PAGINATION
          if (field.type == TType.STRUCT) {
            this.pagination = new com.jfshare.finagle.thrift.pagination.Pagination();
            this.pagination.read(iprot);
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
    if (this.cardtatisticsList != null) {
      oprot.writeFieldBegin(CARDTATISTICS_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.cardtatisticsList.size()));
        for (ProductCardStatistics _iter39 : this.cardtatisticsList)
        {
          _iter39.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    if (this.pagination != null) {
      oprot.writeFieldBegin(PAGINATION_FIELD_DESC);
      this.pagination.write(oprot);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ProductCardStatisticsResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cardtatisticsList:");
    if (this.cardtatisticsList == null) {
      sb.append("null");
    } else {
      sb.append(this.cardtatisticsList);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("pagination:");
    if (this.pagination == null) {
      sb.append("null");
    } else {
      sb.append(this.pagination);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
