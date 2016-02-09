package com.bblvertx.route;

import static com.bblvertx.SeConstants.APP_CONFIG_FILE;
import static com.bblvertx.SeConstants.DB_KEY_PAGINATION;
import static com.bblvertx.SeConstants.MSG_BAD_REQUEST;
import static com.bblvertx.SeConstants.RS_TO_STAY;
import static com.bblvertx.SeConstants.RS_TO_UPDATE;
import static com.bblvertx.utils.CommonUtils.assertParamNotEmpty;
import static com.bblvertx.utils.JSONUtils.objectTojsonQuietly;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

import com.bblvertx.exception.TechnicalException;
import com.bblvertx.indexation.adapter.IndexingAdapter;
import com.bblvertx.persistence.QueryParam;
import com.bblvertx.persistence.QueryParamBuilder;
import com.bblvertx.persistence.RowMapper;
import com.bblvertx.utils.JSONUtils;
import com.bblvertx.utils.singleton.RouteContext;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * Classe abstraite pour les indexations en mode Single.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 * 
 * @param <T> type de l'objet valeur
 *
 */
public abstract class AbstractIndexingSingleRoute<T extends Serializable>
    extends AbstractIndexingRoute {
  /**
   * Adapter pour les spécificités de chaque indexation.
   */
  protected IndexingAdapter<T> adapter;

  /**
   * Constructeur.
   * 
   * @param url
   * @param contentType
   * @param router
   * @param ctx
   */
  public AbstractIndexingSingleRoute(String url, String contentType, Router router,
      RouteContext ctx) {
    super(url, contentType, router, ctx);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void proceedAsync(HttpServerRequest request, HttpServerResponse response) {
    String id = request.getParam("id");
    assertParamNotEmpty(id, String.format(MSG_BAD_REQUEST, "id"));

    indexationNewData(id);
  }

  /**
   * Indexation des nouvelles données.
   */
  private void indexationNewData(String id) {
    try {
      String sql = adapter.getSQLSelectValueObject();
      String sqlUpdate = adapter.getSQLUpdateRsSearch();
      Integer limit = Integer.valueOf(ctx.getProp().get(APP_CONFIG_FILE, DB_KEY_PAGINATION));
      Integer numRow = 0;
      List<T> lstResults = null;
      RowMapper<T> mapper = adapter.getMapper();

      // Paramètres
      QueryParam pId = new QueryParamBuilder() //
          .add("order", 1, Integer.class) //
          .add("value", id, Object.class) //
          .add("clazz", String.class, Class.class) //
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
