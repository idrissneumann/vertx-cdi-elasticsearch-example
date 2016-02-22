package com.bblvertx.indexation.adapter.jdbc;

import java.io.Serializable;

import com.bblvertx.persistence.RowMapper;

/**
 * Indexing delta job adapter.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 * 
 * @param <T> value object's type.
 *
 */
public interface IndexingDeltaAdapter<T extends Serializable> extends IndexingAdapter<T> {
  /**
   * Getting the "select flag" SQL query.
   * 
   * @return the query
   */
  String getSQLSelectFlagIdx();

  /**
   * Getting the "delete rs search" query.
   * 
   * @return the query
   */
  String getSQLDeleteRsSearch();

  /**
   * Getting a mapper for the id.
   * 
   * @return RowMapper<Integer>
   */
  RowMapper<Integer> getIdMapper();
}
