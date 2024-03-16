package com.blog.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.persistence.dao.ImageDao;

public interface CategoryRepository extends JpaRepository<ImageDao, Long> {

}
