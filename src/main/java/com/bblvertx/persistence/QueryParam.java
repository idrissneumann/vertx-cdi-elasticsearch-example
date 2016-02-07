package com.bblvertx.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Paramètre d'une requête.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class QueryParam implements Serializable, Comparable<QueryParam> {
	private static final long serialVersionUID = 1L;

	private Integer order;

	private String name;

	private Object value;

	private Class<?> clazz;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz
	 *            the clazz to set
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * Retourner le paramètre comme une liste.
	 * 
	 * @return List<QueryParam>
	 */
	public List<QueryParam> asList() {
		List<QueryParam> lst = new ArrayList<QueryParam>();
		lst.add(this);
		return lst;
	}

	/**
	 * Retourner le paramètre comme une liste.
	 * 
	 * @param List
	 *            <QueryParam>
	 * @return List<QueryParam>
	 */
	public List<QueryParam> asList(List<QueryParam> l) {
		List<QueryParam> lst = new ArrayList<QueryParam>();
		lst.add(this);
		lst.addAll(l);
		return lst;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(QueryParam o) {
		if (null == this.order && null == o.getOrder()) {
			return 0;
		} else if (null == this.order) {
			return -1;
		} else if (null == o.getOrder()) {
			return 1;
		} else {
			return o.getOrder().compareTo(this.order);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "QueryParam [order=" + order + ", name=" + name + ", value=" + value + "]";
	}
}
