package com.blog.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.persistence.dao.ImageDao;

public interface TagRepository extends JpaRepository<ImageDao, Long>{

}
