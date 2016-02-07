package com.bblvertx.indexation.adapter.impl;

import static com.bblvertx.SeConstants.DELETE_RSSEARCH;
import static com.bblvertx.SeConstants.ES_INDEX_USER;
import static com.bblvertx.SeConstants.SELECT_USER;
import static com.bblvertx.SeConstants.SELECT_USER_FLAG;
import static com.bblvertx.SeConstants.SQL_CONFIG_FILE;
import static com.bblvertx.SeConstants.UPDATE_RSSEARCH;

import java.io.IOException;

import com.bblvertx.exception.TechnicalException;
import com.bblvertx.indexation.adapter.AbstractIndexationDeltaAdapter;
import com.bblvertx.persistence.mapper.UserMapper;
import com.bblvertx.persistence.mapper.IdMapper;
import com.bblvertx.pojo.vo.UserVO;
import com.bblvertx.utils.singleton.RouteContext;

/**
 * Implémentation de l'adapter pour les utilisateurs du chat.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class UserIndexationDeltaAdapter extends AbstractIndexationDeltaAdapter<UserVO> {
	/**
	 * Constructeur.
	 * 
	 * @param ctx
	 */
	public UserIndexationDeltaAdapter(RouteContext ctx) {
		super(ctx);
		rowMapper = new UserMapper();
		rowMapperId = new IdMapper();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSQLSelectFlagIdx() {
		try {
			return ctx.getProp().get(SQL_CONFIG_FILE, SELECT_USER_FLAG);
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSQLDeleteRsSearch() {
		try {
			return ctx.getProp().get(SQL_CONFIG_FILE, DELETE_RSSEARCH);
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSQLUpdateRsSearch() {
		try {
			return ctx.getProp().get(SQL_CONFIG_FILE, UPDATE_RSSEARCH);
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSQLSelectValueObject() {
		try {
			return ctx.getProp().get(SQL_CONFIG_FILE, SELECT_USER);
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIndexName() {
		return ES_INDEX_USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIndexType() {
		return UserVO.class.getSimpleName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getValueObjectClass() {
		return UserVO.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLanguage(UserVO valueObject) {
		return valueObject.getLng();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId(UserVO valueObject) {
		return valueObject.getId();
	}
}
