package com.library.test;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.test.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	//public List<Author> findByAuthorName(String authorName);
	
	@Query("FROM Author Where author_name = ?1")
	List<Author> findAuthorName(String authorName);
	
}
