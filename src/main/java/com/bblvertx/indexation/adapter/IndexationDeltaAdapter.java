package com.bblvertx.indexation.adapter;

import java.io.Serializable;

import com.bblvertx.persistence.RowMapper;

/**
 * Adapter pour les jobs d'indexation en delta.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 * 
 * @param <T>
 *            type de l'objet valeur
 *
 */
public interface IndexationDeltaAdapter<T extends Serializable> extends IndexationAdapter<T> {
	/**
	 * Récupérer la requête SQL de type "select flag".
	 * 
	 * @return String
	 */
	String getSQLSelectFlagIdx();

	/**
	 * Récupérer la requête SQL de type "delete rs search".
	 * 
	 * @return String
	 */
	String getSQLDeleteRsSearch();

	/**
	 * Mapper pour l'id.
	 * 
	 * @return RowMapper<Integer>
	 */
	RowMapper<Integer> getIdMapper();
}
