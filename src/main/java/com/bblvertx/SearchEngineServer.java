package com.bblvertx;

import static com.bblvertx.SeConstants.APP_CONFIG_FILE;
import static com.bblvertx.SeConstants.CLASS_ROUTE_PATTERN;
import static com.bblvertx.SeConstants.KEY_NB_ROUTES;
import static com.bblvertx.SeConstants.KEY_PORT;
import static com.bblvertx.SeConstants.KEY_TPL_ROUTE_CLASS;
import static com.bblvertx.SeConstants.KEY_TPL_ROUTE_CONTENT_TYPE;
import static com.bblvertx.SeConstants.KEY_TPL_ROUTE_URL;
import static com.bblvertx.SeConstants.ROUTE_CONFIG_FILE;

import java.lang.reflect.Constructor;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bblvertx.utils.singleton.PropertyReader;
import com.bblvertx.utils.singleton.RouteContext;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Server engine : main verticle node.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class SearchEngineServer extends AbstractVerticle {
  private static final Logger LOGGER = LogManager.getLogger(SearchEngineServer.class);

  @Inject
  private PropertyReader reader;

  @Inject
  private RouteContext routeCtx;

  /**
   * {@inheritDoc}
   */
  @Override
  public void start() throws Exception {
    LOGGER.info("Launching server...");

    Integer port = reader.getInt(APP_CONFIG_FILE, KEY_PORT);
    Integer nbRoutes = reader.getInt(ROUTE_CONFIG_FILE, KEY_NB_ROUTES);

    final Router router = Router.router(vertx);

    // Automatically connect all routes
    for (Integer i = 1; i <= nbRoutes; i++) {
      String routeClass = reader.get(ROUTE_CONFIG_FILE, String.format(KEY_TPL_ROUTE_CLASS, i));
      String routeUrl = reader.get(ROUTE_CONFIG_FILE, String.format(KEY_TPL_ROUTE_URL, i));
      String routeContentType =
          reader.get(ROUTE_CONFIG_FILE, String.format(KEY_TPL_ROUTE_CONTENT_TYPE, i));

      Class<?> clazz = Class.forName(String.format(CLASS_ROUTE_PATTERN, routeClass));
      Constructor<?> ctor =
          clazz.getConstructor(String.class, String.class, Router.class, RouteContext.class);
      ctor.newInstance(new Object[] {routeUrl, routeContentType, router, routeCtx});
    }

    vertx.createHttpServer().requestHandler(router::accept).listen(port);
  }
}
