package com.eccos.test.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eccos.test.AuthorRepository;
import com.eccos.test.model.Author;


@Service
public class AuthorService {
	
	@Autowired
	public AuthorRepository authorRepository;
	
	private Author authorToEdit;
	
	private List<Author> listaAutora;
	
	private String searchAuthor;
	
	public List<Author> getListaAutora() {
		return listaAutora;
	}

	public void setListaAutora(List<Author> listaAutora) {
		this.listaAutora = listaAutora;
	}

	public String getSearchAuthor() {
		return searchAuthor;
	}

	public void setSearchAuthor(String searchAuthor) {
		this.searchAuthor = searchAuthor;
	}
	
	@PostConstruct
	public void init() {
		listaAutora = getAuthorsList();
	}
	
	public Author getAuthorToEdit() {
		return authorToEdit;
	}

	public void setAuthorToEdit(Author authorToEdit) {
		this.authorToEdit = authorToEdit;
	}
	
	public List<Author> getAuthorsList() {
		return (List<Author>) authorRepository.findAll();
	}
	
	public void addAuthor()  {
		authorRepository.save(authorToEdit);
		authorToEdit = null;
		try {
			reload();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateAuthor() {
		authorRepository.save(authorToEdit);
		authorToEdit = null;
		try {
			reload();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAuthor(Author author) {
		try {
			
		authorRepository.delete(author);
		reload();
		}
		
		catch (Exception e) {
			
			FacesContext facesContex = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage("Upozorenje","Obriši prvo sve knjige koje sadrže odabranog autora.");
			facesContex.addMessage(null, facesMessage);
		}
	}
	
	public void prepareNewAuthor() {
		
		authorToEdit = new Author();
	}
	
	public List<Author> FilteredByAuthorName(){
		System.out.print(authorRepository.findAuthorName(searchAuthor));
		
		if(authorRepository.findAuthorName(searchAuthor).isEmpty()) {
			return getAuthorsList();
		}
		else {
			return (List<Author>) authorRepository.findAuthorName(searchAuthor);
		}
	} 
	
	public void reload() throws IOException {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
}
