package com.blog.persistence.dao;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
public class TagDao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tag_name")
	private String tagName;
	
	@ManyToMany(mappedBy = "tags")
	private List<BlogDao> blogs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<BlogDao> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<BlogDao> blogs) {
		this.blogs = blogs;
	}

}
