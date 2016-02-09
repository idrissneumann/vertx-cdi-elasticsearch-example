package com.bblvertx.utils.singleton;

import static com.bblvertx.SeConstants.APP_CONFIG_FILE;
import static com.bblvertx.SeConstants.DB_KEY_DRIVER;
import static com.bblvertx.SeConstants.DB_KEY_MAX_POOL_SIZE;
import static com.bblvertx.SeConstants.DB_KEY_MIN_POOL_SIZE;
import static com.bblvertx.SeConstants.DB_KEY_PASSWD;
import static com.bblvertx.SeConstants.DB_KEY_URL;
import static com.bblvertx.SeConstants.DB_KEY_USERNAME;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.io.IOException;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bblvertx.persistence.QueryParam;
import com.bblvertx.persistence.RowMapper;

/**
 * Datasource singleton.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
@Singleton
public class SeDataSource {
  private static final Logger LOGGER = LogManager.getLogger(SeDataSource.class);

  @Inject
  private PropertyReader prop;

  private BasicDataSource dataSource;

  /**
   * Initializing connection with the database.
   * 
   * @throws IOException
   */
  @PostConstruct
  public void init() throws IOException {
    dataSource = new BasicDataSource();
    dataSource.setDriverClassName(prop.get(APP_CONFIG_FILE, DB_KEY_DRIVER));
    dataSource.setUrl(prop.get(APP_CONFIG_FILE, DB_KEY_URL));
    dataSource.setUsername(prop.get(APP_CONFIG_FILE, DB_KEY_USERNAME));
    dataSource.setPassword(prop.get(APP_CONFIG_FILE, DB_KEY_PASSWD));
    dataSource.setMinIdle(prop.getInt(APP_CONFIG_FILE, DB_KEY_MIN_POOL_SIZE));
    dataSource.setMaxIdle(prop.getInt(APP_CONFIG_FILE, DB_KEY_MIN_POOL_SIZE));
    dataSource.setMaxTotal(prop.getInt(APP_CONFIG_FILE, DB_KEY_MAX_POOL_SIZE));
    dataSource.setPoolPreparedStatements(true);
  }

  /**
   * Fetching the result of a SQL query with a row mapper.
   * 
   * @param query
   * @return
   * @throws SQLException
   */
  public <T extends Serializable> List<T> execute(String query, RowMapper<T> rowMapper)
      throws SQLException {
    LOGGER.info("Launching " + query);
    Connection co = getDataSource().getConnection();
    Statement stmt = co.createStatement();
    ResultSet rs = stmt.executeQuery(query);

    List<T> rtn = new ArrayList<T>();

    while (rs.next()) {
      rtn.add(rowMapper.map(rs));
    }

    rs.close();
    stmt.close();
    co.close();

    LOGGER.info("End " + query);
    return rtn;
  }

  /**
   * Fetching the result of a query with parameters and a row mapper.
   * 
   * @param query
   * @return
   * @throws SQLException
   */
  public <T extends Serializable> List<T> execute(String query, List<QueryParam> params,
      RowMapper<T> rowMapper) throws SQLException {
    LOGGER.info("Launching " + query);
    Connection co = getDataSource().getConnection();

    PreparedStatement stmt = co.prepareStatement(query);
    if (isNotEmpty(params)) {
      Collections.sort(params);
      for (QueryParam p : params) {
        LOGGER.info(p.toString());
        stmt.setObject(p.getOrder(), p.getClazz().cast(p.getValue()));
      }
    }

    ResultSet rs = stmt.executeQuery();

    List<T> rtn = new ArrayList<T>();

    while (rs.next()) {
      rtn.add(rowMapper.map(rs));
    }

    rs.close();
    stmt.close();
    co.close();

    LOGGER.info("End " + query);
    return rtn;
  }

  /**
   * executing SQL query and return the number of updates.
   * 
   * @param query
   * @return Integer
   * @throws SQLException
   */
  public Integer executeUpdate(String query) throws SQLException {
    LOGGER.info("Launching " + query);
    Connection co = getDataSource().getConnection();
    Statement stmt = co.createStatement();
    Integer rtn = stmt.executeUpdate(query);
    stmt.close();
    co.close();
    LOGGER.info("End " + query);
    return rtn;
  }

  /**
   * executing SQL query with params and return the number of updates.
   * 
   * @param query
   * @param params
   * @return
   * @throws SQLException
   */
  public Integer executeUpdate(String query, List<QueryParam> params) throws SQLException {
    LOGGER.info("Launching " + query);
    Connection co = getDataSource().getConnection();
    PreparedStatement stmt = co.prepareStatement(query);
    if (isNotEmpty(params)) {
      Collections.sort(params);
      for (QueryParam p : params) {
        stmt.setObject(p.getOrder(), p.getClazz().cast(p.getValue()));
        LOGGER.info(p.toString());
      }
    }

    Integer rtn = stmt.executeUpdate();
    stmt.close();
    co.close();

    LOGGER.info("End " + query);
    return rtn;
  }

  /**
   * Launching stored proc.
   * 
   * @param name
   * @throws SQLException
   */
  public void executeStoredProcedure(String name) throws SQLException {
    LOGGER.info("Launching stored proc " + name);
    Connection co = getDataSource().getConnection();
    CallableStatement proc = co.prepareCall("{ call " + name + "() }");
    proc.execute();
    proc.close();
    co.close();
    LOGGER.info("End stored proc " + name);
  }

  /**
   * Getting a rs value as int.
   * 
   * @param rs
   * @param name
   * @return Integer
   * @throws SQLException
   */
  public static Integer getIntFromRS(ResultSet rs, String name) throws SQLException {
    return rs.getInt(name);
  }

  /**
   * Getting a rs value as long.
   * 
   * @param rs
   * @param name
   * @return Integer
   * @throws SQLException
   */
  public static Long getLongFromRS(ResultSet rs, String name) throws SQLException {
    return rs.getLong(name);
  }

  /**
   * Getting a rs value as string.
   * 
   * @param rs
   * @param name
   * @return String
   * @throws SQLException
   */
  public static String getStringFromRS(ResultSet rs, String name) throws SQLException {
    return rs.getString(name);
  }

  /**
   * Getting a rs value as calendar.
   * 
   * @param rs
   * @param name
   * @return Calendar
   * @throws SQLException
   */
  public static Calendar getDateFromRS(ResultSet rs, String name) throws SQLException {
    Calendar cal = (Calendar) Calendar.getInstance().clone();
    cal.setTime(rs.getDate(name));
    return cal;
  }

  /**
   * Getting the datasource.
   * 
   * @return DataSource
   */
  public DataSource getDataSource() {
    return dataSource;
  }
}
