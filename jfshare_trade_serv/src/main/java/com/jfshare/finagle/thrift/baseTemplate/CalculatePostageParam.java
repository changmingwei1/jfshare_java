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

public class CalculatePostageParam implements TBase<CalculatePostageParam, CalculatePostageParam._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("CalculatePostageParam");

  private static final TField SELLER_POSTAGE_BASIC_LIST_FIELD_DESC = new TField("sellerPostageBasicList", TType.LIST, (short)1);
  private static final TField SEND_TO_PROVINCE_FIELD_DESC = new TField("sendToProvince", TType.STRING, (short)2);


  public List<SellerPostageBasic> sellerPostageBasicList;
  public String sendToProvince;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    SELLER_POSTAGE_BASIC_LIST((short)1, "sellerPostageBasicList"),
    SEND_TO_PROVINCE((short)2, "sendToProvince");
  
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
        case 1: // SELLER_POSTAGE_BASIC_LIST
  	return SELLER_POSTAGE_BASIC_LIST;
        case 2: // SEND_TO_PROVINCE
  	return SEND_TO_PROVINCE;
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
    tmpMap.put(_Fields.SELLER_POSTAGE_BASIC_LIST, new FieldMetaData("sellerPostageBasicList", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, SellerPostageBasic.class))));
    tmpMap.put(_Fields.SEND_TO_PROVINCE, new FieldMetaData("sendToProvince", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(CalculatePostageParam.class, metaDataMap);
  }


  public CalculatePostageParam() {
  }

  public CalculatePostageParam(
    List<SellerPostageBasic> sellerPostageBasicList,
    String sendToProvince)
  {
    this();
    this.sellerPostageBasicList = sellerPostageBasicList;
    this.sendToProvince = sendToProvince;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CalculatePostageParam(CalculatePostageParam other) {
    if (other.isSetSellerPostageBasicList()) {
      List<SellerPostageBasic> __this__sellerPostageBasicList = new ArrayList<SellerPostageBasic>();
      for (SellerPostageBasic other_element : other.sellerPostageBasicList) {
        __this__sellerPostageBasicList.add(new SellerPostageBasic(other_element));
      }
      this.sellerPostageBasicList = __this__sellerPostageBasicList;
    }
    if (other.isSetSendToProvince()) {
      this.sendToProvince = other.sendToProvince;
    }
  }

  public CalculatePostageParam deepCopy() {
    return new CalculatePostageParam(this);
  }

  @Override
  public void clear() {
    this.sellerPostageBasicList = null;
    this.sendToProvince = null;
  }

  public int getSellerPostageBasicListSize() {
    return (this.sellerPostageBasicList == null) ? 0 : this.sellerPostageBasicList.size();
  }

  public java.util.Iterator<SellerPostageBasic> getSellerPostageBasicListIterator() {
    return (this.sellerPostageBasicList == null) ? null : this.sellerPostageBasicList.iterator();
  }

  public void addToSellerPostageBasicList(SellerPostageBasic elem) {
    if (this.sellerPostageBasicList == null) {
      this.sellerPostageBasicList = new ArrayList<SellerPostageBasic>();
    }
    this.sellerPostageBasicList.add(elem);
  }

  public List<SellerPostageBasic> getSellerPostageBasicList() {
    return this.sellerPostageBasicList;
  }

  public CalculatePostageParam setSellerPostageBasicList(List<SellerPostageBasic> sellerPostageBasicList) {
    this.sellerPostageBasicList = sellerPostageBasicList;
    
    return this;
  }

  public void unsetSellerPostageBasicList() {
    this.sellerPostageBasicList = null;
  }

  /** Returns true if field sellerPostageBasicList is set (has been asigned a value) and false otherwise */
  public boolean isSetSellerPostageBasicList() {
    return this.sellerPostageBasicList != null;
  }

  public void setSellerPostageBasicListIsSet(boolean value) {
    if (!value) {
      this.sellerPostageBasicList = null;
    }
  }

  public String getSendToProvince() {
    return this.sendToProvince;
  }

  public CalculatePostageParam setSendToProvince(String sendToProvince) {
    this.sendToProvince = sendToProvince;
    
    return this;
  }

  public void unsetSendToProvince() {
    this.sendToProvince = null;
  }

  /** Returns true if field sendToProvince is set (has been asigned a value) and false otherwise */
  public boolean isSetSendToProvince() {
    return this.sendToProvince != null;
  }

  public void setSendToProvinceIsSet(boolean value) {
    if (!value) {
      this.sendToProvince = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SELLER_POSTAGE_BASIC_LIST:
      if (value == null) {
        unsetSellerPostageBasicList();
      } else {
        setSellerPostageBasicList((List<SellerPostageBasic>)value);
      }
      break;
    case SEND_TO_PROVINCE:
      if (value == null) {
        unsetSendToProvince();
      } else {
        setSendToProvince((String)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SELLER_POSTAGE_BASIC_LIST:
      return getSellerPostageBasicList();
    case SEND_TO_PROVINCE:
      return getSendToProvince();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SELLER_POSTAGE_BASIC_LIST:
      return isSetSellerPostageBasicList();
    case SEND_TO_PROVINCE:
      return isSetSendToProvince();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CalculatePostageParam)
      return this.equals((CalculatePostageParam)that);
    return false;
  }

  public boolean equals(CalculatePostageParam that) {
    if (that == null)
      return false;
    boolean this_present_sellerPostageBasicList = true && this.isSetSellerPostageBasicList();
    boolean that_present_sellerPostageBasicList = true && that.isSetSellerPostageBasicList();
    if (this_present_sellerPostageBasicList || that_present_sellerPostageBasicList) {
      if (!(this_present_sellerPostageBasicList && that_present_sellerPostageBasicList))
        return false;
      if (!this.sellerPostageBasicList.equals(that.sellerPostageBasicList))
        return false;
    }
    boolean this_present_sendToProvince = true && this.isSetSendToProvince();
    boolean that_present_sendToProvince = true && that.isSetSendToProvince();
    if (this_present_sendToProvince || that_present_sendToProvince) {
      if (!(this_present_sendToProvince && that_present_sendToProvince))
        return false;
      if (!this.sendToProvince.equals(that.sendToProvince))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_sellerPostageBasicList = true && (isSetSellerPostageBasicList());
    builder.append(present_sellerPostageBasicList);
    if (present_sellerPostageBasicList)
      builder.append(sellerPostageBasicList);
    boolean present_sendToProvince = true && (isSetSendToProvince());
    builder.append(present_sendToProvince);
    if (present_sendToProvince)
      builder.append(sendToProvince);
    return builder.toHashCode();
  }

  public int compareTo(CalculatePostageParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    CalculatePostageParam typedOther = (CalculatePostageParam)other;

    lastComparison = Boolean.valueOf(isSetSellerPostageBasicList()).compareTo(typedOther.isSetSellerPostageBasicList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSellerPostageBasicList()) {
      lastComparison = TBaseHelper.compareTo(this.sellerPostageBasicList, typedOther.sellerPostageBasicList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSendToProvince()).compareTo(typedOther.isSetSendToProvince());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSendToProvince()) {
      lastComparison = TBaseHelper.compareTo(this.sendToProvince, typedOther.sendToProvince);
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
        case 1: // SELLER_POSTAGE_BASIC_LIST
          if (field.type == TType.LIST) {
            {
            TList _list20 = iprot.readListBegin();
            this.sellerPostageBasicList = new ArrayList<SellerPostageBasic>(_list20.size);
            for (int _i21 = 0; _i21 < _list20.size; ++_i21)
            {
              SellerPostageBasic _elem22;
              _elem22 = new SellerPostageBasic();
              _elem22.read(iprot);
              this.sellerPostageBasicList.add(_elem22);
            }
            iprot.readListEnd();
            }
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // SEND_TO_PROVINCE
          if (field.type == TType.STRING) {
            this.sendToProvince = iprot.readString();
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
    if (this.sellerPostageBasicList != null) {
      oprot.writeFieldBegin(SELLER_POSTAGE_BASIC_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.sellerPostageBasicList.size()));
        for (SellerPostageBasic _iter23 : this.sellerPostageBasicList)
        {
          _iter23.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    if (this.sendToProvince != null) {
      oprot.writeFieldBegin(SEND_TO_PROVINCE_FIELD_DESC);
      oprot.writeString(this.sendToProvince);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("CalculatePostageParam(");
    boolean first = true;
    sb.append("sellerPostageBasicList:");
    if (this.sellerPostageBasicList == null) {
      sb.append("null");
    } else {
      sb.append(this.sellerPostageBasicList);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("sendToProvince:");
    if (this.sendToProvince == null) {
      sb.append("null");
    } else {
      sb.append(this.sendToProvince);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}