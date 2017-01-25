package com.vaadin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



import com.vaadin.model.Knjige;


public class UnosDAO extends AbstractDAO{
	
	public static UnosDAO dao=null;
	
	private UnosDAO(){
		
	}
	
	public static UnosDAO getInstance(){
		if(dao==null){
			dao=new UnosDAO();
		}
		return dao;
	}
	
	public boolean addKnjige(Knjige knjige){
		String sql="insert into schema.knjige values(?,?,?,?,?,?);";
		PreparedStatement statement=this.getPreparedStatement(sql);
		
		try{
			statement.setInt(1, knjige.getIsbn());
			statement.setString(2, knjige.getNaziv());
			statement.setString(3, knjige.getAutor());
			statement.setString(4, knjige.getZanr());
			statement.setString(5, knjige.getCena());
			statement.setString(6,knjige.getOpis());
			statement.executeUpdate();
			return true;
		}catch(SQLException ex){
			System.out.println("GRESKA U BAZI PODATAKA");
			return false;
		}
		
		
	}
	
	public List<Knjige> getAll(){
		Statement statement=this.getStatement();
		
		ResultSet rs=null;
		
		try{
			rs=statement.executeQuery("SELECT schema.knjige.isbn, schema.knjige.naziv, schema.knjige.autor, schema.knjige.zanr,schema.knjige.cena,schema.knjige.opis FROM schema.knjige");
		}catch(SQLException ex){
			Logger.getLogger(UnosDAO.class.getName()).log(Level.SEVERE, null, ex);
			
			
		}
		if(rs==null) return null;
		List<Knjige> lista=new ArrayList<Knjige>();
		Knjige knjige=null;
		try{
			while(rs.next()){
				knjige=new Knjige();
				knjige.setIsbn(rs.getInt(1));
				knjige.setNaziv(rs.getString(2));
				knjige.setAutor(rs.getString(3));
				knjige.setZanr(rs.getString(4));
				knjige.setCena(rs.getString(5));
				knjige.setOpis(rs.getString(6));
				lista.add(knjige);
			}
		}catch(SQLException ex){
			Logger.getLogger(UnosDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return lista;
	}

	public List<Knjige> byName(String naziv) {
		Statement statement =this.getStatement();
		
		ResultSet rs=null;
		try{
			rs=statement.executeQuery("SELECT * FROM schema.knjige WHERE schema.knjige.naziv="+"'"+naziv+"'");
		}catch(SQLException ex){
			
		}
		if(rs==null) return null;
		List<Knjige> lista=new ArrayList<Knjige>();
		Knjige knjige=null;
		System.out.println(rs);
		try{
			while(rs.next()){
				knjige=new Knjige();
				knjige.setIsbn(rs.getInt(1));
				knjige.setNaziv(rs.getString(2));
				knjige.setAutor(rs.getString(3));
				knjige.setZanr(rs.getString(4));
				knjige.setCena(rs.getString(5));
				knjige.setOpis(rs.getString(6));
				lista.add(knjige);
		}
		
	}catch(SQLException ex){
		System.out.println(ex);
		Logger.getLogger(UnosDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
		return lista;
	}

	public void deleteBook(int id) {
		Statement statement=this.getStatement();
		try{
			statement.execute("DELETE FROM schema.knjige WHERE schema.knjige.isbn="+"'"+id+"'");
			
	}catch(SQLException ex){
		
	}
		
	}

	public void updateBook(Knjige knjige) {
		
			String sql="UPDATE schema.knjige SET  isbn=?,naziv= ?,autor= ?, zanr= ?"
					+ ",cena= ?, opis= ? WHERE isbn= ?";
			PreparedStatement statement=this.getPreparedStatement(sql);
			System.out.println(knjige.getNaziv());
		
			try{		
				statement.setInt(1, knjige.getIsbn());
				statement.setString(2, knjige.getNaziv());
				statement.setString(3, knjige.getAutor());
				statement.setString(4, knjige.getZanr());
				statement.setString(5, knjige.getCena());
				statement.setString(6,knjige.getOpis());
				statement.setInt(7,knjige.getIsbn());
				statement.executeUpdate();
				
			}catch(SQLException ex){
				System.out.println(ex);
				
			} 
		
	}
}
	

