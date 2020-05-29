package com.eccos.test.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;

	@ManyToOne
	@JoinColumn(name = "AUTHOR")
	private Author author;

	private String bookName;

	private String category;

	private String language;

	private Date pruchaseDate;

	public Book() {
		author = new Author();
	}

	public long getId() {
		return Id;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getPruchaseDate() {
		return pruchaseDate;
	}

	public void setPruchaseDate(Date pruchaseDate) {
		this.pruchaseDate = pruchaseDate;
	}
	
	@Override
	public String toString() {
		return bookName.toString();
	}
}
