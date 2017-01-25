package com.vaadin.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;


import com.vaadin.db.JDBCConnection;
import com.vaadin.exception.DatabaseException;



public class AbstractDAO {
	protected Statement getStatement(){
		Statement statement=null;
		try{
			statement=JDBCConnection.getInstance().getStatament();
		}catch(DatabaseException ex){
			
			
		}
		return statement;
	}
	
	protected PreparedStatement getPreparedStatement(String sql){
		
		PreparedStatement statement=null;
		try{
			statement=JDBCConnection.getInstance().getPreparedStatement(sql);
		}catch(DatabaseException ex){
			
			
		}
		return statement;
	}
}
