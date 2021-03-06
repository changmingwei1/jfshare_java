/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.trade;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.thrift.*;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.meta_data.ListMetaData;
import org.apache.thrift.meta_data.StructMetaData;
import org.apache.thrift.protocol.*;

import java.util.*;

// No additional import required for struct/union.

public class ExchangeResult implements TBase<ExchangeResult, ExchangeResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ExchangeResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField TOTAL_SCORE_FIELD_DESC = new TField("totalScore", TType.STRING, (short)2);
  private static final TField EXCHANGE_SCORE_FIELD_DESC = new TField("exchangeScore", TType.STRING, (short)3);
  private static final TField AMOUNT_FIELD_DESC = new TField("amount", TType.STRING, (short)4);
  private static final TField EXCHANGE_DETAIL_LIST_FIELD_DESC = new TField("exchangeDetailList", TType.LIST, (short)5);


  public com.jfshare.finagle.thrift.result.Result result;
  public String totalScore;
  public String exchangeScore;
  public String amount;
  public List<ExchangeDetail> exchangeDetailList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    TOTAL_SCORE((short)2, "totalScore"),
    EXCHANGE_SCORE((short)3, "exchangeScore"),
    AMOUNT((short)4, "amount"),
    EXCHANGE_DETAIL_LIST((short)5, "exchangeDetailList");
  
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
        case 2: // TOTAL_SCORE
  	return TOTAL_SCORE;
        case 3: // EXCHANGE_SCORE
  	return EXCHANGE_SCORE;
        case 4: // AMOUNT
  	return AMOUNT;
        case 5: // EXCHANGE_DETAIL_LIST
  	return EXCHANGE_DETAIL_LIST;
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
    tmpMap.put(_Fields.TOTAL_SCORE, new FieldMetaData("totalScore", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.EXCHANGE_SCORE, new FieldMetaData("exchangeScore", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.AMOUNT, new FieldMetaData("amount", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.EXCHANGE_DETAIL_LIST, new FieldMetaData("exchangeDetailList", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, ExchangeDetail.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ExchangeResult.class, metaDataMap);
  }


  public ExchangeResult() {
  }

  public ExchangeResult(
    com.jfshare.finagle.thrift.result.Result result,
    String totalScore,
    String exchangeScore,
    String amount,
    List<ExchangeDetail> exchangeDetailList)
  {
    this();
    this.result = result;
    this.totalScore = totalScore;
    this.exchangeScore = exchangeScore;
    this.amount = amount;
    this.exchangeDetailList = exchangeDetailList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ExchangeResult(ExchangeResult other) {
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetTotalScore()) {
      this.totalScore = other.totalScore;
    }
    if (other.isSetExchangeScore()) {
      this.exchangeScore = other.exchangeScore;
    }
    if (other.isSetAmount()) {
      this.amount = other.amount;
    }
    if (other.isSetExchangeDetailList()) {
      List<ExchangeDetail> __this__exchangeDetailList = new ArrayList<ExchangeDetail>();
      for (ExchangeDetail other_element : other.exchangeDetailList) {
        __this__exchangeDetailList.add(new ExchangeDetail(other_element));
      }
      this.exchangeDetailList = __this__exchangeDetailList;
    }
  }

  public ExchangeResult deepCopy() {
    return new ExchangeResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.totalScore = null;
    this.exchangeScore = null;
    this.amount = null;
    this.exchangeDetailList = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public ExchangeResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public String getTotalScore() {
    return this.totalScore;
  }

  public ExchangeResult setTotalScore(String totalScore) {
    this.totalScore = totalScore;
    
    return this;
  }

  public void unsetTotalScore() {
    this.totalScore = null;
  }

  /** Returns true if field totalScore is set (has been asigned a value) and false otherwise */
  public boolean isSetTotalScore() {
    return this.totalScore != null;
  }

  public void setTotalScoreIsSet(boolean value) {
    if (!value) {
      this.totalScore = null;
    }
  }

  public String getExchangeScore() {
    return this.exchangeScore;
  }

  public ExchangeResult setExchangeScore(String exchangeScore) {
    this.exchangeScore = exchangeScore;
    
    return this;
  }

  public void unsetExchangeScore() {
    this.exchangeScore = null;
  }

  /** Returns true if field exchangeScore is set (has been asigned a value) and false otherwise */
  public boolean isSetExchangeScore() {
    return this.exchangeScore != null;
  }

  public void setExchangeScoreIsSet(boolean value) {
    if (!value) {
      this.exchangeScore = null;
    }
  }

  public String getAmount() {
    return this.amount;
  }

  public ExchangeResult setAmount(String amount) {
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

  public int getExchangeDetailListSize() {
    return (this.exchangeDetailList == null) ? 0 : this.exchangeDetailList.size();
  }

  public java.util.Iterator<ExchangeDetail> getExchangeDetailListIterator() {
    return (this.exchangeDetailList == null) ? null : this.exchangeDetailList.iterator();
  }

  public void addToExchangeDetailList(ExchangeDetail elem) {
    if (this.exchangeDetailList == null) {
      this.exchangeDetailList = new ArrayList<ExchangeDetail>();
    }
    this.exchangeDetailList.add(elem);
  }

  public List<ExchangeDetail> getExchangeDetailList() {
    return this.exchangeDetailList;
  }

  public ExchangeResult setExchangeDetailList(List<ExchangeDetail> exchangeDetailList) {
    this.exchangeDetailList = exchangeDetailList;
    
    return this;
  }

  public void unsetExchangeDetailList() {
    this.exchangeDetailList = null;
  }

  /** Returns true if field exchangeDetailList is set (has been asigned a value) and false otherwise */
  public boolean isSetExchangeDetailList() {
    return this.exchangeDetailList != null;
  }

  public void setExchangeDetailListIsSet(boolean value) {
    if (!value) {
      this.exchangeDetailList = null;
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
    case TOTAL_SCORE:
      if (value == null) {
        unsetTotalScore();
      } else {
        setTotalScore((String)value);
      }
      break;
    case EXCHANGE_SCORE:
      if (value == null) {
        unsetExchangeScore();
      } else {
        setExchangeScore((String)value);
      }
      break;
    case AMOUNT:
      if (value == null) {
        unsetAmount();
      } else {
        setAmount((String)value);
      }
      break;
    case EXCHANGE_DETAIL_LIST:
      if (value == null) {
        unsetExchangeDetailList();
      } else {
        setExchangeDetailList((List<ExchangeDetail>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case TOTAL_SCORE:
      return getTotalScore();
    case EXCHANGE_SCORE:
      return getExchangeScore();
    case AMOUNT:
      return getAmount();
    case EXCHANGE_DETAIL_LIST:
      return getExchangeDetailList();
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
    case TOTAL_SCORE:
      return isSetTotalScore();
    case EXCHANGE_SCORE:
      return isSetExchangeScore();
    case AMOUNT:
      return isSetAmount();
    case EXCHANGE_DETAIL_LIST:
      return isSetExchangeDetailList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ExchangeResult)
      return this.equals((ExchangeResult)that);
    return false;
  }

  public boolean equals(ExchangeResult that) {
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
    boolean this_present_totalScore = true && this.isSetTotalScore();
    boolean that_present_totalScore = true && that.isSetTotalScore();
    if (this_present_totalScore || that_present_totalScore) {
      if (!(this_present_totalScore && that_present_totalScore))
        return false;
      if (!this.totalScore.equals(that.totalScore))
        return false;
    }
    boolean this_present_exchangeScore = true && this.isSetExchangeScore();
    boolean that_present_exchangeScore = true && that.isSetExchangeScore();
    if (this_present_exchangeScore || that_present_exchangeScore) {
      if (!(this_present_exchangeScore && that_present_exchangeScore))
        return false;
      if (!this.exchangeScore.equals(that.exchangeScore))
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
    boolean this_present_exchangeDetailList = true && this.isSetExchangeDetailList();
    boolean that_present_exchangeDetailList = true && that.isSetExchangeDetailList();
    if (this_present_exchangeDetailList || that_present_exchangeDetailList) {
      if (!(this_present_exchangeDetailList && that_present_exchangeDetailList))
        return false;
      if (!this.exchangeDetailList.equals(that.exchangeDetailList))
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
    boolean present_totalScore = true && (isSetTotalScore());
    builder.append(present_totalScore);
    if (present_totalScore)
      builder.append(totalScore);
    boolean present_exchangeScore = true && (isSetExchangeScore());
    builder.append(present_exchangeScore);
    if (present_exchangeScore)
      builder.append(exchangeScore);
    boolean present_amount = true && (isSetAmount());
    builder.append(present_amount);
    if (present_amount)
      builder.append(amount);
    boolean present_exchangeDetailList = true && (isSetExchangeDetailList());
    builder.append(present_exchangeDetailList);
    if (present_exchangeDetailList)
      builder.append(exchangeDetailList);
    return builder.toHashCode();
  }

  public int compareTo(ExchangeResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ExchangeResult typedOther = (ExchangeResult)other;

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
    lastComparison = Boolean.valueOf(isSetTotalScore()).compareTo(typedOther.isSetTotalScore());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalScore()) {
      lastComparison = TBaseHelper.compareTo(this.totalScore, typedOther.totalScore);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetExchangeScore()).compareTo(typedOther.isSetExchangeScore());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExchangeScore()) {
      lastComparison = TBaseHelper.compareTo(this.exchangeScore, typedOther.exchangeScore);
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
    lastComparison = Boolean.valueOf(isSetExchangeDetailList()).compareTo(typedOther.isSetExchangeDetailList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExchangeDetailList()) {
      lastComparison = TBaseHelper.compareTo(this.exchangeDetailList, typedOther.exchangeDetailList);
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
        case 2: // TOTAL_SCORE
          if (field.type == TType.STRING) {
            this.totalScore = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // EXCHANGE_SCORE
          if (field.type == TType.STRING) {
            this.exchangeScore = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // AMOUNT
          if (field.type == TType.STRING) {
            this.amount = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // EXCHANGE_DETAIL_LIST
          if (field.type == TType.LIST) {
            {
            TList _list16 = iprot.readListBegin();
            this.exchangeDetailList = new ArrayList<ExchangeDetail>(_list16.size);
            for (int _i17 = 0; _i17 < _list16.size; ++_i17)
            {
              ExchangeDetail _elem18;
              _elem18 = new ExchangeDetail();
              _elem18.read(iprot);
              this.exchangeDetailList.add(_elem18);
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
    if (this.totalScore != null) {
      oprot.writeFieldBegin(TOTAL_SCORE_FIELD_DESC);
      oprot.writeString(this.totalScore);
      oprot.writeFieldEnd();
    }
    if (this.exchangeScore != null) {
      oprot.writeFieldBegin(EXCHANGE_SCORE_FIELD_DESC);
      oprot.writeString(this.exchangeScore);
      oprot.writeFieldEnd();
    }
    if (this.amount != null) {
      oprot.writeFieldBegin(AMOUNT_FIELD_DESC);
      oprot.writeString(this.amount);
      oprot.writeFieldEnd();
    }
    if (this.exchangeDetailList != null) {
      oprot.writeFieldBegin(EXCHANGE_DETAIL_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.exchangeDetailList.size()));
        for (ExchangeDetail _iter19 : this.exchangeDetailList)
        {
          _iter19.write(oprot);
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
    StringBuilder sb = new StringBuilder("ExchangeResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("totalScore:");
    if (this.totalScore == null) {
      sb.append("null");
    } else {
      sb.append(this.totalScore);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("exchangeScore:");
    if (this.exchangeScore == null) {
      sb.append("null");
    } else {
      sb.append(this.exchangeScore);
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
    sb.append("exchangeDetailList:");
    if (this.exchangeDetailList == null) {
      sb.append("null");
    } else {
      sb.append(this.exchangeDetailList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
