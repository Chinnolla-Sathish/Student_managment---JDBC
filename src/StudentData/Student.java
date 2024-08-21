package StudentData;
import java.util.Scanner;



import java.sql.*;
import java.util.*;
public class Student {

	
	
	static Connection con = null;
	static Scanner sc = null;
	static Statement stmt= null;
	static {
		try {
			
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentManagment","root","sathish");
				sc = new Scanner(System.in);
				stmt = con.createStatement();
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
		
	public static void main(String[] args) {
		
		
		
		while(true) {
			
			int option=consoleDetails();
			try {
				
				//Scanner sc = new Scanner(System.in);
				//Statement stmt = con.createStatement();
				
				
				switch(option) {
				
				case 1 : create_table();
						 break;
						
				case 2 : insert_row();
						break;
				case 3:delete_rows();
						break;
					
				case 4: show_rows();
						break;
				case 5: rename_table();
				 		break;
				case 6: alter_table();
						break;
				case 7: String arr[] = display_tables();
						break;
						
			} 
			

				
			}
			catch(Exception e) {
				
				System.out.println(e);
			}
			
			System.out.println("\n'want to Stay in DataBase enter 1, else any number to exit'");
			int select= sc.nextInt();
			sc.nextLine();
			
			if(select==1)
				continue;
			else
				break;
		}
		
		
		
		
		

		
	}
	

	private static void create_table() {
		
		 try {
			 String create_table;
			 System.out.println("You Choosed option 1, so write statement in the form \n{create table table_name {colum1 datatype,column2 datatype,...  ...}} ");
			 System.out.println("Enter Your Statement:");
			 
			 create_table = sc.nextLine();
			 stmt.executeUpdate(create_table);
			 System.out.println("'Table is created Successfully'\n\n The Schema is:\n"+create_table);
			 
		 }
		 catch(Exception e) {
			 
			 System.out.println(e);
		 }
		 
	}
	public static int  consoleDetails() {

		System.out.println("\n******WELCOME TO STUDENTDATABASE MANAGMENT ******\n");
		System.out.println("Select Your Option :\n \n1.Create Table\n2.Insert Rows\n3.Delete Rows\n4.Show Rows\n5.Rename\n6.Alter\n7.Show Tables");
		System.out.println("\nEnter Your Option (only numerics):");
		
		sc = new Scanner(System.in);
		int option = sc.nextInt();
		
		sc.nextLine();
		return option;
	}
	private static void insert_row() {
		
		try {
				while(true) {
				
				System.out.println("-----You Choosed option 2-----\n\n");
				
				
				 String result[] = display_tables();
				 System.out.println("Selected the table you want to insert the rows (only numerics):");
				 
				 int select = sc.nextInt();
				 sc.nextLine();
				 String insert = result[select-1];
				 System.out.println("----You selected '"+insert+"' Table----  \n");
				 
				 String describe_t = "describe "+insert;
				 int idx=0;
				 String columns[] = new String[100];
				 ResultSet rs = stmt.executeQuery(describe_t);
					System.out.println("The Description of the table is :\n");
					System.out.println("Field            DataType\n");
					
					
					 while(rs.next()) {
						 System.out.println(rs.getString(1)+"             "+rs.getString(2));
						 columns[idx] = rs.getString(1); 
						 idx++;
					 }
					 
				 System.out.println("index:"+idx);
				 System.out.println("\n----Enter the Details According to the Table description-----\n");
//				 System.out.println("Ex: insert into table_name values(column1value ,coulmn2value,..... , .....)");
//				 System.out.println("\n\n      OR\n\n INSERT INTO table_name (column1value ,coulmn2value,..... , .....) VALUES(?,?,?,.,.,)");
//				 System.out.println("\nEnter insert Statement-> Table name is:"+insert+"\n");
//				 
				 
				 String insert_stmt ="INSERT INTO "+insert+"  VALUES(";
				 
				 for(int j=0;j<idx;j++) {
					
					 if(j!=0) {
						 insert_stmt +=",";
					 }
					 insert_stmt +="?";
					
					 
				 }
				 insert_stmt +=")";
				 
				 
				 PreparedStatement pstmt = con.prepareStatement(insert_stmt);
				 for(int j =0;j<idx;j++) {
					 
					 System.out.println("Enter "+columns[j]+" :");
					 String value = sc.nextLine();
					 pstmt.setString(j+1,value);
					 
				 }
				//  System.out.println(insert_stmt);
				
				 
				 pstmt.executeUpdate();
				 
				 System.out.println("'values' inserted sucessfully");
				 
				 int tr;
				 System.out.println("\n---->Do you want to insert another record, if so enter 1 or else press any number to exit:");
				 tr = sc.nextInt();
				 
				 if(tr==1)
					 continue;
				 else {
					 System.out.println("Exited Insert Operation Sucessfully");
					 break;
				 }
				
			}
		}
		catch(Exception e) {
			
			System.out.println(e);
		}
	}
	private static void show_rows() {
		
		try {
			System.out.println("******You Choosed option 4*******");
			display_tables();
//			System.out.println("\nEnter your Query in the form of->");
//			System.out.println("Syntax: select * from table_name");
			System.out.println("--->Enter table name you want to display:");
			String show_query = sc.nextLine();
			show_query = "select * from "+show_query;
			ResultSet rs = stmt.executeQuery(show_query);
			
			//System.out.println("sucess");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for(int k=1;k<=count;k++) {
				System.out.print(rsmd.getColumnName(k)+"\t\t");
				
			}
			System.out.println();
			while(rs.next()) {
				
				for(int k=1;k<=count;k++) {
					
					System.out.print(rs.getString(k)+"\t\t");
				}
				System.out.println();
			}
			
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	private static void delete_rows() {
		
		try {
			
			System.out.println("*****You Choosed Option 3********");
			while(true) {
				
				String result[] = display_tables();
				 System.out.println("Selected the table you want to delete the rows (only numerics):");
				 
				 int select = sc.nextInt();
				 sc.nextLine();
				 String delete = result[select-1];
				 System.out.println("----You selected '"+delete+"' Table----  \n");
		
				 String describe_table = "describe "+delete;
				
				ResultSet rs = stmt.executeQuery(describe_table);
				System.out.println("The Description of the table is :\n");
				System.out.println("Field            DataType\n");
				String columns[] = new String[10];
				int idx=0;
				 while(rs.next()) {
					 System.out.println(rs.getString(1)+"             "+rs.getString(2));
					 columns[idx] = rs.getString(1); 
					 idx++;
				 }
			 
				System.out.println("\nEnter delete Statement --> Table Name :"+delete+"\n");
				System.out.println("Syntax: delete from table_name condition");
				System.out.println("Enter Your Statement:");
				String del_stmt = sc.nextLine();
			   stmt.executeUpdate(del_stmt);
			   System.out.println("'Delete operation sucess'");
			   	 int tr;
				 System.out.println("\n---->Do you want continue delete operation, if so enter 1 or else press any key to exit:");
				 tr = sc.nextInt();
				 
				 if(tr==1)
					 continue;
				 else {
					 System.out.println("Exited Delete Operation Sucessfully");
					 break;
				 }
			}
		}
		catch(Exception e) {
			
		}
	}
	private static void rename_table() {
		
		try {
			
			System.out.println("******* You Choosed Option 5 **********");
			
			 String result[]=display_tables();
			 System.out.println("Select Table that you want to Rename(only numerics):");
			 int k = sc.nextInt();
			 sc.nextLine();
			
			 System.out.println("You selected Table:"+result[k-1]);
			 System.out.println("Enter the Rename :");
			 String r_t = sc.nextLine();
			 
			 String rename_stmt = "ALTER TABLE "+result[k-1]+" RENAME TO "+r_t;
			
			 stmt.executeUpdate(rename_stmt);
			 System.out.println("'Table name "+result[k-1]+ " changed to "+r_t+"'");
			 //System.out.println(rename_stmt);
			 
			
		}
		catch(Exception e) {
			
			System.out.println(e);
		}
	}
	
	private static void alter_table() {
		
		System.out.println("\n***** You choosed option 6 *******\n");
		System.out.println("\nSelect your option:\n1.Truncate\n2.Drop\n3.Change column name\n4.Add column");
		System.out.println("\nEnter Your option:");
		int op = sc.nextInt();
		sc.nextLine();
		
		switch(op) {
		
		case 1: truncate();
				break;
		case 2: drop();
				break;
	
			
		}
	}
	private static String [] display_tables() {
		
		String result[] = new String[10];
		try {
			
			String show_tables;
			
			show_tables = "show tables";
			ResultSet rs = stmt.executeQuery(show_tables);
	 
			
			int i=0;
			//int select;
			System.out.println("Available Tables In the Selected DataBase:\n");
			while(rs.next()) {
			 result[i]=rs.getString(1);
			 System.out.println((i+1)+"."+result[i]);
			 i++;
			}
			
		}
		catch(Exception e) {
			
			System.out.println(e);
		}
		return result;
		
		
	}
	private static void describe_table(String insert) {
		String describe_t = "describe "+insert;
		int idx=0;
		String columns[] = new String[100];
		try {
			
			ResultSet rs = stmt.executeQuery(describe_t);
			System.out.println("The Description of the table is :\n");
			System.out.println("Field            DataType\n");
			
			
			 while(rs.next()) {
				 System.out.println(rs.getString(1)+"             "+rs.getString(2));
				 columns[idx] = rs.getString(1); 
				 idx++;
			 }
			 System.out.println("index:"+idx);
			 
		}
		catch(Exception e) {
			
			System.out.println(e);
		}
		 
		
		 
	}
	private static void truncate() {
		String result[] = display_tables();
		
		System.out.println("\nSelect the table you want to truncate:");
		int sop = sc.nextInt();
		
		
		String query = "truncate "+result[sop-1];
		System.out.println(query);
		
		try {
			
			stmt.executeUpdate(query);
			System.out.println("'Truncate Operation is sucess'");
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	private static void drop() {
		String result[] = display_tables();
		
		System.out.println("\nSelect the table you want to drop:");
		int sop = sc.nextInt();
		sc.nextLine();
		
		
		String query = "drop "+result[sop-1];
		System.out.println(query);
		
		try {
			
			stmt.executeUpdate(query);
			System.out.println("'Drop Operation is sucess'");
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}

}

//
//int RollNo;
//String Student_name;
//int Marks;
//int Standard;
//Scanner sc = new Scanner(System.in);
//System.out.println("*****Enter Student details*****");
//System.out.println("Name:");
//Student_name = sc.nextLine();
//System.out.println("RollNo:");
//RollNo = sc.nextInt();
//System.out.println("Marks:");
//Marks = sc.nextInt();
//System.out.println("Standard:");
//Standard = sc.nextInt();
////System.out.println(RollNo);
//
//String sql ="INSERT INTO studentdata (RollNo,Student_name,Marks, Standard) VALUES(?,?,?,?) ";
//
//PreparedStatement pstmt = con.prepareStatement(sql);
//
//pstmt.setInt(1, RollNo);
//pstmt.setString(2, Student_name);
//pstmt.setInt(3, Marks);
//pstmt.setInt(4, Standard);	
//
//int rowsInserted = pstmt.executeUpdate();
//
//if(rowsInserted >0) {
//	System.out.println("Record inserted");
//}
//
//String query = "select * from studentdata";
//
//Statement stmt = con.createStatement();
//String delete  = "delete from studentdata where Student_name in( 'Ranaprathap','yashwanth')";
//stmt.executeUpdate(delete);
//ResultSet rs = stmt.executeQuery(query);
//
//
//
//System.out.println("RollNo		"+"Name		"+"Marks		"+"Standard		");
//while(rs.next()) {
//	
//	System.out.println(rs.getInt(1)+"		"+rs.getString(2)+"		"+rs.getInt(3)+"			"+rs.getInt(4));
//	
//}
//sc.close();
//con.close();
//pstmt.close();

