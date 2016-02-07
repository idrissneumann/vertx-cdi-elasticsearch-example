package com.bblvertx;

/**
 * Interface de constantes.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public interface SeConstants {
	String KEY_PORT = "se.port";
	String KEY_NB_ROUTES = "se.routes.nb";
	String CLASS_ROUTE_PATTERN = "tn.prodit.network.se.route.impl.%s";
	String PREFIX_URL = "/prodit-se/api%s";

	String KEY_TPL_ROUTE_URL = "se.route.%s.url";
	String KEY_TPL_ROUTE_CONTENT_TYPE = "se.route.%s.contenttype";
	String KEY_TPL_ROUTE_CLASS = "se.route.%s.class";

	/**
	 * Fichiers properties
	 */
	String APP_CONFIG_FILE = "application.properties";
	String ROUTE_CONFIG_FILE = "routes.properties";
	String SQL_CONFIG_FILE = "sqlqueries.properties";

	/**
	 * Requêtes select
	 */
	String SELECT_CHECK = "sql.check.connection";
	String SELECT_USER = "sql.user.rssearch";
	String SELECT_SINGLE_USER = "sql.user.single";
	String SELECT_USER_FLAG = "sql.user.flagidx";

	/**
	 * Requêtes delete
	 */
	String DELETE_RSSEARCH = "sql.delete.rssearch";

	/**
	 * Requêtes update
	 */
	String UPDATE_RSSEARCH = "sql.update.rssearch";

	/**
	 * Database
	 */
	String DB_KEY_DRIVER = "db.driver";
	String DB_KEY_URL = "db.url";
	String DB_KEY_USERNAME = "db.username";
	String DB_KEY_PASSWD = "db.password";
	String DB_KEY_SCHEMA = "db.schema";
	String DB_KEY_MIN_POOL_SIZE = "db.min_pool_size";
	String DB_KEY_MAX_POOL_SIZE = "db.min_pool_size";
	String DB_KEY_MAX_STATEMENTS = "db.max_statements";
	String DB_KEY_PAGINATION = "db.pagination";

	/**
	 * Elastic Search
	 */
	String ES_KEY_HOST = "es.host";
	String ES_KEY_PORT = "es.port";
	String ES_KEY_CLUSTER = "es.cluster";

	/**
	 * Indexes
	 */
	String ES_INDEX_USER = "user";

	/**
	 * Valeurs flag RS_SEARCH
	 */
	Integer RS_TO_DELETE = -1;
	Integer RS_TO_UPDATE = 1;
	Integer RS_TO_STAY = 0;

	/**
	 * Messages d'erreurs
	 */
	String MSG_BAD_REQUEST = "Le paramètre %s est obligatoire !";
	String MSG_BAD_REQUEST_MUST_BE_EMPTY = "Le paramètre %s doit être null !";
	String MSG_BAD_REQUEST_MUST_BE_NUMERIC = "Le paramètre %s doit être numérique !";
	String MSG_BAD_REQUEST_MUST_BE_CALENDAR = "Le paramètre %s doit être une date !";

	/**
	 * Autres
	 */
	String EMPTY_STRING = "";
	String EMPTY_JSON = "[]";
	String RESPONSE_HTML_TEMPLATE = "<html><body><div>%s</div></body></html>";
	String NAMESPACE_URI = "http://proditnetwork.com";
	String SEPARATOR_DEFAULT = ";";
}
