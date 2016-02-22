package com.bblvertx.persistence.mapper.jdbc;

import static com.bblvertx.utils.singleton.impl.JdbcDataSource.getIntFromRS;

import com.bblvertx.exception.TechnicalException;
import com.bblvertx.persistence.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User id mapper.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class UserIdMapper implements RowMapper<Integer> {
  /**
   * {@inheritDoc}
   */
  @Override
  public Integer map(Object rs) {
    try {
      return getIntFromRS((ResultSet) rs, "t_user_id");
    } catch (SQLException e) {
      throw new TechnicalException(e);
    }
  }
}
