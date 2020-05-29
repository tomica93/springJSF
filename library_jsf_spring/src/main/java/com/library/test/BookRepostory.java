package com.library.test;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.test.model.Book;

public interface BookRepostory extends JpaRepository<Book, Long>{
	@Query("FROM Book  Where book_name LIKE ?1 OR category LIKE ?1 OR author.authorName LIKE ?1")
	public List<Book> findBookName(String bookName);
	
	@Query("FROM Book Where category = ?1")
	public List<Book> findBookCategory(String category);
}
