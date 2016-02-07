package com.bblvertx.persistence;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface de mapping entre les données en base et les VO.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 * @param <T>
 */
public interface RowMapper<T extends Serializable> {
	/**
	 * Mapper une ligne en base.
	 * 
	 * @param rs
	 * @return T
	 * @throws SQLException
	 */
	T map(ResultSet rs) throws SQLException;
}
