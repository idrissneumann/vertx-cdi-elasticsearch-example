package com.bblvertx.persistence.mapper;

import static com.bblvertx.utils.singleton.SeDataSource.getIntFromRS;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bblvertx.persistence.RowMapper;

/**
 * Mapper pour récupérer l'id profil.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class IdMapper implements RowMapper<Integer> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer map(ResultSet rs) throws SQLException {
		return getIntFromRS(rs, "t_profil_id");
	}
}
