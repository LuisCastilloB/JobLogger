import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DBConnector {
	
	private Connection connection = null;
	private Properties config = new Properties();
	private InputStream configInput = null;
		   
	public DBConnector() throws Exception {
		
		loadConfig();
		Class.forName("com.mysql.jdbc.Driver");  
        String server = config.getProperty("server");
        String dbms = config.getProperty("dbms");
        String port = config.getProperty("port");
        String dbname = config.getProperty("dbname");

        Properties connectionProps = new Properties();
        connectionProps.put("user", config.getProperty("user"));
        connectionProps.put("password",config.getProperty("password"));
        connection = DriverManager.getConnection("jdbc:" + dbms + "://" + server + ":" + port + "/" + dbname, config.getProperty("user"), config.getProperty("password"));
        
	}
	
	public void execute(String query) throws Exception {
		Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
	}
	
	public ResultSet select(String query) throws Exception {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return rs;      
	}
	
	public void loadConfig() throws Exception {
        configInput = new FileInputStream("db.properties");
        config.load(configInput);
    }
}
