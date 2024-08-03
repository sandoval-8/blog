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
		// Borramos el contenido del blog para no guardarlo en en bbdd.
		blog.setContentBlog(null);
		blog.setCreated(LocalDateTime.now());
		blog.setEnabled(Objects.isNull(blog.getEnabled()) ? true : blog.getEnabled());
		// Guardar Blog en BBDD
		BlogDao blogCreated = blogRepository.save(blogMapper.blogDtoToBlogDao(blog));
		return blogMapper.blogDaoToBlogDto(blogCreated);
	}

	@Override
	public Blog updateEntity(Blog updateObject) {
		// Tiene un problema: cuando actualicemos un blog por lo que sea tambien se va
		// aactualizar
		// la fecha de creacion, revisar eso, deberia haber un campo de actualizacion y
		// no modificar el campode creación.
		if (null != updateObject.getId()) {
			Optional<BlogDao> blogDaoOptional = blogRepository.findById(updateObject.getId());
			if (blogDaoOptional.isPresent()) {
				BlogDao blogDao = blogDaoOptional.get();
				// String fileName = blogPersistence.saveImage(updateObject); //No existe blog
				// persistence
				return blogMapper.blogDaoToBlogDto(blogDao);

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
				BlogDao blogResponse = blogRepository.save(blogDisabled);
				return blogMapper.blogDaoToBlogDto(blogResponse);
			}
			// Implementar funcionalidad si no existe el blog a eliminar.
		}
		// Implementar funcionalidad si no existe el blog a eliminar.
		return null;
	}

	public String getHTMLBlog(String filename) {
		String blogContent = blogPersistence.findBlog(filename);
		return blogContent;
	}

	@Override
	public List<? extends Blog> getEntity(Long gettingObect) {
		// Si el parametro de busqueda es nulo traemos todos los datos
		List<BlogDto> blogList = new ArrayList<BlogDto>();
		if (Objects.isNull(gettingObect)) {
			List<BlogDao> blogDaoList = blogRepository.findAll();
			if (!blogDaoList.isEmpty()) {
				for (BlogDao blogDao : blogDaoList) {
					blogList.add(blogMapper.blogDaoToBlogDto(blogDao));
				}
				return blogList;
			} else {
				throw new NotFoundException("No hay blogs para retornar.");
			}
		}
		Optional<BlogDao> blogDaoOptional = blogRepository.findById(gettingObect);
		if (blogDaoOptional.isPresent()) {
			BlogDao blogDao = blogDaoOptional.get();
			BlogDto blog = blogMapper.blogDaoToBlogDto(blogDao);
			blogList.add(blog);
			return blogList;
		} else {
			throw new NotFoundException("No hay blogs para retornar.");
		}
	}

}
