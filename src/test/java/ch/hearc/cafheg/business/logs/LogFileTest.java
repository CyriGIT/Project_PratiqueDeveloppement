package ch.hearc.cafheg.business.logs;

import java.io.File;

public class LogFileTest {
    public static void main(String[] args) {
        File errorLog = new File("logs/err.log");
        File serviceLog = new File("logs/cafheg_" + java.time.LocalDate.now() + ".log");

        System.out.println("Fichier err.log existe : " + errorLog.exists());
        System.out.println("Fichier cafheg_{date-jour}.log existe : " + serviceLog.exists());
    }
}