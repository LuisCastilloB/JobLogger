import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.sql.ResultSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TestJobLogger {
	
	
	@Nested
	public static class TestLogToDB {
		
		private JobLogger jl = new JobLogger();
		
		/**
		 * Delete all the rows in the log table
		 * @throws Exception
		 */
		@BeforeEach 
		public void deleteDBRows() throws Exception {
			DBConnector Db = new DBConnector();
			Db.execute("delete from log_values");
		}
		
		/**
		 * Read one row inserted in the log table
		 * @throws Exception
		 */
		@Test
		public void testMessageToDB() throws Exception {
			jl.LogMessage("This is a warning log message for DB", 3, 3, "logMessages");
			DBConnector Db = new DBConnector();
			ResultSet rs = Db.select("select count(*) as n from Log_Values");
			int numberRow  = 0;
			while(rs.next()){
				numberRow = rs.getInt("n");
		    }
			assertEquals(numberRow, 1);
		}
	}
	
	@Nested
	public static class TestLogToFile {
		
		private JobLogger jl = new JobLogger();
		
		/**
		 * Delete the file log
		 * @throws Exception
		 */
		@BeforeEach
		public void deleteFileLog() throws Exception {
			File file = new File("logMessages/logFile.txt");
		    file.delete();
		}
		
		/**
		 * Validated that exists the new file log
		 * @throws Exception
		 */
		@Test
		public void testMessageToFile() throws Exception {
			jl.LogMessage("This is a Info Log Message for the File", 1, 1, "logMessages");
			File logFile = new File("logMessages/logFile.txt");
			assertTrue(logFile.exists());
		}	
	}
	
	@Nested
	public static class TestLogToConsole {
		
		private JobLogger jl = new JobLogger();
		
		@Test
		public void testMessageToConsole() throws Exception {
			jl.LogMessage("This is a Error Log Message for the Console", 2, 2, "logMessages");
			assertTrue(true);
		}
	}
}
