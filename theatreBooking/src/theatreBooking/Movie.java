package theatreBooking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.io.*;
public class Movie 
{
	static String name="";
	static Connection con = null;
	
	public Connection getConnection() 
	{
	     try
	     {
	    	 Class.forName("com.mysql.cj.jdbc.Driver");
	    	 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie","root","charan2001");
	    	 
	     }
	     catch(Exception c)
	     {
	    	 System.out.println(c);
	     }
	    return con;
	}

	void addmovie(String name,int pricea,int priceb,int pricec) throws Exception
	{
	     String sql = "insert into movies (movie_name,price_a,price_b,price_c) values ('"+name+"','"+pricea+"','"+priceb+"','"+pricec+"')";
	     Statement stmt = (Statement) con.createStatement();
	     stmt.executeUpdate(sql);
	     System.out.println("Movie is added");
	}
	
	void deletemovie(String id) throws Exception
	{
	     String sql = "delete from movies where movie_name = '"+id+"'";
	     Statement stmt = (Statement) con.createStatement();
	     stmt.executeUpdate(sql);
	     System.out.println("Movie is removed");
	}
	
	void cancelmovie(String name,int id,int se) throws Exception
	{
		 String s="update movies set seats=seats+'"+se+"' where movie_id='"+id+"'";
		 Statement ste=(Statement) con.createStatement();
		 ste.executeUpdate(s);
	     String sql = "delete from tickets where movie_id = '"+id+"' and user_name='"+name+"'";
	     Statement stmt = (Statement) con.createStatement();
	     stmt.executeUpdate(sql);
	     System.out.println("Ticket is canceled");
	}
	
	void adduser(String uname,String upass,String uemail,int phone) throws Exception
	{
		String sql = "insert into user (name,password,email,phone_no) values ('"+uname+"','"+upass+"','"+uemail+"','"+phone+"')";
	    Statement stmt = (Statement) con.createStatement();
	    stmt.executeUpdate(sql);
	    System.out.println("Account is created");
	}
	
	void bookticket(String name,int id,int no,int type) 
	{
		try {
			int t=0;
			String h="select seats from movies where movie_id='"+id+"'";
			Statement y=(Statement) con.createStatement();
			y.execute(h);
			ResultSet r=y.getResultSet();
			r.next();
			int k=r.getInt("seats");
			if(no<k) {
				String sql="select * from movies where movie_id='"+id+"'";
				Statement st=(Statement) con.createStatement();
				st.executeQuery(sql);
				ResultSet rs=st.getResultSet();
				rs.next();
				if(t==1)
					t=Integer.parseInt(rs.getString("price_a"));
				else if(t==2)
					t=Integer.parseInt(rs.getString("price_b"));
				else
					t=Integer.parseInt(rs.getString("price_c"));
				int t1=no*t;
				String s="update movies set seats=seats-'"+no+"' where movie_id='"+id+"'";
				Statement ste=(Statement) con.createStatement();
				ste.executeUpdate(s);
				String sql1 = "insert into tickets (user_name,movie_id,no_of_tickets,total_price) values ('"+name+"','"+id+"','"+no+"','"+t1+"')";
			    Statement stmt = (Statement) con.createStatement();
			    stmt.executeUpdate(sql1);
			    System.out.println("Your ticket is confirmed");
			}
			else {
				System.out.println("Only "+k+" tickets are available");
			}
		}
		catch(Exception e) {
			System.out.println("Enter the correct movie id");
		}
	}
	
	void viewmovie()throws Exception {
		String sql="select * from movies";
		Statement st=(Statement) con.createStatement();
		st.executeQuery(sql);
		ResultSet rs=st.getResultSet(); 
		System.out.println("Movie_id\tMovie_name\tGold\tSilver\tPlatinum\tseats");
        while (rs.next()) 
        {  
            String n = rs.getString("movie_id");  
            String nm = rs.getString("movie_name");  
            int s = rs.getInt("price_a");  
            int s1 = rs.getInt("price_b");  
            int s2 = rs.getInt("price_c");  
            int se = rs.getInt("seats");
            System.out.println("\t" + n + "\t" + nm + "\t" + s + "\t"+ s1 + "\t"+ s2 + "\t"+se);   
        }  
	}
	
	void viewticket()throws Exception {
		String sql="select * from tickets";
		Statement st=(Statement) con.createStatement();
		st.executeQuery(sql);
		ResultSet rs=st.getResultSet(); 
		System.out.println("User name Movie id No of Tickets Total");
        while (rs.next()) 
        {  
        	String m = rs.getString("user_name");
            String n = rs.getString("movie_id");  
            String nm = rs.getString("no_of_tickets");  
            int s = rs.getInt("total_price");   
            
            System.out.println("   "+m+"     " + n + "       " + nm + "     " + s + " ");   
        }  
	}
	
