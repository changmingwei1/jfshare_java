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

public class ProductCardResult implements TBase<ProductCardResult, ProductCardResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ProductCardResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField CARD_LIST_FIELD_DESC = new TField("cardList", TType.LIST, (short)2);


  public com.jfshare.finagle.thrift.result.Result result;
  public List<ProductCard> cardList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    CARD_LIST((short)2, "cardList");
  
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
        case 2: // CARD_LIST
  	return CARD_LIST;
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
    tmpMap.put(_Fields.CARD_LIST, new FieldMetaData("cardList", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, ProductCard.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ProductCardResult.class, metaDataMap);
  }


  public ProductCardResult() {
  }

  public ProductCardResult(
    com.jfshare.finagle.thrift.result.Result result,
    List<ProductCard> cardList)
  {
    this();
    this.result = result;
    this.cardList = cardList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductCardResult(ProductCardResult other) {
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetCardList()) {
      List<ProductCard> __this__cardList = new ArrayList<ProductCard>();
      for (ProductCard other_element : other.cardList) {
        __this__cardList.add(new ProductCard(other_element));
      }
      this.cardList = __this__cardList;
    }
  }

  public ProductCardResult deepCopy() {
    return new ProductCardResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.cardList = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public ProductCardResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public int getCardListSize() {
    return (this.cardList == null) ? 0 : this.cardList.size();
  }

  public java.util.Iterator<ProductCard> getCardListIterator() {
    return (this.cardList == null) ? null : this.cardList.iterator();
  }

  public void addToCardList(ProductCard elem) {
    if (this.cardList == null) {
      this.cardList = new ArrayList<ProductCard>();
    }
    this.cardList.add(elem);
  }

  public List<ProductCard> getCardList() {
    return this.cardList;
  }

  public ProductCardResult setCardList(List<ProductCard> cardList) {
    this.cardList = cardList;
    
    return this;
  }

  public void unsetCardList() {
    this.cardList = null;
  }

  /** Returns true if field cardList is set (has been asigned a value) and false otherwise */
  public boolean isSetCardList() {
    return this.cardList != null;
  }

  public void setCardListIsSet(boolean value) {
    if (!value) {
      this.cardList = null;
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
    case CARD_LIST:
      if (value == null) {
        unsetCardList();
      } else {
        setCardList((List<ProductCard>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case CARD_LIST:
      return getCardList();
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
    case CARD_LIST:
      return isSetCardList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductCardResult)
      return this.equals((ProductCardResult)that);
    return false;
  }

  public boolean equals(ProductCardResult that) {
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
    boolean this_present_cardList = true && this.isSetCardList();
    boolean that_present_cardList = true && that.isSetCardList();
    if (this_present_cardList || that_present_cardList) {
      if (!(this_present_cardList && that_present_cardList))
        return false;
      if (!this.cardList.equals(that.cardList))
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
    boolean present_cardList = true && (isSetCardList());
    builder.append(present_cardList);
    if (present_cardList)
      builder.append(cardList);
    return builder.toHashCode();
  }

  public int compareTo(ProductCardResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ProductCardResult typedOther = (ProductCardResult)other;

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
    lastComparison = Boolean.valueOf(isSetCardList()).compareTo(typedOther.isSetCardList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCardList()) {
      lastComparison = TBaseHelper.compareTo(this.cardList, typedOther.cardList);
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
        case 2: // CARD_LIST
          if (field.type == TType.LIST) {
            {
            TList _list28 = iprot.readListBegin();
            this.cardList = new ArrayList<ProductCard>(_list28.size);
            for (int _i29 = 0; _i29 < _list28.size; ++_i29)
            {
              ProductCard _elem30;
              _elem30 = new ProductCard();
              _elem30.read(iprot);
              this.cardList.add(_elem30);
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
    if (this.cardList != null) {
      oprot.writeFieldBegin(CARD_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.cardList.size()));
        for (ProductCard _iter31 : this.cardList)
        {
          _iter31.write(oprot);
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
    StringBuilder sb = new StringBuilder("ProductCardResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cardList:");
    if (this.cardList == null) {
      sb.append("null");
    } else {
      sb.append(this.cardList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
