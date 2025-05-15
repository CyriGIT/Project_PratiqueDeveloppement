package ch.hearc.cafheg.business.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jTest {
    private static final Logger logger = LogManager.getLogger("ch.hearc.cafheg.business");

    public static void main(String[] args) {
        // Messages pour tester l'écriture dans les fichiers de log
        logger.debug("Message de débogage : écrit dans la console uniquement");
        logger.info("Message d'information : écrit dans cafheg_{date-jour}.log");
        logger.error("Message d'erreur : écrit dans err.log");
    }
}
