package com.bblvertx.indexation.adapter;

import java.io.Serializable;

import com.bblvertx.persistence.RowMapper;
import com.bblvertx.utils.singleton.RouteContext;

/**
 * Abstract adapter pour les indexations.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 * @param <T>
 */
public abstract class AbstractIndexationAdapter<T extends Serializable> implements IndexationAdapter<T> {
	protected RouteContext ctx;

	protected RowMapper<T> rowMapper;

	/**
	 * Constructeur.
	 * 
	 * @param ctx
	 */
	public AbstractIndexationAdapter(RouteContext ctx) {
		this.ctx = ctx;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RouteContext getRouteContext() {
		return ctx;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RowMapper<T> getMapper() {
		return rowMapper;
	}
}
