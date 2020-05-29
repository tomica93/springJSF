package com.eccos.test;

import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Component;

@Component
@SessionScoped
public class NavigationBean  {
 
  private String page = "author.xhtml";
 
  public String getPage() {
    return page;
  }
 
  public void setPage(String currentPage) {
    this.page=currentPage;
  }
}