package cz.cvut.fel.pjv.main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class LogTest {
    private static final Logger LOGGER = LogManager.getLogger(LogTest.class);
    public static void main(String[] args) {
        LOGGER.info("This is an INFO level log message!");
        LOGGER.error("This is an ERROR level log message!");
    }
}