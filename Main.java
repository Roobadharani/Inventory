package controller;

import java.io.*;
import java.sql.SQLException;

import dao.LoginDAO;
import dao.ProductDAO;
import dao.TransactionDAO;
import model.Login;
import model.Product;
import model.Transaction;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException, ClassNotFoundException, SQLException
	{
		// TODO Auto-generated method stub
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		//create object to the table
		Login login= new Login();
		LoginDAO logindao = new LoginDAO();
		ProductDAO productdao = new ProductDAO();
		TransactionDAO transactiondao = new TransactionDAO();
		Product product = new Product();
		Transaction transaction = new Transaction();
		
		int option;
		do 
		{
			System.out.println("1.Admin");
			System.out.println("2.Agent");
			System.out.println("3.Exit");
			System.out.println("---------------------------------");
			//user select option
			option=Integer.parseInt(br.readLine());
			
			switch(option)
			{
			//....................................................................1admins login................................................................
			case 1: System.out.println("----------------------------------");
			System.out.println("Enter usename");
			String username = br.readLine();
			System.out.println("Enter password");
			String password = br.readLine();
			
			login.setUSERNAME(username);
			login.setPASSWORD(password);
	
			boolean result = logindao.validate(login);
			
				if(result==true)	// 1admins validation
				{
					System.out.println("Login Successful");
					do {
						System.out.println("----------------------------------");
						System.out.println("1.Add Product");
						System.out.println("2.Display Inventory Details");
						System.out.println("3.Logout");
						System.out.println("----------------------------------");
						option = Integer.parseInt(br.readLine());
						
							switch(option)
							{
								case 1:System.out.println("----------------------------------");
								System.out.println("Enter product name");	//........add product
								String productName=br.readLine();
								System.out.println("Enter product id");
								int productId = Integer.parseInt(br.readLine());
								System.out.println("Enter the min selling quantity");
								int minsell = Integer.parseInt(br.readLine());
								System.out.println("Enter the price of the product");
								int price = Integer.parseInt(br.readLine());
								System.out.println("Enter the quantity");
								int quantity = Integer.parseInt(br.readLine());
								
								product.setPRODUCTNAME(productName);
								product.setPRODUCTID(productId);
								product.setMINSELL(minsell);
								product.setQUANTITY(quantity);
								product.setPRICE(price);
								productdao.addProduct(product);
								System.out.println("--------------------------------");
								System.out.println("Your product has been added to stock");
								
								break;
								//...................product display
								case 2:
									productdao.display();
								//............................logout
								case 3:
									System.out.println("Logged out successfully");
									System.exit(0);
							}
						
						}while(option!=3);
					}
				else	//1admins validation
				{
					System.out.println("Username & Password is not correct");
				}
				break;
				
				
				//..........................................................agent login.........................................................
				
			case 2: System.out.println("----------------------------------");
				System.out.println("Enter usename");
				String agentname = br.readLine();
				System.out.println("Enter password");
				String agentpassword = br.readLine();
				
				login.setUSERNAME(agentname);
				login.setPASSWORD(agentpassword);
				
				result = logindao.validate(login);	//agent validation
					if(result == true)
					{
						System.out.println("Login Successful");
						do {
							System.out.println("----------------------------------");							
							System.out.println("1.Buy/sell");
							System.out.println("2.Show History");
							System.out.println("3.Logout");
							System.out.println("----------------------------------");
							option = Integer.parseInt(br.readLine());
							switch(option)
							{
							case 1:	
				
								System.out.println("Enter the Product ID ");
								int productId = Integer.parseInt(br.readLine());
								
								System.out.println("1.Buy");
								System.out.println("2.sell");
								option = Integer.parseInt(br.readLine());
								if(option==1)
								{
									//buy.....
									productdao.displayrequired(productId);
									System.out.println("Enter the quantity");
									int count=Integer.parseInt(br.readLine());
									int cost = productdao.total(productId,count);
									if(cost==0)
									{
										System.out.println("Product not available");
									}
									else
									{
										System.out.println("Total cost is "+cost);
										System.out.println("1.Confirm Booking");
										System.out.println("2.Cancel Booking");
										int conformation=Integer.parseInt(br.readLine());
										if(conformation==1)
										{
											productdao.changebybuy(productId, count);
											String name = productdao.productname(productId);
											transaction.setPRODUCTID(productId);	
											transaction.setPRODUCTNAME(name);
											int amount = productdao.total(productId,count);
											transaction.setCOST(amount);
											transaction.setQUANTITY(count);
											String method ="buy";
											transaction.setMETHOD(method);
											transactiondao.addTransaction(transaction);
									
										}
									}
								}
								else if(option==2)
								{
									//sell......
									productdao.displayrequired(productId);
									System.out.println("Enter the quantity");
									int quantity = Integer.parseInt(br.readLine());																		
									
									String name =productdao.productname(productId);
									transaction.setPRODUCTID(productId);	
									transaction.setPRODUCTNAME(name);
									productdao.changebysell(productId,quantity);
									int cost = productdao.total(productId,quantity);
									transaction.setCOST(cost);
									transaction.setQUANTITY(quantity);
									String method ="Sell";
									transaction.setMETHOD(method);
									transactiondao.addTransaction(transaction);
								
								}
							break;
							case 2:
								//transaction
								transactiondao.display();
								break;
							case 3:
								break;
							}
							
						}while(option !=3);
					}
					else
					{
						System.out.println("agentname & agentpassword is not correct");
					}
				break;
				
				}
		}while(option!=3);
	}

}
