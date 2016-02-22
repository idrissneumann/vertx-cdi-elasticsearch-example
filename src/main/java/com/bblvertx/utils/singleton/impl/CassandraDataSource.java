package com.bblvertx.utils.singleton.impl;

import static com.bblvertx.SeConstants.APP_CONFIG_FILE;
import static com.bblvertx.SeConstants.CASS_BASE_DELAY_MS;
import static com.bblvertx.SeConstants.CASS_FETCH_SIZE;
import static com.bblvertx.SeConstants.CASS_HOST;
import static com.bblvertx.SeConstants.CASS_KEYSPACE;
import static com.bblvertx.SeConstants.CASS_MAX_DELAY_MS;
import static com.bblvertx.SeConstants.CASS_PASSWD;
import static com.bblvertx.SeConstants.CASS_PORT;
import static com.bblvertx.SeConstants.CASS_USERNAME;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import com.bblvertx.exception.TechnicalException;
import com.bblvertx.persistence.QueryParam;
import com.bblvertx.persistence.RowMapper;
import com.bblvertx.utils.singleton.SeDataSource;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Datasource for cassandra connection singleton.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
@Singleton
public class CassandraDataSource implements SeDataSource {
  private static final Logger LOGGER = LogManager.getLogger(CassandraDataSource.class);

  private Cluster cluster;
  private Session session;
  private String KEYSPACE;
  private Integer FETCH_SIZE;

  @Inject
  private PropertyReader prop;

  /**
   * {@inheritDoc}
   */
  @Override
  @PostConstruct
  public void init() throws TechnicalException {
    try {
      Integer port = prop.getInt(APP_CONFIG_FILE, CASS_PORT);
      String host = prop.get(APP_CONFIG_FILE, CASS_HOST);
      String username = prop.get(APP_CONFIG_FILE, CASS_USERNAME);
      String password = prop.get(APP_CONFIG_FILE, CASS_PASSWD);
      Long baseDelayMs = prop.getLong(APP_CONFIG_FILE, CASS_BASE_DELAY_MS);
      Long maxDelayMs = prop.getLong(APP_CONFIG_FILE, CASS_MAX_DELAY_MS);
      KEYSPACE = prop.get(APP_CONFIG_FILE, CASS_KEYSPACE);
      FETCH_SIZE = prop.getInt(APP_CONFIG_FILE, CASS_FETCH_SIZE);

      InetSocketAddress address = new InetSocketAddress(host, port);

      LOGGER.debug("Instantiate a SimpleClient connecting to {}", address);
      LOGGER.info("Connecting...");

      cluster = Cluster.builder() //
          .addContactPointsWithPorts(Arrays.asList(address)) //
          .withCredentials(username, password) //
          .withReconnectionPolicy(new ExponentialReconnectionPolicy(baseDelayMs, maxDelayMs)) //
          .build();

      Metadata metadata = cluster.getMetadata();
      LOGGER.debug("Connected to cluster: {}", metadata.getClusterName());

      for (Host h : metadata.getAllHosts()) {
        LOGGER.debug("Datacenter: {}; Host: {}; Rack: {}", h.getDatacenter(), h.getAddress(),
            h.getRack());
      }

      session = cluster.connect();
      LOGGER.info("Connected");
    } catch (IOException e) {
      throw new TechnicalException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T extends Serializable> List<T> execute(String query, RowMapper<T> rowMapper) {
    usingKeySpace(KEYSPACE);
    ResultSet rs = execute(query);

    List<T> rtn = new ArrayList<T>();
    for (Row row : rs) {
      rtn.add(rowMapper.map(row));
    }

    LOGGER.info("End " + query);
    return rtn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T extends Serializable> List<T> execute(String query, List<QueryParam> params,
      RowMapper<T> rowMapper) {
    usingKeySpace(KEYSPACE);

    Object[] arrayParams = null;
    if (isNotEmpty(params)) {
      Collections.sort(params);
      arrayParams = new Object[params.size()];
      Integer i = 0;

      for (QueryParam p : params) {
        arrayParams[i] = p.getValue();
        i++;
      }
    }

    ResultSet rs = (null == arrayParams) ? execute(query) : execute(query, arrayParams);

    List<T> rtn = new ArrayList<T>();
    for (Row row : rs) {
      rtn.add(rowMapper.map(row));
    }

    LOGGER.info("End " + query);
    return rtn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer executeUpdate(String query) {
    usingKeySpace(KEYSPACE);
    return execute(query).getAvailableWithoutFetching();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer executeUpdate(String query, List<QueryParam> params) {
    usingKeySpace(KEYSPACE);

    Object[] arrayParams = null;
    if (isNotEmpty(params)) {
      Collections.sort(params);
      arrayParams = new Object[params.size()];
      Integer i = 0;

      for (QueryParam p : params) {
        arrayParams[i] = p.getValue();
        i++;
      }
    }

    ResultSet rs = (null == arrayParams) ? execute(query) : execute(query, arrayParams);
    return rs.getAvailableWithoutFetching();
  }

  /**
   * Execute without params.
   * 
   * @param query
   * @param params
   * @return
   */
  private ResultSet execute(String query) {
    return session.execute(query);
  }

  /**
   * Execute with params.
   * 
   * @param query
   * @param params
   * @return
   */
  private ResultSet execute(String query, Object... params) {
    final BoundStatement bind = getBoundStatement(query, params);
    return session.execute(bind);
  }

  /**
   * Prepare query with params.
   * 
   * @param query
   * @param params
   * @return BoundStatement
   */
  private BoundStatement getBoundStatement(String query, Object... params) {
    final PreparedStatement statement = session.prepare(query);
    return (BoundStatement) statement.bind(params).setFetchSize(FETCH_SIZE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeStoredProcedure(String name) {
    usingKeySpace(KEYSPACE);
  }

  /**
   * Usekeyspace.
   * 
   * @param keyspace
   */
  private void usingKeySpace(String keyspace) {
    LOGGER.debug("setting keyspace to {}", keyspace);
    session.execute("use " + keyspace + ";");
  }

  /**
   * Disconnect.
   */
  public void disconnect() {
    LOGGER.debug("disconnecting...");
    session.close();
    cluster.close();
    LOGGER.info("disconnected");
  }
}