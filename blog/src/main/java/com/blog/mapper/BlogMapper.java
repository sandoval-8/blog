package com.blog.mapper;

import org.mapstruct.Mapper;

import com.blog.core.entity.Blog;
import com.blog.dto.BlogDto;
import com.blog.persistence.dao.BlogDao;

@Mapper(componentModel = "spring")
public interface BlogMapper {

	Blog blogDtoToBlog(BlogDto blogDto);
	BlogDto blogToBlogDto(Blog blog);
	
	BlogDao blogToBlogDao(Blog blog);
	Blog blogDaoToBlog(BlogDao blogDao);
}
