import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WorkWithHistoryDB {
	static String dbUrl = "jdbc:postgresql://localhost/postgres";
    static String user = "postgres";
    static String password = "qwerty";
    static PreparedStatement db;
    static Connection condb;
    
    public static void CreateHistory() throws SQLException{
    	db = condb.prepareStatement("CREATE SEQUENCE msg_id_sq START 1;" +
    	"CREATE TABLE history (msg_id INTEGER DEFAULT nextval('msg_id_sq'),username TEXT NOT NULL,name TEXT,message TEXT);");
    	db.executeUpdate();
    }
    public static void DeleteHistory() throws SQLException{
    	db = condb.prepareStatement("DROP TABLE history;" +
    								"DROP SEQUENCE msg_id_sq;");
    	db.executeUpdate();
    }
    public static void AddRecord() throws SQLException{ 	
    	db = condb.prepareStatement("INSERT INTO history (username,name,message) VALUES ('User','XXX','Very Good');");
    	db.executeUpdate();
    }
    public static void DeleteAllRecords() throws SQLException{ 	
    	db = condb.prepareStatement("DELETE FROM history;ALTER SEQUENCE msg_id_sq RESTART 1;");
    	db.executeUpdate();
    }
    public static void ShowHistory() throws SQLException{
    	String s1,s2,s3;
    	int id;
    	Statement todb = condb.createStatement();
    	ResultSet rs = todb.executeQuery("SELECT * FROM history;");
    	System.out.println("msg_id | nickname | name | message\n");
    	while (rs.next()) {
            id = rs.getInt("msg_id");
            s1 = rs.getString("username");
            s2 = rs.getString("name");
            s3 = rs.getString("message");
            System.out.println(id + " | " + s1 + " | " + s2 + " | " + s3);
    	}
    }
    
    public static void main (String arg[]) throws SQLException {
    	
	    // Загружаем драйвер и регистрируемся
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			System.out.println("Не найден драйвер базы данных");
		}		
		condb = DriverManager.getConnection(dbUrl, user, password);	
	//	CreateHistory();
		//AddRecord();
		ShowHistory();
		//DeleteAllRecords();
	//	DeleteHistory();
    }
}
