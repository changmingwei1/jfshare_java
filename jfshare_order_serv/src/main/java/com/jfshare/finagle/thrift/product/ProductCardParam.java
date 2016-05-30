/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.product;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.thrift.*;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.protocol.*;

import java.util.*;

// No additional import required for struct/union.

public class ProductCardParam implements TBase<ProductCardParam, ProductCardParam._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ProductCardParam");

  private static final TField PRODUCT_ID_FIELD_DESC = new TField("productId", TType.STRING, (short)1);
  private static final TField TRANSACTION_ID_FIELD_DESC = new TField("transactionId", TType.STRING, (short)2);
  private static final TField NUM_FIELD_DESC = new TField("num", TType.I32, (short)3);
  private static final TField SKU_NUM_FIELD_DESC = new TField("skuNum", TType.STRING, (short)4);
  private static final TField BUYER_ID_FIELD_DESC = new TField("buyerId", TType.I32, (short)5);


  public String productId;
  public String transactionId;
  public int num;
  public String skuNum;
  public int buyerId;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    PRODUCT_ID((short)1, "productId"),
    TRANSACTION_ID((short)2, "transactionId"),
    NUM((short)3, "num"),
    SKU_NUM((short)4, "skuNum"),
    BUYER_ID((short)5, "buyerId");
  
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
        case 1: // PRODUCT_ID
  	return PRODUCT_ID;
        case 2: // TRANSACTION_ID
  	return TRANSACTION_ID;
        case 3: // NUM
  	return NUM;
        case 4: // SKU_NUM
  	return SKU_NUM;
        case 5: // BUYER_ID
  	return BUYER_ID;
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
  private static final int __NUM_ISSET_ID = 0;
  private static final int __BUYERID_ISSET_ID = 1;
  private BitSet __isset_bit_vector = new BitSet(2);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT_ID, new FieldMetaData("productId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.TRANSACTION_ID, new FieldMetaData("transactionId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.NUM, new FieldMetaData("num", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.SKU_NUM, new FieldMetaData("skuNum", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.BUYER_ID, new FieldMetaData("buyerId", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ProductCardParam.class, metaDataMap);
  }


  public ProductCardParam() {
  }

  public ProductCardParam(
    String productId,
    String transactionId,
    int num,
    String skuNum)
  {
    this();
    this.productId = productId;
    this.transactionId = transactionId;
    this.num = num;
    setNumIsSet(true);
    this.skuNum = skuNum;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductCardParam(ProductCardParam other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    if (other.isSetTransactionId()) {
      this.transactionId = other.transactionId;
    }
    this.num = other.num;
    if (other.isSetSkuNum()) {
      this.skuNum = other.skuNum;
    }
    this.buyerId = other.buyerId;
  }

  public ProductCardParam deepCopy() {
    return new ProductCardParam(this);
  }

  @Override
  public void clear() {
    this.productId = null;
    this.transactionId = null;
    setNumIsSet(false);
    this.num = 0;
    this.skuNum = null;
    setBuyerIdIsSet(false);
    this.buyerId = 0;
  }

  public String getProductId() {
    return this.productId;
  }

  public ProductCardParam setProductId(String productId) {
    this.productId = productId;
    
    return this;
  }

  public void unsetProductId() {
    this.productId = null;
  }

  /** Returns true if field productId is set (has been asigned a value) and false otherwise */
  public boolean isSetProductId() {
    return this.productId != null;
  }

  public void setProductIdIsSet(boolean value) {
    if (!value) {
      this.productId = null;
    }
  }

  public String getTransactionId() {
    return this.transactionId;
  }

  public ProductCardParam setTransactionId(String transactionId) {
    this.transactionId = transactionId;
    
    return this;
  }

  public void unsetTransactionId() {
    this.transactionId = null;
  }

  /** Returns true if field transactionId is set (has been asigned a value) and false otherwise */
  public boolean isSetTransactionId() {
    return this.transactionId != null;
  }

  public void setTransactionIdIsSet(boolean value) {
    if (!value) {
      this.transactionId = null;
    }
  }

  public int getNum() {
    return this.num;
  }

  public ProductCardParam setNum(int num) {
    this.num = num;
    setNumIsSet(true);

    return this;
  }

  public void unsetNum() {
  __isset_bit_vector.clear(__NUM_ISSET_ID);
  }

  /** Returns true if field num is set (has been asigned a value) and false otherwise */
  public boolean isSetNum() {
    return __isset_bit_vector.get(__NUM_ISSET_ID);
  }

  public void setNumIsSet(boolean value) {
    __isset_bit_vector.set(__NUM_ISSET_ID, value);
  }

  public String getSkuNum() {
    return this.skuNum;
  }

  public ProductCardParam setSkuNum(String skuNum) {
    this.skuNum = skuNum;
    
    return this;
  }

  public void unsetSkuNum() {
    this.skuNum = null;
  }

  /** Returns true if field skuNum is set (has been asigned a value) and false otherwise */
  public boolean isSetSkuNum() {
    return this.skuNum != null;
  }

  public void setSkuNumIsSet(boolean value) {
    if (!value) {
      this.skuNum = null;
    }
  }

  public int getBuyerId() {
    return this.buyerId;
  }

  public ProductCardParam setBuyerId(int buyerId) {
    this.buyerId = buyerId;
    setBuyerIdIsSet(true);

    return this;
  }

  public void unsetBuyerId() {
  __isset_bit_vector.clear(__BUYERID_ISSET_ID);
  }

  /** Returns true if field buyerId is set (has been asigned a value) and false otherwise */
  public boolean isSetBuyerId() {
    return __isset_bit_vector.get(__BUYERID_ISSET_ID);
  }

  public void setBuyerIdIsSet(boolean value) {
    __isset_bit_vector.set(__BUYERID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT_ID:
      if (value == null) {
        unsetProductId();
      } else {
        setProductId((String)value);
      }
      break;
    case TRANSACTION_ID:
      if (value == null) {
        unsetTransactionId();
      } else {
        setTransactionId((String)value);
      }
      break;
    case NUM:
      if (value == null) {
        unsetNum();
      } else {
        setNum((Integer)value);
      }
      break;
    case SKU_NUM:
      if (value == null) {
        unsetSkuNum();
      } else {
        setSkuNum((String)value);
      }
      break;
    case BUYER_ID:
      if (value == null) {
        unsetBuyerId();
      } else {
        setBuyerId((Integer)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_ID:
      return getProductId();
    case TRANSACTION_ID:
      return getTransactionId();
    case NUM:
      return new Integer(getNum());
    case SKU_NUM:
      return getSkuNum();
    case BUYER_ID:
      return new Integer(getBuyerId());
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PRODUCT_ID:
      return isSetProductId();
    case TRANSACTION_ID:
      return isSetTransactionId();
    case NUM:
      return isSetNum();
    case SKU_NUM:
      return isSetSkuNum();
    case BUYER_ID:
      return isSetBuyerId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductCardParam)
      return this.equals((ProductCardParam)that);
    return false;
  }

  public boolean equals(ProductCardParam that) {
    if (that == null)
      return false;
    boolean this_present_productId = true && this.isSetProductId();
    boolean that_present_productId = true && that.isSetProductId();
    if (this_present_productId || that_present_productId) {
      if (!(this_present_productId && that_present_productId))
        return false;
      if (!this.productId.equals(that.productId))
        return false;
    }
    boolean this_present_transactionId = true && this.isSetTransactionId();
    boolean that_present_transactionId = true && that.isSetTransactionId();
    if (this_present_transactionId || that_present_transactionId) {
      if (!(this_present_transactionId && that_present_transactionId))
        return false;
      if (!this.transactionId.equals(that.transactionId))
        return false;
    }
    boolean this_present_num = true;
    boolean that_present_num = true;
    if (this_present_num || that_present_num) {
      if (!(this_present_num && that_present_num))
        return false;
      if (this.num != that.num)
        return false;
    }
    boolean this_present_skuNum = true && this.isSetSkuNum();
    boolean that_present_skuNum = true && that.isSetSkuNum();
    if (this_present_skuNum || that_present_skuNum) {
      if (!(this_present_skuNum && that_present_skuNum))
        return false;
      if (!this.skuNum.equals(that.skuNum))
        return false;
    }
    boolean this_present_buyerId = true && this.isSetBuyerId();
    boolean that_present_buyerId = true && that.isSetBuyerId();
    if (this_present_buyerId || that_present_buyerId) {
      if (!(this_present_buyerId && that_present_buyerId))
        return false;
      if (this.buyerId != that.buyerId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_productId = true && (isSetProductId());
    builder.append(present_productId);
    if (present_productId)
      builder.append(productId);
    boolean present_transactionId = true && (isSetTransactionId());
    builder.append(present_transactionId);
    if (present_transactionId)
      builder.append(transactionId);
    boolean present_num = true;
    builder.append(present_num);
    if (present_num)
      builder.append(num);
    boolean present_skuNum = true && (isSetSkuNum());
    builder.append(present_skuNum);
    if (present_skuNum)
      builder.append(skuNum);
    boolean present_buyerId = true && (isSetBuyerId());
    builder.append(present_buyerId);
    if (present_buyerId)
      builder.append(buyerId);
    return builder.toHashCode();
  }

  public int compareTo(ProductCardParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ProductCardParam typedOther = (ProductCardParam)other;

    lastComparison = Boolean.valueOf(isSetProductId()).compareTo(typedOther.isSetProductId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductId()) {
      lastComparison = TBaseHelper.compareTo(this.productId, typedOther.productId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTransactionId()).compareTo(typedOther.isSetTransactionId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTransactionId()) {
      lastComparison = TBaseHelper.compareTo(this.transactionId, typedOther.transactionId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNum()).compareTo(typedOther.isSetNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNum()) {
      lastComparison = TBaseHelper.compareTo(this.num, typedOther.num);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSkuNum()).compareTo(typedOther.isSetSkuNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSkuNum()) {
      lastComparison = TBaseHelper.compareTo(this.skuNum, typedOther.skuNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBuyerId()).compareTo(typedOther.isSetBuyerId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBuyerId()) {
      lastComparison = TBaseHelper.compareTo(this.buyerId, typedOther.buyerId);
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
        case 1: // PRODUCT_ID
          if (field.type == TType.STRING) {
            this.productId = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // TRANSACTION_ID
          if (field.type == TType.STRING) {
            this.transactionId = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // NUM
          if (field.type == TType.I32) {
            this.num = iprot.readI32();
            setNumIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // SKU_NUM
          if (field.type == TType.STRING) {
            this.skuNum = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // BUYER_ID
          if (field.type == TType.I32) {
            this.buyerId = iprot.readI32();
            setBuyerIdIsSet(true);
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
    if (this.productId != null) {
      oprot.writeFieldBegin(PRODUCT_ID_FIELD_DESC);
      oprot.writeString(this.productId);
      oprot.writeFieldEnd();
    }
    if (this.transactionId != null) {
      oprot.writeFieldBegin(TRANSACTION_ID_FIELD_DESC);
      oprot.writeString(this.transactionId);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(NUM_FIELD_DESC);
    oprot.writeI32(this.num);
    oprot.writeFieldEnd();
    if (this.skuNum != null) {
      oprot.writeFieldBegin(SKU_NUM_FIELD_DESC);
      oprot.writeString(this.skuNum);
      oprot.writeFieldEnd();
    }
    if (isSetBuyerId()) {
      oprot.writeFieldBegin(BUYER_ID_FIELD_DESC);
      oprot.writeI32(this.buyerId);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ProductCardParam(");
    boolean first = true;
    sb.append("productId:");
    if (this.productId == null) {
      sb.append("null");
    } else {
      sb.append(this.productId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("transactionId:");
    if (this.transactionId == null) {
      sb.append("null");
    } else {
      sb.append(this.transactionId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("num:");
    sb.append(this.num);
    first = false;
    if (!first) sb.append(", ");
    sb.append("skuNum:");
    if (this.skuNum == null) {
      sb.append("null");
    } else {
      sb.append(this.skuNum);
    }
    first = false;
    if (isSetBuyerId()) {
      if (!first) sb.append(", ");
      sb.append("buyerId:");
      sb.append(this.buyerId);
      first = false;
      }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
