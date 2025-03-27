package ch.hearc.cafheg.infrastructure.persistance;

import java.sql.Connection;

/**
 * Classe abstraite permettant à chaque implémentation de Mapper
 * de recupérer la connection JDBC active.
 */
public class Mapper {
  protected Connection activeJDBCConnection() {
    return Database.activeJDBCConnection();
  }
}
