package com.bblvertx.persistence.mapper.jdbc;

import static com.bblvertx.utils.singleton.impl.JdbcDataSource.getIntFromRS;

import com.bblvertx.exception.TechnicalException;
import com.bblvertx.persistence.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * COUNT mapper.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class CountMapper implements RowMapper<Integer> {
  /**
   * {@inheritDoc}
   */
  @Override
  public Integer map(Object rs) {
    try {
      return getIntFromRS((ResultSet) rs, "COUNT");
    } catch (SQLException e) {
      throw new TechnicalException(e);
    }
  }
}
