import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class JobLogger {
	
	private static Logger logger = Logger.getLogger("MyLog");
	private static Map<Integer, String> logTypes = new HashMap<Integer, String>();
	private static Integer[] logDestinations = {1, 2, 3};
	private static String fileName = "logFile.txt";
	
	/**
	 * Define the types of message
	 */
	public JobLogger() {
		logTypes.put(1, "Message");
		logTypes.put(2, "Error");
		logTypes.put(3, "Warning");
	}
	
	/**
	 * Process the message log
	 * @param logDestination = (1 -> To File, 2-> To Console, 3 -> To DB)
	 * @param logType = (1 -> Message, 2-> Error , 3-> Warning)
	 * @param logFileFolder = Path to save the log file Ej: (../Logs/Folder)
	 * @return
	 * @throws Exception
	 */
	public static void LogMessage(String messageText, int logType, int logDestination, String logFileFolder) throws Exception {
			
		messageText = messageText.trim();
		logValidation(messageText, logType, logDestination, logFileFolder);
		
		String logMessage = logTypes.get(logType) + ":" + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + "->" + messageText;
		switch (logDestination) {
			case 1 :
				String Path = "" + logFileFolder + "/" + fileName;
				File logFile = new File(Path);
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				FileHandler fh = new FileHandler(""+logFileFolder + "/" + fileName);
				logger.addHandler(fh);
				SimpleFormatter formatter = new SimpleFormatter();  		
			    fh.setFormatter(formatter);  
			    logger.info(logMessage);  
				break;
			case 2:
				ConsoleHandler ch = new ConsoleHandler();
				logger.addHandler(ch);
			    logger.info(logMessage);  
				break;
			case 3:
				DBConnector Db = new DBConnector();
				Db.execute("insert into Log_Values Values ('" + logMessage + "', " + logType + ")");
				break;
		}
	}
	
	/**
	 * Validate the log information
	 * @param messageText
	 * @param logType
	 * @param logDestination
	 * @param logFileFolder
	 * @throws Exception
	 */
	public static void logValidation(String messageText, int logType, int logDestination, String logFileFolder) throws Exception {
		
		if (messageText.isEmpty()) {
			throw new Exception("The log message is empty");
		}
		
		if (logFileFolder.isEmpty()) {
			throw new Exception("The logFolder is not valid");
		}
				
		boolean correctLogType = false;
		for (int key : logTypes.keySet()) {
		    if (key == logType) {
		    	correctLogType = true;
		    	break;
		    }
		}
		if (!correctLogType) {
			throw new Exception("The logType ("+ logType + ") is not valid");
		}
		
		boolean correctLogDestination = false;
		for (int element : logDestinations) {
		    if (element == logDestination) {
		    	correctLogDestination = true;
		        break;
		    }
		}
		if (!correctLogDestination) {
			throw new Exception("The logDestination ("+ logDestination + ") is not valid");
		}
	}
}

