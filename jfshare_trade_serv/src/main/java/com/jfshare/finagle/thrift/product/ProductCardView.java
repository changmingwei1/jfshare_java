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

public class ProductCardView implements TBase<ProductCardView, ProductCardView._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ProductCardView");

  private static final TField PRODUCT_ID_FIELD_DESC = new TField("productId", TType.STRING, (short)1);
  private static final TField CARD_NUMBER_FIELD_DESC = new TField("cardNumber", TType.STRING, (short)2);
  private static final TField PASSWORD_FIELD_DESC = new TField("password", TType.STRING, (short)3);
  private static final TField STATE_FIELD_DESC = new TField("state", TType.I32, (short)4);
  private static final TField SKU_NUM_FIELD_DESC = new TField("skuNum", TType.STRING, (short)5);


  public String productId;
  public String cardNumber;
  public String password;
  public int state;
  public String skuNum;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    PRODUCT_ID((short)1, "productId"),
    CARD_NUMBER((short)2, "cardNumber"),
    PASSWORD((short)3, "password"),
    STATE((short)4, "state"),
    SKU_NUM((short)5, "skuNum");
  
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
        case 2: // CARD_NUMBER
  	return CARD_NUMBER;
        case 3: // PASSWORD
  	return PASSWORD;
        case 4: // STATE
  	return STATE;
        case 5: // SKU_NUM
  	return SKU_NUM;
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
  private static final int __STATE_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT_ID, new FieldMetaData("productId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.CARD_NUMBER, new FieldMetaData("cardNumber", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.PASSWORD, new FieldMetaData("password", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.STATE, new FieldMetaData("state", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.SKU_NUM, new FieldMetaData("skuNum", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ProductCardView.class, metaDataMap);
  }


  public ProductCardView() {
  }

  public ProductCardView(
    String productId,
    String cardNumber,
    String password,
    int state,
    String skuNum)
  {
    this();
    this.productId = productId;
    this.cardNumber = cardNumber;
    this.password = password;
    this.state = state;
    setStateIsSet(true);
    this.skuNum = skuNum;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductCardView(ProductCardView other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    if (other.isSetCardNumber()) {
      this.cardNumber = other.cardNumber;
    }
    if (other.isSetPassword()) {
      this.password = other.password;
    }
    this.state = other.state;
    if (other.isSetSkuNum()) {
      this.skuNum = other.skuNum;
    }
  }

  public ProductCardView deepCopy() {
    return new ProductCardView(this);
  }

  @Override
  public void clear() {
    this.productId = null;
    this.cardNumber = null;
    this.password = null;
    setStateIsSet(false);
    this.state = 0;
    this.skuNum = null;
  }

  public String getProductId() {
    return this.productId;
  }

  public ProductCardView setProductId(String productId) {
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

  public String getCardNumber() {
    return this.cardNumber;
  }

  public ProductCardView setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
    
    return this;
  }

  public void unsetCardNumber() {
    this.cardNumber = null;
  }

  /** Returns true if field cardNumber is set (has been asigned a value) and false otherwise */
  public boolean isSetCardNumber() {
    return this.cardNumber != null;
  }

  public void setCardNumberIsSet(boolean value) {
    if (!value) {
      this.cardNumber = null;
    }
  }

  public String getPassword() {
    return this.password;
  }

  public ProductCardView setPassword(String password) {
    this.password = password;
    
    return this;
  }

  public void unsetPassword() {
    this.password = null;
  }

  /** Returns true if field password is set (has been asigned a value) and false otherwise */
  public boolean isSetPassword() {
    return this.password != null;
  }

  public void setPasswordIsSet(boolean value) {
    if (!value) {
      this.password = null;
    }
  }

  public int getState() {
    return this.state;
  }

  public ProductCardView setState(int state) {
    this.state = state;
    setStateIsSet(true);

    return this;
  }

  public void unsetState() {
  __isset_bit_vector.clear(__STATE_ISSET_ID);
  }

  /** Returns true if field state is set (has been asigned a value) and false otherwise */
  public boolean isSetState() {
    return __isset_bit_vector.get(__STATE_ISSET_ID);
  }

  public void setStateIsSet(boolean value) {
    __isset_bit_vector.set(__STATE_ISSET_ID, value);
  }

  public String getSkuNum() {
    return this.skuNum;
  }

  public ProductCardView setSkuNum(String skuNum) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT_ID:
      if (value == null) {
        unsetProductId();
      } else {
        setProductId((String)value);
      }
      break;
    case CARD_NUMBER:
      if (value == null) {
        unsetCardNumber();
      } else {
        setCardNumber((String)value);
      }
      break;
    case PASSWORD:
      if (value == null) {
        unsetPassword();
      } else {
        setPassword((String)value);
      }
      break;
    case STATE:
      if (value == null) {
        unsetState();
      } else {
        setState((Integer)value);
      }
      break;
    case SKU_NUM:
      if (value == null) {
        unsetSkuNum();
      } else {
        setSkuNum((String)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_ID:
      return getProductId();
    case CARD_NUMBER:
      return getCardNumber();
    case PASSWORD:
      return getPassword();
    case STATE:
      return new Integer(getState());
    case SKU_NUM:
      return getSkuNum();
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
    case CARD_NUMBER:
      return isSetCardNumber();
    case PASSWORD:
      return isSetPassword();
    case STATE:
      return isSetState();
    case SKU_NUM:
      return isSetSkuNum();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductCardView)
      return this.equals((ProductCardView)that);
    return false;
  }

  public boolean equals(ProductCardView that) {
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
    boolean this_present_cardNumber = true && this.isSetCardNumber();
    boolean that_present_cardNumber = true && that.isSetCardNumber();
    if (this_present_cardNumber || that_present_cardNumber) {
      if (!(this_present_cardNumber && that_present_cardNumber))
        return false;
      if (!this.cardNumber.equals(that.cardNumber))
        return false;
    }
    boolean this_present_password = true && this.isSetPassword();
    boolean that_present_password = true && that.isSetPassword();
    if (this_present_password || that_present_password) {
      if (!(this_present_password && that_present_password))
        return false;
      if (!this.password.equals(that.password))
        return false;
    }
    boolean this_present_state = true;
    boolean that_present_state = true;
    if (this_present_state || that_present_state) {
      if (!(this_present_state && that_present_state))
        return false;
      if (this.state != that.state)
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

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_productId = true && (isSetProductId());
    builder.append(present_productId);
    if (present_productId)
      builder.append(productId);
    boolean present_cardNumber = true && (isSetCardNumber());
    builder.append(present_cardNumber);
    if (present_cardNumber)
      builder.append(cardNumber);
    boolean present_password = true && (isSetPassword());
    builder.append(present_password);
    if (present_password)
      builder.append(password);
    boolean present_state = true;
    builder.append(present_state);
    if (present_state)
      builder.append(state);
    boolean present_skuNum = true && (isSetSkuNum());
    builder.append(present_skuNum);
    if (present_skuNum)
      builder.append(skuNum);
    return builder.toHashCode();
  }

  public int compareTo(ProductCardView other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ProductCardView typedOther = (ProductCardView)other;

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
    lastComparison = Boolean.valueOf(isSetCardNumber()).compareTo(typedOther.isSetCardNumber());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCardNumber()) {
      lastComparison = TBaseHelper.compareTo(this.cardNumber, typedOther.cardNumber);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPassword()).compareTo(typedOther.isSetPassword());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPassword()) {
      lastComparison = TBaseHelper.compareTo(this.password, typedOther.password);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetState()).compareTo(typedOther.isSetState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetState()) {
      lastComparison = TBaseHelper.compareTo(this.state, typedOther.state);
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
        case 2: // CARD_NUMBER
          if (field.type == TType.STRING) {
            this.cardNumber = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // PASSWORD
          if (field.type == TType.STRING) {
            this.password = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // STATE
          if (field.type == TType.I32) {
            this.state = iprot.readI32();
            setStateIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // SKU_NUM
          if (field.type == TType.STRING) {
            this.skuNum = iprot.readString();
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
    if (this.cardNumber != null) {
      oprot.writeFieldBegin(CARD_NUMBER_FIELD_DESC);
      oprot.writeString(this.cardNumber);
      oprot.writeFieldEnd();
    }
    if (this.password != null) {
      oprot.writeFieldBegin(PASSWORD_FIELD_DESC);
      oprot.writeString(this.password);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(STATE_FIELD_DESC);
    oprot.writeI32(this.state);
    oprot.writeFieldEnd();
    if (this.skuNum != null) {
      oprot.writeFieldBegin(SKU_NUM_FIELD_DESC);
      oprot.writeString(this.skuNum);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ProductCardView(");
    boolean first = true;
    sb.append("productId:");
    if (this.productId == null) {
      sb.append("null");
    } else {
      sb.append(this.productId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cardNumber:");
    if (this.cardNumber == null) {
      sb.append("null");
    } else {
      sb.append(this.cardNumber);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("password:");
    if (this.password == null) {
      sb.append("null");
    } else {
      sb.append(this.password);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("state:");
    sb.append(this.state);
    first = false;
    if (!first) sb.append(", ");
    sb.append("skuNum:");
    if (this.skuNum == null) {
      sb.append("null");
    } else {
      sb.append(this.skuNum);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
