package com.bblvertx.route;

import com.bblvertx.utils.singleton.RouteContext;

import io.vertx.ext.web.Router;

/**
 * Abstract indexing route.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public abstract class AbstractIndexingRoute extends AbstractAsyncRoute {
  public final static String[] CODE_LANGUAGES = new String[] {"fr", "en", "ar"};
  public final static String ID_TPL = "'%s'";

  /**
   * Constructor.
   * 
   * @param url
   * @param contentType
   * @param router
   * @param ctx
   */
  public AbstractIndexingRoute(String url, String contentType, Router router, RouteContext ctx) {
    super(url, contentType, router, ctx);
  }
}