	void viewticketuser(String name)throws Exception {
		String sql="select * from tickets where user_name='"+name+"'";
		Statement st=(Statement) con.createStatement();
		st.executeQuery(sql);
		ResultSet rs=st.getResultSet(); 
		System.out.println("User name Movie id No of Tickets Total");
        while (rs.next()) 
        {  
        	String m = rs.getString("user_name");
            String n = rs.getString("movie_id");  
            String nm = rs.getString("no_of_tickets");  
            int s = rs.getInt("total_price");   
            
            System.out.println("   "+m+"     " + n + "       " + nm + "     " + s + " ");   
        }  
	}

	boolean checkadmin(String name,String pass)
	{
		try {
			String sql= "select * from admin where name='"+name+"' and password='"+pass+"'";
			Statement st=(Statement) con.createStatement();
			st.executeQuery(sql);
			ResultSet rs=st.getResultSet();
			rs.next();
			if((rs.getString("name")).equals(name) && (rs.getString("password")).equals(pass))
				return true;
			else
				return false;
		}
		catch(Exception c) 
		{
			return false;
		}
	}
	boolean checkuser(String id,String pass)
	{
		try {
			String sql= "select * from user where name='"+id+"' and password='"+pass+"'";
			Statement st=(Statement) con.createStatement();
			st.executeQuery(sql);
			ResultSet rs=st.getResultSet();
			rs.next();
			if((rs.getString("name")).equals(id) && (rs.getString("password")).equals(pass))
				return true;
			else
				return false;
		}
		catch(Exception c) {
			return false;
		}
	}
	public static void main(String[] args) throws Exception
	{
		Console cnsl=System.console();
		Movie m= new Movie();
		m.getConnection();
		Scanner s = new Scanner(System.in);
		
		while(true) {
			System.out.println("\nAdmin Login 1");
			System.out.println("User Login 2");
			System.out.println("New user register 3");
			System.out.println("Exit 4");
			int num = s.nextInt();
			if(num==1)
			{
				System.out.println("Enter your name and password");
			    String aname = s.next();
//			    char ch[]=cnsl.readPassword();
//			    String apass="";
//			    for(int i=0;i<ch.length;i++) {
//			    	apass+=ch[i];
//			    }
			    String apass=s.next();
			    boolean acheck=m.checkadmin(aname,apass);
			    if(acheck==true)
			    {
			    	while(true) {
				    	System.out.println("\nWELCOME "+aname);
				    	System.out.println("View movies 1");
				    	System.out.println("View tickets 2");
				    	System.out.println("Add movie 3");
				    	System.out.println("Delete movie 4");
				    	System.out.println("logout 5");
				    	int a_num=s.nextInt();
				    	if(a_num==1)
				    		m.viewmovie();
				    	if(a_num==2)
				    		m.viewticket();
				    	if(a_num==3)
				    	{
				    		System.out.println("Enter movie name, price for silver, price for gold, price for platinum");
				    		String movie_name=s.next();
				    		int pricea=s.nextInt(); 
				    		int priceb=s.nextInt(); 
				    		int pricec=s.nextInt(); 
				    		m.addmovie(movie_name,pricea,priceb,pricec);
				    	}
				    	if(a_num==4)
				    	{
				    		System.out.println("Enter movie name");
				    		String movie=s.next();
				    		m.deletemovie(movie);
				    	}
				    	if(a_num==5)
				    		break;
			    	}
			    }
			    else
			    	System.out.println("Invalid Admin name or password");
			}
			
			if(num==2)
			{
				System.out.println("Enter your user name and password");
				String uid=s.next();
				String upass=s.next();
				boolean ucheck=m.checkuser(uid,upass);
				if(ucheck==true)
				{
					name=uid;
					while(true) {
						System.out.println("\nWELCOME "+uid);
						System.out.println("View movies 1");
						System.out.println("Book tickets 2");
						System.out.println("Cancel tickets 3");
						System.out.println("Bookings 4");
						System.out.println("logout 5");
						int u_num=s.nextInt();
						if(u_num==1)
							m.viewmovie();
						if(u_num==2)
						{
							System.out.println("Enter movie id, no of tickets and enter 1 for gold,2 for silver, 3 for platinum");
							String st=name;
							int umovie=s.nextInt();
							int notickets=s.nextInt();
							int type=s.nextInt();
							m.bookticket(st,umovie,notickets,type);
						}
						if(u_num==3) {
							System.out.println("Enter movie id and no of tickets");
							String n=name;
							int i=s.nextInt();
							int se=s.nextInt();
							m.cancelmovie(n,i,se);
						}
						if(u_num==4) {
							m.viewticketuser(uid);
						}
						if(u_num==5)
							break;
					}
				}
				else
					System.out.println("Invalid username or password");
			}
			
			if(num==3)
			{
				System.out.println("Enter your name, password, email, phone number");
				String uname=s.next();
				String upass=s.next();
				String uemail=s.next();
				int uphone=s.nextInt();
				m.adduser(uname,upass,uemail,uphone);
			}
			if(num==4)
				break;
		}
	}

}
