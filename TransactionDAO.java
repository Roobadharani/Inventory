package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connectionManager.ConnectionManager;
import model.Transaction;

public class TransactionDAO
{
	public void addTransaction(Transaction transaction)throws ClassNotFoundException, SQLException
	{
		int id = transaction.getPRODUCTID();
		String name = transaction.getPRODUCTNAME();
		int cost = transaction.getCOST();
		int quantity = transaction.getQUANTITY();
		String method = transaction.getMETHOD();
		
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		String query = "insert into "+"TRANSACTION(PRODUCTID,PRODUCTNAME,COST,QUANTITY,METHOD)"
		+"VALUES(?,?,?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1,id);
		ps.setString(2, name);
		ps.setInt(3, cost);
		ps.setInt(4, quantity);
		ps.setString(5, method);
		
		ps.executeUpdate();
		conn.closeConnection();
		
	}


	public void display() throws ClassNotFoundException, SQLException
	{
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM TRANSACTION");
		
		while(rs.next())
		{
			System.out.println(rs.getInt("PRODUCTID")+" | "+
								rs.getString("PRODUCTNAME")+" | "+
									rs.getInt("COST")+" | "+
										rs.getInt("QUANTITY")+" | "+
											rs.getString("METHOD"));
		}
	}

}
