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

public class ProductSurveyQueryParam implements TBase<ProductSurveyQueryParam, ProductSurveyQueryParam._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ProductSurveyQueryParam");

  private static final TField SELLER_ID_FIELD_DESC = new TField("sellerId", TType.I32, (short)1);
  private static final TField PRODUCT_ID_FIELD_DESC = new TField("productId", TType.STRING, (short)2);
  private static final TField PRODUCT_NAME_FIELD_DESC = new TField("productName", TType.STRING, (short)3);
  private static final TField ACTIVE_STATE_FIELD_DESC = new TField("activeState", TType.I32, (short)4);
  private static final TField PAGINATION_FIELD_DESC = new TField("pagination", TType.STRUCT, (short)5);
  private static final TField SORT_FIELD_DESC = new TField("sort", TType.STRING, (short)6);
  private static final TField SUBJECT_ID_FIELD_DESC = new TField("subjectId", TType.I32, (short)7);
  private static final TField BRAND_ID_FIELD_DESC = new TField("brandId", TType.I32, (short)8);
  private static final TField SUBJECT_ID_LIST_FIELD_DESC = new TField("subjectIdList", TType.LIST, (short)9);
  private static final TField PRODUCT_ID_LIST_FIELD_DESC = new TField("productIdList", TType.LIST, (short)10);


  public int sellerId;
  public String productId;
  public String productName;
  public int activeState;
  public com.jfshare.finagle.thrift.pagination.Pagination pagination;
  public String sort;
  public int subjectId;
  public int brandId;
  public List<Integer> subjectIdList;
  public List<String> productIdList;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    SELLER_ID((short)1, "sellerId"),
    PRODUCT_ID((short)2, "productId"),
    PRODUCT_NAME((short)3, "productName"),
    ACTIVE_STATE((short)4, "activeState"),
    PAGINATION((short)5, "pagination"),
    SORT((short)6, "sort"),
    SUBJECT_ID((short)7, "subjectId"),
    BRAND_ID((short)8, "brandId"),
    SUBJECT_ID_LIST((short)9, "subjectIdList"),
    PRODUCT_ID_LIST((short)10, "productIdList");
  
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
        case 2: // PRODUCT_ID
  	return PRODUCT_ID;
        case 3: // PRODUCT_NAME
  	return PRODUCT_NAME;
        case 4: // ACTIVE_STATE
  	return ACTIVE_STATE;
        case 5: // PAGINATION
  	return PAGINATION;
        case 6: // SORT
  	return SORT;
        case 7: // SUBJECT_ID
  	return SUBJECT_ID;
        case 8: // BRAND_ID
  	return BRAND_ID;
        case 9: // SUBJECT_ID_LIST
  	return SUBJECT_ID_LIST;
        case 10: // PRODUCT_ID_LIST
  	return PRODUCT_ID_LIST;
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
  private static final int __ACTIVESTATE_ISSET_ID = 1;
  private static final int __SUBJECTID_ISSET_ID = 2;
  private static final int __BRANDID_ISSET_ID = 3;
  private BitSet __isset_bit_vector = new BitSet(4);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SELLER_ID, new FieldMetaData("sellerId", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.PRODUCT_ID, new FieldMetaData("productId", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.PRODUCT_NAME, new FieldMetaData("productName", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.ACTIVE_STATE, new FieldMetaData("activeState", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.PAGINATION, new FieldMetaData("pagination", TFieldRequirementType.OPTIONAL,
      new StructMetaData(TType.STRUCT, com.jfshare.finagle.thrift.pagination.Pagination.class)));
    tmpMap.put(_Fields.SORT, new FieldMetaData("sort", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.SUBJECT_ID, new FieldMetaData("subjectId", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.BRAND_ID, new FieldMetaData("brandId", TFieldRequirementType.OPTIONAL,
      new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.SUBJECT_ID_LIST, new FieldMetaData("subjectIdList", TFieldRequirementType.OPTIONAL,
      new ListMetaData(TType.LIST,
                new FieldValueMetaData(TType.I32))));
    tmpMap.put(_Fields.PRODUCT_ID_LIST, new FieldMetaData("productIdList", TFieldRequirementType.OPTIONAL,
      new ListMetaData(TType.LIST,
                new FieldValueMetaData(TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ProductSurveyQueryParam.class, metaDataMap);
  }


  public ProductSurveyQueryParam() {
  }

  public ProductSurveyQueryParam(
    int sellerId)
  {
    this();
    this.sellerId = sellerId;
    setSellerIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProductSurveyQueryParam(ProductSurveyQueryParam other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.sellerId = other.sellerId;
    if (other.isSetProductId()) {
      this.productId = other.productId;
    }
    if (other.isSetProductName()) {
      this.productName = other.productName;
    }
    this.activeState = other.activeState;
    if (other.isSetPagination()) {
      this.pagination = new com.jfshare.finagle.thrift.pagination.Pagination(other.pagination);
    }
    if (other.isSetSort()) {
      this.sort = other.sort;
    }
    this.subjectId = other.subjectId;
    this.brandId = other.brandId;
    if (other.isSetSubjectIdList()) {
      List<Integer> __this__subjectIdList = new ArrayList<Integer>();
      for (Integer other_element : other.subjectIdList) {
        __this__subjectIdList.add(other_element);
      }
      this.subjectIdList = __this__subjectIdList;
    }
    if (other.isSetProductIdList()) {
      List<String> __this__productIdList = new ArrayList<String>();
      for (String other_element : other.productIdList) {
        __this__productIdList.add(other_element);
      }
      this.productIdList = __this__productIdList;
    }
  }

  public ProductSurveyQueryParam deepCopy() {
    return new ProductSurveyQueryParam(this);
  }

  @Override
  public void clear() {
    setSellerIdIsSet(false);
    this.sellerId = 0;
    this.productId = null;
    this.productName = null;
    setActiveStateIsSet(false);
    this.activeState = 0;
    this.pagination = null;
    this.sort = null;
    setSubjectIdIsSet(false);
    this.subjectId = 0;
    setBrandIdIsSet(false);
    this.brandId = 0;
    this.subjectIdList = null;
    this.productIdList = null;
  }

  public int getSellerId() {
    return this.sellerId;
  }

  public ProductSurveyQueryParam setSellerId(int sellerId) {
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

  public String getProductId() {
    return this.productId;
  }

  public ProductSurveyQueryParam setProductId(String productId) {
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

  public String getProductName() {
    return this.productName;
  }

  public ProductSurveyQueryParam setProductName(String productName) {
    this.productName = productName;
    
    return this;
  }

  public void unsetProductName() {
    this.productName = null;
  }

  /** Returns true if field productName is set (has been asigned a value) and false otherwise */
  public boolean isSetProductName() {
    return this.productName != null;
  }

  public void setProductNameIsSet(boolean value) {
    if (!value) {
      this.productName = null;
    }
  }

  public int getActiveState() {
    return this.activeState;
  }

  public ProductSurveyQueryParam setActiveState(int activeState) {
    this.activeState = activeState;
    setActiveStateIsSet(true);

    return this;
  }

  public void unsetActiveState() {
  __isset_bit_vector.clear(__ACTIVESTATE_ISSET_ID);
  }

  /** Returns true if field activeState is set (has been asigned a value) and false otherwise */
  public boolean isSetActiveState() {
    return __isset_bit_vector.get(__ACTIVESTATE_ISSET_ID);
  }

  public void setActiveStateIsSet(boolean value) {
    __isset_bit_vector.set(__ACTIVESTATE_ISSET_ID, value);
  }

  public com.jfshare.finagle.thrift.pagination.Pagination getPagination() {
    return this.pagination;
  }

  public ProductSurveyQueryParam setPagination(com.jfshare.finagle.thrift.pagination.Pagination pagination) {
    this.pagination = pagination;
    
    return this;
  }

  public void unsetPagination() {
    this.pagination = null;
  }

  /** Returns true if field pagination is set (has been asigned a value) and false otherwise */
  public boolean isSetPagination() {
    return this.pagination != null;
  }

  public void setPaginationIsSet(boolean value) {
    if (!value) {
      this.pagination = null;
    }
  }

  public String getSort() {
    return this.sort;
  }

  public ProductSurveyQueryParam setSort(String sort) {
    this.sort = sort;
    
    return this;
  }

  public void unsetSort() {
    this.sort = null;
  }

  /** Returns true if field sort is set (has been asigned a value) and false otherwise */
  public boolean isSetSort() {
    return this.sort != null;
  }

  public void setSortIsSet(boolean value) {
    if (!value) {
      this.sort = null;
    }
  }

  public int getSubjectId() {
    return this.subjectId;
  }

  public ProductSurveyQueryParam setSubjectId(int subjectId) {
    this.subjectId = subjectId;
    setSubjectIdIsSet(true);

    return this;
  }

  public void unsetSubjectId() {
  __isset_bit_vector.clear(__SUBJECTID_ISSET_ID);
  }

  /** Returns true if field subjectId is set (has been asigned a value) and false otherwise */
  public boolean isSetSubjectId() {
    return __isset_bit_vector.get(__SUBJECTID_ISSET_ID);
  }

  public void setSubjectIdIsSet(boolean value) {
    __isset_bit_vector.set(__SUBJECTID_ISSET_ID, value);
  }

  public int getBrandId() {
    return this.brandId;
  }

  public ProductSurveyQueryParam setBrandId(int brandId) {
    this.brandId = brandId;
    setBrandIdIsSet(true);

    return this;
  }

  public void unsetBrandId() {
  __isset_bit_vector.clear(__BRANDID_ISSET_ID);
  }

  /** Returns true if field brandId is set (has been asigned a value) and false otherwise */
  public boolean isSetBrandId() {
    return __isset_bit_vector.get(__BRANDID_ISSET_ID);
  }

  public void setBrandIdIsSet(boolean value) {
    __isset_bit_vector.set(__BRANDID_ISSET_ID, value);
  }

  public int getSubjectIdListSize() {
    return (this.subjectIdList == null) ? 0 : this.subjectIdList.size();
  }

  public java.util.Iterator<Integer> getSubjectIdListIterator() {
    return (this.subjectIdList == null) ? null : this.subjectIdList.iterator();
  }

  public void addToSubjectIdList(int elem) {
    if (this.subjectIdList == null) {
      this.subjectIdList = new ArrayList<Integer>();
    }
    this.subjectIdList.add(elem);
  }

  public List<Integer> getSubjectIdList() {
    return this.subjectIdList;
  }

  public ProductSurveyQueryParam setSubjectIdList(List<Integer> subjectIdList) {
    this.subjectIdList = subjectIdList;
    
    return this;
  }

  public void unsetSubjectIdList() {
    this.subjectIdList = null;
  }

  /** Returns true if field subjectIdList is set (has been asigned a value) and false otherwise */
  public boolean isSetSubjectIdList() {
    return this.subjectIdList != null;
  }

  public void setSubjectIdListIsSet(boolean value) {
    if (!value) {
      this.subjectIdList = null;
    }
  }

  public int getProductIdListSize() {
    return (this.productIdList == null) ? 0 : this.productIdList.size();
  }

  public java.util.Iterator<String> getProductIdListIterator() {
    return (this.productIdList == null) ? null : this.productIdList.iterator();
  }

  public void addToProductIdList(String elem) {
    if (this.productIdList == null) {
      this.productIdList = new ArrayList<String>();
    }
    this.productIdList.add(elem);
  }

  public List<String> getProductIdList() {
    return this.productIdList;
  }

  public ProductSurveyQueryParam setProductIdList(List<String> productIdList) {
    this.productIdList = productIdList;
    
    return this;
  }

  public void unsetProductIdList() {
    this.productIdList = null;
  }

  /** Returns true if field productIdList is set (has been asigned a value) and false otherwise */
  public boolean isSetProductIdList() {
    return this.productIdList != null;
  }

  public void setProductIdListIsSet(boolean value) {
    if (!value) {
      this.productIdList = null;
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
    case PRODUCT_ID:
      if (value == null) {
        unsetProductId();
      } else {
        setProductId((String)value);
      }
      break;
    case PRODUCT_NAME:
      if (value == null) {
        unsetProductName();
      } else {
        setProductName((String)value);
      }
      break;
    case ACTIVE_STATE:
      if (value == null) {
        unsetActiveState();
      } else {
        setActiveState((Integer)value);
      }
      break;
    case PAGINATION:
      if (value == null) {
        unsetPagination();
      } else {
        setPagination((com.jfshare.finagle.thrift.pagination.Pagination)value);
      }
      break;
    case SORT:
      if (value == null) {
        unsetSort();
      } else {
        setSort((String)value);
      }
      break;
    case SUBJECT_ID:
      if (value == null) {
        unsetSubjectId();
      } else {
        setSubjectId((Integer)value);
      }
      break;
    case BRAND_ID:
      if (value == null) {
        unsetBrandId();
      } else {
        setBrandId((Integer)value);
      }
      break;
    case SUBJECT_ID_LIST:
      if (value == null) {
        unsetSubjectIdList();
      } else {
        setSubjectIdList((List<Integer>)value);
      }
      break;
    case PRODUCT_ID_LIST:
      if (value == null) {
        unsetProductIdList();
      } else {
        setProductIdList((List<String>)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SELLER_ID:
      return new Integer(getSellerId());
    case PRODUCT_ID:
      return getProductId();
    case PRODUCT_NAME:
      return getProductName();
    case ACTIVE_STATE:
      return new Integer(getActiveState());
    case PAGINATION:
      return getPagination();
    case SORT:
      return getSort();
    case SUBJECT_ID:
      return new Integer(getSubjectId());
    case BRAND_ID:
      return new Integer(getBrandId());
    case SUBJECT_ID_LIST:
      return getSubjectIdList();
    case PRODUCT_ID_LIST:
      return getProductIdList();
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
    case PRODUCT_ID:
      return isSetProductId();
    case PRODUCT_NAME:
      return isSetProductName();
    case ACTIVE_STATE:
      return isSetActiveState();
    case PAGINATION:
      return isSetPagination();
    case SORT:
      return isSetSort();
    case SUBJECT_ID:
      return isSetSubjectId();
    case BRAND_ID:
      return isSetBrandId();
    case SUBJECT_ID_LIST:
      return isSetSubjectIdList();
    case PRODUCT_ID_LIST:
      return isSetProductIdList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProductSurveyQueryParam)
      return this.equals((ProductSurveyQueryParam)that);
    return false;
  }

  public boolean equals(ProductSurveyQueryParam that) {
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
    boolean this_present_productId = true && this.isSetProductId();
    boolean that_present_productId = true && that.isSetProductId();
    if (this_present_productId || that_present_productId) {
      if (!(this_present_productId && that_present_productId))
        return false;
      if (!this.productId.equals(that.productId))
        return false;
    }
    boolean this_present_productName = true && this.isSetProductName();
    boolean that_present_productName = true && that.isSetProductName();
    if (this_present_productName || that_present_productName) {
      if (!(this_present_productName && that_present_productName))
        return false;
      if (!this.productName.equals(that.productName))
        return false;
    }
    boolean this_present_activeState = true && this.isSetActiveState();
    boolean that_present_activeState = true && that.isSetActiveState();
    if (this_present_activeState || that_present_activeState) {
      if (!(this_present_activeState && that_present_activeState))
        return false;
      if (this.activeState != that.activeState)
        return false;
    }
    boolean this_present_pagination = true && this.isSetPagination();
    boolean that_present_pagination = true && that.isSetPagination();
    if (this_present_pagination || that_present_pagination) {
      if (!(this_present_pagination && that_present_pagination))
        return false;
      if (!this.pagination.equals(that.pagination))
        return false;
    }
    boolean this_present_sort = true && this.isSetSort();
    boolean that_present_sort = true && that.isSetSort();
    if (this_present_sort || that_present_sort) {
      if (!(this_present_sort && that_present_sort))
        return false;
      if (!this.sort.equals(that.sort))
        return false;
    }
    boolean this_present_subjectId = true && this.isSetSubjectId();
    boolean that_present_subjectId = true && that.isSetSubjectId();
    if (this_present_subjectId || that_present_subjectId) {
      if (!(this_present_subjectId && that_present_subjectId))
        return false;
      if (this.subjectId != that.subjectId)
        return false;
    }
    boolean this_present_brandId = true && this.isSetBrandId();
    boolean that_present_brandId = true && that.isSetBrandId();
    if (this_present_brandId || that_present_brandId) {
      if (!(this_present_brandId && that_present_brandId))
        return false;
      if (this.brandId != that.brandId)
        return false;
    }
    boolean this_present_subjectIdList = true && this.isSetSubjectIdList();
    boolean that_present_subjectIdList = true && that.isSetSubjectIdList();
    if (this_present_subjectIdList || that_present_subjectIdList) {
      if (!(this_present_subjectIdList && that_present_subjectIdList))
        return false;
      if (!this.subjectIdList.equals(that.subjectIdList))
        return false;
    }
    boolean this_present_productIdList = true && this.isSetProductIdList();
    boolean that_present_productIdList = true && that.isSetProductIdList();
    if (this_present_productIdList || that_present_productIdList) {
      if (!(this_present_productIdList && that_present_productIdList))
        return false;
      if (!this.productIdList.equals(that.productIdList))
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
    boolean present_productId = true && (isSetProductId());
    builder.append(present_productId);
    if (present_productId)
      builder.append(productId);
    boolean present_productName = true && (isSetProductName());
    builder.append(present_productName);
    if (present_productName)
      builder.append(productName);
    boolean present_activeState = true && (isSetActiveState());
    builder.append(present_activeState);
    if (present_activeState)
      builder.append(activeState);
    boolean present_pagination = true && (isSetPagination());
    builder.append(present_pagination);
    if (present_pagination)
      builder.append(pagination);
    boolean present_sort = true && (isSetSort());
    builder.append(present_sort);
    if (present_sort)
      builder.append(sort);
    boolean present_subjectId = true && (isSetSubjectId());
    builder.append(present_subjectId);
    if (present_subjectId)
      builder.append(subjectId);
    boolean present_brandId = true && (isSetBrandId());
    builder.append(present_brandId);
    if (present_brandId)
      builder.append(brandId);
    boolean present_subjectIdList = true && (isSetSubjectIdList());
    builder.append(present_subjectIdList);
    if (present_subjectIdList)
      builder.append(subjectIdList);
    boolean present_productIdList = true && (isSetProductIdList());
    builder.append(present_productIdList);
    if (present_productIdList)
      builder.append(productIdList);
    return builder.toHashCode();
  }

  public int compareTo(ProductSurveyQueryParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ProductSurveyQueryParam typedOther = (ProductSurveyQueryParam)other;

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
    lastComparison = Boolean.valueOf(isSetProductName()).compareTo(typedOther.isSetProductName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductName()) {
      lastComparison = TBaseHelper.compareTo(this.productName, typedOther.productName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetActiveState()).compareTo(typedOther.isSetActiveState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActiveState()) {
      lastComparison = TBaseHelper.compareTo(this.activeState, typedOther.activeState);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPagination()).compareTo(typedOther.isSetPagination());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPagination()) {
      lastComparison = TBaseHelper.compareTo(this.pagination, typedOther.pagination);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSort()).compareTo(typedOther.isSetSort());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSort()) {
      lastComparison = TBaseHelper.compareTo(this.sort, typedOther.sort);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSubjectId()).compareTo(typedOther.isSetSubjectId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubjectId()) {
      lastComparison = TBaseHelper.compareTo(this.subjectId, typedOther.subjectId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBrandId()).compareTo(typedOther.isSetBrandId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBrandId()) {
      lastComparison = TBaseHelper.compareTo(this.brandId, typedOther.brandId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSubjectIdList()).compareTo(typedOther.isSetSubjectIdList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubjectIdList()) {
      lastComparison = TBaseHelper.compareTo(this.subjectIdList, typedOther.subjectIdList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProductIdList()).compareTo(typedOther.isSetProductIdList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductIdList()) {
      lastComparison = TBaseHelper.compareTo(this.productIdList, typedOther.productIdList);
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
        case 2: // PRODUCT_ID
          if (field.type == TType.STRING) {
            this.productId = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // PRODUCT_NAME
          if (field.type == TType.STRING) {
            this.productName = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // ACTIVE_STATE
          if (field.type == TType.I32) {
            this.activeState = iprot.readI32();
            setActiveStateIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // PAGINATION
          if (field.type == TType.STRUCT) {
            this.pagination = new com.jfshare.finagle.thrift.pagination.Pagination();
            this.pagination.read(iprot);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 6: // SORT
          if (field.type == TType.STRING) {
            this.sort = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 7: // SUBJECT_ID
          if (field.type == TType.I32) {
            this.subjectId = iprot.readI32();
            setSubjectIdIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 8: // BRAND_ID
          if (field.type == TType.I32) {
            this.brandId = iprot.readI32();
            setBrandIdIsSet(true);
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 9: // SUBJECT_ID_LIST
          if (field.type == TType.LIST) {
            {
            TList _list8 = iprot.readListBegin();
            this.subjectIdList = new ArrayList<Integer>(_list8.size);
            for (int _i9 = 0; _i9 < _list8.size; ++_i9)
            {
              int _elem10;
              _elem10 = iprot.readI32();
              this.subjectIdList.add(_elem10);
            }
            iprot.readListEnd();
            }
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 10: // PRODUCT_ID_LIST
          if (field.type == TType.LIST) {
            {
            TList _list11 = iprot.readListBegin();
            this.productIdList = new ArrayList<String>(_list11.size);
            for (int _i12 = 0; _i12 < _list11.size; ++_i12)
            {
              String _elem13;
              _elem13 = iprot.readString();
              this.productIdList.add(_elem13);
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
    if (this.productId != null) {
      if (isSetProductId()) {
        oprot.writeFieldBegin(PRODUCT_ID_FIELD_DESC);
        oprot.writeString(this.productId);
        oprot.writeFieldEnd();
      }
    }
    if (this.productName != null) {
      if (isSetProductName()) {
        oprot.writeFieldBegin(PRODUCT_NAME_FIELD_DESC);
        oprot.writeString(this.productName);
        oprot.writeFieldEnd();
      }
    }
    if (isSetActiveState()) {
      oprot.writeFieldBegin(ACTIVE_STATE_FIELD_DESC);
      oprot.writeI32(this.activeState);
      oprot.writeFieldEnd();
    }
    if (this.pagination != null) {
      if (isSetPagination()) {
        oprot.writeFieldBegin(PAGINATION_FIELD_DESC);
        this.pagination.write(oprot);
        oprot.writeFieldEnd();
      }
    }
    if (this.sort != null) {
      if (isSetSort()) {
        oprot.writeFieldBegin(SORT_FIELD_DESC);
        oprot.writeString(this.sort);
        oprot.writeFieldEnd();
      }
    }
    if (isSetSubjectId()) {
      oprot.writeFieldBegin(SUBJECT_ID_FIELD_DESC);
      oprot.writeI32(this.subjectId);
      oprot.writeFieldEnd();
    }
    if (isSetBrandId()) {
      oprot.writeFieldBegin(BRAND_ID_FIELD_DESC);
      oprot.writeI32(this.brandId);
      oprot.writeFieldEnd();
    }
    if (this.subjectIdList != null) {
      if (isSetSubjectIdList()) {
        oprot.writeFieldBegin(SUBJECT_ID_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new TList(TType.I32, this.subjectIdList.size()));
          for (int _iter14 : this.subjectIdList)
          {
            oprot.writeI32(_iter14);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
    }
    if (this.productIdList != null) {
      if (isSetProductIdList()) {
        oprot.writeFieldBegin(PRODUCT_ID_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new TList(TType.STRING, this.productIdList.size()));
          for (String _iter15 : this.productIdList)
          {
            oprot.writeString(_iter15);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ProductSurveyQueryParam(");
    boolean first = true;
    sb.append("sellerId:");
    sb.append(this.sellerId);
    first = false;
    if (isSetProductId()) {
      if (!first) sb.append(", ");
      sb.append("productId:");
      if (this.productId == null) {
        sb.append("null");
      } else {
        sb.append(this.productId);
      }
      first = false;
      }
    if (isSetProductName()) {
      if (!first) sb.append(", ");
      sb.append("productName:");
      if (this.productName == null) {
        sb.append("null");
      } else {
        sb.append(this.productName);
      }
      first = false;
      }
    if (isSetActiveState()) {
      if (!first) sb.append(", ");
      sb.append("activeState:");
      sb.append(this.activeState);
      first = false;
      }
    if (isSetPagination()) {
      if (!first) sb.append(", ");
      sb.append("pagination:");
      if (this.pagination == null) {
        sb.append("null");
      } else {
        sb.append(this.pagination);
      }
      first = false;
      }
    if (isSetSort()) {
      if (!first) sb.append(", ");
      sb.append("sort:");
      if (this.sort == null) {
        sb.append("null");
      } else {
        sb.append(this.sort);
      }
      first = false;
      }
    if (isSetSubjectId()) {
      if (!first) sb.append(", ");
      sb.append("subjectId:");
      sb.append(this.subjectId);
      first = false;
      }
    if (isSetBrandId()) {
      if (!first) sb.append(", ");
      sb.append("brandId:");
      sb.append(this.brandId);
      first = false;
      }
    if (isSetSubjectIdList()) {
      if (!first) sb.append(", ");
      sb.append("subjectIdList:");
      if (this.subjectIdList == null) {
        sb.append("null");
      } else {
        sb.append(this.subjectIdList);
      }
      first = false;
      }
    if (isSetProductIdList()) {
      if (!first) sb.append(", ");
      sb.append("productIdList:");
      if (this.productIdList == null) {
        sb.append("null");
      } else {
        sb.append(this.productIdList);
      }
      first = false;
      }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}
