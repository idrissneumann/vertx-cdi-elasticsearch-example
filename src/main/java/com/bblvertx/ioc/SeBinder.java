package com.bblvertx.ioc;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.bblvertx.utils.singleton.ESClient;
import com.bblvertx.utils.singleton.PropertyReader;
import com.bblvertx.utils.singleton.RouteContext;
import com.bblvertx.utils.singleton.SeDataSource;

/**
 * Init of lifecycle beans context.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class SeBinder extends AbstractBinder {
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configure() {
    bind(PropertyReader.class).to(PropertyReader.class).in(Singleton.class);

    bind(SeDataSource.class).to(SeDataSource.class).in(Singleton.class);

    bind(ESClient.class).to(ESClient.class).in(Singleton.class);

    bind(RouteContext.class).to(RouteContext.class).in(Singleton.class);
  }
}
