import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class ServerLH
{
	public static final int PORT = 8080;
	Vector socks;
	static String dbUrl = "jdbc:postgresql://localhost/postgres";
    static String user = "postgres";
    static String password = "qwerty";
    static PreparedStatement db;
    static Connection condb;

	public void ExecuteRequest(String str)
	{
		for(int i = 0; i < socks.size(); i++)
		{
			((ServerOneLH)socks.get(i)).out.println(str);
		}
	}
    //*****************************************************************
	ServerLH() throws IOException, SQLException
	{
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server started...");

		socks = new Vector();
		// Загружаем драйвер и регистрируемся
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			System.out.println("Не найден драйвер базы данных");
		}	
		condb = DriverManager.getConnection(dbUrl, user, password);
		
		try
		{
			while(true)
			{
				Socket socket = s.accept();

				try
				{
					socks.add(new ServerOneLH(socket));	// это - для того, чтоб потом просмотреть все сокеты и послать им строку
				}
				catch(Exception e)
				{
					System.out.println("Exception: " + e.toString());
					socket.close();
				}

				if(socks.size() == 0)
				break;
			}
		}
		finally
		{
			System.out.println();
			s.close();
		}
	}
	//*****************************************************************
	public static void main(String args[]) throws IOException, SQLException
	{
		new ServerLH();
	}
//********************************************************************************************************************
//********************************************************************************************************************
	class ServerOneLH extends Thread
	{
		private PrintWriter out;
		private BufferedReader in;
		private Socket socket;

		//**************************************************************************************
		public ServerOneLH(Socket s) throws IOException
		{
			socket = s;

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

			start();
		}
	    //**************************************************************************************
		public String ReturnNickname(String msg){
			int a = msg.indexOf('>',10);
			int	b = msg.indexOf('(',a);
			return(msg.substring(a+1, b));
		}
		public String ReturnName(String msg){
			int a = msg.indexOf('(');
			int	b = msg.indexOf(')',a);
			return (msg.substring(a+1, b));
		}
		public String ReturnMessage(String msg){
			int a = msg.lastIndexOf('<');
			int	b = msg.lastIndexOf("\">",a);
			return (msg.substring(b+2, a));
		}
		public void run()
		{
			try
			{
				while(true)
				{
					String str = in.readLine();

					if(ReturnMessage(str).equals("__exit"))
						break;

					db = condb.prepareStatement("INSERT INTO history (username,name,message) VALUES ((?),(?),(?));");
					db.setString(1, ReturnNickname(str));
					db.setString(2, ReturnName(str));
					db.setString(3, ReturnMessage(str));
			    	db.executeUpdate();
					
					System.out.println("Socket: " + socket.getLocalAddress() + ", echo: " + str);
					ExecuteRequest(str);
				}

				System.out.println("Closing socket: " + socket.getLocalAddress());
				socks.removeElement(this);
			}
			catch(Exception e)
			{
				System.out.println("Exception: " + e.toString());
			}
			finally
			{
				try
				{
					socket.close();
				}
				catch(Exception e)
				{
					System.out.println("Can't close socket... :(");
				}
			}
		}
	}
}
