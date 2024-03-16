package com.blog.persistence.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "blog")
public class BlogDao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	@Column(columnDefinition = "TEXT")
	private String contentBlog;
	private LocalDateTime created;
	private boolean enabled;
	private Long type;
	private String category;
	private String filename;
	
	@ManyToMany
	@JoinTable(name = "blog_tag", 
	joinColumns = @JoinColumn(name = "id_blog"), 
	inverseJoinColumns = @JoinColumn(name = "id_tag"))
	private List<TagDao> tags;
	
	@OneToMany
	@JoinColumn(name = "id_blog")
	private List<ImageDao> images;
	
	//private HashMap<Long, String> examplesCode;
	//Crear referencia a ID de otros Blogs relacionados
	//private HashMap<Long, String> linkBibliografy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
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
	public void setEnabled(boolean enabled) {
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
}
