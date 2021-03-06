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

public class ExchangeDetail implements TBase<ExchangeDetail, ExchangeDetail._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ExchangeDetail");

  private static final TField PRODUCT_ID_FIELD_DESC = new TField("productId", TType.STRING, (short)1);
  private static final TField SKU_NUM_FIELD_DESC = new TField("skuNum", TType.STRING, (short)2);
  private static final TField PRICE_FIELD_DESC = new TField("price", TType.STRING, (short)3);
  private static final TField COUNT_FIELD_DESC = new TField("count", TType.I32, (short)4);
  private static final TField SCORE_FIELD_DESC = new TField("score", TType.STRING, (short)5);
  private static final TField AMOUNT_FIELD_DESC = new TField("amount", TType.STRING, (short)6);
  private static final TField RULE_FIELD_DESC = new TField("rule", TType.STRUCT, (short)7);


  public String productId;
  public String skuNum;
  public String price;
  public int count;
  public String score;
  public String amount;
  public ExchangeRule rule;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    PRODUCT_ID((short)1, "productId"),
    SKU_NUM((short)2, "skuNum"),
    PRICE((short)3, "price"),
    COUNT((short)4, "count"),
    SCORE((short)5, "score"),
    AMOUNT((short)6, "amount"),
    RULE((short)7, "rule");
  
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
        case 2: // SKU_NUM
  	return SKU_NUM;
        case 3: // PRICE
  	return PRICE;
        case 4: // COUNT
  	return COUNT;
        case 5: // SCORE
  	return SCORE;
        case 6: // AMOUNT
  	return AMOUNT;
        case 7: // RULE
  	return RULE;
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
  private static final int __COUNT_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT_ID, new FieldMetaData("productId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.SKU_NUM, new FieldMetaData("skuNum", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.PRICE, new FieldMetaData("price", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.COUNT, new FieldMetaData("count", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.SCORE, new FieldMetaData("score", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.AMOUNT, new FieldMetaData("amount", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.RULE, new FieldMetaData("rule", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, ExchangeRule.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ExchangeDetail.class, metaDataMap);
  }


  public ExchangeDetail() {
  }

  public ExchangeDetail(
    String productId,
    String skuNum,
    String price,
    int count,
    String score,
    String amount,
    ExchangeRule rule)
  {
    this();
    this.productId = productId;
    this.skuNum = skuNum;
    this.price = price;
    this.count = count;
    setCountIsSet(true);
    this.score = score;
    this.amount = amount;
    this.rule = rule;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ExchangeDetail(ExchangeDetail other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    if (other.isSetSkuNum()) {
      this.skuNum = other.skuNum;
    }
    if (other.isSetPrice()) {
      this.price = other.price;
    }
    this.count = other.count;
    if (other.isSetScore()) {
      this.score = other.score;
    }
    if (other.isSetAmount()) {
      this.amount = other.amount;
    }
    if (other.isSetRule()) {
      this.rule = new ExchangeRule(other.rule);
    }
  }

  public ExchangeDetail deepCopy() {
    return new ExchangeDetail(this);
  }

  @Override
  public void clear() {
    this.productId = null;
    this.skuNum = null;
    this.price = null;
    setCountIsSet(false);
    this.count = 0;
    this.score = null;
    this.amount = null;
    this.rule = null;
  }

  public String getProductId() {
    return this.productId;
  }

  public ExchangeDetail setProductId(String productId) {
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

  public String getSkuNum() {
    return this.skuNum;
  }

  public ExchangeDetail setSkuNum(String skuNum) {
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

  public String getPrice() {
    return this.price;
  }

  public ExchangeDetail setPrice(String price) {
    this.price = price;
    
    return this;
  }

  public void unsetPrice() {
    this.price = null;
  }

  /** Returns true if field price is set (has been asigned a value) and false otherwise */
  public boolean isSetPrice() {
    return this.price != null;
  }

  public void setPriceIsSet(boolean value) {
    if (!value) {
      this.price = null;
    }
  }

  public int getCount() {
    return this.count;
  }

  public ExchangeDetail setCount(int count) {
    this.count = count;
    setCountIsSet(true);

    return this;
  }

  public void unsetCount() {
  __isset_bit_vector.clear(__COUNT_ISSET_ID);
  }

  /** Returns true if field count is set (has been asigned a value) and false otherwise */
  public boolean isSetCount() {
    return __isset_bit_vector.get(__COUNT_ISSET_ID);
  }

  public void setCountIsSet(boolean value) {
    __isset_bit_vector.set(__COUNT_ISSET_ID, value);
  }

  public String getScore() {
    return this.score;
  }

  public ExchangeDetail setScore(String score) {
    this.score = score;
    
    return this;
  }

  public void unsetScore() {
    this.score = null;
  }

  /** Returns true if field score is set (has been asigned a value) and false otherwise */
  public boolean isSetScore() {
    return this.score != null;
  }

  public void setScoreIsSet(boolean value) {
    if (!value) {
      this.score = null;
    }
  }

  public String getAmount() {
    return this.amount;
  }

  public ExchangeDetail setAmount(String amount) {
    this.amount = amount;
    
    return this;
  }

  public void unsetAmount() {
    this.amount = null;
  }

  /** Returns true if field amount is set (has been asigned a value) and false otherwise */
  public boolean isSetAmount() {
    return this.amount != null;
  }

  public void setAmountIsSet(boolean value) {
    if (!value) {
      this.amount = null;
    }
  }

  public ExchangeRule getRule() {
    return this.rule;
  }

  public ExchangeDetail setRule(ExchangeRule rule) {
    this.rule = rule;
    
    return this;
  }

  public void unsetRule() {
    this.rule = null;
  }

  /** Returns true if field rule is set (has been asigned a value) and false otherwise */
  public boolean isSetRule() {
    return this.rule != null;
  }

  public void setRuleIsSet(boolean value) {
    if (!value) {
      this.rule = null;
    }
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
    case SKU_NUM:
      if (value == null) {
        unsetSkuNum();
      } else {
        setSkuNum((String)value);
      }
      break;
    case PRICE:
      if (value == null) {
        unsetPrice();
      } else {
        setPrice((String)value);
      }
      break;
    case COUNT:
      if (value == null) {
        unsetCount();
      } else {
        setCount((Integer)value);
      }
      break;
    case SCORE:
      if (value == null) {
        unsetScore();
      } else {
        setScore((String)value);
      }
      break;
    case AMOUNT:
      if (value == null) {
        unsetAmount();
      } else {
        setAmount((String)value);
      }
      break;
    case RULE:
      if (value == null) {
        unsetRule();
      } else {
        setRule((ExchangeRule)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_ID:
      return getProductId();
    case SKU_NUM:
      return getSkuNum();
    case PRICE:
      return getPrice();
    case COUNT:
      return new Integer(getCount());
    case SCORE:
      return getScore();
    case AMOUNT:
      return getAmount();
    case RULE:
      return getRule();
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
    case SKU_NUM:
      return isSetSkuNum();
    case PRICE:
      return isSetPrice();
    case COUNT:
      return isSetCount();
    case SCORE:
      return isSetScore();
    case AMOUNT:
      return isSetAmount();
    case RULE:
      return isSetRule();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ExchangeDetail)
      return this.equals((ExchangeDetail)that);
    return false;
  }

  public boolean equals(ExchangeDetail that) {
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
    boolean this_present_skuNum = true && this.isSetSkuNum();
    boolean that_present_skuNum = true && that.isSetSkuNum();
    if (this_present_skuNum || that_present_skuNum) {
      if (!(this_present_skuNum && that_present_skuNum))
        return false;
      if (!this.skuNum.equals(that.skuNum))
        return false;
    }
    boolean this_present_price = true && this.isSetPrice();
    boolean that_present_price = true && that.isSetPrice();
    if (this_present_price || that_present_price) {
      if (!(this_present_price && that_present_price))
        return false;
      if (!this.price.equals(that.price))
        return false;
    }
    boolean this_present_count = true;
    boolean that_present_count = true;
    if (this_present_count || that_present_count) {
      if (!(this_present_count && that_present_count))
        return false;
      if (this.count != that.count)
        return false;
    }
    boolean this_present_score = true && this.isSetScore();
    boolean that_present_score = true && that.isSetScore();
    if (this_present_score || that_present_score) {
      if (!(this_present_score && that_present_score))
        return false;
      if (!this.score.equals(that.score))
        return false;
    }
    boolean this_present_amount = true && this.isSetAmount();
    boolean that_present_amount = true && that.isSetAmount();
    if (this_present_amount || that_present_amount) {
      if (!(this_present_amount && that_present_amount))
        return false;
      if (!this.amount.equals(that.amount))
        return false;
    }
    boolean this_present_rule = true && this.isSetRule();
    boolean that_present_rule = true && that.isSetRule();
    if (this_present_rule || that_present_rule) {
      if (!(this_present_rule && that_present_rule))
        return false;
      if (!this.rule.equals(that.rule))
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
    boolean present_skuNum = true && (isSetSkuNum());
    builder.append(present_skuNum);
    if (present_skuNum)
      builder.append(skuNum);
    boolean present_price = true && (isSetPrice());
    builder.append(present_price);
    if (present_price)
      builder.append(price);
    boolean present_count = true;
    builder.append(present_count);
    if (present_count)
      builder.append(count);
    boolean present_score = true && (isSetScore());
    builder.append(present_score);
    if (present_score)
      builder.append(score);
    boolean present_amount = true && (isSetAmount());
    builder.append(present_amount);
    if (present_amount)
      builder.append(amount);
    boolean present_rule = true && (isSetRule());
    builder.append(present_rule);
    if (present_rule)
      builder.append(rule);
    return builder.toHashCode();
  }

  public int compareTo(ExchangeDetail other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ExchangeDetail typedOther = (ExchangeDetail)other;

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
    lastComparison = Boolean.valueOf(isSetPrice()).compareTo(typedOther.isSetPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrice()) {
      lastComparison = TBaseHelper.compareTo(this.price, typedOther.price);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCount()).compareTo(typedOther.isSetCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCount()) {
      lastComparison = TBaseHelper.compareTo(this.count, typedOther.count);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetScore()).compareTo(typedOther.isSetScore());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetScore()) {
      lastComparison = TBaseHelper.compareTo(this.score, typedOther.score);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAmount()).compareTo(typedOther.isSetAmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAmount()) {
      lastComparison = TBaseHelper.compareTo(this.amount, typedOther.amount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRule()).compareTo(typedOther.isSetRule());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRule()) {
      lastComparison = TBaseHelper.compareTo(this.rule, typedOther.rule);
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
        case 2: // SKU_NUM
          if (field.type == TType.STRING) {
            this.skuNum = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // PRICE
          if (field.type == TType.STRING) {
            this.price = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // COUNT
          if (field.type == TType.I32) {
            this.count = iprot.readI32();
            setCountIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // SCORE
          if (field.type == TType.STRING) {
            this.score = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 6: // AMOUNT
          if (field.type == TType.STRING) {
            this.amount = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 7: // RULE
          if (field.type == TType.STRUCT) {
            this.rule = new ExchangeRule();
            this.rule.read(iprot);
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
    if (this.skuNum != null) {
      oprot.writeFieldBegin(SKU_NUM_FIELD_DESC);
      oprot.writeString(this.skuNum);
      oprot.writeFieldEnd();
    }
    if (this.price != null) {
      oprot.writeFieldBegin(PRICE_FIELD_DESC);
      oprot.writeString(this.price);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(COUNT_FIELD_DESC);
    oprot.writeI32(this.count);
    oprot.writeFieldEnd();
    if (this.score != null) {
      oprot.writeFieldBegin(SCORE_FIELD_DESC);
      oprot.writeString(this.score);
      oprot.writeFieldEnd();
    }
    if (this.amount != null) {
      oprot.writeFieldBegin(AMOUNT_FIELD_DESC);
      oprot.writeString(this.amount);
      oprot.writeFieldEnd();
    }
    if (this.rule != null) {
      oprot.writeFieldBegin(RULE_FIELD_DESC);
      this.rule.write(oprot);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ExchangeDetail(");
    boolean first = true;
    sb.append("productId:");
    if (this.productId == null) {
      sb.append("null");
    } else {
      sb.append(this.productId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("skuNum:");
    if (this.skuNum == null) {
      sb.append("null");
    } else {
      sb.append(this.skuNum);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("price:");
    if (this.price == null) {
      sb.append("null");
    } else {
      sb.append(this.price);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("count:");
    sb.append(this.count);
    first = false;
    if (!first) sb.append(", ");
    sb.append("score:");
    if (this.score == null) {
      sb.append("null");
    } else {
      sb.append(this.score);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("amount:");
    if (this.amount == null) {
      sb.append("null");
    } else {
      sb.append(this.amount);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("rule:");
    if (this.rule == null) {
      sb.append("null");
    } else {
      sb.append(this.rule);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
