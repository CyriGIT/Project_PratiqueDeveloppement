package ch.hearc.cafheg.infrastructure.persistance;

import ch.hearc.cafheg.utils.Log;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;
import javax.sql.DataSource;

public class Database {
  /** Pool de connections JDBC */
  private static DataSource dataSource;

  /** Connection JDBC active par utilisateur/thread (ThreadLocal) */
  private static final ThreadLocal<Connection> connection = new ThreadLocal<>();

  /**
   * Retourne la transaction active ou throw une Exception si pas de transaction
   * active.
   * @return Connection JDBC active
   */
  static Connection activeJDBCConnection() {
    if(connection.get() == null) {
      Log.error("Pas de connection JDBC active");
      throw new RuntimeException("Pas de connection JDBC active");
    }
    return connection.get();
  }

  /**
   * Exécution d'une fonction dans une transaction.
   * @param inTransaction La fonction a éxécuter au travers d'une transaction
   * @param <T> Le type du retour de la fonction
   * @return Le résultat de l'éxécution de la fonction
   */
  public static <T> T inTransaction(Supplier<T> inTransaction) {
    Log.debug("inTransaction#start");
    //System.out.println("inTransaction#start");
    try {
      Log.debug("inTransaction#getConnection");
      //System.out.println("inTransaction#getConnection");
      connection.set(dataSource.getConnection());
      return inTransaction.get();
    } catch (Exception e) {
      Log.error("inTransaction#exception " + e.getMessage());
      throw new RuntimeException(e);
    } finally {
      try {
        Log.debug("inTransaction#closeConnection");
        //System.out.println("inTransaction#closeConnection");
        connection.get().close();
      } catch (SQLException e) {
        Log.error("inTransaction#closeConnection " + e.getMessage());
        throw new RuntimeException(e);
      }
      Log.debug("inTransaction#end");
      //System.out.println("inTransaction#end");
      connection.remove();
    }
  }

  public static void setConnection(Connection conn) {
    connection.set(conn);
  }

  public static void closeConnection() {
    try {
      if (connection.get() != null && !connection.get().isClosed()) {
        connection.get().close();
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erreur lors de la fermeture de la connexion", e);
    } finally {
      connection.remove();
    }
  }

  DataSource dataSource() {
    return dataSource;
  }

  /**
   * Initialisation du pool de connections.
   */
  public void start() {
    Log.info("Initializing datasource");
    //System.out.println("Initializing datasource");
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:h2:mem:sample");
    config.setMaximumPoolSize(20);
    config.setDriverClassName("org.h2.Driver");
    dataSource = new HikariDataSource(config);
    Log.info("Datasource initialized");
    //System.out.println("Datasource initialized");
  }
}
