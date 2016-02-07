package com.bblvertx.route;

import static com.bblvertx.SeConstants.PREFIX_URL;
import io.vertx.ext.web.Router;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bblvertx.utils.singleton.RouteContext;

/**
 * Abstract pour les routes (routines).
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public abstract class AbstractRoute implements Route {
	private static final Logger LOGGER = LogManager.getLogger(AbstractRoute.class);

	protected RouteContext ctx;

	/**
	 * Constructeur permettant d'initaliser une route.
	 * 
	 * @param url
	 * @param contentType
	 * @param router
	 */
	public AbstractRoute(String url, String contentType, Router router, RouteContext ctx) {
		this.ctx = ctx;
		router.get(String.format(PREFIX_URL, url)).handler(req -> {
			LOGGER.info("Lauching route " + url);
			req.response().putHeader("content-type", contentType).end(this.proceed(req.request(), req.response()));
		});
	}
}
