/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.jfshare.finagle.thrift.score;

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

public class ScoreUserResult implements TBase<ScoreUserResult, ScoreUserResult._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ScoreUserResult");

  private static final TField RESULT_FIELD_DESC = new TField("result", TType.STRUCT, (short)1);
  private static final TField SCORE_USERS_FIELD_DESC = new TField("scoreUsers", TType.LIST, (short)2);
  private static final TField PAGEINATION_FIELD_DESC = new TField("pageination", TType.STRUCT, (short)3);


  public com.jfshare.finagle.thrift.result.Result result;
  public List<ScoreUser> scoreUsers;
  public com.jfshare.finagle.thrift.pagination.Pagination pageination;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    RESULT((short)1, "result"),
    SCORE_USERS((short)2, "scoreUsers"),
    PAGEINATION((short)3, "pageination");
  
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
        case 2: // SCORE_USERS
  	return SCORE_USERS;
        case 3: // PAGEINATION
  	return PAGEINATION;
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
    tmpMap.put(_Fields.SCORE_USERS, new FieldMetaData("scoreUsers", TFieldRequirementType.DEFAULT,
      new ListMetaData(TType.LIST,
                new StructMetaData(TType.STRUCT, ScoreUser.class))));
    tmpMap.put(_Fields.PAGEINATION, new FieldMetaData("pageination", TFieldRequirementType.DEFAULT,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.pagination.Pagination.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ScoreUserResult.class, metaDataMap);
  }


  public ScoreUserResult() {
  }

  public ScoreUserResult(
    com.jfshare.finagle.thrift.result.Result result,
    List<ScoreUser> scoreUsers,
    com.jfshare.finagle.thrift.pagination.Pagination pageination)
  {
    this();
    this.result = result;
    this.scoreUsers = scoreUsers;
    this.pageination = pageination;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ScoreUserResult(ScoreUserResult other) {
    if (other.isSetResult()) {
      this.result = new com.jfshare.finagle.thrift.result.Result(other.result);
    }
    if (other.isSetScoreUsers()) {
      List<ScoreUser> __this__scoreUsers = new ArrayList<ScoreUser>();
      for (ScoreUser other_element : other.scoreUsers) {
        __this__scoreUsers.add(new ScoreUser(other_element));
      }
      this.scoreUsers = __this__scoreUsers;
    }
    if (other.isSetPageination()) {
      this.pageination = new com.jfshare.finagle.thrift.pagination.Pagination(other.pageination);
    }
  }

  public ScoreUserResult deepCopy() {
    return new ScoreUserResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.scoreUsers = null;
    this.pageination = null;
  }

  public com.jfshare.finagle.thrift.result.Result getResult() {
    return this.result;
  }

  public ScoreUserResult setResult(com.jfshare.finagle.thrift.result.Result result) {
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

  public int getScoreUsersSize() {
    return (this.scoreUsers == null) ? 0 : this.scoreUsers.size();
  }

  public java.util.Iterator<ScoreUser> getScoreUsersIterator() {
    return (this.scoreUsers == null) ? null : this.scoreUsers.iterator();
  }

  public void addToScoreUsers(ScoreUser elem) {
    if (this.scoreUsers == null) {
      this.scoreUsers = new ArrayList<ScoreUser>();
    }
    this.scoreUsers.add(elem);
  }

  public List<ScoreUser> getScoreUsers() {
    return this.scoreUsers;
  }

  public ScoreUserResult setScoreUsers(List<ScoreUser> scoreUsers) {
    this.scoreUsers = scoreUsers;
    
    return this;
  }

  public void unsetScoreUsers() {
    this.scoreUsers = null;
  }

  /** Returns true if field scoreUsers is set (has been asigned a value) and false otherwise */
  public boolean isSetScoreUsers() {
    return this.scoreUsers != null;
  }

  public void setScoreUsersIsSet(boolean value) {
    if (!value) {
      this.scoreUsers = null;
    }
  }

  public com.jfshare.finagle.thrift.pagination.Pagination getPageination() {
    return this.pageination;
  }

  public ScoreUserResult setPageination(com.jfshare.finagle.thrift.pagination.Pagination pageination) {
    this.pageination = pageination;
    
    return this;
  }

  public void unsetPageination() {
    this.pageination = null;
  }

  /** Returns true if field pageination is set (has been asigned a value) and false otherwise */
  public boolean isSetPageination() {
    return this.pageination != null;
  }

  public void setPageinationIsSet(boolean value) {
    if (!value) {
      this.pageination = null;
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
    case SCORE_USERS:
      if (value == null) {
        unsetScoreUsers();
      } else {
        setScoreUsers((List<ScoreUser>)value);
      }
      break;
    case PAGEINATION:
      if (value == null) {
        unsetPageination();
      } else {
        setPageination((com.jfshare.finagle.thrift.pagination.Pagination)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();
    case SCORE_USERS:
      return getScoreUsers();
    case PAGEINATION:
      return getPageination();
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
    case SCORE_USERS:
      return isSetScoreUsers();
    case PAGEINATION:
      return isSetPageination();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ScoreUserResult)
      return this.equals((ScoreUserResult)that);
    return false;
  }

  public boolean equals(ScoreUserResult that) {
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
    boolean this_present_scoreUsers = true && this.isSetScoreUsers();
    boolean that_present_scoreUsers = true && that.isSetScoreUsers();
    if (this_present_scoreUsers || that_present_scoreUsers) {
      if (!(this_present_scoreUsers && that_present_scoreUsers))
        return false;
      if (!this.scoreUsers.equals(that.scoreUsers))
        return false;
    }
    boolean this_present_pageination = true && this.isSetPageination();
    boolean that_present_pageination = true && that.isSetPageination();
    if (this_present_pageination || that_present_pageination) {
      if (!(this_present_pageination && that_present_pageination))
        return false;
      if (!this.pageination.equals(that.pageination))
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
    boolean present_scoreUsers = true && (isSetScoreUsers());
    builder.append(present_scoreUsers);
    if (present_scoreUsers)
      builder.append(scoreUsers);
    boolean present_pageination = true && (isSetPageination());
    builder.append(present_pageination);
    if (present_pageination)
      builder.append(pageination);
    return builder.toHashCode();
  }

  public int compareTo(ScoreUserResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ScoreUserResult typedOther = (ScoreUserResult)other;

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
    lastComparison = Boolean.valueOf(isSetScoreUsers()).compareTo(typedOther.isSetScoreUsers());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetScoreUsers()) {
      lastComparison = TBaseHelper.compareTo(this.scoreUsers, typedOther.scoreUsers);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPageination()).compareTo(typedOther.isSetPageination());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPageination()) {
      lastComparison = TBaseHelper.compareTo(this.pageination, typedOther.pageination);
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
        case 2: // SCORE_USERS
          if (field.type == TType.LIST) {
            {
            TList _list4 = iprot.readListBegin();
            this.scoreUsers = new ArrayList<ScoreUser>(_list4.size);
            for (int _i5 = 0; _i5 < _list4.size; ++_i5)
            {
              ScoreUser _elem6;
              _elem6 = new ScoreUser();
              _elem6.read(iprot);
              this.scoreUsers.add(_elem6);
            }
            iprot.readListEnd();
            }
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // PAGEINATION
          if (field.type == TType.STRUCT) {
            this.pageination = new com.jfshare.finagle.thrift.pagination.Pagination();
            this.pageination.read(iprot);
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
    if (this.scoreUsers != null) {
      oprot.writeFieldBegin(SCORE_USERS_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.scoreUsers.size()));
        for (ScoreUser _iter7 : this.scoreUsers)
        {
          _iter7.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    if (this.pageination != null) {
      oprot.writeFieldBegin(PAGEINATION_FIELD_DESC);
      this.pageination.write(oprot);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ScoreUserResult(");
    boolean first = true;
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("scoreUsers:");
    if (this.scoreUsers == null) {
      sb.append("null");
    } else {
      sb.append(this.scoreUsers);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("pageination:");
    if (this.pageination == null) {
      sb.append("null");
    } else {
      sb.append(this.pageination);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
