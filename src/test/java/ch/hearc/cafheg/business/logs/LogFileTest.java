package ch.hearc.cafheg.business.logs;

import java.io.File;

public class LogFileTest {
    public static void main(String[] args) {
        File errorLog = new File("logs/err.log");
        File serviceLog = new File("logs/cafheg.log");


        System.out.println("err.log path : " + errorLog.getAbsolutePath());
        System.out.println("cafheg log path : " + serviceLog.getAbsolutePath());

        System.out.println("Fichier err.log existe : " + errorLog.exists());
        System.out.println("Fichier cafheg log existe : " + serviceLog.exists());

    }
}