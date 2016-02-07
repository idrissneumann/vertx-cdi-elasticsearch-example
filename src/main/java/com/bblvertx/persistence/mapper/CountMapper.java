package com.bblvertx.persistence.mapper;

import static com.bblvertx.utils.singleton.SeDataSource.getIntFromRS;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bblvertx.persistence.RowMapper;

/**
 * Mapper le résultat d'une requête COUNT.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class CountMapper implements RowMapper<Integer> {
	/**
	 * {@inheritDoc}
	 * 
	 * @throws SQLException
	 */
	@Override
	public Integer map(ResultSet rs) throws SQLException {
		return getIntFromRS(rs, "COUNT");
	}
}
