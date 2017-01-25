package com.vaadin.model;

import java.io.Serializable;



@SuppressWarnings("serial")
public class Knjige implements Serializable {
	private int isbn;
	private String naziv;
	private String autor;
	private String zanr;
	private String cena;
	private String opis;
	public int getIsbn() {
		return isbn;
	}
	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getZanr() {
		return zanr;
	}
	public void setZanr(String zanr) {
		this.zanr = zanr;
	}
	public String getCena() {
		return cena;
	}
	public void setCena(String cena) {
		this.cena = cena;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public boolean isPersisted() {
		return isbn != 0;
	}
	
	


}
