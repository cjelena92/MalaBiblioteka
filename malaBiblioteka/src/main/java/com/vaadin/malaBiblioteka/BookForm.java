package com.vaadin.malaBiblioteka;

import java.sql.SQLException;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.vaadin.control.UnosControl;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.model.Knjige;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class BookForm extends FormLayout{
	
	private int id;
	private TextField isbn=new TextField("ISBN");
	private TextField naziv=new TextField("Naziv");
	private TextField autor=new TextField("Autor");
	private TextField zanr=new TextField("Zanr");
	private TextField cena=new TextField("Cena");
	private TextField opis=new TextField("Opis");
	private Button save=new Button("Save");
	private Button delete=new Button("Delete");
	private Button update=new Button("Update");
	private UnosControl control=UnosControl.getInstance();
	private Knjige knjige;
	private MyUI myUI;
	
	public BookForm(MyUI myUI){
		this.myUI=myUI;
	
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		save.addClickListener(e -> {
			
			save();
		});
		
		delete.addClickListener(e -> {
			delete();
		});
		update.setStyleName(ValoTheme.BUTTON_PRIMARY);
		update.addClickListener(e -> {
			try {
				update();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		setSizeUndefined();
		HorizontalLayout buttons=new HorizontalLayout(update,save,delete);
		buttons.setSpacing(true);
		addComponents(isbn,naziv,autor,zanr,cena,opis,buttons);
		
		
	}
	
	public void setBook(Knjige knjige){
		this.knjige=knjige;
		BeanFieldGroup.bindFieldsUnbuffered(knjige, this);
		save.setVisible(!knjige.isPersisted());
		delete.setVisible(knjige.isPersisted());
		update.setVisible(knjige.isPersisted());
		setVisible(true);
		isbn.selectAll();
		
	}
	public void save(){
		String isbnS=isbn.getValue();
		String nazivS=naziv.getValue();
		String autorS=autor.getValue();
		String zanrS=zanr.getValue();
		String cenaS=cena.getValue();
		if(isbnS.equals("")){
			Notification.show(null,"Unesite isbn!",Notification.Type.WARNING_MESSAGE);
		}else if(nazivS.equals("")){
			Notification.show(null,"Unesite naziv!",Notification.Type.WARNING_MESSAGE);
		}else if(autorS.equals("")){
			Notification.show(null,"Unesite autora!",Notification.Type.WARNING_MESSAGE);
		}else if(zanrS.equals("")){
			Notification.show(null,"Unesite zanr!",Notification.Type.WARNING_MESSAGE);
		}else if(cenaS.equals("")){
			Notification.show(null,"Unesite cenu!",Notification.Type.WARNING_MESSAGE);
		}else{
		Knjige knjige=new Knjige();
		knjige.setIsbn(Integer.parseInt(isbn.getValue()));
		knjige.setNaziv(naziv.getValue());
		knjige.setAutor(autor.getValue());
		knjige.setZanr(zanr.getValue());
		knjige.setCena(cena.getValue());
		knjige.setOpis(opis.getValue());
		control.unosKnjige(knjige);
		myUI.updateList();
		setVisible(false);
		}
	}
	
	private void delete(){
		
		control.deleteBook(Integer.parseInt(isbn.getValue()));
		myUI.updateList();
		setVisible(false);
		
		
	}
	
	private void update() throws SQLException{
		
		control.updateBook(knjige);
		myUI.updateList();
	}
}
