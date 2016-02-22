package com.bblvertx.persistence.mapper;

import static com.bblvertx.utils.singleton.impl.CassandraDataSource.getIntFromRS;

import com.bblvertx.persistence.RowMapper;
import com.datastax.driver.core.Row;

/**
 * User id mapper in cassandra.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class CassandraUserIdMapper implements RowMapper<Integer> {
  /**
   * {@inheritDoc}
   */
  @Override
  public Integer map(Object rs) {
    return getIntFromRS((Row) rs, "id");
  }
}
