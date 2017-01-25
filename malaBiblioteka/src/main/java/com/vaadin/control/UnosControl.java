package com.vaadin.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.vaadin.dao.UnosDAO;



import com.vaadin.model.Knjige;


import com.vaadin.ui.UI;


public class UnosControl {
	public static UnosControl process=null;
	private final HashMap<Long, Knjige> k = new HashMap<>();
	public static UnosControl getInstance(){
		if(process==null){
			process=new UnosControl();
		}
		return process;
	}
	
	public void unosKnjige(Knjige knjige){
		
		
		boolean result=UnosDAO.getInstance().addKnjige(knjige);
		
		if(result==true){
			System.out.println("Uspesno ste ubacili u bazu");
			
			
		}else{
			System.out.println("Greska");
			
		}
		
	}
	
public List<Knjige> getAll(){
		
		return UnosDAO.getInstance().getAll();
		
	}

public List<Knjige> byName(String naziv){
	return UnosDAO.getInstance().byName(naziv);
}

 public void deleteBook(int id){
	UnosDAO.getInstance().deleteBook(id);
	
}

public void updateBook(Knjige knjige) throws SQLException {
	UnosDAO.getInstance().updateBook(knjige);
	
}

}

	

