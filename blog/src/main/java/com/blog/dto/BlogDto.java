package com.blog.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.blog.core.entity.Blog;
import com.blog.persistence.dao.ImageDao;
import com.blog.persistence.dao.TagDao;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class BlogDto extends Blog implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String contentBlog;
	private LocalDateTime created;
	private Boolean enabled;
	private Long type;
	private String category;
	private String filename;
	private List<TagDao> tags;
	private List<ImageDao> images;
	//private HashMap<Long, String> linksRepository;
	//private HashMap<Long, String> images;
	//private HashMap<Long, String> examplesCode;
	//private HashMap<Long, String> linkBibliografy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentBlog() {
		return contentBlog;
	}
	public void setContentBlog(String contentBlog) {
		this.contentBlog = contentBlog;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public List<TagDao> getTags() {
		return tags;
	}
	public void setTags(List<TagDao> tags) {
		this.tags = tags;
	}
	public List<ImageDao> getImages() {
		return images;
	}
	public void setImages(List<ImageDao> images) {
		this.images = images;
	}
	public Boolean getEnabled() {
		return enabled;
	}
}
