package com.vaadin.db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.exception.DatabaseException;


public class JDBCConnection {
   
   private static JDBCConnection connection=null;
   
   private Connection conn;
   
   private String login="postgres";
   
   private String password="cjelena92";
   
   String url="jdbc:postgresql://localhost:5432/postgres";
   
   public static JDBCConnection  getInstance() throws DatabaseException {
                   
	   if(connection==null){
		   connection=new JDBCConnection();
	   }
	   return connection;
   }
   
   private JDBCConnection() throws DatabaseException{
	   this.initConnection();   
   }
	
	public void initConnection() throws DatabaseException {
		try{
		   DriverManager.registerDriver(new org.postgresql.Driver());
		}catch (SQLException ex){
		  Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.openConnection();
	}
	
	public void openConnection() throws DatabaseException{
		
		try {
			
		Properties props=new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password",password);
		
	    this.conn=DriverManager.getConnection(this.url,props);
	    
		}catch(SQLException ex){
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
			throw new DatabaseException("Error accessing the DB!Safe connection available!?");	
		}
	}
	
	public Statement getStatament() throws DatabaseException{
		
		try{
			if(this.conn.isClosed()){
				this.openConnection();
			}
			
	       return this.conn.createStatement();
		}catch(SQLException ex){
			
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public PreparedStatement getPreparedStatement(String sql) throws DatabaseException{
		
		try{
			if(this.conn.isClosed()){
				this.openConnection();
			}
			
	       return this.conn.prepareStatement(sql);
		}catch(SQLException ex){
			
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public void closeConnection(){
		try {
			this.conn.close();
		} catch (SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
			
		}
		
	}
	
}
