/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.order;

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

public class ExportOrderInfo implements TBase<ExportOrderInfo, ExportOrderInfo._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ExportOrderInfo");

  private static final TField ID_FIELD_DESC = new TField("id", TType.I32, (short)1);
  private static final TField SELLER_ID_FIELD_DESC = new TField("sellerId", TType.I32, (short)2);
  private static final TField CREATE_TIME_FIELD_DESC = new TField("createTime", TType.STRING, (short)3);
  private static final TField OP_STATE_FIELD_DESC = new TField("opState", TType.I32, (short)4);
  private static final TField OP_URL_FIELD_DESC = new TField("opUrl", TType.STRING, (short)5);
  private static final TField CONDITIONS_FIELD_DESC = new TField("conditions", TType.STRUCT, (short)6);
  private static final TField ACTUAL_COUNT_FIELD_DESC = new TField("actualCount", TType.I32, (short)7);
  private static final TField TOTAL_COUNT_FIELD_DESC = new TField("totalCount", TType.I32, (short)8);
  private static final TField FS_TYPE_FIELD_DESC = new TField("fsType", TType.I32, (short)9);


  public int id;
  public int sellerId;
  public String createTime;
  public int opState;
  public String opUrl;
  public OrderQueryConditions conditions;
  public int actualCount;
  public int totalCount;
  public int fsType;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    ID((short)1, "id"),
    SELLER_ID((short)2, "sellerId"),
    CREATE_TIME((short)3, "createTime"),
    OP_STATE((short)4, "opState"),
    OP_URL((short)5, "opUrl"),
    CONDITIONS((short)6, "conditions"),
    ACTUAL_COUNT((short)7, "actualCount"),
    TOTAL_COUNT((short)8, "totalCount"),
    FS_TYPE((short)9, "fsType");
  
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
        case 1: // ID
  	return ID;
        case 2: // SELLER_ID
  	return SELLER_ID;
        case 3: // CREATE_TIME
  	return CREATE_TIME;
        case 4: // OP_STATE
  	return OP_STATE;
        case 5: // OP_URL
  	return OP_URL;
        case 6: // CONDITIONS
  	return CONDITIONS;
        case 7: // ACTUAL_COUNT
  	return ACTUAL_COUNT;
        case 8: // TOTAL_COUNT
  	return TOTAL_COUNT;
        case 9: // FS_TYPE
  	return FS_TYPE;
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
  private static final int __ID_ISSET_ID = 0;
  private static final int __SELLERID_ISSET_ID = 1;
  private static final int __OPSTATE_ISSET_ID = 2;
  private static final int __ACTUALCOUNT_ISSET_ID = 3;
  private static final int __TOTALCOUNT_ISSET_ID = 4;
  private static final int __FSTYPE_ISSET_ID = 5;
  private BitSet __isset_bit_vector = new BitSet(6);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new FieldMetaData("id", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.SELLER_ID, new FieldMetaData("sellerId", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.CREATE_TIME, new FieldMetaData("createTime", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.OP_STATE, new FieldMetaData("opState", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.OP_URL, new FieldMetaData("opUrl", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.CONDITIONS, new FieldMetaData("conditions", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, OrderQueryConditions.class)));
    tmpMap.put(_Fields.ACTUAL_COUNT, new FieldMetaData("actualCount", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.TOTAL_COUNT, new FieldMetaData("totalCount", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.FS_TYPE, new FieldMetaData("fsType", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ExportOrderInfo.class, metaDataMap);
  }


  public ExportOrderInfo() {
  }

  public ExportOrderInfo(
    String createTime,
    int opState,
    String opUrl,
    OrderQueryConditions conditions)
  {
    this();
    this.createTime = createTime;
    this.opState = opState;
    setOpStateIsSet(true);
    this.opUrl = opUrl;
    this.conditions = conditions;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ExportOrderInfo(ExportOrderInfo other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.id = other.id;
    this.sellerId = other.sellerId;
    if (other.isSetCreateTime()) {
      this.createTime = other.createTime;
    }
    this.opState = other.opState;
    if (other.isSetOpUrl()) {
      this.opUrl = other.opUrl;
    }
    if (other.isSetConditions()) {
      this.conditions = new OrderQueryConditions(other.conditions);
    }
    this.actualCount = other.actualCount;
    this.totalCount = other.totalCount;
    this.fsType = other.fsType;
  }

  public ExportOrderInfo deepCopy() {
    return new ExportOrderInfo(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setSellerIdIsSet(false);
    this.sellerId = 0;
    this.createTime = null;
    setOpStateIsSet(false);
    this.opState = 0;
    this.opUrl = null;
    this.conditions = null;
    setActualCountIsSet(false);
    this.actualCount = 0;
    setTotalCountIsSet(false);
    this.totalCount = 0;
    setFsTypeIsSet(false);
    this.fsType = 0;
  }

  public int getId() {
    return this.id;
  }

  public ExportOrderInfo setId(int id) {
    this.id = id;
    setIdIsSet(true);

    return this;
  }

  public void unsetId() {
  __isset_bit_vector.clear(__ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been asigned a value) and false otherwise */
  public boolean isSetId() {
    return __isset_bit_vector.get(__ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bit_vector.set(__ID_ISSET_ID, value);
  }

  public int getSellerId() {
    return this.sellerId;
  }

  public ExportOrderInfo setSellerId(int sellerId) {
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

  public String getCreateTime() {
    return this.createTime;
  }

  public ExportOrderInfo setCreateTime(String createTime) {
    this.createTime = createTime;
    
    return this;
  }

  public void unsetCreateTime() {
    this.createTime = null;
  }

  /** Returns true if field createTime is set (has been asigned a value) and false otherwise */
  public boolean isSetCreateTime() {
    return this.createTime != null;
  }

  public void setCreateTimeIsSet(boolean value) {
    if (!value) {
      this.createTime = null;
    }
  }

  public int getOpState() {
    return this.opState;
  }

  public ExportOrderInfo setOpState(int opState) {
    this.opState = opState;
    setOpStateIsSet(true);

    return this;
  }

  public void unsetOpState() {
  __isset_bit_vector.clear(__OPSTATE_ISSET_ID);
  }

  /** Returns true if field opState is set (has been asigned a value) and false otherwise */
  public boolean isSetOpState() {
    return __isset_bit_vector.get(__OPSTATE_ISSET_ID);
  }

  public void setOpStateIsSet(boolean value) {
    __isset_bit_vector.set(__OPSTATE_ISSET_ID, value);
  }

  public String getOpUrl() {
    return this.opUrl;
  }

  public ExportOrderInfo setOpUrl(String opUrl) {
    this.opUrl = opUrl;
    
    return this;
  }

  public void unsetOpUrl() {
    this.opUrl = null;
  }

  /** Returns true if field opUrl is set (has been asigned a value) and false otherwise */
  public boolean isSetOpUrl() {
    return this.opUrl != null;
  }

  public void setOpUrlIsSet(boolean value) {
    if (!value) {
      this.opUrl = null;
    }
  }

  public OrderQueryConditions getConditions() {
    return this.conditions;
  }

  public ExportOrderInfo setConditions(OrderQueryConditions conditions) {
    this.conditions = conditions;
    
    return this;
  }

  public void unsetConditions() {
    this.conditions = null;
  }

  /** Returns true if field conditions is set (has been asigned a value) and false otherwise */
  public boolean isSetConditions() {
    return this.conditions != null;
  }

  public void setConditionsIsSet(boolean value) {
    if (!value) {
      this.conditions = null;
    }
  }

  public int getActualCount() {
    return this.actualCount;
  }

  public ExportOrderInfo setActualCount(int actualCount) {
    this.actualCount = actualCount;
    setActualCountIsSet(true);

    return this;
  }

  public void unsetActualCount() {
  __isset_bit_vector.clear(__ACTUALCOUNT_ISSET_ID);
  }

  /** Returns true if field actualCount is set (has been asigned a value) and false otherwise */
  public boolean isSetActualCount() {
    return __isset_bit_vector.get(__ACTUALCOUNT_ISSET_ID);
  }

  public void setActualCountIsSet(boolean value) {
    __isset_bit_vector.set(__ACTUALCOUNT_ISSET_ID, value);
  }

  public int getTotalCount() {
    return this.totalCount;
  }

  public ExportOrderInfo setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    setTotalCountIsSet(true);

    return this;
  }

  public void unsetTotalCount() {
  __isset_bit_vector.clear(__TOTALCOUNT_ISSET_ID);
  }

  /** Returns true if field totalCount is set (has been asigned a value) and false otherwise */
  public boolean isSetTotalCount() {
    return __isset_bit_vector.get(__TOTALCOUNT_ISSET_ID);
  }

  public void setTotalCountIsSet(boolean value) {
    __isset_bit_vector.set(__TOTALCOUNT_ISSET_ID, value);
  }

  public int getFsType() {
    return this.fsType;
  }

  public ExportOrderInfo setFsType(int fsType) {
    this.fsType = fsType;
    setFsTypeIsSet(true);

    return this;
  }

  public void unsetFsType() {
  __isset_bit_vector.clear(__FSTYPE_ISSET_ID);
  }

  /** Returns true if field fsType is set (has been asigned a value) and false otherwise */
  public boolean isSetFsType() {
    return __isset_bit_vector.get(__FSTYPE_ISSET_ID);
  }

  public void setFsTypeIsSet(boolean value) {
    __isset_bit_vector.set(__FSTYPE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;
    case SELLER_ID:
      if (value == null) {
        unsetSellerId();
      } else {
        setSellerId((Integer)value);
      }
      break;
    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((String)value);
      }
      break;
    case OP_STATE:
      if (value == null) {
        unsetOpState();
      } else {
        setOpState((Integer)value);
      }
      break;
    case OP_URL:
      if (value == null) {
        unsetOpUrl();
      } else {
        setOpUrl((String)value);
      }
      break;
    case CONDITIONS:
      if (value == null) {
        unsetConditions();
      } else {
        setConditions((OrderQueryConditions)value);
      }
      break;
    case ACTUAL_COUNT:
      if (value == null) {
        unsetActualCount();
      } else {
        setActualCount((Integer)value);
      }
      break;
    case TOTAL_COUNT:
      if (value == null) {
        unsetTotalCount();
      } else {
        setTotalCount((Integer)value);
      }
      break;
    case FS_TYPE:
      if (value == null) {
        unsetFsType();
      } else {
        setFsType((Integer)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return new Integer(getId());
    case SELLER_ID:
      return new Integer(getSellerId());
    case CREATE_TIME:
      return getCreateTime();
    case OP_STATE:
      return new Integer(getOpState());
    case OP_URL:
      return getOpUrl();
    case CONDITIONS:
      return getConditions();
    case ACTUAL_COUNT:
      return new Integer(getActualCount());
    case TOTAL_COUNT:
      return new Integer(getTotalCount());
    case FS_TYPE:
      return new Integer(getFsType());
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case SELLER_ID:
      return isSetSellerId();
    case CREATE_TIME:
      return isSetCreateTime();
    case OP_STATE:
      return isSetOpState();
    case OP_URL:
      return isSetOpUrl();
    case CONDITIONS:
      return isSetConditions();
    case ACTUAL_COUNT:
      return isSetActualCount();
    case TOTAL_COUNT:
      return isSetTotalCount();
    case FS_TYPE:
      return isSetFsType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ExportOrderInfo)
      return this.equals((ExportOrderInfo)that);
    return false;
  }

  public boolean equals(ExportOrderInfo that) {
    if (that == null)
      return false;
    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }
    boolean this_present_sellerId = true && this.isSetSellerId();
    boolean that_present_sellerId = true && that.isSetSellerId();
    if (this_present_sellerId || that_present_sellerId) {
      if (!(this_present_sellerId && that_present_sellerId))
        return false;
      if (this.sellerId != that.sellerId)
        return false;
    }
    boolean this_present_createTime = true && this.isSetCreateTime();
    boolean that_present_createTime = true && that.isSetCreateTime();
    if (this_present_createTime || that_present_createTime) {
      if (!(this_present_createTime && that_present_createTime))
        return false;
      if (!this.createTime.equals(that.createTime))
        return false;
    }
    boolean this_present_opState = true;
    boolean that_present_opState = true;
    if (this_present_opState || that_present_opState) {
      if (!(this_present_opState && that_present_opState))
        return false;
      if (this.opState != that.opState)
        return false;
    }
    boolean this_present_opUrl = true && this.isSetOpUrl();
    boolean that_present_opUrl = true && that.isSetOpUrl();
    if (this_present_opUrl || that_present_opUrl) {
      if (!(this_present_opUrl && that_present_opUrl))
        return false;
      if (!this.opUrl.equals(that.opUrl))
        return false;
    }
    boolean this_present_conditions = true && this.isSetConditions();
    boolean that_present_conditions = true && that.isSetConditions();
    if (this_present_conditions || that_present_conditions) {
      if (!(this_present_conditions && that_present_conditions))
        return false;
      if (!this.conditions.equals(that.conditions))
        return false;
    }
    boolean this_present_actualCount = true && this.isSetActualCount();
    boolean that_present_actualCount = true && that.isSetActualCount();
    if (this_present_actualCount || that_present_actualCount) {
      if (!(this_present_actualCount && that_present_actualCount))
        return false;
      if (this.actualCount != that.actualCount)
        return false;
    }
    boolean this_present_totalCount = true && this.isSetTotalCount();
    boolean that_present_totalCount = true && that.isSetTotalCount();
    if (this_present_totalCount || that_present_totalCount) {
      if (!(this_present_totalCount && that_present_totalCount))
        return false;
      if (this.totalCount != that.totalCount)
        return false;
    }
    boolean this_present_fsType = true && this.isSetFsType();
    boolean that_present_fsType = true && that.isSetFsType();
    if (this_present_fsType || that_present_fsType) {
      if (!(this_present_fsType && that_present_fsType))
        return false;
      if (this.fsType != that.fsType)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present_id = true && (isSetId());
    builder.append(present_id);
    if (present_id)
      builder.append(id);
    boolean present_sellerId = true && (isSetSellerId());
    builder.append(present_sellerId);
    if (present_sellerId)
      builder.append(sellerId);
    boolean present_createTime = true && (isSetCreateTime());
    builder.append(present_createTime);
    if (present_createTime)
      builder.append(createTime);
    boolean present_opState = true;
    builder.append(present_opState);
    if (present_opState)
      builder.append(opState);
    boolean present_opUrl = true && (isSetOpUrl());
    builder.append(present_opUrl);
    if (present_opUrl)
      builder.append(opUrl);
    boolean present_conditions = true && (isSetConditions());
    builder.append(present_conditions);
    if (present_conditions)
      builder.append(conditions);
    boolean present_actualCount = true && (isSetActualCount());
    builder.append(present_actualCount);
    if (present_actualCount)
      builder.append(actualCount);
    boolean present_totalCount = true && (isSetTotalCount());
    builder.append(present_totalCount);
    if (present_totalCount)
      builder.append(totalCount);
    boolean present_fsType = true && (isSetFsType());
    builder.append(present_fsType);
    if (present_fsType)
      builder.append(fsType);
    return builder.toHashCode();
  }

  public int compareTo(ExportOrderInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ExportOrderInfo typedOther = (ExportOrderInfo)other;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(typedOther.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = TBaseHelper.compareTo(this.id, typedOther.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = Boolean.valueOf(isSetCreateTime()).compareTo(typedOther.isSetCreateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTime()) {
      lastComparison = TBaseHelper.compareTo(this.createTime, typedOther.createTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOpState()).compareTo(typedOther.isSetOpState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOpState()) {
      lastComparison = TBaseHelper.compareTo(this.opState, typedOther.opState);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOpUrl()).compareTo(typedOther.isSetOpUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOpUrl()) {
      lastComparison = TBaseHelper.compareTo(this.opUrl, typedOther.opUrl);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetConditions()).compareTo(typedOther.isSetConditions());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetConditions()) {
      lastComparison = TBaseHelper.compareTo(this.conditions, typedOther.conditions);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetActualCount()).compareTo(typedOther.isSetActualCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActualCount()) {
      lastComparison = TBaseHelper.compareTo(this.actualCount, typedOther.actualCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalCount()).compareTo(typedOther.isSetTotalCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalCount()) {
      lastComparison = TBaseHelper.compareTo(this.totalCount, typedOther.totalCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFsType()).compareTo(typedOther.isSetFsType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFsType()) {
      lastComparison = TBaseHelper.compareTo(this.fsType, typedOther.fsType);
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
        case 1: // ID
          if (field.type == TType.I32) {
            this.id = iprot.readI32();
            setIdIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // SELLER_ID
          if (field.type == TType.I32) {
            this.sellerId = iprot.readI32();
            setSellerIdIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // CREATE_TIME
          if (field.type == TType.STRING) {
            this.createTime = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // OP_STATE
          if (field.type == TType.I32) {
            this.opState = iprot.readI32();
            setOpStateIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // OP_URL
          if (field.type == TType.STRING) {
            this.opUrl = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 6: // CONDITIONS
          if (field.type == TType.STRUCT) {
            this.conditions = new OrderQueryConditions();
            this.conditions.read(iprot);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 7: // ACTUAL_COUNT
          if (field.type == TType.I32) {
            this.actualCount = iprot.readI32();
            setActualCountIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 8: // TOTAL_COUNT
          if (field.type == TType.I32) {
            this.totalCount = iprot.readI32();
            setTotalCountIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 9: // FS_TYPE
          if (field.type == TType.I32) {
            this.fsType = iprot.readI32();
            setFsTypeIsSet(true);
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
    if (isSetId()) {
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(this.id);
      oprot.writeFieldEnd();
    }
    if (isSetSellerId()) {
      oprot.writeFieldBegin(SELLER_ID_FIELD_DESC);
      oprot.writeI32(this.sellerId);
      oprot.writeFieldEnd();
    }
    if (this.createTime != null) {
      oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
      oprot.writeString(this.createTime);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(OP_STATE_FIELD_DESC);
    oprot.writeI32(this.opState);
    oprot.writeFieldEnd();
    if (this.opUrl != null) {
      oprot.writeFieldBegin(OP_URL_FIELD_DESC);
      oprot.writeString(this.opUrl);
      oprot.writeFieldEnd();
    }
    if (this.conditions != null) {
      oprot.writeFieldBegin(CONDITIONS_FIELD_DESC);
      this.conditions.write(oprot);
      oprot.writeFieldEnd();
    }
    if (isSetActualCount()) {
      oprot.writeFieldBegin(ACTUAL_COUNT_FIELD_DESC);
      oprot.writeI32(this.actualCount);
      oprot.writeFieldEnd();
    }
    if (isSetTotalCount()) {
      oprot.writeFieldBegin(TOTAL_COUNT_FIELD_DESC);
      oprot.writeI32(this.totalCount);
      oprot.writeFieldEnd();
    }
    if (isSetFsType()) {
      oprot.writeFieldBegin(FS_TYPE_FIELD_DESC);
      oprot.writeI32(this.fsType);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ExportOrderInfo(");
    boolean first = true;
    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
      }
    if (isSetSellerId()) {
      if (!first) sb.append(", ");
      sb.append("sellerId:");
      sb.append(this.sellerId);
      first = false;
      }
    if (!first) sb.append(", ");
    sb.append("createTime:");
    if (this.createTime == null) {
      sb.append("null");
    } else {
      sb.append(this.createTime);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("opState:");
    sb.append(this.opState);
    first = false;
    if (!first) sb.append(", ");
    sb.append("opUrl:");
    if (this.opUrl == null) {
      sb.append("null");
    } else {
      sb.append(this.opUrl);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("conditions:");
    if (this.conditions == null) {
      sb.append("null");
    } else {
      sb.append(this.conditions);
    }
    first = false;
    if (isSetActualCount()) {
      if (!first) sb.append(", ");
      sb.append("actualCount:");
      sb.append(this.actualCount);
      first = false;
      }
    if (isSetTotalCount()) {
      if (!first) sb.append(", ");
      sb.append("totalCount:");
      sb.append(this.totalCount);
      first = false;
      }
    if (isSetFsType()) {
      if (!first) sb.append(", ");
      sb.append("fsType:");
      sb.append(this.fsType);
      first = false;
      }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}