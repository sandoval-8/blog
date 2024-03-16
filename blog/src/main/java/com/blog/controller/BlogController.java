package com.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.core.entity.Blog;
import com.blog.dto.BlogDto;
import com.blog.mapper.BlogMapper;
import com.blog.service.BlogServiceImpl;


@RestController
@RequestMapping(path = "/blog")
public class BlogController {
	
	@Autowired
	private BlogServiceImpl blogService;
	
	@Autowired
	private BlogMapper blogMapper;
	
	@GetMapping(path = "/search")
	public ResponseEntity<List<BlogDto>> getListBlog(Long id) {
		List<Blog> blogList = blogService.getEntity(id);
		List<BlogDto> blogDtoList = new ArrayList<BlogDto>();
		if(!blogList.isEmpty()) { //No deberían llegar vacíos nunca
			for(Blog blog : blogList) {
				blogDtoList.add(blogMapper.blogToBlogDto(blog));
			}
		}
		return new ResponseEntity<List<BlogDto>>(blogDtoList, HttpStatus.OK);
	}
	
	@GetMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getBlog(String filename) {
		String blogContent = blogService.getHTMLBlog(filename);
		return new ResponseEntity<String>(blogContent, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<BlogDto> createBlog(@RequestBody String contentBlog, BlogDto blogParams) {
		blogParams.setContentBlog(contentBlog);
		BlogDto blogResponse = (BlogDto) blogService.createEntity(blogParams);
		blogResponse.setContentBlog(null);
		return new ResponseEntity<BlogDto>(blogResponse, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<BlogDto> updateBlog(BlogDto blogRequest) {
		Blog blogUpdated = blogService.updateEntity(blogMapper.blogDtoToBlog(blogRequest));
		BlogDto blogResponse = blogMapper.blogToBlogDto(blogUpdated);
		return new ResponseEntity<BlogDto>(blogResponse, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteBlog(@Valid @NotNull Long id) {
		Blog blog = blogService.deleteEntity(id);
		return new ResponseEntity<Long>(blog.getId(), HttpStatus.OK);
	}

}
