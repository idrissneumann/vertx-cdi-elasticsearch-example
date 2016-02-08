package com.bblvertx.persistence.mapper;

import static com.bblvertx.utils.singleton.SeDataSource.getIntFromRS;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bblvertx.persistence.RowMapper;

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
  public Integer map(ResultSet rs) throws SQLException {
    return getIntFromRS(rs, "t_user_id");
  }
}
