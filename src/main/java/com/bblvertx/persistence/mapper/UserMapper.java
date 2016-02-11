package com.bblvertx.persistence.mapper;

import static com.bblvertx.utils.singleton.SeDataSource.getDateFromRS;
import static com.bblvertx.utils.singleton.SeDataSource.getIntFromRS;
import static com.bblvertx.utils.singleton.SeDataSource.getStringFromRS;

import com.bblvertx.persistence.RowMapper;
import com.bblvertx.pojo.vo.UserVO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Row mapper for chat users.
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
    rtn.setName(getStringFromRS(rs, "name"));
    rtn.setFirstname(getStringFromRS(rs, "firstname"));
    rtn.setEmail(getStringFromRS(rs, "email"));
    rtn.setSkill(getStringFromRS(rs, "skill"));
    rtn.setRsSearch(getIntFromRS(rs, "rs_search"));
    return rtn;
  }
}
