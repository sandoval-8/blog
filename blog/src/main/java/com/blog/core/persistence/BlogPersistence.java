package com.blog.core.persistence;

import com.blog.core.entity.Blog;

/**
 * Interfaz estandar para la persistencia de {@link Blog}. Ej: .xml, .html, .txt, etc
 * 
 * @author lucas
 */
public interface BlogPersistence <T>{
	
	public String saveBlog(T blog);
	
	public String findBlog(String name);

}
