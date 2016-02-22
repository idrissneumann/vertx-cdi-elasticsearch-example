package com.bblvertx.utils.singleton.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Route singleton context.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
@Singleton
public class RouteContext {
  @Inject
  private JdbcDataSource dataSource;

  @Inject
  private PropertyReader prop;

  @Inject
  private ESClient esClient;

  /**
   * @return the dataSource
   */
  public JdbcDataSource getDataSource() {
    return dataSource;
  }

  /**
   * @param dataSource the dataSource to set
   */
  public void setDataSource(JdbcDataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * @return the prop
   */
  public PropertyReader getProp() {
    return prop;
  }

  /**
   * @param prop the prop to set
   */
  public void setProp(PropertyReader prop) {
    this.prop = prop;
  }

  /**
   * @return the esClient
   */
  public ESClient getEsClient() {
    return esClient;
  }

  /**
   * @param esClient the esClient to set
   */
  public void setEsClient(ESClient esClient) {
    this.esClient = esClient;
  }
}
