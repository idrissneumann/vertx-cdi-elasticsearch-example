package com.bblvertx.route;

import static com.bblvertx.SeConstants.APP_CONFIG_FILE;
import static com.bblvertx.SeConstants.DB_KEY_PAGINATION;
import static com.bblvertx.SeConstants.MSG_BAD_REQUEST;
import static com.bblvertx.SeConstants.RS_TO_STAY;
import static com.bblvertx.SeConstants.RS_TO_UPDATE;
import static com.bblvertx.utils.CommonUtils.assertParamNotEmpty;
import static com.bblvertx.utils.JSONUtils.objectTojsonQuietly;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import com.bblvertx.exception.TechnicalException;
import com.bblvertx.indexation.adapter.jdbc.IndexingAdapter;
import com.bblvertx.persistence.QueryParam;
import com.bblvertx.persistence.QueryParamBuilder;
import com.bblvertx.persistence.RowMapper;
import com.bblvertx.utils.JSONUtils;
import com.bblvertx.utils.singleton.impl.RouteContext;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * Generic impl of single mode indexing route.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 * 
 * @param <T> value object's type
 *
 */
public abstract class AbstractJdbcIndexingSingleRoute<T extends Serializable>
    extends AbstractJdbcIndexingRoute {
  /**
   * Adapter pour les spécificités de chaque indexation.
   */
  protected IndexingAdapter<T> adapter;

  /**
   * Constructor.
   * 
   * @param url
   * @param contentType
   * @param router
   * @param ctx
   */
  public AbstractJdbcIndexingSingleRoute(String url,
      String contentType,
      Router router,
      RouteContext ctx) {
    super(url, contentType, router, ctx);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void proceedAsync(HttpServerRequest request, HttpServerResponse response) {
    String strId = request.getParam("id");
    assertParamNotEmpty(strId, String.format(MSG_BAD_REQUEST, "id"));

    Integer id = (StringUtils.isNumeric(strId)) ? Integer.valueOf(strId) : null;
    assertParamNotEmpty(id, String.format(MSG_BAD_REQUEST, "id"));

    indexingNewData(id);
  }

  /**
   * Indexing new data (id = ... and rs search = 1)
   */
  private void indexingNewData(Integer id) {
    try {
      String sql = adapter.getSQLSelectValueObject();
      String sqlUpdate = adapter.getSQLUpdateRsSearch();
      Integer limit = Integer.valueOf(ctx.getProp().get(APP_CONFIG_FILE, DB_KEY_PAGINATION));
      Integer numRow = 0;
      List<T> lstResults = null;
      RowMapper<T> mapper = adapter.getMapper();

      QueryParam pId = new QueryParamBuilder() //
          .add("order", 1, Integer.class) //
          .add("value", id, Object.class) //
          .add("clazz", Integer.class, Class.class) //
          .getParam();

      QueryParam pRsSearch = new QueryParamBuilder() //
          .add("order", 2, Integer.class) //
          .add("value", RS_TO_UPDATE, Object.class) //
          .add("clazz", Integer.class, Class.class) //
          .getParam();

      QueryParam pLimit = new QueryParamBuilder() //
          .add("order", 3, Integer.class) //
          .add("value", limit, Object.class) //
          .add("clazz", Integer.class, Class.class) //
          .getParam();

      QueryParam pOffset = null;
      StringJoiner idElems = new StringJoiner(",");

      do {
        pOffset = new QueryParamBuilder() //
            .add("order", 4, Integer.class) //
            .add("value", numRow, Object.class) //
            .add("clazz", Integer.class, Class.class) //
            .getParam();

        lstResults = ctx.getDataSource().execute(sql,
            pId.asList(pRsSearch.asList(pLimit.asList(pOffset.asList()))), mapper);

        if (isNotEmpty(lstResults)) {
          for (T elem : lstResults) {
            ctx.getEsClient() //
                .getClient() //
                .prepareIndex(adapter.getIndexName(), adapter.getIndexType(), adapter.getId(elem)) //
                .setSource(objectTojsonQuietly(elem, adapter.getValueObjectClass())) //
                .execute() //
                .actionGet();

            idElems.add(String.format(ID_TPL, adapter.getId(elem)));
          }
        }

        numRow += limit;
      } while (isNotEmpty(lstResults));

      if (idElems.length() > 0) {
        QueryParam pRsSearch2 = new QueryParamBuilder() //
            .add("order", 1, Integer.class) //
            .add("value", RS_TO_STAY, Object.class) //
            .add("clazz", Integer.class, Class.class) //
            .getParam();

        ctx.getDataSource().executeUpdate(String.format(sqlUpdate, idElems.toString()),
            pRsSearch2.asList());
      }
    } catch (Exception e) {
      throw new TechnicalException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String proceed(HttpServerRequest request, HttpServerResponse response) {
    return JSONUtils.objectTojsonQuietly("Indexation en cours...", String.class);
  }

  /**
   * @return the adapter
   */
  public IndexingAdapter<T> getAdapter() {
    return adapter;
  }

  /**
   * @param adapter the adapter to set
   */
  public void setAdapter(IndexingAdapter<T> adapter) {
    this.adapter = adapter;
  }
}