package com.bblvertx.indexation.adapter;

import java.io.Serializable;

import com.bblvertx.persistence.RowMapper;
import com.bblvertx.utils.singleton.RouteContext;

/**
 * Interface adapter pour les indexations en général.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 * 
 * @param <T>
 *            type de l'objet valeur
 */
public interface IndexationAdapter<T extends Serializable> {
	/**
	 * Récupérer le context des singletons.
	 * 
	 * @return RouteContext
	 */
	RouteContext getRouteContext();

	/**
	 * Récupérer la requête SQL de type "update rs search".
	 * 
	 * @return
	 */
	String getSQLUpdateRsSearch();

	/**
	 * Récupérer la requête SQL de type "select value object".
	 * 
	 * @return String
	 */
	String getSQLSelectValueObject();

	/**
	 * Récupérer le nom de l'index.
	 * 
	 * @return String
	 */
	String getIndexName();

	/**
	 * Récupérer le type de l'index.
	 * 
	 * @return String
	 */
	String getIndexType();

	/**
	 * Récupérer la class.
	 * 
	 * @return Class<?>
	 */
	Class<?> getValueObjectClass();

	/**
	 * Récupérer la langue d'un objet.
	 * 
	 * @param valueObject
	 * @return String
	 */
	String getLanguage(T valueObject);

	/**
	 * Récupérer l'id d'un objet.
	 * 
	 * @param valueObject
	 * @return String
	 */
	String getId(T valueObject);

	/**
	 * Récupérer un row mapper.
	 * 
	 * @return
	 */
	RowMapper<T> getMapper();
}
