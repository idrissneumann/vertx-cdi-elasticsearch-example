package com.bblvertx.route;

import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;
import io.vertx.ext.web.Router;

import org.elasticsearch.index.query.QueryBuilder;

import com.bblvertx.utils.singleton.RouteContext;

/**
 * Abstract pour les routes de recherche.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public abstract class AbstractSearchIndexRoute extends AbstractRoute {
	public final static String WILDCARD = "*";
	public final static String WILDCARD_ESCAPE = "\\*";
	public final static String WILDCARD_REPLACE = ".*";

	/**
	 * Constructeur.
	 * 
	 * @param url
	 * @param contentType
	 * @param router
	 * @param ctx
	 */
	public AbstractSearchIndexRoute(String url, String contentType, Router router, RouteContext ctx) {
		super(url, contentType, router, ctx);
	}

	/**
	 * Construitre un critère de recherche avec support de "*".
	 * 
	 * @param fieldName
	 * @param value
	 * @return QueryBuilder
	 */
	protected QueryBuilder match(String fieldName, String value) {
		if (value.contains(WILDCARD)) {
			value = value.replaceAll(WILDCARD_ESCAPE, WILDCARD_REPLACE);
			return regexpQuery(fieldName, value.toLowerCase());
		} else {
			return regexpQuery(fieldName, value.toLowerCase() + WILDCARD_REPLACE);
		}
	}
}
