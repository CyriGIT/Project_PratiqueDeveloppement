package ch.hearc.cafheg.business.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jTest {
    private static final Logger logger = LogManager.getLogger(Log4jTest.class);

    public static void main(String[] args) {
        logger.debug("Message de débogage : OK");
        logger.info("Message d'information : OK");
        logger.error("Message d'erreur : OK");
    }
}