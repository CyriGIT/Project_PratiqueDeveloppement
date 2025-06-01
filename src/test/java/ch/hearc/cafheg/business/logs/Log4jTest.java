package ch.hearc.cafheg.business.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jTest {
    private static final Logger logger = LogManager.getLogger(Log4jTest.class);


    public static void main(String[] args) {
        System.setProperty("log4j2.debug", "true");

        // Messages pour tester l'écriture dans les fichiers de log
        logger.debug("Message de débogage : écrit dans la console uniquement");
        logger.info("Message d'information : écrit dans cafheg_{date-jour}.log");
        logger.error("Message d'erreur : écrit dans err.log");
    }
}
