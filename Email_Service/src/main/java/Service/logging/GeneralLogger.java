package Service.logging;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GeneralLogger {
    private static final Logger logger = LogManager.getLogger("GeneralLogger");

    public static Logger getLogger() {
        return logger;
    }
}