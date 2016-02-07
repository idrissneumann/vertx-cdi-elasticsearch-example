package com.bblvertx.route;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

/**
 * Interface pour définir une route.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public interface Route {
	/**
	 * Action with response.
	 * 
	 * @param request
	 * @param response
	 */
	String proceed(HttpServerRequest request, HttpServerResponse response);
}
