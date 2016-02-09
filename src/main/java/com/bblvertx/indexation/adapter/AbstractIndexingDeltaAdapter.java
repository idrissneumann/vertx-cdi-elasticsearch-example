package com.bblvertx.indexation.adapter;

import java.io.Serializable;

import com.bblvertx.persistence.RowMapper;
import com.bblvertx.utils.singleton.RouteContext;

/**
 * Abstract delta indexing adapter.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 * 
 * @param <T> value object type
 *
 */
public abstract class AbstractIndexingDeltaAdapter<T extends Serializable>
    extends AbstractIndexingAdapter<T> implements IndexingDeltaAdapter<T> {
  protected RowMapper<Integer> rowMapperId;

  /**
   * Constructor.
   * 
   * @param ctx
   */
  public AbstractIndexingDeltaAdapter(RouteContext ctx) {
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
