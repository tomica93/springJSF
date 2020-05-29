package com.eccos.test.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.eccos.test.AuthorRepository;
import com.eccos.test.BookRepostory;
import com.eccos.test.model.Author;
import com.eccos.test.model.Book;

import org.springframework.util.StringUtils;


@Service
public class BookManager {

	{
		bookToEdit = new Book();
	}
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private BookRepostory bookRepostory;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	private Book bookToEdit;
	private Long bookToEditAuthorsId;

	public List<Book> bookList;

	public Book getBookToEdit() {
		return bookToEdit;
	}
	
	public void setBookToEdit(Book bookToEdit) {
		this.bookToEdit = bookToEdit;
	}

	public Long getBookToEditAuthorsId() {
		if(bookToEditAuthorsId == null && bookToEdit != null) {
			setBookToEditAuthorsId(bookToEdit.getAuthor().getId());
		}
		return bookToEditAuthorsId;
	}

	public void setBookToEditAuthorsId(Long bookToEditAuthorsId) {
		this.bookToEditAuthorsId = bookToEditAuthorsId;
	}
	
	private String searchBookName;	
	
	public String getSearchBookName() {
		return searchBookName;
	}

	public void setSearchBookName(String searchBookName) {
		this.searchBookName = searchBookName;
	}

	public List<Book> getBook(){
		return (List<Book>) bookRepostory.findAll();
	}
	
	@PostConstruct
	public void init() {
		bookList = getBook();
	}
	
	public void addBook() {
		Author author = authorRepository.findOne(bookToEditAuthorsId);
		bookToEdit.setAuthor(author);
		bookRepostory.save(bookToEdit);
		
		try {
			reload();
			clear();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editBook() {
		
		Author author = authorRepository.findOne(bookToEditAuthorsId);
		bookToEdit.setAuthor(author);
		bookRepostory.save(bookToEdit);
		clear();
		
		try {
			reload();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBook(Book book) {
		
		try {
			bookRepostory.delete(book);
			reload();
		}
		
		catch (Exception e) {
			
			FacesContext facesContex = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage("Upozorenje","Nije moguće obrisati knjigu.");
			facesContex.addMessage(null, facesMessage);
		}
	}
	
	public void prepareNewBook() {
		bookToEdit = new Book();
	}

	public List<Book> filteredByBookName() {
		if (bookRepostory.findBookName(searchBookName).isEmpty()) {
			return getBook();
		} else {
			return (List<Book>) bookRepostory.findBookName(searchBookName);
		}
	}
	
	private static String bookName;
	
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		BookManager.bookName = bookName;
	}
	
	//obicno pretrazivanje 
/*	public List<Book> searchBooksTable() {
	  
		String qlQuery = "FROM Book Where book_name LIKE ?1 OR category LIKE ?1 OR author.authorName LIKE ?1";
		Query query = entityManager.createQuery(qlQuery);
		query.setParameter(1, bookName);
	
		return (List<Book>) query.getResultList();
	}
*/	
	
	// Pretrazivanje po criteria queriju za knjigu, kategoriju i autora
	public List<Book> searchBooksTable(){
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
	
		Root<Book> bookRoot = cq.from(Book.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(bookName)) {
			predicates.add(cb.like(bookRoot.get("bookName"), bookName + "%"));
			predicates.add(cb.like(bookRoot.get("category"), bookName + "%"));
			predicates.add(cb.like(bookRoot.get("author").get("authorName"), bookName + "%"));
			predicates.add(cb.like(bookRoot.get("language"), bookName + "%"));
			
		}
		
		cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		
		TypedQuery<Book> query = entityManager.createQuery(cq);
		
		bookName = null;
		return (List<Book>) query.getResultList(); 		
	}
		/*
		public List<Book> searchBooksTable(){
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
	
		Root<Book> bookRoot = cq.from(Book.class);
		
		List<Predicate> predicatesList = new ArrayList<>();
		
		if(StringUtils.hasText(bookName)) {
			predicatesList.add(cb.like(bookRoot.get("language"), bookName + "%"));		
		}
		
		cq.where(cb.or(predicatesList.toArray(new Predicate[predicatesList.size()])));
	
		TypedQuery<Book> query = entityManager.createQuery(cq);

		return (List<Book>) query.getResultList(); 		
	}*/
		
	//osvjezi tablicu prilikom dodavanja/izmjene novog autora/knjige
	public void reload() throws IOException {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	
	public void clear() {
		bookToEdit = null;
		bookToEditAuthorsId = null;
	}
	
	public void clearAllFilters() {

		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("dataTable:booksTable");
		if (!dataTable.getFilters().isEmpty()) {
		    dataTable.reset();

		    PrimeFaces.current().ajax().update("dataTable:booksTable");
		}
	}
}
