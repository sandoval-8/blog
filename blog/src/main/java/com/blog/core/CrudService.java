package com.blog.core;

import java.util.List;

/**
 * Interfaz común para todos los servicios.
 * 
 * @param <K> Type of ID
 * @param <T> Object return
 * @param <E> Object request
 * 
 */
public interface CrudService<T, E, K> {

	T createEntity(E createObject);

	T updateEntity(E updateObject);

	T deleteEntity(K deleteObject);

	List<T> getEntity(K gettingObect);

}
