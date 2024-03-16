package com.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.core.entity.Blog;
import com.blog.core.persistence.BlogPersistence;
import com.blog.core.service.BlogService;
import com.blog.dto.BlogDto;
import com.blog.exception.NotFoundException;
import com.blog.mapper.BlogMapper;
import com.blog.persistence.BlogRepository;
import com.blog.persistence.dao.BlogDao;


@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private BlogPersistence blogPersistence;

	@Autowired
	private BlogMapper blogMapper;

	@Override
	public Blog createEntity(Blog createObject) {
		BlogDto blog = (BlogDto) createObject;
		// Guardar archivo HTML
		String blogFilename = blogPersistence.saveBlog(blog);
		// Guardar el nombre del archivo en 'createObect'
		blog.setFilename(blogFilename);
		//Borramos el contenido del blog para no guardarlo en en bbdd.
		blog.setContentBlog(null);
		blog.setCreated(LocalDateTime.now());
		blog.setEnabled(Objects.isNull(blog.isEnabled())? true:blog.isEnabled());
		// Guardar Blog en BBDD
		BlogDao blogCreated = blogRepository.save(blogMapper.blogToBlogDao(blog));
		return blogMapper.blogDaoToBlog(blogCreated);
	}

	@Override
	public Blog updateEntity(Blog updateObject) {
		// Tiene un problema: cuando actualicemos un blog por lo que sea tambien se va aactualizar
		// la fecha de creacion, revisar eso, deberia haber un campo de actualizacion y
		// no modificar elcampode creación.
		if (null != updateObject.getId()) {
			Optional<BlogDao> blogDaoOptional = blogRepository.findById(updateObject.getId());
			if (blogDaoOptional.isPresent()) {
				BlogDao blogDao = blogDaoOptional.get();
				// String fileName = blogPersistence.saveImage(updateObject); //No existe blog
				// persistence
				return blogMapper.blogDaoToBlog(blogDao);

			} else {
				// implementar funcionalidad si el id es nulo.
				return null;
			}
		} else {
			// implementar funcionalidad si el id no existe en BBDD.
			return null;
		}
	}

	@Override
	public Blog deleteEntity(Long deleteObject) {
		if (null != deleteObject) {
			Optional<BlogDao> blogDaoOptional = blogRepository.findById(deleteObject);
			if (blogDaoOptional.isPresent()) {
				BlogDao blogDisabled = blogDaoOptional.get();
				blogDisabled.setEnabled(false);
				BlogDao imageResponse = blogRepository.save(blogDisabled);
				return blogMapper.blogDaoToBlog(imageResponse);
			}
		}
		// Implementar funcionalidad si no existe la imagen a eliminar.
		return null;
	}

	@Override
	public List<Blog> getEntity(Long gettingObect) {
		// Si el parametro de busqueda es nulo traemos todos los datos
		List<Blog> blogList = new ArrayList<Blog>();
		if(Objects.isNull(gettingObect)) {
			List<BlogDao> blogDaoList = blogRepository.findAll();
			if(!blogDaoList.isEmpty()) {
				for(BlogDao blogDao : blogDaoList) {
					blogList.add(blogMapper.blogDaoToBlog(blogDao));
				}
				return blogList;	
			} else {
				throw new NotFoundException("No hay blogs para retornar.");
			}
		}
		Optional<BlogDao> blogDaoOptional = blogRepository.findById(gettingObect);
		if (blogDaoOptional.isPresent()) {
			BlogDao blogDao = blogDaoOptional.get();
			Blog blog = blogMapper.blogDaoToBlog(blogDao);
			blogList.add(blog);
			return blogList;
		} else {
			throw new NotFoundException("No hay blogs para retornar.");
		}
	}
	
	public String getHTMLBlog(String filename) {
		String blogContent = blogPersistence.findBlog(filename);
		return blogContent;
	}

}
