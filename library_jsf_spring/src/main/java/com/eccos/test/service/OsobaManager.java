package com.eccos.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eccos.test.model.Osoba;


@Component
@Scope("session")
public class OsobaManager {

    private List<Osoba> osobeList = new ArrayList<>();

    String ime, prezime;
    
    public List<Osoba> getOsobeList() {
        return osobeList;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void dodajOsobu() {
        osobeList.add(new Osoba(ime, prezime));
    }

    public void obrisiOsobu(Osoba osoba) {
        osobeList.remove(osoba);
    }
}
