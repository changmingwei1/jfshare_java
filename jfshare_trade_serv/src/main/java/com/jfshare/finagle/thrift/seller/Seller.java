/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.seller;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.thrift.*;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.protocol.*;

import java.util.*;

// No additional import required for struct/union.

public class Seller implements TBase<Seller, Seller._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("Seller");

  private static final TField SELLER_ID_FIELD_DESC = new TField("sellerId", TType.I32, (short)1);
  private static final TField LOGIN_NAME_FIELD_DESC = new TField("loginName", TType.STRING, (short)2);
  private static final TField SELLER_NAME_FIELD_DESC = new TField("sellerName", TType.STRING, (short)3);
  private static final TField PWD_ENC_FIELD_DESC = new TField("pwdEnc", TType.STRING, (short)4);


  public int sellerId;
  public String loginName;
  public String sellerName;
  public String pwdEnc;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    SELLER_ID((short)1, "sellerId"),
    LOGIN_NAME((short)2, "loginName"),
    SELLER_NAME((short)3, "sellerName"),
    PWD_ENC((short)4, "pwdEnc");
  
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
        case 2: // LOGIN_NAME
  	return LOGIN_NAME;
        case 3: // SELLER_NAME
  	return SELLER_NAME;
        case 4: // PWD_ENC
  	return PWD_ENC;
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
    tmpMap.put(_Fields.LOGIN_NAME, new FieldMetaData("loginName", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.SELLER_NAME, new FieldMetaData("sellerName", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.PWD_ENC, new FieldMetaData("pwdEnc", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(Seller.class, metaDataMap);
  }


  public Seller() {
  }

  public Seller(
    int sellerId,
    String loginName,
    String sellerName,
    String pwdEnc)
  {
    this();
    this.sellerId = sellerId;
    setSellerIdIsSet(true);
    this.loginName = loginName;
    this.sellerName = sellerName;
    this.pwdEnc = pwdEnc;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Seller(Seller other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.sellerId = other.sellerId;
    if (other.isSetLoginName()) {
      this.loginName = other.loginName;
    }
    if (other.isSetSellerName()) {
      this.sellerName = other.sellerName;
    }
    if (other.isSetPwdEnc()) {
      this.pwdEnc = other.pwdEnc;
    }
  }

  public Seller deepCopy() {
    return new Seller(this);
  }

  @Override
  public void clear() {
    setSellerIdIsSet(false);
    this.sellerId = 0;
    this.loginName = null;
    this.sellerName = null;
    this.pwdEnc = null;
  }

  public int getSellerId() {
    return this.sellerId;
  }

  public Seller setSellerId(int sellerId) {
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

  public String getLoginName() {
    return this.loginName;
  }

  public Seller setLoginName(String loginName) {
    this.loginName = loginName;
    
    return this;
  }

  public void unsetLoginName() {
    this.loginName = null;
  }

  /** Returns true if field loginName is set (has been asigned a value) and false otherwise */
  public boolean isSetLoginName() {
    return this.loginName != null;
  }

  public void setLoginNameIsSet(boolean value) {
    if (!value) {
      this.loginName = null;
    }
  }

  public String getSellerName() {
    return this.sellerName;
  }

  public Seller setSellerName(String sellerName) {
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

  public String getPwdEnc() {
    return this.pwdEnc;
  }

  public Seller setPwdEnc(String pwdEnc) {
    this.pwdEnc = pwdEnc;
    
    return this;
  }

  public void unsetPwdEnc() {
    this.pwdEnc = null;
  }

  /** Returns true if field pwdEnc is set (has been asigned a value) and false otherwise */
  public boolean isSetPwdEnc() {
    return this.pwdEnc != null;
  }

  public void setPwdEncIsSet(boolean value) {
    if (!value) {
      this.pwdEnc = null;
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
    case LOGIN_NAME:
      if (value == null) {
        unsetLoginName();
      } else {
        setLoginName((String)value);
      }
      break;
    case SELLER_NAME:
      if (value == null) {
        unsetSellerName();
      } else {
        setSellerName((String)value);
      }
      break;
    case PWD_ENC:
      if (value == null) {
        unsetPwdEnc();
      } else {
        setPwdEnc((String)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SELLER_ID:
      return new Integer(getSellerId());
    case LOGIN_NAME:
      return getLoginName();
    case SELLER_NAME:
      return getSellerName();
    case PWD_ENC:
      return getPwdEnc();
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
    case LOGIN_NAME:
      return isSetLoginName();
    case SELLER_NAME:
      return isSetSellerName();
    case PWD_ENC:
      return isSetPwdEnc();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Seller)
      return this.equals((Seller)that);
    return false;
  }

  public boolean equals(Seller that) {
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
    boolean this_present_loginName = true && this.isSetLoginName();
    boolean that_present_loginName = true && that.isSetLoginName();
    if (this_present_loginName || that_present_loginName) {
      if (!(this_present_loginName && that_present_loginName))
        return false;
      if (!this.loginName.equals(that.loginName))
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
    boolean this_present_pwdEnc = true && this.isSetPwdEnc();
    boolean that_present_pwdEnc = true && that.isSetPwdEnc();
    if (this_present_pwdEnc || that_present_pwdEnc) {
      if (!(this_present_pwdEnc && that_present_pwdEnc))
        return false;
      if (!this.pwdEnc.equals(that.pwdEnc))
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
    boolean present_loginName = true && (isSetLoginName());
    builder.append(present_loginName);
    if (present_loginName)
      builder.append(loginName);
    boolean present_sellerName = true && (isSetSellerName());
    builder.append(present_sellerName);
    if (present_sellerName)
      builder.append(sellerName);
    boolean present_pwdEnc = true && (isSetPwdEnc());
    builder.append(present_pwdEnc);
    if (present_pwdEnc)
      builder.append(pwdEnc);
    return builder.toHashCode();
  }

  public int compareTo(Seller other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Seller typedOther = (Seller)other;

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
    lastComparison = Boolean.valueOf(isSetLoginName()).compareTo(typedOther.isSetLoginName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoginName()) {
      lastComparison = TBaseHelper.compareTo(this.loginName, typedOther.loginName);
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
    lastComparison = Boolean.valueOf(isSetPwdEnc()).compareTo(typedOther.isSetPwdEnc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPwdEnc()) {
      lastComparison = TBaseHelper.compareTo(this.pwdEnc, typedOther.pwdEnc);
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
        case 2: // LOGIN_NAME
          if (field.type == TType.STRING) {
            this.loginName = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // SELLER_NAME
          if (field.type == TType.STRING) {
            this.sellerName = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // PWD_ENC
          if (field.type == TType.STRING) {
            this.pwdEnc = iprot.readString();
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
    if (this.loginName != null) {
      oprot.writeFieldBegin(LOGIN_NAME_FIELD_DESC);
      oprot.writeString(this.loginName);
      oprot.writeFieldEnd();
    }
    if (this.sellerName != null) {
      oprot.writeFieldBegin(SELLER_NAME_FIELD_DESC);
      oprot.writeString(this.sellerName);
      oprot.writeFieldEnd();
    }
    if (this.pwdEnc != null) {
      oprot.writeFieldBegin(PWD_ENC_FIELD_DESC);
      oprot.writeString(this.pwdEnc);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Seller(");
    boolean first = true;
    sb.append("sellerId:");
    sb.append(this.sellerId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("loginName:");
    if (this.loginName == null) {
      sb.append("null");
    } else {
      sb.append(this.loginName);
    }
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
    sb.append("pwdEnc:");
    if (this.pwdEnc == null) {
      sb.append("null");
    } else {
      sb.append(this.pwdEnc);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
