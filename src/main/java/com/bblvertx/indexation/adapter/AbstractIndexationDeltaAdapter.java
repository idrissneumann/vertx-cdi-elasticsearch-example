package com.bblvertx.indexation.adapter;

import java.io.Serializable;

import com.bblvertx.persistence.RowMapper;
import com.bblvertx.utils.singleton.RouteContext;

/**
 * Abstract pour les adapters des jobs d'indexation en delta.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 * 
 * @param <T>
 *            value object type
 *
 */
public abstract class AbstractIndexationDeltaAdapter<T extends Serializable> extends AbstractIndexationAdapter<T> implements IndexationDeltaAdapter<T> {
	protected RowMapper<Integer> rowMapperId;

	/**
	 * Constructeur.
	 * 
	 * @param ctx
	 */
	public AbstractIndexationDeltaAdapter(RouteContext ctx) {
		super(ctx);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RowMapper<Integer> getIdMapper() {
		return rowMapperId;
	}
}
