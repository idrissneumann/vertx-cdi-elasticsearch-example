package com.bblvertx.persistence.mapper;

import static com.bblvertx.utils.singleton.SeDataSource.getDateFromRS;
import static com.bblvertx.utils.singleton.SeDataSource.getIntFromRS;
import static com.bblvertx.utils.singleton.SeDataSource.getLongFromRS;
import static com.bblvertx.utils.singleton.SeDataSource.getStringFromRS;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bblvertx.persistence.RowMapper;
import com.bblvertx.pojo.vo.UserVO;

/**
 * Row mapper pour les utilisateurs du chat.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class UserMapper implements RowMapper<UserVO> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserVO map(ResultSet rs) throws SQLException {
		UserVO rtn = new UserVO();
		rtn.setDateConnect(getDateFromRS(rs, "date_connect"));
		rtn.setDateUpdate(getDateFromRS(rs, "date_update"));
		rtn.setId(getStringFromRS(rs, "id"));
		rtn.setNom(getStringFromRS(rs, "nom"));
		rtn.setPrenom(getStringFromRS(rs, "prenom"));
		rtn.setEmail(getStringFromRS(rs, "email"));
		rtn.setLng(getStringFromRS(rs, "lng"));
		rtn.setRsSearch(getIntFromRS(rs, "rs_search"));
		rtn.setIdPhotoProfil(getLongFromRS(rs, "id_photo_profil"));
		return rtn;
	}

}
