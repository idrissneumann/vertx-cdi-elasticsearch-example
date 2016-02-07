package com.bblvertx.route;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

/**
 * Interface pour définir une route asynchrone.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public interface AsyncRoute extends Route {
	/**
	 * Async action.
	 * 
	 * @param request
	 * @param response
	 */
	void proceedAsync(HttpServerRequest request, HttpServerResponse response);
}
