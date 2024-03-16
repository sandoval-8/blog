package com.blog.core.persistence;

import com.blog.core.entity.Image;

/**
 * Interfaz estandar para la persistencia de {@link Image}. Ej: .png, .jpg, .jpeg, etc
 * 
 * @author lucas
 */
public interface ImagePersistence {
	
	public String saveImage(Image image);
	
	public byte[] findImage(String name);

}
