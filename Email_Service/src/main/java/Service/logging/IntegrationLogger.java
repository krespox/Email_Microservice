package Service.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegrationLogger {
    private static final Logger logger = LogManager.getLogger("IntegrationLogger");

    public static Logger getLogger() {
        return logger;
    }
}
