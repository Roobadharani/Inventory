package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connectionManager.ConnectionManager;
import model.Product;

public class ProductDAO {
	
	public void addProduct(Product product)throws ClassNotFoundException, SQLException
	{
		int id = product.getPRODUCTID();
		String name = product.getPRODUCTNAME();
		int minsell = product.getMINSELL();
		int price = product.getPRICE();
		int quantity = product.getQUANTITY();
		
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		String query = "insert into "+"PRODUCT(PRODUCTID,PRODUCTNAME,MINSELL,PRICE,QUANTITY)"
		+"VALUES(?,?,?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1,id);
		ps.setString(2, name);
		ps.setInt(3, minsell);
		ps.setInt(4, price);
		ps.setInt(5, quantity);
		
		ps.executeUpdate();
		conn.closeConnection();
		
	}


	public void display() throws ClassNotFoundException, SQLException
	{
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM PRODUCT");
		
		while(rs.next())
		{
			System.out.println(rs.getString("PRODUCTNAME")+" | "+
								rs.getInt("PRODUCTID")+" | "+
					 rs.getInt("MINSELL")+" | "+
					rs.getInt("PRICE")+" | "+
					 rs.getInt("QUANTITY"));
		}
	}
	public String productname(int id) throws ClassNotFoundException, SQLException
	{
		String name="";
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM PRODUCT");
		
		while(rs.next())
		{
			if(rs.getInt("PRODUCTID")==id)
			{
						name = rs.getString("PRODUCTNAME");
			}
		}
		return name;
	}
	public void displayrequired(int value) throws ClassNotFoundException, SQLException
	{
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM PRODUCT");
		
		while(rs.next())
		{
			if(rs.getInt("PRODUCTID")==value)
			{
						System.out.println(rs.getString("PRODUCTNAME")+" | "+rs.getInt("PRICE")+" | ");
			}
		}
	}
	public int total(int id,int count) throws ClassNotFoundException, SQLException
	{
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM PRODUCT");
		
		int cost = 0;
		while(rs.next())
		{
			
			if(rs.getInt("PRODUCTID")==id)
			{
						int mincount = rs.getInt("MINSELL");
						int price = rs.getInt("PRICE");
						if(mincount <=count)
						{
							cost = count*price;
						}
			}
		}
		return cost;
	}
	public void changebybuy(int id,int count) throws ClassNotFoundException, SQLException
	{
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM PRODUCT");
		
		int available = 0;
		while(rs.next())
		{
			if(rs.getInt("PRODUCTID")==id)
			{
				int stock = rs.getInt("QUANTITY");
				available = stock-count;
			}
		}
		String query = "UPDATE PRODUCT SET QUANTITY=? WHERE PRODUCTID=?";
				
				
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1,available);
				ps.setInt(2,id);
				
				ps.executeUpdate();
				conn.closeConnection();
				System.out.println("Done");
	}
	
	public void changebysell(int id,int count) throws ClassNotFoundException, SQLException
	{
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM PRODUCT");
		
		int available = 0;
		while(rs.next())
		{
			if(rs.getInt("PRODUCTID")==id)
			{
				int stock = rs.getInt("QUANTITY");
				available = stock+count;
			}
		}
		String query = "UPDATE PRODUCT SET QUANTITY=? WHERE PRODUCTID=?";
				
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1,available);
				ps.setInt(2,id);

				
				ps.executeUpdate();
				conn.closeConnection();
				System.out.println("Done");
		
	}
}