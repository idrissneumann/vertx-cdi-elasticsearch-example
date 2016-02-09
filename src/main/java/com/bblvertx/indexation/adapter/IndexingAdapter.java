package com.bblvertx.indexation.adapter;

import java.io.Serializable;

import com.bblvertx.persistence.RowMapper;
import com.bblvertx.utils.singleton.RouteContext;

/**
 * Indexing adapter interface.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 * 
 * @param <T> value object's type
 */
public interface IndexingAdapter<T extends Serializable> {
  /**
   * Getting bean context.
   * 
   * @return RouteContext
   */
  RouteContext getRouteContext();

  /**
   * Getting "update rs search" SQL query.
   * 
   * @return the query
   */
  String getSQLUpdateRsSearch();

  /**
   * Getting "select value object" SQL query.
   * 
   * @return the query
   */
  String getSQLSelectValueObject();

  /**
   * Getting index name.
   * 
   * @return the name
   */
  String getIndexName();

  /**
   * Getting index type.
   * 
   * @return the type
   */
  String getIndexType();

  /**
   * Getting the value object class.
   * 
   * @return the class
   */
  Class<?> getValueObjectClass();

  /**
   * Getting the id of value object.
   * 
   * @param valueObject
   * @return the id
   */
  String getId(T valueObject);

  /**
   * Getting a row mapper for the value object.
   * 
   * @return the row mapper
   */
  RowMapper<T> getMapper();
}
