package com.blog.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.persistence.dao.BlogDao;

public interface BlogRepository extends JpaRepository<BlogDao, Long> {

}
