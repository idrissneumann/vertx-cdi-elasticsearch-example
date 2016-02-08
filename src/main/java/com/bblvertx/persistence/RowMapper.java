package com.bblvertx.persistence;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping between database and VO.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 * @param <T>
 */
public interface RowMapper<T extends Serializable> {
  /**
   * Mapping a row in resultset.
   * 
   * @param rs
   * @return T
   * @throws SQLException
   */
  T map(ResultSet rs) throws SQLException;
}
