/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.trade;

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

public class BuySellerDetail implements TBase<BuySellerDetail, BuySellerDetail._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("BuySellerDetail");

  private static final TField SELLER_ID_FIELD_DESC = new TField("sellerId", TType.I32, (short)1);
  private static final TField SELLER_NAME_FIELD_DESC = new TField("sellerName", TType.STRING, (short)2);
  private static final TField BUYER_COMMENT_FIELD_DESC = new TField("buyerComment", TType.STRING, (short)3);
  private static final TField PRODUCT_LIST_FIELD_DESC = new TField("productList", TType.LIST, (short)4);


  public int sellerId;
  public String sellerName;
  public String buyerComment;
  public List<com.jfshare.finagle.thrift.order.OrderInfo> productList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    SELLER_ID((short)1, "sellerId"),
    SELLER_NAME((short)2, "sellerName"),
    BUYER_COMMENT((short)3, "buyerComment"),
    PRODUCT_LIST((short)4, "productList");
  
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
        case 1: // SELLER_ID
  	return SELLER_ID;
        case 2: // SELLER_NAME
  	return SELLER_NAME;
        case 3: // BUYER_COMMENT
  	return BUYER_COMMENT;
        case 4: // PRODUCT_LIST
  	return PRODUCT_LIST;
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
  private static final int __SELLERID_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SELLER_ID, new FieldMetaData("sellerId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.SELLER_NAME, new FieldMetaData("sellerName", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.BUYER_COMMENT, new FieldMetaData("buyerComment", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.PRODUCT_LIST, new FieldMetaData("productList", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.order.OrderInfo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(BuySellerDetail.class, metaDataMap);
  }


  public BuySellerDetail() {
  }

  public BuySellerDetail(
    int sellerId,
    String sellerName,
    String buyerComment,
    List<com.jfshare.finagle.thrift.order.OrderInfo> productList)
  {
    this();
    this.sellerId = sellerId;
    setSellerIdIsSet(true);
    this.sellerName = sellerName;
    this.buyerComment = buyerComment;
    this.productList = productList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BuySellerDetail(BuySellerDetail other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.sellerId = other.sellerId;
    if (other.isSetSellerName()) {
      this.sellerName = other.sellerName;
    }
    if (other.isSetBuyerComment()) {
      this.buyerComment = other.buyerComment;
    }
    if (other.isSetProductList()) {
      List<com.jfshare.finagle.thrift.order.OrderInfo> __this__productList = new ArrayList<com.jfshare.finagle.thrift.order.OrderInfo>();
      for (com.jfshare.finagle.thrift.order.OrderInfo other_element : other.productList) {
        __this__productList.add(new com.jfshare.finagle.thrift.order.OrderInfo(other_element));
      }
      this.productList = __this__productList;
    }
  }

  public BuySellerDetail deepCopy() {
    return new BuySellerDetail(this);
  }

  @Override
  public void clear() {
    setSellerIdIsSet(false);
    this.sellerId = 0;
    this.sellerName = null;
    this.buyerComment = null;
    this.productList = null;
  }

  public int getSellerId() {
    return this.sellerId;
  }

  public BuySellerDetail setSellerId(int sellerId) {
    this.sellerId = sellerId;
    setSellerIdIsSet(true);

    return this;
  }

  public void unsetSellerId() {
  __isset_bit_vector.clear(__SELLERID_ISSET_ID);
  }

  /** Returns true if field sellerId is set (has been asigned a value) and false otherwise */
  public boolean isSetSellerId() {
    return __isset_bit_vector.get(__SELLERID_ISSET_ID);
  }

  public void setSellerIdIsSet(boolean value) {
    __isset_bit_vector.set(__SELLERID_ISSET_ID, value);
  }

  public String getSellerName() {
    return this.sellerName;
  }

  public BuySellerDetail setSellerName(String sellerName) {
    this.sellerName = sellerName;
    
    return this;
  }

  public void unsetSellerName() {
    this.sellerName = null;
  }

  /** Returns true if field sellerName is set (has been asigned a value) and false otherwise */
  public boolean isSetSellerName() {
    return this.sellerName != null;
  }

  public void setSellerNameIsSet(boolean value) {
    if (!value) {
      this.sellerName = null;
    }
  }

  public String getBuyerComment() {
    return this.buyerComment;
  }

  public BuySellerDetail setBuyerComment(String buyerComment) {
    this.buyerComment = buyerComment;
    
    return this;
  }

  public void unsetBuyerComment() {
    this.buyerComment = null;
  }

  /** Returns true if field buyerComment is set (has been asigned a value) and false otherwise */
  public boolean isSetBuyerComment() {
    return this.buyerComment != null;
  }

  public void setBuyerCommentIsSet(boolean value) {
    if (!value) {
      this.buyerComment = null;
    }
  }

  public int getProductListSize() {
    return (this.productList == null) ? 0 : this.productList.size();
  }

  public java.util.Iterator<com.jfshare.finagle.thrift.order.OrderInfo> getProductListIterator() {
    return (this.productList == null) ? null : this.productList.iterator();
  }

  public void addToProductList(com.jfshare.finagle.thrift.order.OrderInfo elem) {
    if (this.productList == null) {
      this.productList = new ArrayList<com.jfshare.finagle.thrift.order.OrderInfo>();
    }
    this.productList.add(elem);
  }

  public List<com.jfshare.finagle.thrift.order.OrderInfo> getProductList() {
    return this.productList;
  }

  public BuySellerDetail setProductList(List<com.jfshare.finagle.thrift.order.OrderInfo> productList) {
    this.productList = productList;
    
    return this;
  }

  public void unsetProductList() {
    this.productList = null;
  }

  /** Returns true if field productList is set (has been asigned a value) and false otherwise */
  public boolean isSetProductList() {
    return this.productList != null;
  }

  public void setProductListIsSet(boolean value) {
    if (!value) {
      this.productList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SELLER_ID:
      if (value == null) {
        unsetSellerId();
      } else {
        setSellerId((Integer)value);
      }
      break;
    case SELLER_NAME:
      if (value == null) {
        unsetSellerName();
      } else {
        setSellerName((String)value);
      }
      break;
    case BUYER_COMMENT:
      if (value == null) {
        unsetBuyerComment();
      } else {
        setBuyerComment((String)value);
      }
      break;
    case PRODUCT_LIST:
      if (value == null) {
        unsetProductList();
      } else {
        setProductList((List<com.jfshare.finagle.thrift.order.OrderInfo>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SELLER_ID:
      return new Integer(getSellerId());
    case SELLER_NAME:
      return getSellerName();
    case BUYER_COMMENT:
      return getBuyerComment();
    case PRODUCT_LIST:
      return getProductList();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SELLER_ID:
      return isSetSellerId();
    case SELLER_NAME:
      return isSetSellerName();
    case BUYER_COMMENT:
      return isSetBuyerComment();
    case PRODUCT_LIST:
      return isSetProductList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BuySellerDetail)
      return this.equals((BuySellerDetail)that);
    return false;
  }

  public boolean equals(BuySellerDetail that) {
    if (that == null)
      return false;
    boolean this_present_sellerId = true;
    boolean that_present_sellerId = true;
    if (this_present_sellerId || that_present_sellerId) {
      if (!(this_present_sellerId && that_present_sellerId))
        return false;
      if (this.sellerId != that.sellerId)
        return false;
    }
    boolean this_present_sellerName = true && this.isSetSellerName();
    boolean that_present_sellerName = true && that.isSetSellerName();
    if (this_present_sellerName || that_present_sellerName) {
      if (!(this_present_sellerName && that_present_sellerName))
        return false;
      if (!this.sellerName.equals(that.sellerName))
        return false;
    }
    boolean this_present_buyerComment = true && this.isSetBuyerComment();
    boolean that_present_buyerComment = true && that.isSetBuyerComment();
    if (this_present_buyerComment || that_present_buyerComment) {
      if (!(this_present_buyerComment && that_present_buyerComment))
        return false;
      if (!this.buyerComment.equals(that.buyerComment))
        return false;
    }
    boolean this_present_productList = true && this.isSetProductList();
    boolean that_present_productList = true && that.isSetProductList();
    if (this_present_productList || that_present_productList) {
      if (!(this_present_productList && that_present_productList))
        return false;
      if (!this.productList.equals(that.productList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_sellerId = true;
    builder.append(present_sellerId);
    if (present_sellerId)
      builder.append(sellerId);
    boolean present_sellerName = true && (isSetSellerName());
    builder.append(present_sellerName);
    if (present_sellerName)
      builder.append(sellerName);
    boolean present_buyerComment = true && (isSetBuyerComment());
    builder.append(present_buyerComment);
    if (present_buyerComment)
      builder.append(buyerComment);
    boolean present_productList = true && (isSetProductList());
    builder.append(present_productList);
    if (present_productList)
      builder.append(productList);
    return builder.toHashCode();
  }

  public int compareTo(BuySellerDetail other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    BuySellerDetail typedOther = (BuySellerDetail)other;

    lastComparison = Boolean.valueOf(isSetSellerId()).compareTo(typedOther.isSetSellerId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSellerId()) {
      lastComparison = TBaseHelper.compareTo(this.sellerId, typedOther.sellerId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSellerName()).compareTo(typedOther.isSetSellerName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSellerName()) {
      lastComparison = TBaseHelper.compareTo(this.sellerName, typedOther.sellerName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBuyerComment()).compareTo(typedOther.isSetBuyerComment());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBuyerComment()) {
      lastComparison = TBaseHelper.compareTo(this.buyerComment, typedOther.buyerComment);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductList()).compareTo(typedOther.isSetProductList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductList()) {
      lastComparison = TBaseHelper.compareTo(this.productList, typedOther.productList);
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
        case 1: // SELLER_ID
          if (field.type == TType.I32) {
            this.sellerId = iprot.readI32();
            setSellerIdIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // SELLER_NAME
          if (field.type == TType.STRING) {
            this.sellerName = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // BUYER_COMMENT
          if (field.type == TType.STRING) {
            this.buyerComment = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // PRODUCT_LIST
          if (field.type == TType.LIST) {
            {
            TList _list0 = iprot.readListBegin();
            this.productList = new ArrayList<com.jfshare.finagle.thrift.order.OrderInfo>(_list0.size);
            for (int _i1 = 0; _i1 < _list0.size; ++_i1)
            {
              com.jfshare.finagle.thrift.order.OrderInfo _elem2;
              _elem2 = new com.jfshare.finagle.thrift.order.OrderInfo();
              _elem2.read(iprot);
              this.productList.add(_elem2);
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
    oprot.writeFieldBegin(SELLER_ID_FIELD_DESC);
    oprot.writeI32(this.sellerId);
    oprot.writeFieldEnd();
    if (this.sellerName != null) {
      oprot.writeFieldBegin(SELLER_NAME_FIELD_DESC);
      oprot.writeString(this.sellerName);
      oprot.writeFieldEnd();
    }
    if (this.buyerComment != null) {
      oprot.writeFieldBegin(BUYER_COMMENT_FIELD_DESC);
      oprot.writeString(this.buyerComment);
      oprot.writeFieldEnd();
    }
    if (this.productList != null) {
      oprot.writeFieldBegin(PRODUCT_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.productList.size()));
        for (com.jfshare.finagle.thrift.order.OrderInfo _iter3 : this.productList)
        {
          _iter3.write(oprot);
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
    StringBuilder sb = new StringBuilder("BuySellerDetail(");
    boolean first = true;
    sb.append("sellerId:");
    sb.append(this.sellerId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("sellerName:");
    if (this.sellerName == null) {
      sb.append("null");
    } else {
      sb.append(this.sellerName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("buyerComment:");
    if (this.buyerComment == null) {
      sb.append("null");
    } else {
      sb.append(this.buyerComment);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("productList:");
    if (this.productList == null) {
      sb.append("null");
    } else {
      sb.append(this.productList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
