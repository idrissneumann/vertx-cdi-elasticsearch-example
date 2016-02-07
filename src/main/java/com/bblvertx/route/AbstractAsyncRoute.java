package com.bblvertx.route;

import static com.bblvertx.SeConstants.PREFIX_URL;
import io.vertx.ext.web.Router;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bblvertx.utils.singleton.RouteContext;

/**
 * Abstract pour créer des routes asynchrones.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public abstract class AbstractAsyncRoute implements AsyncRoute {
	private static final Logger LOGGER = LogManager.getLogger(AbstractAsyncRoute.class);

	protected RouteContext ctx;

	/**
	 * Constructeur permettant d'initaliser une route asynchrone.
	 * 
	 * @param url
	 * @param contentType
	 * @param router
	 */
	public AbstractAsyncRoute(String url, String contentType, Router router, RouteContext ctx) {
		this.ctx = ctx;
		router.get(String.format(PREFIX_URL, url)).handler(req -> {
			LOGGER.info("Lauching route " + url);
			req.response().putHeader("content-type", contentType).end(this.proceed(req.request(), req.response()));

			new Thread(new Runnable() {
				public void run() {
					LOGGER.info("Lauching route " + url + " async action...");
					proceedAsync(req.request(), req.response());
				}
			}).start();
		});
	}
}
