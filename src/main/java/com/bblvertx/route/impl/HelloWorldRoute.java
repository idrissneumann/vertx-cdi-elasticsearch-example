package com.bblvertx.route.impl;

import static com.bblvertx.SeConstants.RESPONSE_HTML_TEMPLATE;

import com.bblvertx.route.AbstractRoute;
import com.bblvertx.utils.singleton.RouteContext;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * Route pour tester le démarrage du verticle.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class HelloWorldRoute extends AbstractRoute {
	/**
	 * Constructeur.
	 * 
	 * @param url
	 * @param contentType
	 * @param router
	 * @param ctx
	 */
	public HelloWorldRoute(String url, String contentType, Router router, RouteContext ctx) {
		super(url, contentType, router, ctx);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String proceed(HttpServerRequest request, HttpServerResponse response) {
		return String.format(RESPONSE_HTML_TEMPLATE, "Hello world");
	}
}
