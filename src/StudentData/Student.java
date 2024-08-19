package StudentData;
import java.util.Scanner;
import java.sql.*;
import java.util.*;
public class Student {

	public static void main(String[] args) {
		
		
		int RollNo;
		String Student_name;
		int Marks;
		int Standard;
		Scanner sc = new Scanner(System.in);
		System.out.println("*****Enter Student details*****");
		System.out.println("Name:");
		Student_name = sc.nextLine();
		System.out.println("RollNo:");
		RollNo = sc.nextInt();
		System.out.println("Marks:");
		Marks = sc.nextInt();
		System.out.println("Standard:");
		Standard = sc.nextInt();
		
		try {
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentManagment","root","sathish");
			
			
			//System.out.println(RollNo);
			
			String sql ="INSERT INTO studentdata (RollNo,Student_name,Marks, Standard) VALUES(?,?,?,?) ";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, RollNo);
			pstmt.setString(2, Student_name);
			pstmt.setInt(3, Marks);
			pstmt.setInt(4, Standard);	
			
			int rowsInserted = pstmt.executeUpdate();
			
			if(rowsInserted >0) {
				System.out.println("Record inserted");
			}
			
			String query = "select * from studentdata where RollNo=512";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			
		
			while(rs.next()) {
				
				System.out.println("RollNo:"+rs.getString(2));
				
			}
			sc.close();
			con.close();
			pstmt.close();

			
		}
		catch(Exception e) {
			
			System.out.println(e);
		}
		
		

		
	}

}
