package ch.hearc.cafheg.infrastructure.persistance;

import org.flywaydb.core.Flyway;

/**
 * Gestion des scripts de migration sur la base de données.
 */
public class Migrations {

  private final Database database;
  private final boolean forTest;

  public Migrations(Database database) {
    this.database = database;
    this.forTest = false;
  }

  /**
   * Exécution des migrations
   * */
  public void start() {
    System.out.println("Doing migrations");

    String location;
    // Pour les tests, on éxécute que les scripts DDL (création de tables)
    // et pas les scripts d'insertion de données.
    if(forTest) {
      location =  "classpath:db/ddl";
    } else {
      location =  "classpath:db";
    }

    Flyway flyway = Flyway.configure()
        .dataSource(database.dataSource())
        .locations(location)
        .load();

    flyway.migrate();
    System.out.println("Migrations done");
  }

}
