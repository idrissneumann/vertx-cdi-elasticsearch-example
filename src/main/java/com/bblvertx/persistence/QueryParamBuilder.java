package com.bblvertx.persistence;

import static com.bblvertx.utils.CommonUtils.setterFromProperty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Assistant de création de la classe QueryParam.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class QueryParamBuilder {
	private static final Logger LOGGER = LogManager.getLogger(QueryParamBuilder.class);

	private QueryParam param;

	/**
	 * Constructeur
	 */
	public QueryParamBuilder() {
		param = new QueryParam();
	}

	/**
	 * Invoquer un setter en retournant l'objet
	 * 
	 * @param propertyName
	 * @param value
	 * @return DemoMemberCriteres
	 */
	public QueryParamBuilder add(String propertyName, Object value, Class<?> clazz) {
		String setter = setterFromProperty(propertyName);
		try {
			Method m = param.getClass().getMethod(setter, clazz);
			m.invoke(param, clazz.cast(value));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOGGER.error("Exception inattendue : " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
		return this;
	}

	/**
	 * Getter de l'attribut param.
	 * 
	 * @return param
	 */
	public QueryParam getParam() {
		return param;
	}
}