package com.bblvertx.route;

import static com.bblvertx.SeConstants.APP_CONFIG_FILE;
import static com.bblvertx.SeConstants.DB_KEY_PAGINATION;
import static com.bblvertx.SeConstants.RESPONSE_HTML_TEMPLATE;
import static com.bblvertx.SeConstants.RS_TO_DELETE;
import static com.bblvertx.SeConstants.RS_TO_STAY;
import static com.bblvertx.SeConstants.RS_TO_UPDATE;
import static com.bblvertx.utils.JSONUtils.objectTojsonQuietly;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

import com.bblvertx.exception.TechnicalException;
import com.bblvertx.indexation.adapter.IndexingDeltaAdapter;
import com.bblvertx.persistence.QueryParam;
import com.bblvertx.persistence.QueryParamBuilder;
import com.bblvertx.persistence.RowMapper;
import com.bblvertx.utils.singleton.RouteContext;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * Implémentation générique d'une indexation en delta.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 * @param <T> type de l'objet valeur
 */
public abstract class AbstractIndexingDeltaRoute<T extends Serializable>
    extends AbstractIndexingRoute {
  /**
   * Adapter pour les spécificités de chaque indexation.
   */
  protected IndexingDeltaAdapter<T> adapter;

  /**
   * Constructeur.
   * 
   * @param url
   * @param contentType
   * @param router
   * @param ctx
   */
  public AbstractIndexingDeltaRoute(String url, String contentType, Router router,
      RouteContext ctx) {
    super(url, contentType, router, ctx);
  }

  /**
   * Purge des anciennes données.
   */
  private void purgeOldData() {
    try {
      String sql = adapter.getSQLSelectFlagIdx();
      String sqlDelete = adapter.getSQLDeleteRsSearch();
      Integer limit = Integer.valueOf(ctx.getProp().get(APP_CONFIG_FILE, DB_KEY_PAGINATION));
      Integer numRow = 0;
      List<Integer> lstResults = null;

      // Paramètres
      QueryParam pRsSearch = new QueryParamBuilder() //
          .add("order", 1, Integer.class) //
          .add("value", RS_TO_DELETE, Object.class) //
          .add("clazz", Integer.class, Class.class) //
          .getParam();

      QueryParam pLimit = new QueryParamBuilder() //
          .add("order", 2, Integer.class) //
          .add("value", limit, Object.class) //
          .add("clazz", Integer.class, Class.class) //
          .getParam();

      QueryParam pOffset = null;

      StringJoiner idElems = new StringJoiner(",");
      do {
        pOffset = new QueryParamBuilder() //
            .add("order", 3, Integer.class) //
            .add("value", numRow, Object.class) //
            .add("clazz", Integer.class, Class.class) //
            .getParam();

        lstResults = ctx.getDataSource().execute(sql,
            pRsSearch.asList(pLimit.asList(pOffset.asList())), adapter.getIdMapper());

        if (isNotEmpty(lstResults)) {
          for (Integer idElem : lstResults) {
            for (String lng : CODE_LANGUAGES) {
              ctx.getEsClient() //
                  .getClient() //
                  .prepareDelete(adapter.getIndexName(), adapter.getIndexType(),
                      String.valueOf(idElem + "_" + lng)) //
                  .execute().actionGet();
            }

            idElems.add(String.format(ID_TPL, String.valueOf(idElem)));
          }
        }

        numRow += limit;
      } while (isNotEmpty(lstResults));

      if (idElems.length() > 0) {
        ctx.getDataSource().executeUpdate(String.format(sqlDelete, idElems.toString()));
      }
    } catch (Exception e) {
      throw new TechnicalException(e);
    }
  }

  /**
   * Indexation des nouvelles données.
   */
  private void indexationNewData() {
    try {
      String sql = adapter.getSQLSelectValueObject();
      String sqlUpdate = adapter.getSQLUpdateRsSearch();
      Integer limit = Integer.valueOf(ctx.getProp().get(APP_CONFIG_FILE, DB_KEY_PAGINATION));
      Integer numRow = 0;
      List<T> lstResults = null;
      RowMapper<T> mapper = adapter.getMapper();

      // Paramètres
      QueryParam pRsSearch = new QueryParamBuilder() //
          .add("order", 1, Integer.class) //
          .add("value", RS_TO_UPDATE, Object.class) //
          .add("clazz", Integer.class, Class.class) //
          .getParam();

      QueryParam pLimit = new QueryParamBuilder() //
          .add("order", 2, Integer.class) //
          .add("value", limit, Object.class) //
          .add("clazz", Integer.class, Class.class) //
          .getParam();

      QueryParam pOffset = null;

      StringJoiner idElems = new StringJoiner(",");
      do {
        pOffset = new QueryParamBuilder() //
            .add("order", 3, Integer.class) //
            .add("value", numRow, Object.class) //
            .add("clazz", Integer.class, Class.class) //
            .getParam();

        lstResults = ctx.getDataSource().execute(sql,
            pRsSearch.asList(pLimit.asList(pOffset.asList())), mapper);

        if (isNotEmpty(lstResults)) {
          for (T object : lstResults) {
            ctx.getEsClient() //
                .getClient() //
                .prepareIndex(adapter.getIndexName(), adapter.getIndexType(), adapter.getId(object)) //
                .setSource(objectTojsonQuietly(object, adapter.getValueObjectClass())) //
                .execute() //
                .actionGet();

            idElems.add(String.format(ID_TPL, adapter.getId(object)));
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
  public void proceedAsync(HttpServerRequest request, HttpServerResponse response) {
    indexationNewData();
    purgeOldData();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String proceed(HttpServerRequest request, HttpServerResponse response) {
    return String.format(RESPONSE_HTML_TEMPLATE, "Indexation en cours...");
  }

  /**
   * @return the adapter
   */
  public IndexingDeltaAdapter<T> getAdapter() {
    return adapter;
  }

  /**
   * @param adapter the adapter to set
   */
  public void setAdapter(IndexingDeltaAdapter<T> adapter) {
    this.adapter = adapter;
  }
}
